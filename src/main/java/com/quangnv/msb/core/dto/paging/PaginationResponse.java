package com.quangnv.msb.core.dto.paging;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponse<T> {
    private List<T> items;
    private Integer totalRecords;
    private Integer totalPages;
}
