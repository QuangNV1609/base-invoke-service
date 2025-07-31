package com.quangnv.msb.core.utils;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;

@UtilityClass
public class AppConstants {
    public static final String SERVICE_CODE = "MA";
    public static final String MA_OPS = "MA_OPS";
    public static final String UPLOAD = "UPLOAD";
    public static final String UNDERLINE = "_";
    public static final String SSB_EMAIL_PREFIX = "@seabank.com.vn";
    public SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
}
