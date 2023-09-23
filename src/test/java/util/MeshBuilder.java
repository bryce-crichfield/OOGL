package util;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class MeshBuilder {
    List<Float> floats;

    public MeshBuilder() {
        this.floats = new ArrayList<>();
    }

    public MeshBuilder position(float x, float y) {
        floats.add(x);
        floats.add(y);
        return this;
    }

    public MeshBuilder texture(float x, float y) {
        floats.add(x);
        floats.add(y);
        return this;
    }

    public FloatBuffer build() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(floats.size());
        for (float vertex : floats) {
            buffer.put(vertex);
        }

        buffer.rewind();

        return buffer;
    }
}
