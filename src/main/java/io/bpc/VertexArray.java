package io.bpc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static org.lwjgl.opengl.GL30.*;

@RequiredArgsConstructor(access = PRIVATE)
public class VertexArray implements AutoCloseable {
    @Getter
    private final int glId;

    public void bindAttribute(Attribute attribute, VertexBuffer buffer) {
        int location = attribute.getLocation();
        glBindVertexArray(this.glId);
        glBindBuffer(GL_ARRAY_BUFFER, buffer.getGlId());
        glEnableVertexAttribArray(location);
        glVertexAttribPointer(location, attribute.getCount(), attribute.getDataType().getGlId(), false, attribute.getStride(), attribute.getOffset());
    }

    public void bindElements(VertexBuffer buffer) {
        glBindVertexArray(this.glId);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffer.getGlId());
    }

    public void bindTransformFeedback(int index, VertexBuffer buffer) {
        glBindVertexArray(this.glId);
        glBindBufferBase(GL_TRANSFORM_FEEDBACK_BUFFER, index, buffer.getGlId());
    }

    @Override
    public void close() throws Exception {
        glDeleteVertexArrays(this.glId);
    }

    public static VertexArray create() throws Exception {
        int id = glGenVertexArrays();

        if (id == 0) {
            throw new RuntimeException("Could not create vertex array");
        }

        return new VertexArray(id);
    }
}
