import io.bpc.gloop.*;
import io.bpc.gloop.enums.*;
import io.bpc.gloop.enums.input.Action;
import io.bpc.gloop.window.CursorListener;
import io.bpc.gloop.window.KeyListener;
import io.bpc.gloop.window.Window;
import io.bpc.three.BoxGeometry;
import io.bpc.three.IGeometry;
import io.bpc.three.Model;
import lombok.Cleanup;
import lombok.Data;
import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import util.AttributeListBuilder;

import java.awt.*;
import java.util.List;

public class Test3dRender {
    public static void main(String[] args) throws Exception {
        Window window = Window.create(800, 800, "");
        Context context = Context.create(window);

        @Cleanup Program program = Test.createProgram("three/vert3d", "three/frag3d");
        @Cleanup Texture texture = Test.createDebugTexture(512, 8, 8, Color.RED, Color.GREEN);

        List<Attribute> attributes = new AttributeListBuilder().define(0, DataType.FLOAT, 3).define(1, DataType.FLOAT,
                                                                                                    3
        ).define(2, DataType.FLOAT, 2).build();


        @Data
        class Camera {
            @Getter
            private final Matrix4f viewMatrix = new Matrix4f();
            private double lastX = 0;
            private double lastY = 0;

            private Vector3f position = new Vector3f();
            private Vector3f rotation = new Vector3f();

            private boolean upPressed = false;
            private boolean downPressed = false;
            private boolean leftPressed = false;
            private boolean rightPressed = false;

            void recalculateViewMatrix() {
                viewMatrix.identity();
                viewMatrix.rotateX((float) Math.toRadians(rotation.x));
                viewMatrix.rotateY((float) Math.toRadians(rotation.y));
                viewMatrix.rotateZ((float) Math.toRadians(rotation.z));
                viewMatrix.translate(-position.x, -position.y, -position.z);
            }



            public KeyListener keyListener = ((key, action, modifiers) -> {
                float speed = 0.1f;

                if (action == Action.PRESS) {
                    switch (key) {
                        case W -> upPressed = true;
                        case S -> downPressed = true;
                        case A -> leftPressed = true;
                        case D -> rightPressed = true;
                    }
                }

                if (action == Action.RELEASE) {
                    switch (key) {
                        case W -> upPressed = false;
                        case S -> downPressed = false;
                        case A -> leftPressed = false;
                        case D -> rightPressed = false;
                    }
                }

            });

            public CursorListener cursorListener = ((x, y) -> {
                double deltaX = x - lastX;
                double deltaY = y - lastY;
                lastX = x;
                lastY = y;

                float sensitivity = 0.05f;

                rotation.y += deltaX * sensitivity;
                rotation.x += deltaY * sensitivity;

                rotation.x = Math.max(-90, Math.min(90, rotation.x));

                recalculateViewMatrix();
            });

            void update() {

                float flippedY = -rotation.y;
                Vector3f forward = new Vector3f(0, 0, -1).rotateY((float) Math.toRadians(flippedY)).normalize();
                Vector3f backward = new Vector3f(0, 0, 1).rotateY((float) Math.toRadians(flippedY)).normalize();
                Vector3f right = new Vector3f(1, 0, 0).rotateY((float) Math.toRadians(flippedY)).normalize();
                Vector3f left = new Vector3f(-1, 0, 0).rotateY((float) Math.toRadians(flippedY)).normalize();

                if (upPressed) {
                    position.add(forward.mul(0.1f));
                }

                if (downPressed) {
                    position.add(backward.mul(0.1f));
                }

                if (leftPressed) {
                    position.add(left.mul(0.1f));
                }

                if (rightPressed) {
                    position.add(right.mul(0.1f));
                }

                recalculateViewMatrix();
            }
        }

        Camera camera = new Camera();
        camera.setPosition(new Vector3f(0, 0, 15));
        camera.recalculateViewMatrix();

        window.setKeyListener(camera.getKeyListener());
        window.setCursorListener(camera.getCursorListener());
        window.setCursorVisibility(false);
        window.setCursorMode(true);

        IGeometry box = new BoxGeometry();
        Model model = new Model(box);
        model.setPosition(new Vector3f(0, 0, 10));


        Model model2 = new Model(box);
        model2.setPosition(new Vector3f(4, 0, 5));

        VertexArray vao = box.toVao(attributes);

        context.enable(Capability.DEPTH_TEST);

        FrameBuffer frameBuffer = FrameBuffer.create(800, 800, InternalFormat.RGB8);
        context.bindFrameBuffer(frameBuffer);


        while (window.isOpen()) {
            camera.update();
            context.setClearColor(0, 0, 0.2f, 1);
            context.clear(BufferBit.COLOR, BufferBit.DEPTH);
            context.bindProgram(program);
            context.bindTexture(texture);

            Matrix4f view = camera.getViewMatrix();
            Matrix4f projection = new Matrix4f().perspective((float) Math.toRadians(45), 1, 0.1f, 100f);

            Uniform uModel = program.getUniform("uModel");
            Uniform uView = program.getUniform("uView");
            Uniform uProjection = program.getUniform("uProjection");

            program.setUniform(uModel, model.getTransformationMatrix());
            program.setUniform(uView, view);
            program.setUniform(uProjection, projection);

            context.drawElements(vao, Primitive.TRIANGLES, 0, 36);


            program.setUniform(uModel, model2.getTransformationMatrix());
            context.drawElements(vao, Primitive.TRIANGLES, 0, 36);

            window.flush();
        }
    }
}
