package cjdict2356pc.state.trans;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cjdict2356pc.dto.Item;
import cjdict2356pc.mb.MbUtils;

// 四角號碼
public class InputMethodStatusCnElseSghm extends InputMethodStatusCnElse {

    private static final Map<String, String> abcToNumMap = new HashMap<String, String>();

    static {
        abcToNumMap.put("q", "1");
        abcToNumMap.put("w", "2");
        abcToNumMap.put("e", "3");
        abcToNumMap.put("r", "4");
        abcToNumMap.put("t", "5");
        abcToNumMap.put("y", "6");
        abcToNumMap.put("u", "7");
        abcToNumMap.put("i", "8");
        abcToNumMap.put("o", "9");
        abcToNumMap.put("p", "0");
    }

    public InputMethodStatusCnElseSghm() {
        this.setSubType(MbUtils.TYPE_CODE_SIGOHAOMA);
        this.setSubTypeName("4角");
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_SIGOHAOMA);
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(this.getSubType(), cha);
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        List<Item> items = MbUtils.selectDbByCode(this.getSubType(), code, (null != code && code.length() > 3), code,
                extraResolve);
        // 排序
        if (null != items && !items.isEmpty()) {
            try {
                Collections.sort(items, new Comparator<Item>() {
                    @Override
                    public int compare(Item one, Item two) {
                        String num1 = code2Num(one.getEncode());
                        String num2 = code2Num(two.getEncode());
                        // 不是漢字
                        if (null == num1 || null == num2) {
                            if (null == num1) {
                                return 1; // 编码爲空，在最後
                            } else {
                                return -1;
                            }
                        } else if (num1.length() > num2.length()) {
                            return 1;
                        } else if (num1.length() == num2.length()) {
                            if (one.getCharacter().length() > two.getCharacter().length()) {
                                return 1;
                            } else if (one.getCharacter().length() == two.getCharacter().length()) {
                                if (Integer.parseInt(num1) > Integer.parseInt(num2)) {
                                    return 1;
                                } else if (Integer.parseInt(num1) == Integer.parseInt(num2)) {
                                    return 0; // 使用默認
                                } else {
                                    return -1;
                                }
                            } else {
                                return -1;
                            }
                        } else {
                            return -1;
                        }
                    }

                });
            } catch (Exception e) {
            }
        }
        return items;
    }

    private String code2Num(String cod) {
        if (null == cod) {
            return null;
        }
        String num = cod.toLowerCase();
        for (int i = 0; i < num.length(); i++) {
            Character ch = num.charAt(i);
            if (null == abcToNumMap.get(ch.toString())) {
                return null; // 不是漢字
            } else {
                num = num.replaceFirst(ch.toString(), abcToNumMap.get(ch.toString()));
            }
        }
        return num;
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(this.getSubType(), code);
    }

    @Override
    public Map<String, Object> getKeysNameMap() {
        Map<String, Object> mbTransMap = new HashMap<String, Object>();
        mbTransMap.put("q", "橫");
        mbTransMap.put("w", "垂");
        mbTransMap.put("e", "點");
        mbTransMap.put("r", "叉");
        mbTransMap.put("t", "插");
        mbTransMap.put("y", "方");
        mbTransMap.put("u", "角");
        mbTransMap.put("i", "八");
        mbTransMap.put("o", "小");
        mbTransMap.put("p", "頭");
        mbTransMap.put("a", "，");
        mbTransMap.put("s", "。");
        mbTransMap.put("d", "、");
        mbTransMap.put("f", "！");
        mbTransMap.put("g", "？");
        mbTransMap.put("h", "…");
        mbTransMap.put("j", "“");
        mbTransMap.put("k", "：");
        mbTransMap.put("l", "＋");
        mbTransMap.put("z", "￥");
        mbTransMap.put("x", "（");
        mbTransMap.put("c", "〈");
        mbTransMap.put("v", "《");
        mbTransMap.put("b", "［");
        mbTransMap.put("n", "【");
        mbTransMap.put("m", "＊");
        return mbTransMap;
    }

}
