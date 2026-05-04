package com.eduation.marketing;

import com.eduation.common.api.ApiResponse;
import com.eduation.common.model.EduModels.Banner;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/marketing")
public class MarketingController {
    @GetMapping("/banners")
    public ApiResponse<List<Banner>> banners() {
        return ApiResponse.ok(List.of(
                new Banner(1L, "系统化学习 Spring Cloud", "https://images.unsplash.com/photo-1516321318423-f06f85e504b3", "/courses/1"),
                new Banner(2L, "Vue3 管理系统训练营", "https://images.unsplash.com/photo-1555066931-4365d14bab8c", "/courses/2")
        ));
    }

    @GetMapping("/campaigns")
    public ApiResponse<List<Map<String, Object>>> campaigns() {
        return ApiResponse.ok(List.of(
                Map.of("title", "五一学习季", "tag", "限时优惠", "discount", "满199减50"),
                Map.of("title", "教师成长计划", "tag", "专题", "discount", "精选课程8折")
        ));
    }
}
