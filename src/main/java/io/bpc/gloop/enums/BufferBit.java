package io.bpc.gloop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.lwjgl.opengl.GL11.*;

@AllArgsConstructor
public enum BufferBit {
    COLOR(GL_COLOR_BUFFER_BIT), DEPTH(GL_DEPTH_BUFFER_BIT), STENCIL(GL_STENCIL_BUFFER_BIT);

    @Getter
    private final int glId;
}
