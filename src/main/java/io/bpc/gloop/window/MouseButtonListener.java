package io.bpc.gloop.window;

import io.bpc.gloop.enums.input.Action;
import io.bpc.gloop.enums.input.Modifier;
import io.bpc.gloop.enums.input.Mouse;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import java.util.List;

public interface MouseButtonListener extends GLFWMouseButtonCallbackI {

    @Override
    default void invoke(long window, int button, int action, int mods) {
        Mouse mouse = Mouse.fromGlId(button);
        Action a = Action.fromGlId(action);
        List<Modifier> m = Modifier.fromMask(mods);

        this.onButton(mouse, a, m);
    }

    void onButton(Mouse mouse, Action action, List<Modifier> modifier);
}
