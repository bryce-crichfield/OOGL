package io.bpc.gloop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.lwjgl.opengl.GL11.*;

@AllArgsConstructor
public enum Capability {
    DEPTH_TEST(GL_DEPTH_TEST), CULL_FACE(GL_CULL_FACE), BLEND(GL_BLEND), STENCIL_TEST(GL_STENCIL_TEST);

    @Getter
    private final int glId;
}
