package com.eduation.learning;

import com.eduation.common.api.ApiResponse;
import com.eduation.common.model.EduModels.LearningProgress;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/learning")
public class LearningController {
    @GetMapping("/my-courses")
    public ApiResponse<List<LearningProgress>> myCourses() {
        return ApiResponse.ok(List.of(
                new LearningProgress(1L, "Spring Cloud 在线教育实战", 64, 2L, LocalDateTime.now().minusHours(3)),
                new LearningProgress(2L, "Vue3 + Element Plus 管理系统", 28, 1L, LocalDateTime.now().minusDays(2))
        ));
    }

    @PostMapping("/enroll")
    public ApiResponse<Map<String, Object>> enroll(@RequestBody Map<String, Long> request) {
        return ApiResponse.ok(Map.of("courseId", request.getOrDefault("courseId", 1L), "status", "ENROLLED"));
    }

    @PostMapping("/progress")
    public ApiResponse<Map<String, Object>> progress(@RequestBody Map<String, Object> request) {
        return ApiResponse.ok(Map.of("saved", true, "progress", request));
    }

    @GetMapping("/notes")
    public ApiResponse<List<Map<String, Object>>> notes() {
        return ApiResponse.ok(List.of(Map.of("courseTitle", "Spring Cloud 在线教育实战", "content", "服务边界需要围绕业务能力划分。")));
    }
}
