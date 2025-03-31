package com.example.backend.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Link to User entity

    @ManyToOne
    @JoinColumn(name = "part_id", nullable = false)
    private Part part;  // Link to Part entity

    private int quantity;
    private double totalPrice;
    
    private LocalDateTime orderDate = LocalDateTime.now();
}
