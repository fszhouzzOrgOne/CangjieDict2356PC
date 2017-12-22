package cjdict2356pc.view;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;

/**
 * 結果元素列表
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2017年12月21日 下午10:49:37
 */
public class Cangjie2356ListItemList extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Cangjie2356ListItemList(List<Cangjie2356ListView> views) {
        int width = views.get(0).getWidth();
        int height = views.get(0).getHeight() * views.size();
        this.setSize(width, height);
        this.setLayout(null);

        for (int i = 0; i < views.size(); i++) {
            Cangjie2356ListView view = views.get(i);
            int viewWidth = view.getWidth();
            int viewHeight = view.getHeight();
            int viewX = 0;
            int viewY = i * viewHeight;
            view.setBounds(viewX, viewY, viewWidth, viewHeight);
            add(view);
        }

        // 爲了把自己放到JScrollPane中後，JScrollPane顯示垂直的滾動條
        // 參見：http://blog.csdn.net/h002399/article/details/47340441
        this.setPreferredSize(new Dimension(0, height));
    }
}
