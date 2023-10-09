package io.bpc.three;

import lombok.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@Data
@RequiredArgsConstructor
public class Model {
    @NonNull
    IGeometry geometry;

    @Getter
    @Setter
    private Vector3f position = new Vector3f(0, 0, 0);

    @Getter
    @Setter
    private Vector3f rotation = new Vector3f(0, 0, 0);

    @Getter
    @Setter
    private Vector3f scale = new Vector3f(1, 1, 1);

    public Matrix4f getTransformationMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(position);
        matrix.rotateX((float) Math.toRadians(rotation.x));
        matrix.rotateY((float) Math.toRadians(rotation.y));
        matrix.rotateZ((float) Math.toRadians(rotation.z));
        matrix.scale(scale);
        return matrix;
    }
}
