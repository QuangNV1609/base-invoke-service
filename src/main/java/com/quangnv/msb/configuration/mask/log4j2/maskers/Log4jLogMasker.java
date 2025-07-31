package com.quangnv.msb.configuration.mask.log4j2.maskers;

import com.quangnv.msb.configuration.mask.masker.LogMasker;

public interface Log4jLogMasker extends LogMasker {
    default LogMasker toLogMasker() {
        return this;
    }
}
