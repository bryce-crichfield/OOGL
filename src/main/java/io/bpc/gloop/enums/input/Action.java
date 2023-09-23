package io.bpc.gloop.enums.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;

import java.util.Map;

@AllArgsConstructor
public enum Action {
    PRESS(GLFW.GLFW_PRESS),
    RELEASE(GLFW.GLFW_RELEASE),
    REPEAT(GLFW.GLFW_REPEAT);

    private static Map<Integer, Action> actionMap = null;
    @Getter
    private final int glId;

    public static Action fromGlId(int glId) {
        if (actionMap == null) {
            actionMap = Map.of(
                    GLFW.GLFW_PRESS, PRESS,
                    GLFW.GLFW_RELEASE, RELEASE,
                    GLFW.GLFW_REPEAT, REPEAT
            );
        }

        return actionMap.get(glId);
    }
}

