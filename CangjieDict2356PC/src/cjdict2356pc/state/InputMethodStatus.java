package cjdict2356pc.state;

import java.util.HashMap;
import java.util.Map;

/**
 * 當前輸入狀態
 * 
 * @author t
 * @time 2017-1-7下午9:54:20
 */
public abstract class InputMethodStatus {

    /** 本種類內的下個輸入法 */
    private InputMethodStatus nextStatus;
    /** 所在種類下一個輸入法，爲下一個種類的第一個輸入法 */
    private InputMethodStatus nextStatusType;

    private String type;
    private String typeName;
    private String subType;
    private String subTypeName;

    protected InputMethodStatus() {
    }

    /**
     * 輸入法是否要翻譯
     * 
     * @author fszhouzz@qq.com
     * @time 2017年10月31日下午12:58:12
     * @return
     */
    public abstract boolean isShouldTranslate();

    /**
     * 得到輸入法名字
     */
    public abstract String getInputMethodName();

    /**
     * 得到下一個狀態
     */
    public final InputMethodStatus getNextStatus() {
        return this.nextStatus;
    }

    /**
     * 得到下一個輸入類別的默認輸入狀態
     */
    public final InputMethodStatus getNextStatusType() {
        return this.nextStatusType;
    }

    /**
     * 26個英文鍵，各鍵對應顯示的名字
     * 
     * @author fszhouzz@qq.com
     * @time 2018年7月19日 下午10:55:32
     * @return
     */
    public Map<String, Object> getKeysNameMap() {
        String letters1 = "abcdefghijklmnopqrstuvwxyz";
        String letters2 = letters1.toUpperCase();
        Map<String, Object> mbTransMap = new HashMap<String, Object>();
        int index = 0;
        while (index <= letters1.length() - 1) {
            mbTransMap.put(letters1.substring(index, index + 1), letters2.substring(index, index + 1));
            index++;
        }
        return mbTransMap;
    }

    /**
     * 根據鍵名找到英文鍵位
     * 
     * @author t
     * @time 2017-1-7下午10:54:41
     */
    public String getKeyByValue(String val) {
        Map<String, Object> map = getKeysNameMap();
        if (null != map) {
            for (String key : map.keySet()) {
                if (map.get(key).equals(val)) {
                    return key;
                }
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    @Override
    public String toString() {
        return "InputMethodStatus [type=" + type + ", typeName=" + typeName + ", subType=" + subType + ", subTypeName="
                + subTypeName + "]";
    }

    public void setNextStatus(InputMethodStatus nextStatus) {
        this.nextStatus = nextStatus;
    }

    public void setNextStatusType(InputMethodStatus nextStatusType) {
        this.nextStatusType = nextStatusType;
    }
}
