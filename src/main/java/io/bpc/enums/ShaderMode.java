package io.bpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ShaderMode {
    VERTEX(0x8B31),
    FRAGMENT(0x8B30);

    @Getter
    private final int glType;
}
