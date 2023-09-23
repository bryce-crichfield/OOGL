package util;

import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class IntBufferBuilder {
    private List<Integer> ints = new ArrayList<>();

    public IntBufferBuilder put(int value) {
        ints.add(value);
        return this;
    }

    public IntBuffer build() {
        IntBuffer buffer = BufferUtils.createIntBuffer(ints.size());
        for (int i : ints) {
            buffer.put(i);
        }
        buffer.flip();
        return buffer;
    }
}
