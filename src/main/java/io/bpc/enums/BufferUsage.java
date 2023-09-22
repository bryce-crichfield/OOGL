package io.bpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BufferUsage {
    STATIC_DRAW(0x88E4),
    DYNAMIC_DRAW(0x88E8),
    STREAM_DRAW(0x88E0);

    @Getter
    private final int glUsage;
}
