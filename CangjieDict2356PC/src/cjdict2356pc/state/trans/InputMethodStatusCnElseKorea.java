package cjdict2356pc.state.trans;

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

    public InputMethodStatusCnElseKorea() {
        this.setSubType(MbUtils.TYPE_CODE_CJGENKOREA);
        this.setSubTypeName("韓");
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(this.getSubType(), code, (null != code && code.length() > 1), code, false);
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(this.getSubType(), cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(this.getSubType(), code);
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(this.getSubType());
    }

}