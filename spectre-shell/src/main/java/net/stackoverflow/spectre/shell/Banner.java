package net.stackoverflow.spectre.shell;

import net.stackoverflow.spectre.common.util.ColorUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

/**
 * banner类
 *
 * @author wormhole
 */
public class Banner {

    public void render() {
        InputStream is = ClassLoader.getSystemResourceAsStream("banner.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = null;
        try {
            Properties properties = new Properties();
            properties.load(Banner.class.getClassLoader().getResourceAsStream("spectre.properties"));
            ColorUtils.color(ColorUtils.ORIGINAL, ColorUtils.BOLD);
            System.out.println("==========================================================");
            while ((line = reader.readLine()) != null) {
                ColorUtils.color(ColorUtils.F_RED, ColorUtils.BOLD);
                System.out.print(line.substring(0, 8));
                ColorUtils.color(ColorUtils.F_L_RED, ColorUtils.BOLD);
                System.out.print(line.substring(8, 16));
                ColorUtils.color(ColorUtils.F_L_YELLOW, ColorUtils.BOLD);
                System.out.print(line.substring(16, 24));
                ColorUtils.color(ColorUtils.F_L_GREEN, ColorUtils.BOLD);
                System.out.print(line.substring(24, 32));
                ColorUtils.color(ColorUtils.F_L_CYAN, ColorUtils.BOLD);
                System.out.print(line.substring(32, 41));
                ColorUtils.color(ColorUtils.F_L_BLUE, ColorUtils.BOLD);
                System.out.print(line.substring(41, 49));
                ColorUtils.color(ColorUtils.F_L_PURPLE, ColorUtils.BOLD);
                System.out.println(line.substring(49));
            }
            ColorUtils.color(ColorUtils.ORIGINAL);
            System.out.println();
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                System.out.printf("%-7s : %s%n", entry.getKey(), entry.getValue());
            }
            ColorUtils.color(ColorUtils.ORIGINAL, ColorUtils.BOLD);
            System.out.println("==========================================================");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ColorUtils.color(ColorUtils.ORIGINAL);
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
