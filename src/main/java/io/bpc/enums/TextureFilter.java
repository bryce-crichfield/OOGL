package io.bpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.lwjgl.opengl.GL11.*;

@AllArgsConstructor
public enum TextureFilter {
    LINEAR(GL_LINEAR), NEAREST(GL_NEAREST), LINEAR_MIPMAP_LINEAR(GL_LINEAR_MIPMAP_LINEAR), NEAREST_MIPMAP_NEAREST(
            GL_NEAREST_MIPMAP_NEAREST), LINEAR_MIPMAP_NEAREST(GL_LINEAR_MIPMAP_NEAREST), NEAREST_MIPMAP_LINEAR(
            GL_NEAREST_MIPMAP_LINEAR);

    @Getter
    private final int glId;
}
