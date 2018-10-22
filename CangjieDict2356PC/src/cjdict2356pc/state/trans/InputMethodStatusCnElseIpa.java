package cjdict2356pc.state.trans;

import java.util.List;

import cjdict2356pc.dto.Item;
import cjdict2356pc.mb.MbUtils;

/**
 * 國際音標
 * 
 * @author t
 * @time 2017-1-9下午10:10:25
 */
public class InputMethodStatusCnElseIpa extends InputMethodStatusCnElse {

    public InputMethodStatusCnElseIpa() {
        this.setSubType(MbUtils.TYPE_CODE_CJGEN_IPA);
        this.setSubTypeName("音");
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
