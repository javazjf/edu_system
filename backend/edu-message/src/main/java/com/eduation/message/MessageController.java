package com.eduation.message;

import com.eduation.common.api.ApiResponse;
import com.eduation.common.model.EduModels.Notice;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @GetMapping("/notices")
    public ApiResponse<List<Notice>> notices() {
        return ApiResponse.ok(List.of(
                new Notice(1L, "课程审核规范更新", "教师发布课程前请完善章节和封面。", LocalDateTime.now().minusDays(1)),
                new Notice(2L, "学习季活动上线", "学生端可领取新人优惠券。", LocalDateTime.now().minusHours(8))
        ));
    }

    @PostMapping("/send")
    public ApiResponse<Map<String, Object>> send(@RequestBody Map<String, Object> request) {
        return ApiResponse.ok(Map.of("sent", true, "message", request));
    }
}
