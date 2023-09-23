import io.bpc.gloop.Image2D;
import io.bpc.gloop.Program;
import io.bpc.gloop.Shader;
import io.bpc.gloop.Texture;
import io.bpc.gloop.enums.ShaderMode;
import io.bpc.gloop.enums.TextureFilter;
import io.bpc.gloop.enums.TextureWrapping;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Test {

    public static Program createProgram(String vert, String frag) throws Exception {
        Shader vertex = Shader.fromFile(ShaderMode.VERTEX, "src/main/resources/" + vert + ".glsl");
        Shader fragment = Shader.fromFile(ShaderMode.FRAGMENT, "src/main/resources/" + frag + ".glsl");
        return Program.create(vertex, fragment);
    }

    public static Texture createDebugTexture(int size, int gridX, int gridY, Color light, Color dark) throws Exception {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        // render an image with four colors red, green, blue, and gray
        int gridWidth = (int) (size / gridX);
        int gridHeight = (int) (size / gridY);
        for (int x = 0; x < gridX; x++) {
            Color color = x % 2 == 0 ? light : dark;
            for (int y = 0; y < gridY; y++) {
                graphics.setColor(color);
                graphics.fillRect(x * gridWidth, y * gridHeight, gridWidth, gridHeight);
                color = color == light ? dark : light;
            }
        }


        Texture texture = Texture.create();
        Image2D image2d = Image2D.fromBufferedImage(image);
        texture.setImage2D(image2d);
        texture.setWrapping(TextureWrapping.REPEAT, TextureWrapping.REPEAT);
        texture.setFilter(TextureFilter.LINEAR, TextureFilter.LINEAR);
        texture.generateMipmaps();

        return texture;
    }
}
