package io.bpc.gloop;

import io.bpc.gloop.enums.DataType;
import io.bpc.gloop.enums.InternalFormat;
import io.bpc.gloop.enums.TextureFormat;
import lombok.Data;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

@Data
public class Image2D {
    private final int width;
    private final int height;
    private final InternalFormat internalFormat;
    private final TextureFormat format;
    private final ByteBuffer data;
    private final DataType dataType;

    public static Image2D fromBufferedImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        for (int i = 0; i < width * height; i++) {
            int x = i % width;
            int y = i / width;

            int pixel = image.getRGB(x, y);

            int red = (pixel >> 16) & 0xFF;
            int green = (pixel >> 8) & 0xFF;
            int blue = pixel & 0xFF;
            int alpha = (pixel >> 24) & 0xFF;

            buffer.put((byte) red);
            buffer.put((byte) green);
            buffer.put((byte) blue);
            buffer.put((byte) alpha);
        }

        buffer.rewind();

        return new Image2D(width, height, InternalFormat.RGBA8, TextureFormat.RGBA, buffer, DataType.UNSIGNED_BYTE);
    }
}
