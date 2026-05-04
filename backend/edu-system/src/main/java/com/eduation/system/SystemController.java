package com.eduation.system;

import com.eduation.common.api.ApiResponse;
import com.eduation.common.model.EduModels.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class SystemController {
    @GetMapping("/users")
    public ApiResponse<List<User>> users() {
        return ApiResponse.ok(List.of(
                new User(1L, "admin", "运营管理员", "ADMIN", ""),
                new User(2L, "teacher", "演示教师", "TEACHER", ""),
                new User(3L, "student", "演示学生", "STUDENT", "")
        ));
    }

    @GetMapping("/menus")
    public ApiResponse<List<Map<String, Object>>> menus(@RequestParam(defaultValue = "ADMIN") String role) {
        return ApiResponse.ok(List.of(
                Map.of("title", "工作台", "path", "/dashboard", "icon", "DataBoard"),
                Map.of("title", "课程管理", "path", "/courses", "icon", "Reading"),
                Map.of("title", "订单管理", "path", "/orders", "icon", "Tickets"),
                Map.of("title", "考试题库", "path", "/exams", "icon", "EditPen"),
                Map.of("title", "系统权限", "path", "/system", "icon", "Setting")
        ));
    }

    @GetMapping("/dicts")
    public ApiResponse<Map<String, List<String>>> dicts() {
        return ApiResponse.ok(Map.of(
                "courseStatus", List.of("DRAFT", "PENDING", "PUBLISHED", "OFFLINE"),
                "orderStatus", List.of("CREATED", "PAID", "REFUNDED", "CLOSED"),
                "roles", List.of("ADMIN", "TEACHER", "STUDENT")
        ));
    }
}
