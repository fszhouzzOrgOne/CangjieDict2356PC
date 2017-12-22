package cjdict2356pc.state.trans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cjdict2356pc.dto.Item;
import cjdict2356pc.state.InputMethodStatus;
import cjdict2356pc.utils.StringUtils;

/**
 * 要翻譯的輸入法
 */
public abstract class InputMethodStatusCn extends InputMethodStatus {

    private boolean isInputingCn = false; // 用户是否正在打中文中
    // 中文臨時輸入的內容
    private Map<String, String> inputTempCn = new HashMap<String, String>();

    InputMethodStatusCn() {
    }

    @Override
    public boolean isShouldTranslate() {
        return true;
    }

    /**
     * 得到候選信息
     * 
     * @param code
     *            當前輸入
     * @param extraResolve
     *            是否解析結果，如加入時間等
     */
    public abstract List<Item> getCandidatesInfo(String code, boolean extraResolve);

    /**
     * 按字查詢輸入法的编码
     * 
     * @param cha
     *            文字
     * @author fsz
     * @time 2017年9月27日下午5:01:18
     * @return
     */
    public abstract List<Item> getCandidatesInfoByChar(String cha);

    /**
     * 是否還可以繼續鍵入
     */
    public abstract boolean couldContinueInputing(String code);

    /**
     * 根据英文鍵位，得到鍵名串
     */
    public String translateCode2Name(String str) {
        String result = null;
        Map<String, Object> map = getKeysNameMap();
        if (null != map && StringUtils.hasText(str)) {
            for (int index = 0; index < str.length(); index++) {
                Character c = str.charAt(index);
                if (null == result) {
                    result = "";
                }
                result += map.get(c.toString());
            }
        }
        return result;
    }

    /**
     * 正在輸入中文，保存臨時輸入
     */
    public String inputingCnCode(String key, String value) {
        String resultValue = "";
        // 什麼都沒有傳進來，就不是臨時輸入，是最後的提交
        if (!StringUtils.hasText(key) || !StringUtils.hasText(value)) {
            setInputingCn(false);
        } else {
            setInputingCn(true);

            if (null == inputTempCn.get("code")) {
                inputTempCn.put("code", "");
            }
            inputTempCn.put("code", inputTempCn.get("code") + key);

            if (null == inputTempCn.get("value")) {
                inputTempCn.put("value", "");
            }
            inputTempCn.put("value", inputTempCn.get("value") + value);

            resultValue = inputTempCn.get("value");
        }

        // 保持提示框中的顯示一致
        // ((Cj2356InputMethodService) this.getContext()).setComposingText(getInputingCnCode());
        return resultValue;
    }

    /**
     * 得到臨時輸入的所有鍵名
     */
    public String getInputingCnValue() {
        String result = "";
        if (null != inputTempCn && StringUtils.hasText(inputTempCn.get("value"))) {
            result = inputTempCn.get("value");
        }
        return result;
    }

    /**
     * 得到臨時輸入的所有鍵名，用於回車鍵輸入編碼
     */
    public String getInputingCnValueForEnter() {
        return getInputingCnValue();
    }

    /**
     * 得到臨時輸入的所有英文碼位
     */
    public String getInputingCnCode() {
        String result = "";
        if (null != inputTempCn && StringUtils.hasText(inputTempCn.get("code"))) {
            result = inputTempCn.get("code");
        }
        return result;
    }

    public boolean isInputingCn() {
        return isInputingCn;
    }

    public void setInputingCn(boolean isInputingCn) {
        this.isInputingCn = isInputingCn;
        if (false == isInputingCn) {
            inputTempCn = new HashMap<String, String>();
//            // 候選置空
//            ((Cj2356InputMethodService) this.getContext()).setSuggestions(null);
//            // 保持提示框中的顯示一致
//            ((Cj2356InputMethodService) this.getContext()).setComposingText("");
        }
    }

    public Map<String, String> getInputTempCn() {
        return inputTempCn;
    }

    public void setInputTempCn(Map<String, String> inputTempCn) {
        this.inputTempCn = inputTempCn;
    }

}
