package com.eduation.course;

import com.eduation.common.api.ApiResponse;
import com.eduation.common.api.PageResult;
import com.eduation.common.model.EduModels.Chapter;
import com.eduation.common.model.EduModels.Course;
import com.eduation.common.model.EduModels.Lesson;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    private final List<Course> courses = List.of(
            new Course(1L, "Spring Cloud 在线教育实战", "后端开发", "演示教师", new BigDecimal("299.00"), "/covers/spring-cloud.jpg", "PUBLISHED", 36, 1280, 4.9),
            new Course(2L, "Vue3 + Element Plus 管理系统", "前端开发", "演示教师", new BigDecimal("199.00"), "/covers/vue-admin.jpg", "PUBLISHED", 28, 940, 4.8),
            new Course(3L, "AI 产品经理训练营", "产品设计", "运营导师", new BigDecimal("399.00"), "/covers/ai-pm.jpg", "PENDING", 42, 620, 4.7)
    );

    @GetMapping("/courses")
    public ApiResponse<PageResult<Course>> courses(@RequestParam(defaultValue = "1") int pageNo,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        return ApiResponse.ok(PageResult.of(courses, pageNo, pageSize));
    }

    @GetMapping("/courses/{id}")
    public ApiResponse<Map<String, Object>> detail(@PathVariable Long id) {
        Course course = courses.stream().filter(item -> item.id().equals(id)).findFirst().orElse(courses.getFirst());
        return ApiResponse.ok(Map.of("course", course, "chapters", chapters(id)));
    }

    @PostMapping("/courses")
    public ApiResponse<Course> create(@RequestBody Course course) {
        return ApiResponse.ok(course);
    }

    @PostMapping("/courses/{id}/publish")
    public ApiResponse<Map<String, Object>> publish(@PathVariable Long id) {
        return ApiResponse.ok(Map.of("courseId", id, "status", "PENDING"));
    }

    @PostMapping("/courses/{id}/approve")
    public ApiResponse<Map<String, Object>> approve(@PathVariable Long id) {
        return ApiResponse.ok(Map.of("courseId", id, "status", "PUBLISHED"));
    }

    @GetMapping("/categories")
    public ApiResponse<List<String>> categories() {
        return ApiResponse.ok(List.of("后端开发", "前端开发", "产品设计", "考试认证", "职业素养"));
    }

    private List<Chapter> chapters(Long courseId) {
        return List.of(
                new Chapter(1L, courseId, "课程导学", List.of(new Lesson(1L, 1L, "平台与环境准备", 18, "VIDEO"))),
                new Chapter(2L, courseId, "核心实战", List.of(new Lesson(2L, 2L, "业务建模", 32, "VIDEO"), new Lesson(3L, 2L, "接口联调", 45, "VIDEO")))
        );
    }
}
