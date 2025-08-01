package com.quangnv.msb.core.utils;

import com.quangnv.msb.core.dto.paging.PaginationResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

@UtilityClass
public class PaginationUtils {
    public static <T> PaginationResponse<T> buildPaginationResponse(Page<T> page) {
        return PaginationResponse.<T>builder()
                .items(page.getContent())
                .totalRecords((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
