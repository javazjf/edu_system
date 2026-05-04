package com.eduation.exam;

import com.eduation.common.api.ApiResponse;
import com.eduation.common.model.EduModels.ExamPaper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exam")
public class ExamController {
    @GetMapping("/papers")
    public ApiResponse<List<ExamPaper>> papers() {
        return ApiResponse.ok(List.of(
                new ExamPaper(1L, "Spring Cloud 阶段测验", 1L, 20, 100),
                new ExamPaper(2L, "Vue3 项目实战测验", 2L, 15, 100)
        ));
    }

    @GetMapping("/questions")
    public ApiResponse<List<Map<String, Object>>> questions() {
        return ApiResponse.ok(List.of(
                Map.of("id", 1, "type", "SINGLE", "title", "Gateway 的核心职责是什么？", "score", 5),
                Map.of("id", 2, "type", "JUDGE", "title", "JWT 可以携带用户角色声明。", "score", 5)
        ));
    }

    @PostMapping("/submit")
    public ApiResponse<Map<String, Object>> submit(@RequestBody Map<String, Object> request) {
        return ApiResponse.ok(Map.of("paperId", request.getOrDefault("paperId", 1), "score", 92, "passed", true));
    }
}
