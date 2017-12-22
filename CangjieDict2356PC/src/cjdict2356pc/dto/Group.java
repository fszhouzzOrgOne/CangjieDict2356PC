package cjdict2356pc.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 分組
 * 
 * @author t
 * @time 2016-12-18下午1:02:38
 */
public class Group {

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

}
