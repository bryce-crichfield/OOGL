package io.bpc.three;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class GeometryBuilder {
    List<Float> positions = new ArrayList<>();
    List<Float> normals = new ArrayList<>();
    List<Float> uvs = new ArrayList<>();
    List<Integer> indices = new ArrayList<>();

    public GeometryBuilder position(float x, float y, float z) {
        positions.add(x);
        positions.add(y);
        positions.add(z);
        return this;
    }

    public GeometryBuilder normal(float x, float y, float z) {
        normals.add(x);
        normals.add(y);
        normals.add(z);
        return this;
    }

    public GeometryBuilder uv(float u, float v) {
        uvs.add(u);
        uvs.add(v);
        return this;
    }

    public GeometryBuilder face(int a, int b, int c) {
        indices.add(a);
        indices.add(b);
        indices.add(c);
        return this;
    }

    public FloatBuffer buildVertices() {
        int nVerts = positions.size() / 3;
        int nNorms = normals.size() / 3;
        int nUvs = uvs.size() / 2;
        if (nVerts != nNorms || nVerts != nUvs) {
            throw new RuntimeException("Positions, normals and uvs must have the same size");
        }

        FloatBuffer buffer = BufferUtils.createFloatBuffer(positions.size() + normals.size() + uvs.size());

        for (int i = 0; i < positions.size(); i += 3) {
            buffer.put(positions.get(i));
            buffer.put(positions.get(i + 1));
            buffer.put(positions.get(i + 2));
            buffer.put(normals.get(i));
            buffer.put(normals.get(i + 1));
            buffer.put(normals.get(i + 2));
            buffer.put(uvs.get(i / 3 * 2));
            buffer.put(uvs.get(i / 3 * 2 + 1));
        }

        buffer.flip();

        return buffer;
    }

    public IntBuffer buildIndices() {
        IntBuffer buffer = BufferUtils.createIntBuffer(indices.size());

        for (int i = 0; i < indices.size(); i++) {
            buffer.put(indices.get(i));
        }

        buffer.flip();

        return buffer;
    }

}
