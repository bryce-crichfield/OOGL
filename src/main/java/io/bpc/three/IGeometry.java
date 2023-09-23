package io.bpc.three;

import io.bpc.gloop.Attribute;
import io.bpc.gloop.IndexBuffer;
import io.bpc.gloop.VertexArray;
import io.bpc.gloop.VertexBuffer;
import io.bpc.gloop.enums.BufferUsage;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

public interface IGeometry {
    FloatBuffer getVertices();

    IntBuffer getIndices();

    default VertexArray toVao(List<Attribute> attributes) throws Exception {
        VertexArray vao = VertexArray.create();
        VertexBuffer vbo = VertexBuffer.create();
        IndexBuffer ibo = IndexBuffer.create();

        for (Attribute attribute : attributes) {
            vao.bindAttribute(attribute, vbo);
        }

        vbo.setData(this.getVertices(), BufferUsage.STATIC_DRAW);
        ibo.setData(this.getIndices(), BufferUsage.STATIC_DRAW);

        return vao;
    }
}
