package com.eduation.common.api;

import java.util.List;

public record PageResult<T>(List<T> records, long total, int pageNo, int pageSize) {
    public static <T> PageResult<T> of(List<T> records, int pageNo, int pageSize) {
        return new PageResult<>(records, records.size(), pageNo, pageSize);
    }
}
