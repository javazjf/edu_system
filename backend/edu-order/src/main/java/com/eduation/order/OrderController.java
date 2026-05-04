package com.eduation.order;

import com.eduation.common.api.ApiResponse;
import com.eduation.common.model.EduModels.Order;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @GetMapping("/orders")
    public ApiResponse<List<Order>> orders() {
        return ApiResponse.ok(List.of(order(1L, "EDU202605040001", 1L, "Spring Cloud 在线教育实战", "PAID")));
    }

    @PostMapping("/orders")
    public ApiResponse<Order> create(@RequestBody Map<String, Long> request) {
        return ApiResponse.ok(order(99L, "EDU" + System.currentTimeMillis(), request.getOrDefault("courseId", 1L), "演示课程", "CREATED"));
    }

    @PostMapping("/orders/{id}/mock-pay")
    public ApiResponse<Map<String, Object>> mockPay(@PathVariable Long id) {
        return ApiResponse.ok(Map.of("orderId", id, "status", "PAID", "tradeNo", "MOCK" + System.currentTimeMillis()));
    }

    @GetMapping("/coupons")
    public ApiResponse<List<Map<String, Object>>> coupons() {
        return ApiResponse.ok(List.of(
                Map.of("id", 1, "name", "新人立减 50", "amount", 50, "threshold", 199),
                Map.of("id", 2, "name", "职业课 8 折券", "amount", 20, "threshold", 0)
        ));
    }

    private Order order(Long id, String no, Long courseId, String title, String status) {
        return new Order(id, no, courseId, title, new BigDecimal("299.00"), status, LocalDateTime.now().minusDays(1));
    }
}
