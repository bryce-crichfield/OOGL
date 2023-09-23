package io.bpc.gloop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL14.GL_MIRRORED_REPEAT;

@AllArgsConstructor
public enum TextureWrapping {
    REPEAT(GL_REPEAT), MIRRORED_REPEAT(GL_MIRRORED_REPEAT), CLAMP_TO_EDGE(GL_CLAMP_TO_EDGE), CLAMP_TO_BORDER(
            GL_CLAMP_TO_BORDER);

    @Getter
    private final int glId;
}
