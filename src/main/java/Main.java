import io.bpc.Window;
import io.bpc.*;
import io.bpc.enums.*;
import util.AttributeListBuilder;
import util.IntBufferBuilder;
import util.MeshBuilder;
import lombok.Cleanup;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        @Cleanup Window window = Window.create(1400, 1400, "Hello World!");
        @Cleanup Context context = Context.create(window);
        @Cleanup Program passthrough = createProgram("vertex", "fragment");
        @Cleanup Program postprocessing = createProgram("vertex", "postprocessing");
        @Cleanup VertexArray triangle = createTestTriangle();
        @Cleanup VertexArray quad = createTestQuad();
        @Cleanup FrameBuffer offscreenBuffer = FrameBuffer.create(2800, 2800, InternalFormat.RGB8);
        @Cleanup Texture texture = createTestTexture();

        Stage stage1 = new Stage(offscreenBuffer, passthrough);
        Stage stage2 = new Stage(null, postprocessing);

        while (window.isOpen()) {
            stage1.render(context, texture,
                () -> context.drawElements(quad, Primitive.TRIANGLES, 0, 6)
            );
            stage2.render(context, offscreenBuffer.getTextureColor(),
                () -> context.drawElements(quad, Primitive.TRIANGLES, 0, 6)
            );
            window.flush();
        }
    }

    private static VertexArray createTestTriangle() throws Exception {
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
        FloatBuffer vertices = new MeshBuilder()
                .position(-1, -1)
                .position(1, -1)
                .position(1, 1)
                .texture(0f, 0)
                .texture(1, 0)
                .texture(1, 1)
                .build();
        vertexBuffer.setData(vertices, BufferUsage.STATIC_DRAW);

        // MAKE THE INDICES
        IntBuffer indices = new IntBufferBuilder()
                .put(0)
                .put(1)
                .put(2)
                .build();
        indexBuffer.setData(indices, BufferUsage.STATIC_DRAW);

        return vertexArray;
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
        FloatBuffer vertices = new MeshBuilder()
                .position(-1, -1)
                .texture(0f, 0)
                .position(1, -1)
                .texture(1, 0)
                .position(1, 1)
                .texture(1, 1)
                .position(-1, 1)
                .texture(0, 1)
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

    private static Program createProgram(String vertexShaderName, String fragmentShaderName) throws Exception {
        String vertexPath = "src/main/resources/" + vertexShaderName + ".glsl";
        String fragmentPath = "src/main/resources/" + fragmentShaderName + ".glsl";
        @Cleanup Shader vertexShader = Shader.fromFile(ShaderMode.VERTEX, vertexPath);
        @Cleanup Shader fragmentShader = Shader.fromFile(ShaderMode.FRAGMENT, fragmentPath);
        return Program.create(vertexShader, fragmentShader);
    }

    private static Texture createTestTexture() throws Exception {
        int size = 64;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        // render an image with four colors red, green, blue, and gray
        int half = (int) (size / 2f);
        graphics.setColor(Color.RED);
        graphics.fillRect(0, 0, half, half);
        graphics.setColor(Color.GREEN);
        graphics.fillRect(half, 0, half, half);
        graphics.setColor(Color.BLUE);
        graphics.fillRect(0, half, half, half);
        graphics.setColor(Color.GRAY);
        graphics.fillRect(half, half, half, half);

        Texture texture = Texture.create();
        Image2D image2d = Image2D.fromBufferedImage(image);
        texture.setImage2D(image2d);
        texture.setWrapping(TextureWrapping.REPEAT, TextureWrapping.REPEAT);
        texture.setFilter(TextureFilter.LINEAR, TextureFilter.LINEAR);
        texture.generateMipmaps();

        return texture;
    }

    @Data
    public static class Stage {
        private final FrameBuffer frameBuffer;
        private final Program program;
        public void render(Context context, Texture texture, Runnable draw) {
            if (frameBuffer == null) {
                context.bindFrameBuffer();
            } else {
                context.bindFrameBuffer(frameBuffer);
            }
            context.bindProgram(program);
            context.setClearColor(1f, 1f, 1f, 1f);
            context.clear(BufferBit.COLOR);
            context.bindTexture(texture);
            draw.run();
        }

    }
}