package com.eduation.common.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public final class EduModels {
    private EduModels() {
    }

    public record User(Long id, String username, String realName, String role, String avatar) {
    }

    public record LoginRequest(String username, String password) {
    }

    public record LoginResponse(String accessToken, String refreshToken, User user, List<String> permissions) {
    }

    public record Course(Long id, String title, String category, String teacherName, BigDecimal price,
                         String coverUrl, String status, int lessons, int students, double rating) {
    }

    public record Chapter(Long id, Long courseId, String title, List<Lesson> lessons) {
    }

    public record Lesson(Long id, Long chapterId, String title, int durationMinutes, String resourceType) {
    }

    public record Order(Long id, String orderNo, Long courseId, String courseTitle, BigDecimal amount,
                        String status, LocalDateTime createdAt) {
    }

    public record LearningProgress(Long courseId, String courseTitle, int percent, Long currentLessonId,
                                   LocalDateTime lastLearnAt) {
    }

    public record ExamPaper(Long id, String title, Long courseId, int questionCount, int totalScore) {
    }

    public record Banner(Long id, String title, String imageUrl, String linkUrl) {
    }

    public record Notice(Long id, String title, String content, LocalDateTime publishedAt) {
    }

    public record Metric(String label, String value, String trend) {
    }
}
