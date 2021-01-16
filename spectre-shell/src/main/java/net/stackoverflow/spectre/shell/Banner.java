package net.stackoverflow.spectre.shell;

import org.fusesource.jansi.Ansi;

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
            System.out.print(Ansi.ansi().fgMagenta().bold());
            System.out.println("==========================================================");
            while ((line = reader.readLine()) != null) {
                System.out.print(Ansi.ansi().fgRed().bold());
                System.out.print(line.substring(0, 8));
                System.out.print(Ansi.ansi().fgBrightRed().bold());
                System.out.print(line.substring(8, 16));
                System.out.print(Ansi.ansi().fgBrightYellow().bold());
                System.out.print(line.substring(16, 24));
                System.out.print(Ansi.ansi().fgBrightGreen().bold());
                System.out.print(line.substring(24, 32));
                System.out.print(Ansi.ansi().fgBrightCyan().bold());
                System.out.print(line.substring(32, 41));
                System.out.print(Ansi.ansi().fgBrightBlue().bold());
                System.out.print(line.substring(41, 49));
                System.out.print(Ansi.ansi().fgBrightMagenta().bold());
                System.out.println(line.substring(49));
            }
            System.out.println(Ansi.ansi().reset());
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                System.out.printf("%-7s : %s%n", entry.getKey(), entry.getValue());
            }
            System.out.print(Ansi.ansi().fgMagenta().bold());
            System.out.println("==========================================================");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.print(Ansi.ansi().reset());
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
