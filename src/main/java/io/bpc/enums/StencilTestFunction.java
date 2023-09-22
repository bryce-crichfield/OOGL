package io.bpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.lwjgl.opengl.GL11.*;

@AllArgsConstructor
public enum StencilTestFunction {
    NEVER(GL_NEVER), LESS(GL_LESS), EQUAL(GL_EQUAL), LEQUAL(GL_LEQUAL), GREATER(GL_GREATER), NOTEQUAL(
            GL_NOTEQUAL), GEQUAL(GL_GEQUAL), ALWAYS(GL_ALWAYS);

    @Getter
    private final int glId;
}
