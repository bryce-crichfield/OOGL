package io.bpc.three;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BoxGeometry implements IGeometry {
    private final Cache<FloatBuffer> vertices = new Cache<>(() -> new GeometryBuilder()
            .position(-1, -1, -1)
            .position(1, -1, -1)
            .position(1, 1, -1)
            .position(-1, 1, -1)
            .position(-1, -1, 1)
            .position(1, -1, 1)
            .position(1, 1, 1)
            .position(-1, 1, 1)
            .normal(0, 0, -1)
            .normal(0, 0, 1)
            .normal(0, -1, 0)
            .normal(0, 1, 0)
            .normal(-1, 0, 0)
            .normal(1, 0, 0)
            .normal(0, 0, -1)
            .normal(0, 0, 1)
            .uv(0, 0)
            .uv(1, 0)
            .uv(1, 1)
            .uv(0, 1)
            .uv(0, 0)
            .uv(1, 0)
            .uv(1, 1)
            .uv(0, 1)
            .buildVertices());

    private final Cache<IntBuffer> indices = new Cache<>(() -> new GeometryBuilder()
            .face(0, 2, 1)
            .face(0, 3, 2)
            .face(4, 5, 6)
            .face(4, 6, 7)
            .face(4, 1, 5)
            .face(4, 0, 1)
            .face(3, 6, 2)
            .face(3, 7, 6)
            .face(1, 6, 5)
            .face(1, 2, 6)
            .face(4, 7, 3)
            .face(4, 3, 0)
            .buildIndices());

    @Override
    public FloatBuffer getVertices() {
        return vertices.get();
    }

    @Override
    public IntBuffer getIndices() {
        return indices.get();
    }
}
