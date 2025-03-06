package com.tech.microservices.order.service;

import com.tech.microservices.order.client.InventoryClient;
import com.tech.microservices.order.dto.OrderRequest;
import com.tech.microservices.order.event.OrderPlacedEvent;
import com.tech.microservices.order.model.Order;
import com.tech.microservices.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest){

        var isProductInStock = inventoryClient.isItemInStock(orderRequest.skuCode(), orderRequest.quantity());
        final String emailId = "hetalgada29@gmail.com";

        if(isProductInStock) {
            //map orderRequest to Order object

            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setQuantity(orderRequest.quantity());
            order.setSkuCode(orderRequest.skuCode());

            //Save Order object in OrderRepository
            orderRepository.save(order);

            //Send the message to kafka topic
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setEmailId(emailId);
            orderPlacedEvent.setFirstName(orderRequest.firstName());
            orderPlacedEvent.setLastName(orderRequest.lastName());
            log.info("Start - Sending OrderPlacedEvent {} to kafka topic order-placed", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End - Sending OrderPlacedEvent {} to kafka topic order-placed", orderPlacedEvent);

        }else{
            throw new RuntimeException("Product with SkuCode " + orderRequest.skuCode() + " is not in stock right now");
        }

    }
}
