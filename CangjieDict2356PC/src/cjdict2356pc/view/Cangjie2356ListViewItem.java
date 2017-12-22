package cjdict2356pc.view;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import cjdict2356pc.CangjieDict2356PCMain;
import cjdict2356pc.dto.Item;
import cjdict2356pc.utils.UnicodeHanziUtil;

/**
 * 字典查詢結果列表項
 * 
 * @author fszhouzz@qq.com
 * @time 2017年12月21日 下午10:19:30
 */
public class Cangjie2356ListViewItem extends Cangjie2356ListView {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Item item = null;

    private JLabel labelChar = null;
    private JLabel labelUnicodeRangeName = null;
    private JLabel labelEncode = null;

    public Cangjie2356ListViewItem(Item it) {
        item = it;
        this.setLayout(null);
        int width = 423;
        int height = 50;
        setSize(width, height);
        Border border = BorderFactory.createEtchedBorder();
        this.setBorder(border);

        String charLabelText = item.getCharacter();
        String encodeLabelText = item.getEncode() + "（" + item.getEncodeName() + "）";
        String unicodeRangeName = "";
        if (item.isEmpty()) {
            charLabelText = "(空)";
            encodeLabelText = "無結果。";
        } else {
            unicodeRangeName = UnicodeHanziUtil.getRangeNameByChar(item.getCharacter());
        }

        Font font30 = new Font(null, Font.BOLD, 32);
        Font font20 = new Font(CangjieDict2356PCMain.FONT_NAME_HEITI, Font.BOLD, 18);
        
        labelChar = new JLabel(charLabelText, null, SwingConstants.CENTER);
        labelChar.setFont(font30);
        int charX = 0;
        int charY = 0;
        int charWidth = height * 150 / 100;
        int charHeight = height;
        labelChar.setBounds(charX, charY, charWidth, charHeight);
        add(labelChar);

        labelUnicodeRangeName = new JLabel(unicodeRangeName, null, SwingConstants.LEFT);
        labelUnicodeRangeName.setFont(font20);
        int rangeX = charWidth;
        int rangeY = 0;
        int rangeWidth = width - charWidth;
        int rangeHeight = height / 2;
        labelUnicodeRangeName.setBounds(rangeX, rangeY, rangeWidth, rangeHeight);
        add(labelUnicodeRangeName);

        labelEncode = new JLabel(encodeLabelText, null, SwingConstants.LEFT);
        labelEncode.setFont(font20);
        int encX = charWidth;
        int encY = rangeHeight;
        int encWidth = rangeWidth;
        int encHeight = rangeHeight;
        labelEncode.setBounds(encX, encY, encWidth, encHeight);
        add(labelEncode);
    }
    
    public Item getItemData() {
        return item;
    }
}
