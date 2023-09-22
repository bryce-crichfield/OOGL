package util;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ByteBufferBuilder {
    List<Float> vertices = new ArrayList<>();

    public ByteBufferBuilder put(float value) {
        vertices.add(value);
        return this;
    }

    public ByteBufferBuilder put(float x, float y) {
        put(x);
        put(y);
        return this;
    }

    public ByteBufferBuilder put(float x, float y, float z) {
        put(x);
        put(y);
        put(z);
        return this;
    }

    public ByteBufferBuilder put(float x, float y, float z, float w) {
        put(x);
        put(y);
        put(z);
        put(w);
        return this;
    }

    public ByteBuffer build() {
        ByteBuffer buffer = BufferUtils.createByteBuffer(vertices.size() * Float.BYTES);
        for (float vertex : vertices) {
            buffer.putFloat(vertex);
        }
        buffer.rewind();
        return buffer;
    }
}
