package io.bpc;

import java.nio.ByteBuffer;

public class MeshBuilder {
    ByteBufferBuilder bufferBuilder;

    public MeshBuilder() {
        bufferBuilder = new ByteBufferBuilder();
    }

    public MeshBuilder position(float x, float y) {
        bufferBuilder.put(x);
        bufferBuilder.put(y);
        return this;
    }

    public MeshBuilder texture(float x, float y) {
        bufferBuilder.put(x);
        bufferBuilder.put(y);
        return this;
    }

    public ByteBuffer build() {
        return bufferBuilder.build();
    }
}
