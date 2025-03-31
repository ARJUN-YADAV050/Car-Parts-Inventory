package com.example.backend.services;

import com.example.backend.models.Order;
import com.example.backend.models.Part;
import com.example.backend.models.User;
import com.example.backend.repositories.OrderRepository;
import com.example.backend.repositories.PartRepository;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartRepository partRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order placeOrder(Order order) {
        User user = userRepository.findById(order.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Part part = partRepository.findById(order.getPart().getId())
                .orElseThrow(() -> new RuntimeException("Part not found"));

        order.setUser(user);
        order.setPart(part);

        return orderRepository.save(order);
    }

public Order updateOrder(Long id, Order updatedOrder) {
    return orderRepository.findById(id).map(order -> {
        order.setQuantity(updatedOrder.getQuantity());
        order.setTotalPrice(updatedOrder.getQuantity() * order.getPart().getPrice().doubleValue());
        return orderRepository.save(order);
    }).orElse(null);
}
 

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
