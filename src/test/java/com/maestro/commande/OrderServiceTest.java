package com.maestro.commande;



import com.maestro.commande.client.CartClient;
import com.maestro.commande.client.ProductClient;
import com.maestro.commande.client.UserClient;
import com.maestro.commande.client.dto.OrderItemRequest;
import com.maestro.commande.client.dto.OrderRequest;
import com.maestro.commande.model.Address;
import com.maestro.commande.model.Order;
import com.maestro.commande.model.OrderItem;
import com.maestro.commande.model.OrderStatus;
import com.maestro.commande.repository.AddressRepository;
import com.maestro.commande.repository.OrderItemRepository;
import com.maestro.commande.repository.OrderRepository;
import com.maestro.commande.service.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class OrderServiceTest {
/*
    @InjectMocks
    private OrderService orderService;

    @Mock
    private UserClient userClient;

    @Mock
    private ProductClient productClient;

    @Mock
    private CartClient cartClient;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrderWithItems() {
        // Mock data
        OrderRequest request = new OrderRequest();
        request.setUserId("usr_12345");
        request.setAddress(new Address("123 Rue", "Casablanca", "20000", "Maroc"));
        List<OrderItemRequest> items = new ArrayList<>();
        items.add(new OrderItemRequest("prod_67890", 2, 29.99));
        request.setItems(items);

        Address savedAddress = new Address();
        savedAddress.setId(UUID.randomUUID());
        savedAddress.setStreet("123 Rue");
        savedAddress.setCity("Casablanca");
        savedAddress.setPostalCode("20000");
        savedAddress.setCountry("Maroc");

        Order savedOrder = new Order();
        savedOrder.setId(UUID.randomUUID());
        savedOrder.setUserId("usr_12345");
        savedOrder.setStatus(OrderStatus.PENDING);
        savedOrder.setAddress(savedAddress);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(UUID.randomUUID());
        orderItem.setProductId("prod_67890");
        orderItem.setQuantity(2);
        orderItem.setUnitPrice(29.99);
        orderItem.setOrder(savedOrder);

        // Mock responses
        when(userClient.getUserById("usr_12345")).thenReturn(Mono.just(new Object())); // Simuler un utilisateur
        when(productClient.getProductById("prod_67890")).thenReturn(Mono.just(new ProductDTO("prod_67890", 29.99))); // Simuler un produit
        when(addressRepository.save(any(Address.class))).thenReturn(savedAddress);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        when(orderItemRepository.saveAll(anyList())).thenReturn(Collections.singletonList(orderItem));

        // Test
        Mono<Order> result = orderService.createOrder(request);

        StepVerifier.create(result)
                .expectNextMatches(order -> order.getId() != null && order.getStatus() == OrderStatus.PENDING)
                .verifyComplete();
    }
}

// DTOs mockés (à ajouter dans com.maestro.commande.client.dto si nécessaire)
class ProductDTO {
    private String id;
    private double price;

    public ProductDTO(String id, double price) {
        this.id = id;
        this.price = price;
    }

    public String getId() { return id; }
    public double getPrice() { return price; }
}

class OrderItemRequest {
    private String productId;
    private int quantity;
    private double pricePerUnit;

    public OrderItemRequest(String productId, int quantity, double pricePerUnit) {
        this.productId = productId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getPricePerUnit() { return pricePerUnit; }*/
}
