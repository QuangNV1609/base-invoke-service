package com.quangnv.msb.core.dto.okhttp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileResponse {
    private String path;
    private long sizeInByte;
    private String contentType;
}
