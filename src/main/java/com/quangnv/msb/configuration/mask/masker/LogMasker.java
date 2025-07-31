package com.quangnv.msb.configuration.mask.masker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Log masker used for masking the contents of a log event. The maskers are executed one after the other
 * starting with the last known unmasked position
 */
public interface LogMasker {
    List<Character> KNOWN_DELIMITERS = Collections.unmodifiableList(Arrays.asList('\'', '"', '<', '>', '='));

    default boolean isDelimiter(char character) {
        return Character.isWhitespace(character) || KNOWN_DELIMITERS.contains(character);
    }

    default int indexOfNextDelimiter(StringBuilder builder, int startPos, int buffLength) {
        while (startPos < buffLength && !isDelimiter(builder.charAt(startPos))) {
            startPos++;
        }
        return startPos;
    }

    default void initialize(String params){
    }

    /**
     * Mask the StringBuilder starting at the start position
     * @param builder The StringBuilder which contains the text that should be masked
     * @param startPos The starting position from which masking should be attempted
     * @param buffLength The total length of the buffer
     * @return Return the starting position for the next masker. If no masking was done, the end position should be
     * the same as the start position. If masking was done, the end position should be the end of the masked text.
     */
    int maskData(StringBuilder builder, int startPos, int buffLength);

    /**
     * Set the character which will be used for masking the data
     * @param maskChar The character which will mask the data
     */
    void setMaskChar(char maskChar);
}
