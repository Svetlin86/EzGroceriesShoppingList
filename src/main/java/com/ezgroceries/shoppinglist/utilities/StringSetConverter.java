package com.ezgroceries.shoppinglist.utilities;

import jakarta.persistence.AttributeConverter;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StringSetConverter implements AttributeConverter<Set<String>,String> {
    @Override
    public String convertToDatabaseColumn(Set<String> set) {
        if (!CollectionUtils.isEmpty(set)) {
            return "," + String.join(",", set) + ",";
        } else {
            return null;
        }
    }

    @Override
    public Set<String> convertToEntityAttribute(String joined) {
        if (joined != null) {
            String values = joined.substring(1, joined.length() - 1); //Removes leading and trailing commas
            return new HashSet<>(Arrays.asList(values.split(",")));
        }
        return new HashSet<>();
    }
}
