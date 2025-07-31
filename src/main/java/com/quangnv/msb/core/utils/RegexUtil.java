package com.quangnv.msb.core.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegexUtil {
    public static final String REGEX_PHONE = "^0\\d{9}$";
    public static final String REGEX_ID_CARD = "^(?:\\d{9}|\\d{12})$";
    public static final String REGEX_GENDER = "^[FM]$";
    public static final String REGEX_BOOLEAN = "^(0|1)$";
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String VIETNAMESE_DIACRITIC_CHARACTERS = "a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễếệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ";
    public static final String REGEX_NAME = "^[" + VIETNAMESE_DIACRITIC_CHARACTERS + "\\s-]*$";
    public static final String REGEX_NAME_AND_BUSINESS_ADDRESS = "^[0-9A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀẾỂỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪỬỮỰỲỴÝỶỸ].{1,255}$";

    public static final String REGEX_FULL_NAME = "^(?:[" + VIETNAMESE_DIACRITIC_CHARACTERS + "]+\\s*)+[" + VIETNAMESE_DIACRITIC_CHARACTERS + "]*$";
    public static final String REGEX_ADDRESS = "^[" + VIETNAMESE_DIACRITIC_CHARACTERS + "0-9\\s-]*$";
    public static final String REGEX_COUNTRY_CODE = "^[A-Z]{2}$";
    public static final String REGEX_LETTER_NUMBER = "^[a-zA-Z0-9]*$";

    public static final String REGEX_DWP_UPPER = "^.*[A-Z].*$";
    public static final String REGEX_DWP_LOWER = "^.*[a-z].*$";
    public static final String REGEX_DWP_DIGIT = "^.*[0-9].*$";
    public static final String REGEX_DWP_SPECIAL = "^.*[~!@#$%&*)(_+}{|:?><,./\\=^-].*$";
}
