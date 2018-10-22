package cjdict2356pc.state.trans;

import java.util.List;

import cjdict2356pc.dto.Item;
import cjdict2356pc.mb.MbUtils;

/**
 * 中古漢語輸入法
 */
public class InputMethodStatusCnElseKoxhanh extends InputMethodStatusCnElse {

    public InputMethodStatusCnElseKoxhanh() {
        this.setSubType(MbUtils.TYPE_CODE_CJGEN_KOXHANH);
        this.setSubTypeName("漢");
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(this.getSubType());
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(this.getSubType(), code, false, code, false);
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(this.getSubType(), cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(this.getSubType(), code);
    }

}
