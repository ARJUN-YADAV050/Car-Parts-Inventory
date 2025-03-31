    package com.example.backend.controllers;

    import com.example.backend.models.Order;
    import com.example.backend.services.OrderService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Optional;

    @CrossOrigin(origins = "http://localhost:3000") 
    @RestController
    @RequestMapping("/api/orders")
    public class OrderController {

        @Autowired
        private OrderService orderService;

  
        @GetMapping
        public ResponseEntity<List<Order>> getAllOrders() {
            return ResponseEntity.ok(orderService.getAllOrders());
        }

     
        @GetMapping("/{id}")
        public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
            Optional<Order> order = orderService.getOrderById(id);
            return order.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
        }

   
        @GetMapping("/user/{userId}")
        public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
            return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
        }


@PutMapping("/{id}")
public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
    Order updatedOrder = orderService.updateOrder(id, orderDetails);
    return updatedOrder != null ? ResponseEntity.ok(updatedOrder) : ResponseEntity.notFound().build();
}



        @PostMapping
        public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
            try {
                Order savedOrder = orderService.placeOrder(order);
                return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

  
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
            boolean deleted = orderService.deleteOrder(id);
            if (deleted) {
                return ResponseEntity.ok("Order deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
            }
        }
    }
