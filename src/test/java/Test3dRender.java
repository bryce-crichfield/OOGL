import io.bpc.gloop.*;
import io.bpc.gloop.window.Window;
import io.bpc.gloop.enums.DataType;
import io.bpc.gloop.enums.Primitive;
import io.bpc.three.BoxGeometry;
import io.bpc.three.IGeometry;
import lombok.Cleanup;
import org.joml.Matrix4f;
import util.AttributeListBuilder;

import java.awt.*;
import java.util.List;

public class Test3dRender {
    public static void main(String[] args) throws Exception {
        Window window = Window.create(800, 800, "");
        Context context = Context.create(window);

        @Cleanup Program program = Test.createProgram("three/vert3d", "three/frag3d");
        @Cleanup Texture texture = Test.createDebugTexture(512, 8, 8, Color.RED, Color.GREEN);

        List<Attribute> attributes = new AttributeListBuilder()
                .define(0, DataType.FLOAT, 3)
                .define(1, DataType.FLOAT, 3)
                .define(2, DataType.FLOAT, 2)
                .build();

        IGeometry box = new BoxGeometry(0, 0, 0);
        VertexArray vao = box.toVao(attributes);

        while (window.isOpen()) {
            context.setClearColor(0, 0, 0, 1);
            context.clear();
            context.bindProgram(program);
            context.bindTexture(texture);

            Matrix4f view = new Matrix4f().lookAt(0, 5, 5, 0, 0, 0, 0, 1, 0);
            Matrix4f projection = new Matrix4f().perspective((float) Math.toRadians(45), 1, 0.1f, 100f);

            Uniform uView = program.getUniform("uView");
            Uniform uProjection = program.getUniform("uProjection");

            program.setUniform(uView, view);
            program.setUniform(uProjection, projection);

            context.drawElements(vao, Primitive.TRIANGLES, 0, 6);
            window.flush();
        }
    }
}
