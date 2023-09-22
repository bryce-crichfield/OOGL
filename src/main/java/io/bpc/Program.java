package io.bpc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import static lombok.AccessLevel.PRIVATE;
import static org.lwjgl.opengl.GL20.*;

@RequiredArgsConstructor(access = PRIVATE)
public class Program implements AutoCloseable {
    @Getter
    private final int glId;

    private void attachShader(Shader shader) {
        glAttachShader(this.glId, shader.getGlId());
    }

    private boolean link() {
        glLinkProgram(this.glId);

        if (glGetProgrami(this.glId, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Failed to link program!");
            System.err.println(glGetProgramInfoLog(this.glId));
            return false;
        }

        return true;
    }

    public int getAttributeLocation(String name) throws Exception {
        int location = glGetAttribLocation(this.glId, name);

        if (location == -1) {
            throw new RuntimeException("Could not find attribute " + name + "!");
        }

        return location;
    }

    public Uniform getUniform(String name) throws Exception {
        int location = glGetUniformLocation(this.glId, name);

        if (location == -1) {
            throw new RuntimeException("Could not find uniform " + name + "!");
        }

        return new Uniform(location);
    }

    public void setUniform(Uniform uniform, int value) {
        glUniform1i(uniform.getLocation(), value);
    }

    public void setUniform(Uniform uniform, float value) {
        glUniform1f(uniform.getLocation(), value);
    }

    public void setUniform(Uniform uniform, float x, float y) {
        glUniform2f(uniform.getLocation(), x, y);
    }

    public void setUniform(Uniform uniform, float x, float y, float z) {
        glUniform3f(uniform.getLocation(), x, y, z);
    }

    public void setUniform(Uniform uniform, float x, float y, float z, float w) {
        glUniform4f(uniform.getLocation(), x, y, z, w);
    }

    public void setUniform(Uniform uniform, Matrix3f value) {
        glUniformMatrix3fv(uniform.getLocation(), false, value.get(new float[9]));
    }

    public void setUniform(Uniform uniform, Matrix4f value) {
        glUniformMatrix4fv(uniform.getLocation(), false, value.get(new float[16]));
    }

    @Override
    public void close() throws Exception {
        glDeleteProgram(this.glId);
    }

    private static Program create() throws Exception {
        int id = glCreateProgram();

        if (id == 0) {
            throw new Exception("Failed to create program!");
        }

        return new Program(id);
    }

    public static Program create(Shader vertex, Shader fragment) throws Exception {
        Program program = create();
        program.attachShader(vertex);
        program.attachShader(fragment);
        program.link();
        return program;
    }
}
