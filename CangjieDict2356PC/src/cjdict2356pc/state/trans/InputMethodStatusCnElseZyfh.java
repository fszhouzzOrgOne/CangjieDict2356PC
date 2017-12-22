package cjdict2356pc.state.trans;

import java.util.List;

import cjdict2356pc.dto.Item;
import cjdict2356pc.mb.MbUtils;

/**
 * 注音符號
 * 
 * @author t
 * @time 2017-1-9下午10:10:25
 */
public class InputMethodStatusCnElseZyfh extends InputMethodStatusCnElse {

    public InputMethodStatusCnElseZyfh() {
        this.setSubType(MbUtils.TYPE_CODE_ZYFH);
        this.setSubTypeName("注");
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_ZYFH);
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(MbUtils.TYPE_CODE_ZYFH, code, true, code, false);
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(this.getSubType(), cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(MbUtils.TYPE_CODE_ZYFH, code);
    }

}
