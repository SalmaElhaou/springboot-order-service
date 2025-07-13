package com.maestro.commande.service;




import com.maestro.commande.client.CartClient;
import com.maestro.commande.client.NotificationClient;
import com.maestro.commande.client.ProductClient;
import com.maestro.commande.client.UserClient;
import com.maestro.commande.client.dto.NotificationRequest;
import com.maestro.commande.client.dto.OrderRequest;

import com.maestro.commande.client.dto.PaymentCancellationRequest;
import com.maestro.commande.client.dto.PaymentConfirmationRequest;
import com.maestro.commande.client.dto.UserDTO;
import com.maestro.commande.model.Address;
import com.maestro.commande.model.Order;
import com.maestro.commande.model.OrderItem;
import com.maestro.commande.model.OrderStatus;
import com.maestro.commande.repository.AddressRepository;
import com.maestro.commande.repository.OrderItemRepository;
import com.maestro.commande.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private CartClient cartClient;

    @Autowired
    private  NotificationClient notificationClient ;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public Mono<Object> createOrder(OrderRequest request) {
        return userClient.validateUser(request.getUserId())
            .flatMap(isValid -> {
                if (!isValid) {
                    return Mono.error(new RuntimeException("Utilisateur non authentifié"));
                }

                Address address = addressRepository.save(request.getShippingAddress());

                Order order = new Order();
                order.setUserId(request.getUserId());
                order.setStatus(OrderStatus.PENDING);
                order.setAddress(address);
                orderRepository.save(order);
                
                

                return cartClient.getCartByUserId(request.getUserId())
                    .flatMapMany(cart -> Flux.fromIterable(cart.getItems()))
                    .map(cartItem -> {
                        OrderItem item = new OrderItem();
                        item.setProductName(cartItem.getProductName());
                        item.setProductId(cartItem.getProductId());
                        item.setQuantity(cartItem.getQuantity());
                        item.setUnitPrice(cartItem.getUnitPrice());
                        item.setOrder(order);
                        return item;
                    })
                    .collectList()
                    .flatMap(items -> {
                        orderItemRepository.saveAll(items);
                        order.setItems(items);
                        double total = items.stream()
                                .mapToDouble(i -> i.getUnitPrice() * i.getQuantity())
                                .sum();
                            order.setTotalAmount(total);
                        return Mono.fromCallable(() -> orderRepository.save(order))
                                   .subscribeOn(Schedulers.boundedElastic());
                    });
                
            });
        
    }

    
    

    public Mono<List<Order>> getOrders(String userId, LocalDateTime date) {
        if (userId == null || userId.isBlank()) {
            return Mono.error(new IllegalArgumentException("userId is required"));
        }

        if (date != null) {
            return Mono.just(orderRepository.findByUserIdAndCreatedAt(userId, date));
        } else {
            return Mono.just(orderRepository.findByUserId(userId));
        }
    }


    public Mono<Order> getOrderById(String orderId) {
        return Mono.justOrEmpty(orderRepository.findById(orderId))
                .switchIfEmpty(Mono.error(new RuntimeException("Commande introuvable : " + orderId)));
    }
    
    
    public Mono<Order> updateOrder(String orderId, PaymentConfirmationRequest request) {
        logger.info("Starting updateOrder for orderId: {}", orderId);
        
        return Mono.justOrEmpty(orderRepository.findById(orderId))
            .doOnNext(order -> logger.info("Order found: {}", order))
            .switchIfEmpty(Mono.error(new RuntimeException("Commande introuvable : " + orderId)))
            .flatMap(order -> {
                order.setPaymentId(request.getPaymentId());
                order.setPaymentMethod(request.getPaymentMethod());
                order.setStatus(OrderStatus.PAID);
                order.setUpdatedAt(LocalDateTime.now());
                logger.info("Order updated with payment info: paymentId={}, paymentMethod={}", 
                            request.getPaymentId(), request.getPaymentMethod());

                return Mono.fromCallable(() -> orderRepository.save(order))
                           .subscribeOn(Schedulers.boundedElastic())
                           .doOnSuccess(savedOrder -> logger.info("Order saved successfully: {}", savedOrder))
                           .doOnError(e -> logger.error("Error saving order", e))
                           .flatMap(savedOrder -> {
                                logger.info("Building notification for orderId: {}", savedOrder.getId());
                                return buildNotificationFromOrder(order, "orderConfirmation")
                                    .flatMap(notificationRequest -> {
                                        logger.info("Sending notification for orderId: {}", savedOrder.getId());
                                        return notificationClient.sendOrderConfirmation(notificationRequest)
                                            .doOnSuccess(unused -> logger.info("Notification sent successfully"))
                                            .doOnError(e -> logger.error("Error sending notification", e));
                                    })
                                    .thenReturn(savedOrder);
                            });
            })
            .doOnError(e -> logger.error("Error in updateOrder for orderId: {}", orderId, e));
    }


    public Mono<Order> cancelOrder(String orderId, PaymentCancellationRequest request) {
        logger.info("Starting cancelOrder for orderId: {}", orderId);

        return Mono.justOrEmpty(orderRepository.findById(orderId))
            .doOnNext(order -> logger.info("Order found: {}", order))
            .switchIfEmpty(Mono.error(new RuntimeException("Commande introuvable : " + orderId)))
            .flatMap(order -> {
                if (order.getStatus() == OrderStatus.PAID) {
                    logger.warn("Cannot cancel a PAID order: {}", orderId);
                    return Mono.error(new RuntimeException("Impossible d'annuler une commande déjà payée"));
                }

                order.setStatus(OrderStatus.CANCELLED);
                order.setCancellationReason(request.getReason());
                order.setUpdatedAt(LocalDateTime.now());

                logger.info("Order updated with cancellation info: paymentId={}, reason={}",
                            request.getPaymentId(), request.getReason());

                return Mono.fromCallable(() -> orderRepository.save(order))
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(savedOrder -> {
                        logger.info("Building cancellation notification for orderId: {}", savedOrder.getId());
                        return buildNotificationFromOrder(savedOrder, "orderCancelled")
                            .flatMap(notificationClient::sendOrderConfirmation) // ✅
                            .doOnSuccess(unused -> logger.info("Cancellation notification sent successfully"))
                            .doOnError(e -> logger.error("Error sending cancellation notification", e))
                            .thenReturn(savedOrder);
                    });
            })
            .doOnError(e -> logger.error("Error in cancelOrder for orderId: {}", orderId, e));
    }

    
    
    private Mono<NotificationRequest> buildNotificationFromOrder(Order order, String type) {
        // Map OrderItems → ItemDTO
        List<NotificationRequest.ItemDTO> items = order.getItems().stream()
            .map(item -> new NotificationRequest.ItemDTO(
                item.getProductName(),
                item.getUnitPrice(),
                item.getQuantity()
            ))
            .toList();

        // Adresse de livraison
        NotificationRequest.ShippingAddress address = new NotificationRequest.ShippingAddress(
            order.getAddress().getStreet(),
            order.getAddress().getCity(),
            order.getAddress().getPostalCode(),
            order.getAddress().getCountry()
        );

        // Shipping temporaire
        NotificationRequest.Shipping shipping = new NotificationRequest.Shipping("to be calculated");

        // Récupérer l'utilisateur et construire la notification
        return userClient.getUserById(order.getUserId())
            .map(user -> {
                String customerName = user.getName();
                String email = user.getEmail();

                NotificationRequest.NotificationData data = new NotificationRequest.NotificationData(
                    order.getId().toString(),
                    customerName,
                    email,
                    order.getPaymentMethod(),
                    items,
                    shipping,
                    address
                );

                return new NotificationRequest(type, data); // ✅ dynamique: type = "orderCancelled" ou "orderConfirmation"
            });
    }
  
}