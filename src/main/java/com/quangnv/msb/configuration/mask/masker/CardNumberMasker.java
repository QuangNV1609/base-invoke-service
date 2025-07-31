package com.quangnv.msb.configuration.mask.masker;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Masker that masks a potential card number (PAN) while still providing enough information to have it easily identifiable for log analysis. The first digit as well as the last 6 digits won't be masked. All other digits will be replaced by the mask character.
 *
 * A potential PAN is considered any sequence of digits that is between 8 and 19 characters long, surrounded by whitespaces, and starts with known digits for PAN schemes (1, 3, 4, 5, or 6)
 *
 * Ex: 4916246076443617 will become 4*********443617
 */
public class CardNumberMasker implements LogMasker {
    private static final List<Character> KNOWN_PAN_START_DIGITS = Arrays.asList('1', '3', '4', '5', '6');

    protected int startKeep = 6;
    protected int endKeep = 4;

    protected boolean luhnCheck = false;

    protected char maskChar = '*';

    @Override
    public void initialize(String args) {
        if (StringUtils.isBlank(args)) {
            startKeep = 6;
            endKeep = 4;
        } else {
            String[] params = StringUtils.split(args, '|');
            if (params.length != 2) {
                throw new ExceptionInInitializerError("Invalid parameters supplied for CardNumber masker: " + args);
            }
            startKeep = Integer.valueOf(params[0]);
            endKeep = Integer.valueOf(params[1]);

            if (startKeep < 1 || startKeep > 6 ) {
                throw new ExceptionInInitializerError("The number of unmasked digits at the start of the pan can't be more than 6 or less than 1");
            }

            if (endKeep < 1 || endKeep > 8) {
                throw new ExceptionInInitializerError("The number of unmasked digits at the end of the pan can't be more than 8 or less than 1");
            }
        }
    }

    @Override
    public int maskData(StringBuilder builder, int startPos, int buffLength) {
        int pos = startPos;
        int panLength;
        Character checkChar = builder.charAt(pos);
        if (KNOWN_PAN_START_DIGITS.contains(checkChar)) {
            panLength = 1;
            pos++;
            while (pos < buffLength && Character.isDigit(builder.charAt(pos))) {
                panLength++;
                pos++;
            }

            if (validPan(builder, startPos, panLength, buffLength)) {
                builder.replace(startPos + startKeep,
                        startPos + panLength - endKeep,
                        StringUtils.repeat(maskChar, panLength - startKeep - endKeep));

                return startPos + panLength;
            }
        }

        return startPos;
    }

    @Override
    public void setMaskChar(char maskChar) {
        this.maskChar = maskChar;
    }

    private boolean validPan(StringBuilder builder, int startPos, int panLength, int buffLength) {
        return panLength >= 8 && panLength <= 19 && (startPos + panLength == buffLength || isDelimiter(builder.charAt(startPos + panLength)))
                && luhnValid(builder, startPos, panLength);
    }

    private boolean luhnValid(StringBuilder builder, int startPos, int panLength) {
        if (luhnCheck) {
            int sum = 0;
            int pos = 0;
            for (int count = startPos; count < panLength + startPos; count++) {
                if (pos % 2 == 0) {
                    int val = Character.getNumericValue(builder.charAt(count)) * 2;
                    sum += val;
                    if (val > 9) {
                        sum -= 9;
                    }
                } else {
                    sum += Character.getNumericValue(builder.charAt(count));
                }
                pos++;
            }

            return sum % 10 == 0;
        }

        return true;
    }
}
