package com.quangnv.msb.core.dto.paging;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sort {
    public enum EnumSort {
        ASC,
        DESC,
    }

    private String column;
    private EnumSort order;
}
