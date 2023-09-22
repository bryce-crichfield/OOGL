package io.bpc;

import io.bpc.enums.*;
import lombok.RequiredArgsConstructor;
import org.joml.Vector4f;
import org.joml.Vector4i;
import org.lwjgl.opengl.GL;

import static lombok.AccessLevel.PRIVATE;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

@RequiredArgsConstructor(access = PRIVATE)
public class Context implements AutoCloseable {
    private final Vector4i defaultViewport;

    void enable(Capability capability) {
        glEnable(capability.getGlId());
    }

    void disable(Capability capability) {
        glDisable(capability.getGlId());
    }

    public void clear(BufferBit... bufferBits) {
        int mask = 0;
        for (BufferBit bufferBit : bufferBits) {
            mask |= bufferBit.getGlId();
        }

        glClear(mask);
    }

    public void setClearColor(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
    }

    void setDepthMaskEnable(boolean enable) {
        glDepthMask(enable);
    }

    void setStencilTestFunction(StencilTestFunction function, int reference, int mask) {
        glStencilFunc(function.getGlId(), reference, mask);
    }

    void setStencilAction(StencilAction sfail, StencilAction dpfail, StencilAction dppass) {
        glStencilOp(sfail.getGlId(), dpfail.getGlId(), dppass.getGlId());
    }

    void setStencilMask(int mask) {
        glStencilMask(mask);
    }

    public void bindProgram(Program program) {
        glUseProgram(program.getGlId());
    }

    public void bindTexture(Texture texture) {
        glBindTexture(GL_TEXTURE_2D, texture.getGlId());
    }

    public void bindFrameBuffer(FrameBuffer frameBuffer) {
        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer.getGlId());
        int obj = glGetFramebufferAttachmentParameteri(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0,
                                                       GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME
        );
        int[] texture = new int[1];
        glGetIntegerv(GL_TEXTURE_BINDING_2D, texture);
        glBindTexture(GL_TEXTURE_2D, obj);
        int[] width = new int[1];
        glGetTexLevelParameteriv(GL_TEXTURE_2D, 0, GL_TEXTURE_WIDTH, width);
        int[] height = new int[1];
        glGetTexLevelParameteriv(GL_TEXTURE_2D, 0, GL_TEXTURE_HEIGHT, height);
        glViewport(0, 0, width[0], height[0]);
    }

    public void bindFrameBuffer() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(defaultViewport.x, defaultViewport.y, defaultViewport.z, defaultViewport.w);
    }

    void beginTransformFeedback(Primitive primitive) {
        glBeginTransformFeedback(primitive.getGlId());
    }

    void endTransformFeedback() {
        glEndTransformFeedback();
    }

    public void drawArray(VertexArray vao, Primitive mode, int offset, int vertices) {
        glBindVertexArray(vao.getGlId());
        glDrawArrays(mode.getGlId(), offset, vertices);
    }

    public void drawElements(VertexArray vao, Primitive mode, int offset, int vertices) {
        glBindVertexArray(vao.getGlId());
        glDrawElements(mode.getGlId(), vertices, GL_UNSIGNED_INT, offset);
    }

    @Override
    public void close() throws Exception {

    }

    public static Context create(Window window) {
        glfwMakeContextCurrent(window.getGlfwId());
        GL.createCapabilities();
        Vector4i viewport = window.getViewport();
        return new Context(viewport);
    }
}
