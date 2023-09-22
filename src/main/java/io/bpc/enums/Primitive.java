package io.bpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.lwjgl.opengl.GL11.*;

@AllArgsConstructor
public enum Primitive {
    TRIANGLES(GL_TRIANGLES), LINES(GL_LINES), POINTS(GL_POINTS), QUADS(GL_QUADS);

    @Getter
    private final int glId;
}
