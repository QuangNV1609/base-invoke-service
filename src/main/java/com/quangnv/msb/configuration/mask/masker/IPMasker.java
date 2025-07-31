package com.quangnv.msb.configuration.mask.masker;

/**
 * Masker that masks a potential IBAN while still providing enough information to have it easily identifiable for log analysis. Only the first character of each group is retained, while the rest is replaced with the mask character.
 *
 * Ex: 84.232.150.27 will become 8*.2**.1**.2*
 */
public class IPMasker implements LogMasker {
    protected char maskChar = '*';

    @Override
    public int maskData(StringBuilder builder,  int startPos, int buffLength) {
        int pos = startPos;
        Character character = builder.charAt(pos);
        if (Character.isDigit(character)) {
            int noDigits = 1;
            int noDots = 0;
            pos++;
            while (pos < buffLength && !isDelimiter(builder.charAt(pos))) {
                character = builder.charAt(pos);
                pos++;
                if (Character.isDigit(character)) {
                    noDigits++;
                    if (noDigits > 3) {
                        return startPos;
                    }
                } else if ('.' == character) {
                    noDots++;
                    noDigits = 0;
                } else {
                    return startPos;
                }
            }

            if (noDots == 3 || isDotAtEnd(noDots, builder, pos, buffLength)) {
                StringBuilder masked = new StringBuilder();
                int consecutiveDigits = 0;
                for (int charPos = startPos; charPos < pos; charPos++) {
                    if ('.' == builder.charAt(charPos)) {
                        masked.append('.');
                        consecutiveDigits = 0;
                    } else if (consecutiveDigits == 0) {
                        masked.append(builder.charAt(charPos));
                        consecutiveDigits++;
                    } else {
                        masked.append(maskChar);
                    }
                }

                builder.replace(startPos, pos, masked.toString());
                return pos;
            }
        }

        return startPos;
    }

    @Override
    public void setMaskChar(char maskChar) {
        this.maskChar = maskChar;
    }

    private boolean isDotAtEnd(int noDots, StringBuilder builder, int charPos, int buffLength) {
        return noDots == 4 &&
                (charPos == buffLength || isDelimiter(builder.charAt(charPos)));
    }
}
