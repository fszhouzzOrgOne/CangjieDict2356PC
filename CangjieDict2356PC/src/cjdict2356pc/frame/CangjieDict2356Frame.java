package cjdict2356pc.frame;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import cjdict2356pc.CangjieDict2356PCMain;

/**
 * 倉頡字典2356窗口
 * 
 * @author fszhouzz@qq.com
 * @time 2017年12月21日下午2:25:09
 */
public class CangjieDict2356Frame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static class LazyHolder {
        private static final CangjieDict2356Frame frame = new CangjieDict2356Frame();
    }

    /**
     * 獲得實例
     */
    public static CangjieDict2356Frame getInstance() {
        return LazyHolder.frame;
    }

    /**
     * 設置全局字體
     * 
     * @author t
     */
    public static void initGlobalFontSetting() {
        FontUIResource fontUIResource = new FontUIResource(
                new Font(CangjieDict2356PCMain.FONT_NAME_HEITI, Font.PLAIN, 16));
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontUIResource);
            }
        }
    }

    public CangjieDict2356Frame() {
        // 必須放在第一個
        initGlobalFontSetting();

        setTitle("倉頡字典2356電腦版");
        setResizable(false);

        int contentWidth = 450;
        int contentHeight = 540;
        int frameWidth = contentWidth + 6;
        int frameHeight = contentHeight + 28;
        setBounds(100, 100, frameWidth, frameHeight);

        // 自定義圖標
        BufferedImage image = null;
        try {
            InputStream proIs = this.getClass().getResourceAsStream("/ic_launcher.png");
            image = ImageIO.read(proIs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null != image) {
            this.setIconImage(image);
        }
    }
}
