package cjdict2356pc.tab;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import cjdict2356pc.dto.Group;
import cjdict2356pc.dto.Item;
import cjdict2356pc.mb.SettingDictMbUtils;
import cjdict2356pc.state.trans.InputMethodStatusCnElseUnicode;
import cjdict2356pc.utils.UnicodeConvertUtil;
import cjdict2356pc.view.Cangjie2356ListItemList;
import cjdict2356pc.view.Cangjie2356ListView;
import cjdict2356pc.view.Cangjie2356ListViewGroup;
import cjdict2356pc.view.Cangjie2356ListViewItem;

/**
 * 倉頡字典的內容
 * 
 * @author fszhouzz@qq.com
 * @time 2017年12月21日下午4:34:27
 */
public class CangjieDict2356TabDictPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JLabel searchLabel = null;
    private JTextField searchField = null;
    private JButton searchButton = null;

    /**
     * 查詢結果展示列表
     */
    private JScrollPane resListPanel = null;

    private boolean lostVirginity = false;
    /**
     * 所有的分組和查詢結果
     */
    private List<Group> gData = null;
    /** 已經打開的分組，分組代碼 */
    private List<String> openGroupCodes = new ArrayList<String>();
    /**
     * 繼續查詢框
     */
    private JFrame continueSearchFrame;

    public CangjieDict2356TabDictPanel() {
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);

        int gap = 10;
        int compHeight = 30;

        int labelWidth = 100;
        int labelX = gap;
        int labelY = 0;
        searchLabel = new JLabel("輸入查詢：", null, SwingConstants.RIGHT);
        labelY = gap;
        searchLabel.setBounds(labelX, labelY, labelWidth, compHeight);
        add(searchLabel);

        searchField = new JTextField(null, 14);
        Font font20 = new Font(null, Font.BOLD, 18);
        searchField.setFont(font20);
        int fieldX = labelX + labelWidth + gap;
        int fieldY = gap;
        int fieldWidth = 200;
        searchField.setBounds(fieldX, fieldY, fieldWidth, compHeight);
        searchField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (KeyEvent.getKeyText(event.getKeyCode()).compareToIgnoreCase("Enter") == 0) {
                    if (null != searchButton) {
                        searchButton.doClick();
                    }
                }
            }
        });
        add(searchField);

        searchButton = new JButton("查詢");
        int buttonX = fieldX + fieldWidth + gap;
        int buttonY = gap;
        int buttonWidth = 80;
        searchButton.setBounds(buttonX, buttonY, buttonWidth, compHeight);
        searchButton.addActionListener(new SearchButtonActionListener());
        add(searchButton);

        resListPanel = getJScrollPane();
        int listX = labelX;
        int listY = gap + compHeight + gap;
        int listWidth = 425;
        int listHeight = 440;
        resListPanel.setBounds(listX, listY, listWidth, listHeight);
        add(resListPanel);

        setgData(null);
    }

    /**
     * 生成列表
     * 
     * @author fszhouzz@qq.com
     * @time 2017年12月21日下午5:22:19
     * @return
     */
    private JScrollPane getJScrollPane() {
        String[] words = { "quick", "brown", "hungry", "wild" };
        JList list = new JList(words);
        JScrollPane resListPanel = new JScrollPane(list);
        return resListPanel;
    }

    /**
     * 設置查詢結果數據
     * 
     * @author fsz
     * @time 2017年9月27日上午9:44:04
     * @param gData
     */
    public void setgData(List<Group> gDataPar) {
        if (null == gDataPar || gDataPar.isEmpty()) {
            gDataPar = SettingDictMbUtils.initGroupDatas();
        }
        gData = gDataPar;
        tryInitUnicodeGroup(gData);

        openGroupCodes.clear();
        // 默認展開有結果的分組
        for (Group g : gData) {
            if (null != g.getItems() && !g.getItems().isEmpty() && !g.getItems().get(0).isEmpty()) {
                openGroupCodes.add(g.getgCode());
            }
        }
        if (lostVirginity && openGroupCodes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "沒有查詢到任何數據。");
        }

        updateResListPanel();
    }

    /**
     * 構造一個統一碼分組
     * 
     * @author fszhouzz@qq.com
     * @time 2018年10月20日 上午11:40:29
     * @param groups
     *            肯定不爲Null
     */
    private void tryInitUnicodeGroup(List<Group> groups) {
        Group gu = Group.unicodeGroup.clone();
        if (null != searchField) {
            String query = searchField.getText();
            if (query.length() > 0) {
                // 先按編碼
                List<Item> items = new ArrayList<Item>();
                InputMethodStatusCnElseUnicode uniIm = new InputMethodStatusCnElseUnicode();
                List<Item> byCodes = uniIm.getCandidatesInfoByTrueCode(query, false);
                if (null != byCodes && !byCodes.isEmpty()) {
                    items.addAll(byCodes);
                }
                List<Item> byChas = uniIm.getCandidatesInfoByChar(query);
                if (null != byChas && !byChas.isEmpty()) {
                    items.addAll(byChas);
                }
                if (!items.isEmpty()) {
                    gu.setItems(items);
                }
            } // end query.length()
        } // end editText
        groups.add(gu);
    }

    /**
     * 更新查詢結果展示列表
     * 
     * @author fszhouzz@qq.com
     * @time 2017年12月21日 下午11:41:39
     */
    public void updateResListPanel() {
        lostVirginity = true;

        if (null == resListPanel) {
            return;
        }
        List<Cangjie2356ListView> its = new ArrayList<Cangjie2356ListView>();
        for (Group gp : gData) {
            boolean isOpen = openGroupCodes.contains(gp.getgCode());
            List<Item> items = new ArrayList<Item>(gp.getItems());
            if (isOpen) {
                gp = new Group(gp.getgId(), gp.getgCode(), "▲" + gp.getgName());
            } else {
                gp = new Group(gp.getgId(), gp.getgCode(), "▼" + gp.getgName());
            }

            Cangjie2356ListViewGroup vg = new Cangjie2356ListViewGroup(gp);
            vg.addMouseListener(new ListViewGroupMouseListener());
            its.add(vg);

            if (isOpen) {
                for (Item it : items) {
                    Cangjie2356ListViewItem vi = new Cangjie2356ListViewItem(it);
                    vi.addMouseListener(new ListViewItemMouseListener());
                    its.add(vi);
                }
            }
        }

        Cangjie2356ListItemList list = new Cangjie2356ListItemList(its);

        resListPanel.setViewportView(list);
        resListPanel.repaint();
        resListPanel.requestFocus();

        if (null != continueSearchFrame) {
            continueSearchFrame.dispose();
            continueSearchFrame = null;
        }
    }

    /**
     * 查詢按鈕點擊事件
     * 
     * 
     * @author fszhouzz@qq.com
     * @time 2017年12月21日 下午9:47:54
     */
    class SearchButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (null != searchField) {
                List<Group> gData = null;
                String textInput = searchField.getText();
                if (null != textInput && !"".equals(textInput.trim().replaceAll(" ", ""))) {
                    textInput = textInput.trim().toLowerCase();
                    String pattern = "[a-zA-Z]{1,}";
                    if (textInput.matches(pattern)) {
                        gData = SettingDictMbUtils.selectDbByCode(textInput);
                    } else {
                        gData = SettingDictMbUtils.selectDbByChar(textInput);
                    }
                } else {
                    searchField.setText("");
                    searchField.requestFocus();
                    JOptionPane.showMessageDialog(null, "輸入編碼或文字查詢。");
                    return;
                }
                setgData(gData);
            }
        }

    }

    /**
     * 列表分組的點擊事件
     * 
     * @author fszhouzz@qq.com
     * @time 2017年12月22日上午9:21:46
     */
    class ListViewGroupMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            String gCode = null;
            if (null != e.getSource()) {
                Cangjie2356ListViewGroup vg = (Cangjie2356ListViewGroup) e.getSource();
                if (null != vg.getGroupData()) {
                    gCode = vg.getGroupData().getgCode();
                    if (!openGroupCodes.contains(gCode)) {
                        openGroupCodes.add(gCode);
                    } else {
                        openGroupCodes.remove(gCode);
                    }

                    updateResListPanel();
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * 列表結果項的點擊事件
     * 
     * @author fszhouzz@qq.com
     * @time 2017年12月22日上午9:21:46
     */
    class ListViewItemMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (null != e.getSource()) {
                Cangjie2356ListViewItem vi = (Cangjie2356ListViewItem) e.getSource();
                if (null != vi.getItemData()) {
                    Item it = vi.getItemData();
                    if (it.isEmpty()) {
                        return;
                    }

                    if (null != continueSearchFrame) {
                        continueSearchFrame.dispose();
                        continueSearchFrame = null;
                    }
                    Rectangle commonRect = new Rectangle((int) vi.getBounds().getX() + 50,
                            (int) vi.getBounds().getY() + 50, 250, 340);
                    Rectangle unicodeRect = new Rectangle((int) vi.getBounds().getX() + 50,
                            (int) vi.getBounds().getY() + 50, 250, 300);

                    continueSearchFrame = new JFrame("繼續查詢？");
                    continueSearchFrame.setBounds(commonRect);
                    if (it.isUnicodeItem()) {
                        continueSearchFrame.setBounds(unicodeRect);
                    }
                    continueSearchFrame.setLocationRelativeTo(null);
                    continueSearchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    continueSearchFrame.setLayout(null);
                    continueSearchFrame.setResizable(false);
                    continueSearchFrame.setUndecorated(false);

                    JLabel jl = new JLabel("繼續查詢：", null, SwingConstants.LEFT);
                    int jlX = 5;
                    int jlY = 0;
                    int jlwidth = 235;
                    int jlheight = 30;
                    jl.setBounds(jlX, jlY, jlwidth, jlheight);
                    continueSearchFrame.getContentPane().add(jl);

                    Font font20 = new Font(null, Font.BOLD, 18);
                    BevelBorder raisedBevelBorder = (BevelBorder) BorderFactory.createRaisedBevelBorder();

                    JLabel charLabel = new JLabel("查詢文字“" + it.getCharacter() + "”", null, SwingConstants.LEFT);
                    charLabel.setFont(font20);
                    charLabel.setBorder(raisedBevelBorder);
                    int charX = 5;
                    int charY = jlY + jlheight + 5;
                    int charWidth = jlwidth;
                    int charHeight = 40;
                    charLabel.setBounds(charX, charY, charWidth, charHeight);
                    charLabel.addMouseListener(new ContinueSearchMouseListener());
                    continueSearchFrame.getContentPane().add(charLabel);

                    JLabel codeLabel = new JLabel("查詢編碼“" + it.getEncode() + "”", null, SwingConstants.LEFT);
                    codeLabel.setFont(font20);
                    codeLabel.setBorder(raisedBevelBorder);
                    int codeX = 5;
                    int codeY = charY + charHeight + 5;
                    int codeWidth = jlwidth;
                    int codeHeight = charHeight;
                    codeLabel.setBounds(codeX, codeY, codeWidth, codeHeight);
                    codeLabel.addMouseListener(new ContinueSearchMouseListener());
                    continueSearchFrame.getContentPane().add(codeLabel);

                    JLabel copyChaLabel = new JLabel("複製文字“" + it.getCharacter() + "”", null, SwingConstants.LEFT);
                    copyChaLabel.setFont(font20);
                    copyChaLabel.setBorder(raisedBevelBorder);
                    int cochaX = 5;
                    int cochaY = codeY + codeHeight + 5;
                    int cochaWidth = jlwidth;
                    int cochaHeight = charHeight;
                    copyChaLabel.setBounds(cochaX, cochaY, cochaWidth, cochaHeight);
                    copyChaLabel.addMouseListener(new ContinueSearchMouseListener());
                    continueSearchFrame.getContentPane().add(copyChaLabel);

                    JLabel copyCodLabel = new JLabel("複製編碼“" + it.getEncode() + "”", null, SwingConstants.LEFT);
                    copyCodLabel.setFont(font20);
                    copyCodLabel.setBorder(raisedBevelBorder);
                    int cocodX = 5;
                    int cocodY = cochaY + cochaHeight + 5;
                    int cocodWidth = jlwidth;
                    int cocodHeight = charHeight;
                    copyCodLabel.setBounds(cocodX, cocodY, cocodWidth, cocodHeight);
                    copyCodLabel.addMouseListener(new ContinueSearchMouseListener());
                    continueSearchFrame.getContentPane().add(copyCodLabel);

                    JLabel copyChaCodLabel = new JLabel("複製文字和編碼“" + it.getCharacter() + " " + it.getEncode() + "”",
                            null, SwingConstants.LEFT);
                    copyChaCodLabel.setFont(font20);
                    copyChaCodLabel.setBorder(raisedBevelBorder);
                    int cochacodX = 5;
                    int cochacodY = cocodY + cocodHeight + 5;
                    int cochacodWidth = jlwidth;
                    int cochacodHeight = charHeight;
                    copyChaCodLabel.setBounds(cochacodX, cochacodY, cochacodWidth, cochacodHeight);
                    copyChaCodLabel.addMouseListener(new ContinueSearchMouseListener());
                    continueSearchFrame.getContentPane().add(copyChaCodLabel);

                    // 統一碼
                    List<String> strUnics = UnicodeConvertUtil.getUnicodeStr4ListFromStr(it.getCharacter());
                    final String unicode = (null == strUnics || strUnics.isEmpty()) ? null : strUnics.get(0);
                    if (!it.isUnicodeItem() && null != unicode) {
                        JLabel copyUnicodeLabel = new JLabel("複製統一碼“" + unicode + "”", null, SwingConstants.LEFT);
                        copyUnicodeLabel.setFont(font20);
                        copyUnicodeLabel.setBorder(raisedBevelBorder);
                        int coUnicodeX = 5;
                        int coUnicodeY = cochacodY + cochacodHeight + 5;
                        int coUnicodeWidth = jlwidth;
                        int coUnicodeHeight = charHeight;
                        copyUnicodeLabel.setBounds(coUnicodeX, coUnicodeY, coUnicodeWidth, coUnicodeHeight);
                        copyUnicodeLabel.addMouseListener(new ContinueSearchMouseListener());
                        continueSearchFrame.getContentPane().add(copyUnicodeLabel);
                    }

                    continueSearchFrame.setVisible(true);
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * 點擊了繼續查詢選項事件
     * 
     * @author fszhouzz@qq.com
     * @time 2017年12月22日下午3:09:03
     */
    class ContinueSearchMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (null != e.getSource()) {
                JLabel la = (JLabel) e.getSource();
                BevelBorder border = (BevelBorder) BorderFactory.createLoweredBevelBorder();
                la.setBorder(border);
                continueSearchFrame.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (null != e.getSource()) {
                JLabel la = (JLabel) e.getSource();
                BevelBorder border = (BevelBorder) BorderFactory.createRaisedBevelBorder();
                la.setBorder(border);
                continueSearchFrame.repaint();

                String textInput = la.getText();
                if (null != textInput) {
                    if (textInput.startsWith("查詢")) {
                        textInput = textInput.replaceAll("[“”]|(查詢)|(文字)|(編碼)", "");
                        if (textInput.length() > 0) {
                            if (null != searchField) {
                                searchField.setText(textInput);
                                searchButton.doClick();
                            }
                        }
                    } else if (textInput.startsWith("複製")) {
                        textInput = textInput.replaceAll("[“”]|(複製)|(文字)|(和)|(編碼)|(統一碼)", "");
                        if (textInput.length() > 0) {
                            setSysClipboardText(textInput);
                        }
                    }
                }

                if (null != continueSearchFrame) {
                    continueSearchFrame.dispose();
                    continueSearchFrame = null;
                }
            }
        }

        /**
         * 将字符串复制到剪切板。 <br/>
         * 參見：http://blog.csdn.net/aminfo/article/details/7858109
         */
        public void setSysClipboardText(String writeMe) {
            Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable tText = new StringSelection(writeMe);
            clip.setContents(tText, null);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (null != e.getSource()) {
                JLabel la = (JLabel) e.getSource();
                BevelBorder border = (BevelBorder) BorderFactory.createRaisedBevelBorder();
                la.setBorder(border);
                continueSearchFrame.repaint();
            }
        }

    }
}
