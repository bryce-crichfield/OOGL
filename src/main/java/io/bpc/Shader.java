package io.bpc;

import io.bpc.enums.ShaderMode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.lwjgl.opengl.GL20.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Shader implements AutoCloseable {
    @Getter
    private final int glId;

    @Override
    public void close() throws Exception {
        glDeleteShader(this.glId);
    }

    public static Shader fromFile(ShaderMode mode, String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new Exception("File " + path + " does not exist!");
        }

        InputStream stream = new FileInputStream(file);
        if (stream == null) {
            throw new Exception("Failed to open stream for " + path + "!");
        }

        BufferedInputStream buffer = new BufferedInputStream(stream);
        StringBuilder builder = new StringBuilder();
        while (buffer.available() > 0) {
            builder.append((char) buffer.read());
        }

        return fromSource(mode, builder.toString());
    }

    public static Shader fromSource(ShaderMode mode, String source) throws Exception {
        Shader shader = create(mode);
        shader.compile(source);
        return shader;
    }

    private static Shader create(ShaderMode mode) throws Exception {
        int id = glCreateShader(mode.getGlType());

        if (id == 0) {
            throw new Exception("Failed to create shader!");
        }

        return new Shader(id);
    }

    private void compile(String source) throws Exception {
        glShaderSource(this.glId, source);

        glCompileShader(this.glId);

        if (glGetShaderi(this.glId, GL_COMPILE_STATUS) == GL_FALSE) {
            String reason = glGetShaderInfoLog(this.glId);
            throw new Exception("Failed to compile shader: " + reason);
        }
    }
}
