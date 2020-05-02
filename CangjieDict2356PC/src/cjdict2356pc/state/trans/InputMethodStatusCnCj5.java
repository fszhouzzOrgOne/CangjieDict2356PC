package cjdict2356pc.state.trans;

import java.util.List;
import java.util.Map;

import cjdict2356pc.dto.Item;
import cjdict2356pc.mb.MbUtils;

public class InputMethodStatusCnCj5 extends InputMethodStatusCnCj {

    public InputMethodStatusCnCj5() {
        this.setSubType(MbUtils.TYPE_CODE_CJGEN5);
        this.setSubTypeName("倉5");
    }
    
    @Override
    public Map<String, Object> getKeysNameMap() {
        Map<String, Object> mbTransMap = super.getKeysNameMap();
        // 三代依Arthurmcarthur建議，z鍵中文字母改爲片字，今五代也改爲片。
        mbTransMap.put("z", "片");
        return mbTransMap;
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(new String[] { MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGEN5 }, code,
                false, null, extraResolve);
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(new String[] { MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGEN5 }, cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(new String[] { MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGEN5 }, code);
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_CJGEN5);
    }

}
