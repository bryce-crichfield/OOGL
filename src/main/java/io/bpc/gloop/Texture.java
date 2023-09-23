package io.bpc.gloop;

import io.bpc.gloop.enums.TextureFilter;
import io.bpc.gloop.enums.TextureWrapping;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.ByteBuffer;

import static lombok.AccessLevel.PRIVATE;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.opengl.GL30.glTexParameterIi;

@RequiredArgsConstructor(access = PRIVATE)
public class Texture implements AutoCloseable {
    @Getter
    private final int glId;

    private int restoreId;

    public static Texture create() throws Exception {
        int id = glGenTextures();

        if (id == 0) {
            throw new RuntimeException("Could not create texture");
        }

        return new Texture(id);
    }

    public void setImage2D(Image2D image) {
        bind();
        int internalFormat = image.getInternalFormat().getGlId();
        int format = image.getFormat().getGlId();
        int type = image.getDataType().getGlId();
        int width = image.getWidth();
        int height = image.getHeight();
        ByteBuffer data = image.getData();
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, type, data);
        unbind();
    }

    private void bind() {
        restoreId = glGetInteger(GL_TEXTURE_BINDING_2D);
        glBindTexture(GL_TEXTURE_2D, glId);
    }

    private void unbind() {
        glBindTexture(GL_TEXTURE_2D, restoreId);
    }

    public void setWrapping(TextureWrapping s) {
        bind();
        glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, s.getGlId());
        unbind();
    }

    public void setWrapping(TextureWrapping s, TextureWrapping t) {
        bind();
        glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, s.getGlId());
        glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, t.getGlId());
        unbind();
    }

    public void setWrapping(TextureWrapping s, TextureWrapping t, TextureWrapping r) {
        bind();
        glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, s.getGlId());
        glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, t.getGlId());
        glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_WRAP_R, r.getGlId());
        unbind();
    }

    public void setFilter(TextureFilter min, TextureFilter mag) {
        bind();
        glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, min.getGlId());
        glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, mag.getGlId());
        unbind();
    }

    public void setBorderColor(float r, float g, float b, float a) {
        bind();
        int ir = (int) (r * 255);
        int ig = (int) (g * 255);
        int ib = (int) (b * 255);
        int ia = (int) (a * 255);
        int icolor = (ir << 24) | (ig << 16) | (ib << 8) | ia;
        glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, icolor);
        unbind();
    }

    public void generateMipmaps() {
        bind();
        glGenerateMipmap(GL_TEXTURE_2D);
        unbind();
    }

    @Override
    public void close() throws Exception {
        glDeleteTextures(glId);
    }
}
