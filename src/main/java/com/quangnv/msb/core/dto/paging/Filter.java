package com.quangnv.msb.core.dto.paging;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filter {
    private String column;
    private String value;
}
