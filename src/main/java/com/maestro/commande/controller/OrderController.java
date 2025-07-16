package com.maestro.commande.controller;

import com.maestro.commande.client.dto.OrderRequest;
import com.maestro.commande.client.dto.PaymentCancellationRequest;
import com.maestro.commande.client.dto.PaymentConfirmationRequest;
import com.maestro.commande.model.Order;
import com.maestro.commande.model.OrderStatus;
import com.maestro.commande.repository.OrderRepository;
import com.maestro.commande.service.OrderService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Object> createOrder(@Valid @RequestBody OrderRequest request) {
        
        return orderService.createOrder(request);
    }

    
    @GetMapping
   // @PreAuthorize("isAuthenticated()") 
    public Mono<List<Order>> getOrders(@RequestParam(required = false) String userId,
                                       @RequestParam(required = false) String dateString) {
    	LocalDateTime date = null;
    	if (dateString != null && !dateString.isEmpty()) {
    	    date = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    	}
        return orderService.getOrders(userId, date);
    }

    @GetMapping("/{orderId}")
    public Mono<Order> getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    
    @PutMapping("/{orderId}")
    public Mono<ResponseEntity<Order>> updatePayment(
            @PathVariable String orderId,
            @RequestBody PaymentConfirmationRequest request) {
        
        return orderService.updateOrder(orderId, request)
                .map(savedOrder -> ResponseEntity.ok(savedOrder))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }



    


    @PutMapping("/{orderId}/cancel")
    public Mono<Order> cancelOrder(
        @PathVariable String orderId,
        @RequestBody PaymentCancellationRequest request) {
        return orderService.cancelOrder(orderId, request);
    }

   
}
