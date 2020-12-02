package com.guillaumecl.puzzle2;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class PasswordInstance {
    private final int lPos;
    private final int rPos;
    private final char chr;
    private final String password;

    private PasswordInstance(int lPos, int rPos, char chr, String password) {
        this.lPos = lPos;
        this.rPos = rPos;
        this.chr = chr;
        this.password = password;
    }

    public boolean isValid() {
        int count = StringUtils.countMatches(password, chr);
        return count >= lPos && count <= rPos;
    }

    public boolean isValid2() {
        boolean isLChar = password.charAt(lPos - 1) == chr;
        boolean isRChar = password.charAt(rPos - 1) == chr;
        return isLChar ^ isRChar;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("lPos", lPos)
                .append("rPos", rPos)
                .append("chr", chr)
                .append("password", password)
                .toString();
    }

    public static PasswordInstance fromString(String str) {
        var mainParts = StringUtils.split(str, ":", 2);
        String password = StringUtils.trim(mainParts[1]);
        String rule = StringUtils.trim(mainParts[0]);
        var ruleParts = StringUtils.split(rule, " ", 2);
        char chr = StringUtils.trim(ruleParts[1]).charAt(0);
        String range = StringUtils.trim(ruleParts[0]);
        var rangeParts = StringUtils.split(range, "-", 2);
        int lPos = Integer.parseUnsignedInt(StringUtils.trim(rangeParts[0]));
        int rPos = Integer.parseUnsignedInt(StringUtils.trim(rangeParts[1]));
        return  new PasswordInstance(lPos, rPos, chr, password);
    }
}
