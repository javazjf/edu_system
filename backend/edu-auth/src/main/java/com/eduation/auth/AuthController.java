package com.eduation.auth;

import com.eduation.common.api.ApiResponse;
import com.eduation.common.model.EduModels.LoginRequest;
import com.eduation.common.model.EduModels.LoginResponse;
import com.eduation.common.model.EduModels.User;
import com.eduation.common.security.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService = new JwtService("eduation-system-demo-secret-key-must-be-at-least-32");

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = switch (request.username()) {
            case "admin" -> new User(1L, "admin", "运营管理员", "ADMIN", "");
            case "teacher" -> new User(2L, "teacher", "演示教师", "TEACHER", "");
            case "student" -> new User(3L, "student", "演示学生", "STUDENT", "");
            default -> throw new IllegalArgumentException("账号不存在");
        };
        if (!"123456".equals(request.password())) {
            throw new IllegalArgumentException("密码错误");
        }
        String token = jwtService.createToken(user.username(), Map.of("role", user.role(), "userId", user.id()), 7200);
        String refresh = jwtService.createToken(user.username(), Map.of("type", "refresh"), 86400);
        return ApiResponse.ok(new LoginResponse(token, refresh, user, permissions(user.role())));
    }

    @GetMapping("/captcha")
    public ApiResponse<Map<String, String>> captcha() {
        return ApiResponse.ok(Map.of("captchaId", "demo", "image", "data:image/svg+xml;base64,PHN2Zy8+"));
    }

    private List<String> permissions(String role) {
        return switch (role) {
            case "ADMIN" -> List.of("*:*:*");
            case "TEACHER" -> List.of("course:manage", "exam:manage", "statistics:teacher");
            default -> List.of("course:view", "learning:study", "order:create", "exam:join");
        };
    }
}
