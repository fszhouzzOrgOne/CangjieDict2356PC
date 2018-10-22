package cjdict2356pc.state.trans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cjdict2356pc.dto.Item;
import cjdict2356pc.mb.MbUtils;

/**
 * 朝鮮諺文
 * 
 * @author t
 * @time 2017-2-9下午9:36:55
 */
public class InputMethodStatusCnElseKorea extends InputMethodStatusCnElse {

    private static String yanwenPtn = "[\\uAC00-\\uD7AF\\u1100-\\u11FF\\u3130-\\u318F\\uA960-\\uA97F\\uD7B0-\\uD7FF}]+";

    public InputMethodStatusCnElseKorea() {
        this.setSubType(MbUtils.TYPE_CODE_CJGENKOREA);
        this.setSubTypeName("韓");

//        Namaja2HangeulTest.init(con);
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        List<Item> res = new ArrayList<Item>();
        if (null != code && code.length() > 0) {
//            List<String> hangeuls = Namaja2HangeulTest.getHangeulFromNamaja(code);
//            if (null != hangeuls && !hangeuls.isEmpty()) {
//                for (String geul : hangeuls) {
//                    Item it = new Item(null, MbUtils.TYPE_CODE_CJGENKOREA, code, geul);
//                    res.add(it);
//                }
//            }
        }

        List<Item> items = MbUtils.selectDbByCode(this.getSubType(), code, false, code, false);
        // 排序，把韓文放前面
        if (null != items && !items.isEmpty()) {
            Collections.sort(items, new Comparator<Item>() {
                @Override
                public int compare(Item lhs, Item rhs) {
                    if (null == lhs.getEncode() || null == rhs.getEncode()) {
                        if (null == lhs.getEncode()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {
                        if (lhs.getCharacter().matches(yanwenPtn)) {
                            return -1;
                        } else if (rhs.getCharacter().matches(yanwenPtn)) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
            res.addAll(items);
        }
        return res;
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(this.getSubType(), cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return true;
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(this.getSubType());
    }

}