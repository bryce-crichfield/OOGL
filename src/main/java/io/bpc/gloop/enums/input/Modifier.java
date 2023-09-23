package io.bpc.gloop.enums.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public enum Modifier {
    SHIFT(GLFW.GLFW_MOD_SHIFT),
    CONTROL(GLFW.GLFW_MOD_CONTROL),
    ALT(GLFW.GLFW_MOD_ALT),
    SUPER(GLFW.GLFW_MOD_SUPER),
    CAPS_LOCK(GLFW.GLFW_MOD_CAPS_LOCK),
    NUM_LOCK(GLFW.GLFW_MOD_NUM_LOCK);

    @Getter
    private final int glId;

    public static List<Modifier> fromMask(int mask) {
        // parse out mods enums from the provided bitmask
        List<Modifier> mods = List.of(SHIFT, CONTROL, ALT, SUPER, CAPS_LOCK, NUM_LOCK);
        List<Modifier> result = new ArrayList<>();

        for (Modifier mod : mods) {
            if ((mask & mod.getGlId()) != 0) {
                result.add(mod);
            }
        }

        return result;
    }
}
