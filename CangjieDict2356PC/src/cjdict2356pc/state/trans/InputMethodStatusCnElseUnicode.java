package cjdict2356pc.state.trans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cjdict2356pc.dto.Item;
import cjdict2356pc.mb.MbUtils;
import cjdict2356pc.utils.UnicodeConvertUtil;

/**
 * 統一碼16進制
 * 
 * @author t
 * @time 2017-2-9下午9:36:55
 */
public class InputMethodStatusCnElseUnicode extends InputMethodStatusCnElse {

    public static final String UNICODE16_PATTERN = "[0-9a-fA-F]+";

    private static final String UNICODE16_MAXNUM = "10FFFF";

    public InputMethodStatusCnElseUnicode() {
        this.setSubType(MbUtils.TYPE_CODE_CJGENUNICODE);
        this.setSubTypeName("統");
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        if (null == code || "".equals(code.trim())) {
            return null;
        }
        String trueCode = getTrueCode(code);
        return getCandidatesInfoByTrueCode(trueCode, extraResolve);
    }

    public List<Item> getCandidatesInfoByTrueCode(String trueCode, boolean extraResolve) {
        List<Item> items = new ArrayList<Item>();
        try {
            int wrongStart = Integer.parseInt("D800", 16);
            int wrongEnd = Integer.parseInt("DFFF", 16);
            int intCode = Integer.parseInt(trueCode, 16);
            if (intCode >= wrongStart && intCode <= wrongEnd) {
                return items;
            }

            Item it = Item.unicodeItem.clone();
            String cha = UnicodeConvertUtil.getStringByUnicodeStr(trueCode);
            if (null != cha) {
                String tempCode = trueCode.toUpperCase();
                while (tempCode.length() < 4) {
                    tempCode = "0" + tempCode;
                }
                it.setCharacter(cha);
                it.setEncode(tempCode);
                items.add(it);

                // 加些時間的提示
//                ArrayList<Item> dateItems = null;
//                if (extraResolve) {
//                    dateItems = DateUtils.resolveTime(it);
//                }
//                // 時間的提示加在末尾
//                if (null != dateItems && !dateItems.isEmpty()) {
//                    items.addAll(dateItems);
//                }
            }
        } catch (Exception e) {
        }
        return items;
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        if (null == cha) {
            return null;
        }
        try {
            List<String> codes = UnicodeConvertUtil.getUnicodeStr4ListFromStr(cha);
            if (null != codes && !codes.isEmpty()) {
                List<Item> items = new ArrayList<Item>();
                for (String code : codes) {
                    Item it = Item.unicodeItem.clone();
                    String cha1 = UnicodeConvertUtil.getStringByUnicodeStr(code);
                    if (null != cha1) {
                        it.setCharacter(cha1);
                        it.setEncode(code);
                        items.add(it);
                    }
                }
                if (!items.isEmpty()) {
                    return items;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public boolean couldContinueInputing(String code) {
        if (null == code || "".equals(code.trim())) {
            return false;
        }
        String trueCode = this.getTrueCode(code);
        try {
            int max = Integer.parseInt(UNICODE16_MAXNUM, 16);
            int par = Integer.parseInt(trueCode, 16);
            return trueCode.length() <= UNICODE16_MAXNUM.length() && par >= 0 && par <= max;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getInputMethodName() {
        return "統一碼(10F⁴)";
    }

    private String getTrueCode(String code) {
        Map<String, Object> keysNameMap = getKeysNameMap();
        String trueCode = "";
        for (Character key : code.toCharArray()) {
            trueCode += keysNameMap.get(key.toString());
        }
        return trueCode;
    }

    @Override
    public String getComposingTextForInputConn() {
        String code = getInputingCnCode();
        return getTrueCode(code);
    }

    @Override
    public Map<String, Object> getKeysNameMap() {
        String letters1 = "qwertyuiopasdfghjklzxcvbnm";
        String letters2 = " 0 1 2 3 4 5 6 7 8 9 A B C D E F 00 10 20 30 34 4D 4E 9F A6 FF ";
        Map<String, Object> mbTransMap = super.getKeysNameMap();
        int index = 0;
        for (String one : letters2.trim().split(" +")) {
            mbTransMap.put(letters1.substring(index, index + 1), one);
            index++;
        }
        return mbTransMap;
    }
}