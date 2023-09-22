package io.bpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.lwjgl.opengl.GL11.*;

@AllArgsConstructor
public enum DataType {
    BYTE(GL_BYTE, 1),
    UNSIGNED_BYTE(GL_UNSIGNED_BYTE, 1),
    SHORT(GL_SHORT, 2),
    UNSIGNED_SHORT(GL_UNSIGNED_SHORT, 2),
    INT(GL_INT, 4),
    UNSIGNED_INT(GL_UNSIGNED_INT, 4),
    FLOAT(GL_FLOAT, 4);

    @Getter
    private final int glId;

    @Getter
    private final int byteSize;
}
