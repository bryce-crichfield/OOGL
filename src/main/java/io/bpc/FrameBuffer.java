package io.bpc;

import io.bpc.enums.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL30.*;

@RequiredArgsConstructor(access = PRIVATE)
public class FrameBuffer implements AutoCloseable {
    @Getter
    private final int glId;
    private int restoreId;
    @Getter
    private final Texture textureColor;

    @Getter
    private final Optional<Texture> textureDepth;

    private void bind() {
        restoreId = glGetInteger(GL_FRAMEBUFFER_BINDING);
        glBindFramebuffer(GL_FRAMEBUFFER, this.glId);
    }

    private void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, restoreId);
    }

    @Override
    public void close() throws Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    public static FrameBuffer create(int width, int heigh, InternalFormat color) throws Exception {
        return create(width, heigh, color, Optional.empty());
    }

    public static FrameBuffer create(int width, int height, InternalFormat color, Optional<InternalFormat> depth) throws Exception {
        if (color != InternalFormat.RGB8) {
            throw new Exception("Only RGB8 is supported for color");
        }

        if (depth.isPresent() && depth.get() != InternalFormat.DEPTH_COMPONENT16) {
            throw new Exception("Only DEPTH_COMPONENT16 is supported for depth");
        }

        int restoreId = glGetInteger(GL_FRAMEBUFFER_BINDING);
        int glId = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, glId);

        Texture textureColor = Texture.create();
        Image2D imageColor = new Image2D(width, height, color, TextureFormat.RGBA, null, DataType.UNSIGNED_BYTE);
        textureColor.setImage2D(imageColor);
        textureColor.setFilter(TextureFilter.LINEAR, TextureFilter.LINEAR);
        glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureColor.getGlId(), 0);


        Optional<Texture> textureDepthOpt = Optional.empty();
        if (depth.isPresent()) {
            Texture textureDepth = Texture.create();
            Image2D imageDepth = new Image2D(width, height, depth.get(), TextureFormat.DEPTH, null, DataType.UNSIGNED_BYTE);
            textureDepth.setImage2D(imageDepth);
            textureDepth.setWrapping(TextureWrapping.CLAMP_TO_EDGE, TextureWrapping.CLAMP_TO_EDGE);
            textureDepth.setFilter(TextureFilter.NEAREST, TextureFilter.NEAREST);
            glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, textureDepth.getGlId(), 0);
            textureDepthOpt = Optional.of(textureDepth);
        }

        int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);
        if (status != GL_FRAMEBUFFER_COMPLETE) {
            throw new Exception("Failed to create framebuffer");
        }

        glBindFramebuffer(GL_FRAMEBUFFER, restoreId);
        return new FrameBuffer(glId, textureColor, textureDepthOpt);
    }
}
