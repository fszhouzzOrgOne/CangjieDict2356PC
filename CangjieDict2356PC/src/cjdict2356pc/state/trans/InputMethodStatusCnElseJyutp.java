package cjdict2356pc.state.trans;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cjdict2356pc.dto.Item;
import cjdict2356pc.mb.MbUtils;

/**
 * 粵語拼音
 */
public class InputMethodStatusCnElseJyutp extends InputMethodStatusCnElse {

    /**
     * 用什麼代替聲調
     */
    private static final String TONE_REPLACE_CHAR = "q";

    public InputMethodStatusCnElseJyutp() {
        this.setSubType(MbUtils.TYPE_CODE_JYUTPING);
        this.setSubTypeName("粵");
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_JYUTPING);
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(this.getSubType(), cha);
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        // 只有沒有聲調的才模糊查詢，有聲調了就不再模糊查詢了
        boolean isPrompt = null != code && code.trim().length() > 0 && !code.endsWith(TONE_REPLACE_CHAR);
        List<Item> items = MbUtils.selectDbByCode(MbUtils.TYPE_CODE_JYUTPING, code, isPrompt, code + TONE_REPLACE_CHAR,
                extraResolve);

        // 排序
        if (null != items && !items.isEmpty()) {
            try {
                Collections.sort(items, new Comparator<Item>() {

                    @Override
                    public int compare(Item one, Item two) {
                        String num1 = translateCode2Name(one.getEncode());
                        String num2 = translateCode2Name(two.getEncode());
                        if (null == num1 || null == num2) {
                            if (null == num1) {
                                return 1; // 编码爲空，在最後
                            } else {
                                return -1;
                            }
                        } else {
                            int numComp = num1.compareTo(num2);
                            return numComp;
                        }
                    }
                });
            } catch (Exception e) {
            }
        }

//        // 如果不展示編碼，就去褈
//        if (false == CandidateItemTextView.showEncode && null != items && !items.isEmpty()) {
//            Set<String> chaSet = new HashSet<String>();
//            for (int i = items.size() - 1; i >= 0; i--) {
//                Item it = items.get(i);
//                if (chaSet.contains(it.getCharacter())) {
//                    items.remove(i);
//                } else {
//                    chaSet.add(it.getCharacter());
//                }
//            }
//        }
        return items;
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(MbUtils.TYPE_CODE_JYUTPING, code);
    }

    @Override
    public String getInputingCnValueForEnter() {
        String code = getInputingCnCode();
        return translateCode2Name(code);
    }

    @Override
    public String translateCode2Name(String str) {
        String result = super.translateCode2Name(str);
        String code = result;
        if (null != code && code.toLowerCase().endsWith(TONE_REPLACE_CHAR)) {
            int start = code.toLowerCase().indexOf(TONE_REPLACE_CHAR);
            // 和官話拼音不同，沒有v開頭的音
            // if (start == 0) {
            // start = 1;
            // }
            String ms = code.substring(start);
            result = code.substring(0, start) + ms.length();
        }
        return result;
    }

}
