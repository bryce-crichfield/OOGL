package io.bpc.gloop.enums.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum Key {
    A(GLFW.GLFW_KEY_A),
    B(GLFW.GLFW_KEY_B),
    C(GLFW.GLFW_KEY_C),
    D(GLFW.GLFW_KEY_D),
    E(GLFW.GLFW_KEY_E),
    F(GLFW.GLFW_KEY_F),
    G(GLFW.GLFW_KEY_G),
    H(GLFW.GLFW_KEY_H),
    I(GLFW.GLFW_KEY_I),
    J(GLFW.GLFW_KEY_J),
    K(GLFW.GLFW_KEY_K),
    L(GLFW.GLFW_KEY_L),
    M(GLFW.GLFW_KEY_M),
    N(GLFW.GLFW_KEY_N),
    O(GLFW.GLFW_KEY_O),
    P(GLFW.GLFW_KEY_P),
    Q(GLFW.GLFW_KEY_Q),
    R(GLFW.GLFW_KEY_R),
    S(GLFW.GLFW_KEY_S),
    T(GLFW.GLFW_KEY_T),
    U(GLFW.GLFW_KEY_U),
    V(GLFW.GLFW_KEY_V),
    W(GLFW.GLFW_KEY_W),
    X(GLFW.GLFW_KEY_X),
    Y(GLFW.GLFW_KEY_Y),
    Z(GLFW.GLFW_KEY_Z),
    SPACE(GLFW.GLFW_KEY_SPACE),
    ESCAPE(GLFW.GLFW_KEY_ESCAPE),
    ENTER(GLFW.GLFW_KEY_ENTER),
    UP(GLFW.GLFW_KEY_UP),
    DOWN(GLFW.GLFW_KEY_DOWN),
    LEFT(GLFW.GLFW_KEY_LEFT),
    RIGHT(GLFW.GLFW_KEY_RIGHT),
    LEFT_SHIFT(GLFW.GLFW_KEY_LEFT_SHIFT),
    RIGHT_SHIFT(GLFW.GLFW_KEY_RIGHT_SHIFT),
    LEFT_CONTROL(GLFW.GLFW_KEY_LEFT_CONTROL),
    RIGHT_CONTROL(GLFW.GLFW_KEY_RIGHT_CONTROL),
    LEFT_ALT(GLFW.GLFW_KEY_LEFT_ALT),
    RIGHT_ALT(GLFW.GLFW_KEY_RIGHT_ALT),
    LEFT_SUPER(GLFW.GLFW_KEY_LEFT_SUPER),
    RIGHT_SUPER(GLFW.GLFW_KEY_RIGHT_SUPER),
    TAB(GLFW.GLFW_KEY_TAB),
    BACKSPACE(GLFW.GLFW_KEY_BACKSPACE),
    INSERT(GLFW.GLFW_KEY_INSERT),
    DELETE(GLFW.GLFW_KEY_DELETE),
    PAGE_UP(GLFW.GLFW_KEY_PAGE_UP),
    PAGE_DOWN(GLFW.GLFW_KEY_PAGE_DOWN),
    HOME(GLFW.GLFW_KEY_HOME),
    END(GLFW.GLFW_KEY_END),
    CAPS_LOCK(GLFW.GLFW_KEY_CAPS_LOCK),
    SCROLL_LOCK(GLFW.GLFW_KEY_SCROLL_LOCK),
    NUM_LOCK(GLFW.GLFW_KEY_NUM_LOCK),
    PRINT_SCREEN(GLFW.GLFW_KEY_PRINT_SCREEN),
    PAUSE(GLFW.GLFW_KEY_PAUSE),
    F1(GLFW.GLFW_KEY_F1),
    F2(GLFW.GLFW_KEY_F2),
    F3(GLFW.GLFW_KEY_F3),
    F4(GLFW.GLFW_KEY_F4),
    F5(GLFW.GLFW_KEY_F5),
    F6(GLFW.GLFW_KEY_F6),
    F7(GLFW.GLFW_KEY_F7),
    F8(GLFW.GLFW_KEY_F8),
    F9(GLFW.GLFW_KEY_F9),
    F10(GLFW.GLFW_KEY_F10),
    F11(GLFW.GLFW_KEY_F11),
    F12(GLFW.GLFW_KEY_F12),
    F13(GLFW.GLFW_KEY_F13),
    F14(GLFW.GLFW_KEY_F14),
    F15(GLFW.GLFW_KEY_F15);

    private static Map<Integer, Key> keyMap = null;
    @Getter
    private final int glId;

    public static Key fromGlId(int glId) {
        if (keyMap == null) {
            keyMap = Arrays.stream(Key.values()).collect(Collectors.toMap(Key::getGlId, Function.identity()));
        }
        return keyMap.get(glId);
    }

}
