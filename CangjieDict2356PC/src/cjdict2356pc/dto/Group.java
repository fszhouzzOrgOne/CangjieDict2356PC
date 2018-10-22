package cjdict2356pc.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 分組
 * 
 * @author t
 * @time 2016-12-18下午1:02:38
 */
public class Group implements Cloneable {
    /** 統一碼 */
    public static final Group unicodeGroup = new Group(-2, "unicode", "統一碼(10FFFF)");

    private int gId;
    private String gCode;
    private String gName;

    private List<Item> items;

    public Group(int id, String code, String name) {
        this.gId = id;
        this.gName = name;
        this.gCode = code;

        items = new ArrayList<Item>();
        items.add(Item.emptyItem);
    }

    /**
     * 是統一碼分組
     * 
     * @author fszhouzz@qq.com
     * @time 2018年10月20日 下午12:44:04
     * @return
     */
    public boolean isUnicodeGroup() {
        return this.gId == unicodeGroup.getgId();
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getgCode() {
        return gCode;
    }

    public void setgCode(String gCode) {
        this.gCode = gCode;
    }

    public int getgId() {
        return gId;
    }

    public void setgId(int gId) {
        this.gId = gId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        String itemStr = ", items.size=" + ((null == items) ? "0" : items.size());
        return "Group [gId=" + gId + ", gCode=" + gCode + ", gName=" + gName + itemStr + "]";
    }

    @Override
    public Group clone() {
        Group g = new Group(gId, gCode, gName);
        List<Item> itemList = new ArrayList<Item>();
        for (Item it : items) {
            itemList.add(it.clone());
        }
        g.setItems(itemList);
        return g;
    }

}
