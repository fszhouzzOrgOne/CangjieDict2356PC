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

    private InputMethodStatus nextStatus;
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

    public Map<String, Object> getKeysNameMap() {
        Map<String, Object> mbTransMap = new HashMap<String, Object>();
        mbTransMap.put("a", "A");
        mbTransMap.put("b", "B");
        mbTransMap.put("c", "C");
        mbTransMap.put("d", "D");
        mbTransMap.put("e", "E");
        mbTransMap.put("f", "F");
        mbTransMap.put("g", "G");
        mbTransMap.put("h", "H");
        mbTransMap.put("i", "I");
        mbTransMap.put("j", "J");
        mbTransMap.put("k", "K");
        mbTransMap.put("l", "L");
        mbTransMap.put("m", "M");
        mbTransMap.put("n", "N");
        mbTransMap.put("o", "O");
        mbTransMap.put("p", "P");
        mbTransMap.put("q", "Q");
        mbTransMap.put("r", "R");
        mbTransMap.put("s", "S");
        mbTransMap.put("t", "T");
        mbTransMap.put("u", "U");
        mbTransMap.put("v", "V");
        mbTransMap.put("w", "W");
        mbTransMap.put("x", "X");
        mbTransMap.put("y", "Y");
        mbTransMap.put("z", "Z");
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
