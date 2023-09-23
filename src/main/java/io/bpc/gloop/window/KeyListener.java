package io.bpc.gloop.window;

import io.bpc.gloop.enums.input.Action;
import io.bpc.gloop.enums.input.Key;
import io.bpc.gloop.enums.input.Modifier;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.List;

public interface KeyListener extends GLFWKeyCallbackI {
    @Override
    default void invoke(long window, int key, int scancode, int action, int mods) {
        Key k = Key.fromGlId(key);
        Action a = Action.fromGlId(action);
        List<Modifier> m = Modifier.fromMask(mods);

        this.onKey(k, a, m);
    }

    void onKey(Key key, Action action, List<Modifier> modifiers);
}
