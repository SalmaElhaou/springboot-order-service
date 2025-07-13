package com.maestro.commande;

import com.maestro.commande.client.CartClient;
import com.maestro.commande.client.ProductClient;
import com.maestro.commande.client.UserClient;
import com.maestro.commande.client.dto.*;
import com.maestro.commande.model.*;
import com.maestro.commande.repository.*;
import com.maestro.commande.service.OrderService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceIntegrationTest {
	/*
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @MockBean
    private UserClient userClient;

    @MockBean
    private ProductClient productClient;

    @MockBean
    private CartClient cartClient;

    @Test
    public void testCreateOrder_WithItems_ShouldCreateOrderSuccessfully() {
        // 👉 Étape 1 : Données de test
        String userId = "u1";
        String productId = "p1";

        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setProductId(productId);
        itemRequest.setQuantity(2);

        OrderRequest request = new OrderRequest();
        request.setUserId(userId);
        request.setItems(List.of(itemRequest));

        Address address = new Address();
        address.setStreet("Test Rue");
        address.setCity("Casablanca");
        address.setPostalCode("20000");
        address.setCountry("Maroc");
        request.setAddress(address);

        // 👉 Étape 2 : Mock les services externes
        Mockito.when(userClient.getUserById(userId))
                .thenReturn(Mono.just(new UserDTO(userId, "test@example.com", "Test")));

        Mockito.when(productClient.getProductById(productId))
                .thenReturn(Mono.just(new ProductDTO(productId, "PC Portable",  5000)));

        // 👉 Étape 3 : Appeler le service
        Object result = orderService.createOrder(request).block();

        // 👉 Étape 4 : Vérifier le résultat
        assertNotNull(result);
        assertTrue(result instanceof Order);

        Order savedOrder = (Order) result;

        assertEquals(OrderStatus.PENDING, savedOrder.getStatus());
        assertEquals(userId, savedOrder.getUserId());
        assertNotNull(savedOrder.getItems());
        assertEquals(1, savedOrder.getItems().size());
        assertEquals(productId, savedOrder.getItems().get(0).getProductId());
    }*/
}
