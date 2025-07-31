package com.quangnv.msb.core.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MAErrorCode {
    public static final String SUCCESS = "00";
    public static final String FAILED = "01";
    public static final String INVALID_DATA_FORMAT = "03";
    public static final String TIME_OUT = "04";
    public static final String JSON_PROCESSING_ERROR = "05";
    public static final String CRYPTO_ERROR = "06";
    public static final String INIT_REDIS_SERIAL_KEY = "10";
    public static final String CONFIG_NOT_FOUND = "11";
    public static final String INVALID_SECURE_HASH = "12";
    public static final String CACHE_NOT_FOUND = "13";
    public static final String INVALID_REFRESH_TOKEN = "14";
    public static final String INVALID_API = "15";

    public static final String INVALID_FILE_EXTENSION = "16";

    public static final String INVALID_FILE_SIZE = "17";

    public static final String LOGIN_ID_ALREADY_EXISTS = "300";
    public static final String USER_DOES_NOT_EXIST = "301";

    public static final String AUTH_FAIL = "302";
    public static final String DWP_NOT_MATCH = "303";
    public static final String NOT_ENABLE_LOGIN_APP = "304";

    public static final String PHONE_ALREADY_EXIST = "305";

    public static final String EMAIL_ALREADY_EXIST = "306";
    public static final String AUTHZ_FAIL = "307";
    public static final String NOT_FOUND_USER = "308";
    public static final String CANNOT_CHANGE_DWP_SSB_USER = "309";
    public static final String NOT_FOUND_VA_ACCOUNT = "310";

    public static final String ACCESS_DENIED = "403";
    public static final String UNAUTHORIZED = "401";
    public static final String IMAGE_EXT_NOT_ACCEPT = "406";
    public static final String MAX_UPLOAD_SIZE = "407";
    public static final String INVALID_OTP = "408";

    public static final String INVALID_DWP_MIN_LENGTH = "409";
    public static final String INVALID_DWP_LOWER_CASE_REQUIRE = "410";
    public static final String INVALID_DWP_UPPER_CASE_REQUIRE = "411";
    public static final String INVALID_DWP_DIGIT_REQUIRE = "412";
    public static final String INVALID_DWP_SPECIAL_CHAR_REQUIRE = "413";
    public static final String OPT_ATTEMPTS_LIMIT_EXCEED = "414";

    public static final String OPT_GENERATE_LIMIT_EXCEED = "415";
    public static final String PHONE_OR_EMAIL_ALREADY_EXIST = "416";
    public static final String PARTNER_NOT_EXISTS = "417";
    public static final String USER_DOES_NOT_EXIST_OR_BLOCK = "418";
    public static final String MERCHANT_DOES_NOT_EXIST = "419";
    public static final String TERMINAL_CREATION_REQUEST_NOT_FOUND = "420";
    public static final String TERMINAL_CREATION_REQUEST_MAX_LIMIT = "421";

    public static final String VA_ACCOUNT_DOES_NOT_EXIST = "1018";

    public static final String EXT_UNAUTHORIZED = "1048";
    public static final String EXT_ACCESS_DENIED = "1047";
    public static final String EXTERNAL_API_ERROR = "1050";
    public static final String PARTNER_VA_ACCOUNT_NOT_EXISTS = "1051";
    public static final String REQUEST_NOT_FOUND = "1052";
    public static final String REQUEST_ALREADY_EXIST = "1053";
    public static final String REQUEST_CANNOT_CREATE = "1054";
    public static final String REQUEST_MAX_LIMIT = "1055";

    public static final String AN_API_ERROR = "3001";
}
