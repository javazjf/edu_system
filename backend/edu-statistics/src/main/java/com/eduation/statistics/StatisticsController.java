package com.eduation.statistics;

import com.eduation.common.api.ApiResponse;
import com.eduation.common.model.EduModels.Metric;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @GetMapping("/overview")
    public ApiResponse<Map<String, Object>> overview() {
        return ApiResponse.ok(Map.of(
                "metrics", List.of(
                        new Metric("课程数", "128", "+12%"),
                        new Metric("付费订单", "3,482", "+18%"),
                        new Metric("学习人数", "24,910", "+9%"),
                        new Metric("考试通过率", "86%", "+4%")
                ),
                "sales", List.of(120, 180, 240, 300, 360, 420, 510),
                "learning", List.of(320, 460, 520, 680, 720, 810, 900)
        ));
    }

    @GetMapping("/teacher")
    public ApiResponse<Map<String, Object>> teacher() {
        return ApiResponse.ok(Map.of("students", 1280, "income", 58200, "rating", 4.9, "completionRate", "72%"));
    }
}
