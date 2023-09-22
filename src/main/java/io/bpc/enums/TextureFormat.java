package io.bpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_RG;

@AllArgsConstructor
public enum TextureFormat {
    RED(GL_RED), RG(GL_RG), RGB(GL_RGB), RGBA(GL_RGBA), DEPTH(GL_DEPTH_COMPONENT);

    @Getter
    private final int glId;
}
