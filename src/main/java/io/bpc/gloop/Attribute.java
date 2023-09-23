package io.bpc.gloop;

import io.bpc.gloop.enums.DataType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Attribute {
    private final int location;
    private final DataType dataType;
    private final int count;
    private final int stride;
    private final int offset;
}
