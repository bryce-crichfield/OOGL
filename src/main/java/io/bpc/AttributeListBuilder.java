package io.bpc;

import io.bpc.enums.DataType;

import java.util.ArrayList;
import java.util.List;

public class AttributeListBuilder {
    private record Entry(int location, DataType dataType, int count) { }

    private final List<Entry> attributes = new ArrayList<>();

    public AttributeListBuilder define(int location, DataType dataType, int count) {
        attributes.add(new Entry(location, dataType, count));
        return this;
    }

    public List<Attribute> build() {
        List<Attribute> result = new ArrayList<>();
        int offset = 0;
        for (Entry entry : attributes) {
            result.add(Attribute.builder()
                    .location(entry.location())
                    .dataType(entry.dataType())
                    .count(entry.count())
                    .stride(entry.dataType().getByteSize() * entry.count())
                    .offset(offset)
                    .build());
            offset += entry.dataType().getByteSize() * entry.count();
        }
        return result;
    }
}
