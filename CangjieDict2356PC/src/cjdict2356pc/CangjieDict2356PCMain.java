package cjdict2356pc;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cjdict2356pc.frame.CangjieDict2356Frame;
import cjdict2356pc.tab.CangjieDict2356Tab;

/**
 * 倉頡字典2356PC版
 * 
 * @author fszhouzz@qq.com
 * @time 2017年12月21日下午2:20:47
 */
public class CangjieDict2356PCMain {

    public static final String FONT_NAME_HEITI = "黑体";
    public static final String FONT_NAME_SONGTI = "宋体";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // JFrame.setDefaultLookAndFeelDecorated(true); // java外觀

                JFrame frame = CangjieDict2356Frame.getInstance();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null); // 居中顯示
                frame.setLayout(null);

                JPanel contentPane = new CangjieDict2356Tab();
                frame.setContentPane(contentPane);
                frame.setVisible(true);
            }
        });
    }
}
