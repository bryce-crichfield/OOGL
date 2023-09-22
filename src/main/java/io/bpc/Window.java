package io.bpc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.Vector4f;
import org.joml.Vector4i;

import static lombok.AccessLevel.PRIVATE;
import static org.lwjgl.glfw.GLFW.*;

@RequiredArgsConstructor(access = PRIVATE)
public class Window implements AutoCloseable {
    @Getter
    private final long glfwId;

    public boolean isOpen() {
        boolean shouldClose = glfwWindowShouldClose(this.glfwId);
        return !shouldClose;
    }

    public void flush() {
        glfwSwapBuffers(this.glfwId);
        glfwPollEvents();
    }

    @Override
    public void close() throws Exception {
        glfwDestroyWindow(this.glfwId);
        glfwTerminate();
    }

    public Vector4i getViewport() {
        // get the window size
        int[] width = new int[1];
        int[] height = new int[1];
        glfwGetWindowSize(this.glfwId, width, height);
        return new Vector4i(0, 0, width[0], height[0]);
    }

    public static Window create(int width, int height, String title) throws Exception {
        if (!glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }

        long glfwId = glfwCreateWindow(width, height, title, 0, 0);
        if (glfwId == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        return new Window(glfwId);
    }
}
