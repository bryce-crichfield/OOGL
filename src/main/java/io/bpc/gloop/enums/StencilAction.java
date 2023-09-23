package io.bpc.gloop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_DECR_WRAP;
import static org.lwjgl.opengl.GL14.GL_INCR_WRAP;

@AllArgsConstructor
public enum StencilAction {
    KEEP(GL_KEEP), ZERO(GL_ZERO), REPLACE(GL_REPLACE), INCREASE(GL_INCR), INCREASE_WRAP(GL_INCR_WRAP), DECREASE(
            GL_DECR), DECREASE_WRAP(GL_DECR_WRAP), INVERT(GL_INVERT);

    @Getter
    private final int glId;
}
