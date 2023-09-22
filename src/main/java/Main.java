import io.bpc.*;
import io.bpc.Attribute;
import io.bpc.Image2D;
import io.bpc.Texture;
import io.bpc.enums.DataType;
import io.bpc.enums.*;
import io.bpc.Program;
import io.bpc.Shader;
import io.bpc.Window;
import lombok.Cleanup;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws Exception {
        @Cleanup Window window = Window.create(1400, 1400, "Hello World!");
        @Cleanup Context context = Context.create(window);
        @Cleanup Program program = createProgram();

        @Cleanup VertexArray quad = createTestQuad();
        @Cleanup FrameBuffer offscreenBuffer = FrameBuffer.create(10, 10, InternalFormat.RGB8);

        @Cleanup Texture texture = createTestTexture();

        context.setClearColor(1f, 1f, 1f, 1f);
        while (window.isOpen()) {
            context.clear(BufferBit.COLOR);
            context.bindProgram(program);
            context.bindTexture(texture);
            context.bindFrameBuffer(offscreenBuffer);
            context.drawElements(quad, Primitive.TRIANGLES, 0, 6);

            Texture textureColor = offscreenBuffer.getTextureColor();
            context.bindTexture(textureColor);
            context.bindFrameBuffer();
            context.drawElements(quad, Primitive.TRIANGLES, 0, 6);

            window.flush();
        }
    }

    private static VertexArray createTestQuad() throws Exception {
        // CREATE THE VERTEX ARRAY, VERTEX BUFFER, AND INDEX BUFFER
        VertexArray vertexArray = VertexArray.create();
        VertexBuffer vertexBuffer = VertexBuffer.create();
        IndexBuffer indexBuffer = IndexBuffer.create();

        // DEFINE AND BIND THE ATTRIBUTES
        // 0: position (vec2)
        // 1: texture (vec2)
        List<Attribute> attributes = new AttributeListBuilder()
                .define(0, DataType.FLOAT, 2)
                .define(1, DataType.FLOAT, 2)
                .build();

        for (Attribute attribute : attributes) {
            vertexArray.bindAttribute(attribute, vertexBuffer);
        }


        // MAKE THE VERTICES
        ByteBuffer vertices = new MeshBuilder()
                .position(-0.5f, -0.5f)
                .position(0.5f, -0.5f)
                .position(0.5f, 0.5f)
                .position(-0.5f, 0.5f)
                .texture(0f, 0f)
                .texture(1f, 0f)
                .texture(1f, 1f)
                .texture(0f, 1f)
                .build();
        vertexBuffer.setData(vertices, BufferUsage.STATIC_DRAW);

        // MAKE THE INDICES
        IntBuffer indices = new IntBufferBuilder()
                .put(0)
                .put(1)
                .put(2)
                .put(3)
                .put(0)
                .put(2)
                .build();
        indexBuffer.setData(indices, BufferUsage.STATIC_DRAW);

        return vertexArray;
    }

    private static Program createProgram() throws Exception {
        @Cleanup Shader vertexShader = Shader.fromFile(ShaderMode.VERTEX, "src/main/resources/vertex.glsl");
        @Cleanup Shader fragmentShader = Shader.fromFile(ShaderMode.FRAGMENT, "src/main/resources/fragment.glsl");
        return Program.create(vertexShader, fragmentShader);
    }

    private static Texture createTestTexture() throws Exception {
        var image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        var graphics = image.getGraphics();
        for (int x = 0; x < 100; x += 10) {
            for (int y = 0; y < 100; y += 10) {
                if ((x / 10 + y / 10) % 2 == 0) {
                    graphics.setColor(Color.BLACK);
                } else {
                    graphics.setColor(Color.GRAY);
                }

                graphics.fillRect(x, y, 10, 10);
            }
        }

        graphics.dispose();

        var texture = Texture.create();
        var image2d = Image2D.fromBufferedImage(image);
        texture.setImage2D(image2d);
        texture.setWrapping(TextureWrapping.REPEAT, TextureWrapping.REPEAT);
        texture.setFilter(TextureFilter.NEAREST, TextureFilter.NEAREST);
        texture.generateMipmaps();

        return texture;
    }
}