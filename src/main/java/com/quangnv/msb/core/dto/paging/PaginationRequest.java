package com.quangnv.msb.core.dto.paging;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationRequest {
    private Integer page;
    private Integer limit;
    private String keyword;
    private List<Filter> filters;
    private List<Sort> sorts;

    public Pageable getPageable() {
        return PageRequest.of(page - 1, limit);
    }
}
