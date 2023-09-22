package io.bpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

@AllArgsConstructor
public enum InternalFormat {
    RGB8(GL_RGB8),
    RGBA8(GL_RGBA8),
    RG8(GL_RG8),
    R8(GL_R8),
    DEPTH_COMPONENT16(GL_DEPTH_COMPONENT16),
    DEPTH_COMPONENT24(GL_DEPTH_COMPONENT24),
    DEPTH_COMPONENT32(GL_DEPTH_COMPONENT32);

    @Getter
    private final int glId;
}
