package io.bpc.gloop.enums.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;

import java.util.Map;

@AllArgsConstructor
public enum Mouse {
    LEFT(GLFW.GLFW_MOUSE_BUTTON_LEFT),
    RIGHT(GLFW.GLFW_MOUSE_BUTTON_RIGHT),
    MIDDLE(GLFW.GLFW_MOUSE_BUTTON_MIDDLE);

    private static Map<Integer, Mouse> mouseMap = null;
    @Getter
    private final int glId;

    public static Mouse fromGlId(int glId) {
        if (mouseMap == null) {
            mouseMap = Map.of(
                    GLFW.GLFW_MOUSE_BUTTON_LEFT, LEFT,
                    GLFW.GLFW_MOUSE_BUTTON_RIGHT, RIGHT,
                    GLFW.GLFW_MOUSE_BUTTON_MIDDLE, MIDDLE
            );
        }

        return mouseMap.get(glId);
    }
}
