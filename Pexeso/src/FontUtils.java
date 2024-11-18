//|----------FONT CLASS----------|
//- no pak tady mám class, přímo na font, aby jste i vy měl ten font můj font! :D

import java.awt.*;
import java.io.*;

public class FontUtils {
    public static Font loadCustomFont(String fontName, int style, float size) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    FontUtils.class.getResourceAsStream("/fonts/" + fontName + ".ttf"));
            return customFont.deriveFont(style, size);
        } catch (IOException | FontFormatException e) {
            System.err.println("Error!: " + e.getMessage());
            return new Font(Font.SANS_SERIF, style, (int)size);
        }
    }
}