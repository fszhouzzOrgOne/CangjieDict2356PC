package cjdict2356pc.state.trans;

import java.util.List;

import cjdict2356pc.dto.Item;
import cjdict2356pc.mb.MbUtils;

public class InputMethodStatusCnCj35 extends InputMethodStatusCnCj {

    public InputMethodStatusCnCj35() {
        this.setSubType(MbUtils.TYPE_CODE_CJGEN35);
        this.setSubTypeName("倉35");
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(new String[] { MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGEN35 }, code,
                false, null, extraResolve);
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(new String[] { MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGEN35 }, cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(new String[] { MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGEN35 },
                code);
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_CJGEN35);
    }
}
