package com.quangnv.msb.configuration.mask.masker;

/**
 * Log Masker used for masking a text. All maskers of type SequentialLogmasker will be executed one after the other,
 * with the entire text being sent
 */
public interface SequentialLogMasker {
    /**
     * Reset the masker configuration using the provided arguments
     * @param args The arguments
     */
    default void initialize(String args) {
    }

    /**
     * Mask the content of the supplied String Buffer using the provided mask character
     * @param unmasked The StringBuffer that should be masked
     * @return If anything was masked or not
     */
    boolean mask(StringBuilder unmasked);

    /**
     * Set the character which will be used for masking the data
     * @param maskChar The character which will mask the data
     */
    void setMaskChar(char maskChar);
}
