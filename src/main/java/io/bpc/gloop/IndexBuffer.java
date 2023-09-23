package io.bpc.gloop;

import io.bpc.gloop.enums.BufferUsage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static lombok.AccessLevel.PRIVATE;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.GL_MAP_READ_BIT;
import static org.lwjgl.opengl.GL30.glMapBufferRange;

@RequiredArgsConstructor(access = PRIVATE)
public class IndexBuffer implements AutoCloseable {
    @Getter
    private final int glId;

    public static IndexBuffer create() throws Exception {
        int id = glGenBuffers();

        if (id == 0) {
            throw new RuntimeException("Could not create index buffer");
        }

        return new IndexBuffer(id);
    }

    public void setData(IntBuffer data, BufferUsage usage) {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.glId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.getGlUsage());
    }

    public void setSubData(IntBuffer data, int offset) {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.glId);
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, data);
    }

    public IntBuffer getSubData(int offset, int length) {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.glId);
        ByteBuffer buffer = glMapBufferRange(GL_ELEMENT_ARRAY_BUFFER, offset, length, GL_MAP_READ_BIT, null);
        assert buffer != null;
        return buffer.asIntBuffer();
    }

    @Override
    public void close() throws Exception {
        glDeleteBuffers(this.glId);
    }
}
