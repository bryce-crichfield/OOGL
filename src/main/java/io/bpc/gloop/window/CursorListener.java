package io.bpc.gloop.window;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;

public interface CursorListener extends GLFWCursorPosCallbackI {
    @Override
    default void invoke(long window, double xpos, double ypos) {
        this.onCursor(xpos, ypos);
    }

    void onCursor(double x, double y);
}
