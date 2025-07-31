package com.quangnv.msb.core.dto.paging;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {
    private Integer page;
    private Integer limit;
    private String keyword;
    private List<Filter> filters;
    private List<Sort> sorts;
}
