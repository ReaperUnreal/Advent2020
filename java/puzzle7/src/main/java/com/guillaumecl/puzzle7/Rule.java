package com.guillaumecl.puzzle7;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class Rule {
    private final String name;
    private final List<RuleDestination> destinations;

    public Rule(String name, List<RuleDestination> destinations) {
        this.name = name;
        this.destinations = destinations;
    }

    public String getName() {
        return name;
    }

    public List<RuleDestination> getDestinations() {
        return destinations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        return new EqualsBuilder()
                .append(name, rule.name)
                .append(destinations, rule.destinations)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(destinations)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("destinations", destinations)
                .toString();
    }

    public static Rule fromString(String str) {
        var parts = StringUtils.splitByWholeSeparator(str, " bags contain");
        String name = StringUtils.trim(parts[0]);
        String destinationsStr = StringUtils.trim(parts[1]);
        if (StringUtils.contains(parts[1], "no other bags")) {
            return new Rule(name, Collections.emptyList());
        }
        if (StringUtils.endsWith(destinationsStr, ".")) {
            destinationsStr = StringUtils.trim(StringUtils.substringBeforeLast(destinationsStr, "."));
        }
        var destinations = Arrays.stream(StringUtils.split(destinationsStr, ','))
                .map(StringUtils::trim)
                .map(RuleDestination::fromString)
                .collect(Collectors.toList());

        return new Rule(name, destinations);
    }
}
