package cjdict2356pc.state.trans;

import java.util.List;

import cjdict2356pc.dto.Item;
import cjdict2356pc.mb.MbUtils;

public class InputMethodStatusCnCj2 extends InputMethodStatusCnCj {

    public InputMethodStatusCnCj2() {
        this.setSubType(MbUtils.TYPE_CODE_CJGEN2);
        this.setSubTypeName("倉2");
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(MbUtils.TYPE_CODE_CJGEN2, code, false, null, extraResolve);
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(MbUtils.TYPE_CODE_CJGEN2, cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(MbUtils.TYPE_CODE_CJGEN2, code);
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_CJGEN2);
    }

}
