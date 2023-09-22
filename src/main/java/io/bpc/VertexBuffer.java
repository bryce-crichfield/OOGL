package io.bpc;


import io.bpc.enums.BufferUsage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.ByteBuffer;

import static lombok.AccessLevel.PRIVATE;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.GL_MAP_READ_BIT;
import static org.lwjgl.opengl.GL30.glMapBufferRange;

@RequiredArgsConstructor(access = PRIVATE)
public class VertexBuffer implements AutoCloseable {
    @Getter
    private final int glId;

    public void setData(ByteBuffer data, BufferUsage usage) {
        glBindBuffer(GL_ARRAY_BUFFER, this.glId);
        glBufferData(GL_ARRAY_BUFFER, data, usage.getGlUsage());
    }

    public void setSubData(ByteBuffer data, int offset) {
        glBindBuffer(GL_ARRAY_BUFFER, this.glId);
        glBufferSubData(GL_ARRAY_BUFFER, offset, data);
    }

    public ByteBuffer getSubData(int offset, int length) {
        glBindBuffer(GL_ARRAY_BUFFER, this.glId);
        return glMapBufferRange(GL_ARRAY_BUFFER, offset, length, GL_MAP_READ_BIT, null);
    }

    @Override
    public void close() throws Exception {
        glDeleteBuffers(this.glId);
    }

    public static VertexBuffer create() throws Exception {
        int id = glGenBuffers();

        if (id == 0) {
            throw new RuntimeException("Could not create vertex buffer");
        }

        return new VertexBuffer(id);
    }
}
