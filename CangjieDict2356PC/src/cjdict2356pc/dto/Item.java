package cjdict2356pc.dto;

/**
 * 子列表元素
 * 
 * @author t
 * @time 2016-12-18下午1:02:48
 */
public class Item implements Cloneable {

    /** 空元素 */
    public static Item emptyItem = new Item(-1, null, "(none)", "(空)");
    /** 統一碼 */
    public static Item unicodeItem = new Item(-2, null, "", "");

    private Integer id; // 主键
    private String genCode; // 第幾代
    private String encode; // 編碼
    private String encodeName; // 編碼中文名
    private String character; // 内容

    public Item(Integer id, String gen, String code, String cha) {
        this.id = id;
        this.genCode = gen;
        this.encode = code;
        this.character = cha;
    }

    /**
     * 是否空對象
     * 
     * @author t
     * @time 2016-12-21下午3:10:19
     */
    public boolean isEmpty() {
        return this.id == emptyItem.getId();
    }

    /**
     * 是統一碼展示項
     * 
     * @author fszhouzz@qq.com
     * @time 2018年10月20日 下午12:02:08
     * @return
     */
    public boolean isUnicodeItem() {
        return this.id == unicodeItem.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenCode() {
        return genCode;
    }

    public void setGenCode(String genCode) {
        this.genCode = genCode;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getEncodeName() {
        return encodeName;
    }

    public void setEncodeName(String encodeName) {
        this.encodeName = encodeName;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", genCode=" + genCode + ", encode=" + encode + ", encodeName=" + encodeName
                + ", character=" + character + "]";
    }

    @Override
    public Item clone() {
        Item it = new Item(id, genCode, encode, character);
        it.setEncodeName(encodeName);
        return it;
    }

}
