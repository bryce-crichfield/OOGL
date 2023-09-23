import io.bpc.gloop.window.Window;
import lombok.Cleanup;

public class TestWindowEvents {
    public static void main(String[] args) throws Exception {
        @Cleanup Window window = Window.create(800, 600, "Test Window Events");

        window.setCursorListener((x, y) -> {
            System.out.println("Cursor: " + x + ", " + y);
        });

        window.setKeyListener((key, action, modifiers) -> {
            System.out.println("Key: " + key + ", Action: " + action + ", Modifiers: " + modifiers);
        });

        window.setMouseButtonListener((mouse, action, modifiers) -> {
            System.out.println("Mouse: " + mouse + ", Action: " + action + ", Modifiers: " + modifiers);
        });

        while (window.isOpen()) {
            window.flush();
        }
    }
}
