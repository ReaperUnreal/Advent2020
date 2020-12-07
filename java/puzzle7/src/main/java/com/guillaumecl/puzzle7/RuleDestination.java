package com.guillaumecl.puzzle7;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class RuleDestination {
    private final String destination;
    private final int quantity;

    public RuleDestination(String destination, int quantity) {
        this.destination = destination;
        this.quantity = quantity;
    }

    public String getDestination() {
        return destination;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RuleDestination that = (RuleDestination) o;

        return new EqualsBuilder()
                .append(quantity, that.quantity)
                .append(destination, that.destination)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(destination)
                .append(quantity)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("destination", destination)
                .append("quantity", quantity)
                .toString();
    }

    public static RuleDestination fromString(String str) {
        var parts = StringUtils.split(str, " ", 2);
        int quantity = Integer.parseUnsignedInt(parts[0]);
        String name = StringUtils.trim(StringUtils.substringBeforeLast(parts[1], "bag"));
        return new RuleDestination(name, quantity);
    }
}
