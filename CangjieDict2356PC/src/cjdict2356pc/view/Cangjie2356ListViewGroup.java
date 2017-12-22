package cjdict2356pc.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import cjdict2356pc.CangjieDict2356PCMain;
import cjdict2356pc.dto.Group;

/**
 * 字典查詢結果分組
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2017年12月21日 下午11:47:56
 */
public class Cangjie2356ListViewGroup extends Cangjie2356ListView {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Group group;

    private JLabel labelGroupName = null;

    public Cangjie2356ListViewGroup(Group gp) {
        group = gp;
        this.setLayout(null);
        int width = 423;
        int height = 50;
        setSize(width, height);
        Border border = BorderFactory.createLineBorder(Color.gray);
        this.setBorder(border);
        this.setBackground(Color.darkGray);

        labelGroupName = new JLabel(" " + group.getgName(), null, SwingConstants.LEFT);
        Font font = new Font(CangjieDict2356PCMain.FONT_NAME_HEITI, Font.BOLD, 30);
        labelGroupName.setFont(font);
        // 前景，字體顏色
        labelGroupName.setForeground(Color.LIGHT_GRAY);
        int groupX = 0;
        int groupY = 0;
        int groupWidth = width;
        int groupHeight = height;
        labelGroupName.setBounds(groupX, groupY, groupWidth, groupHeight);
        add(labelGroupName);
    }

    public Group getGroupData() {
        return group;
    }
}
