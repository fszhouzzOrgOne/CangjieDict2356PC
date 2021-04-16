package cjdict2356pc.mb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cjdict2356pc.dto.Group;
import cjdict2356pc.dto.Item;
import cjdict2356pc.state.InputMethodStatus;
import cjdict2356pc.state.trans.InputMethodStatusCn;
import cjdict2356pc.state.trans.InputMethodStatusCnCj2;
import cjdict2356pc.state.trans.InputMethodStatusCnCj3;
import cjdict2356pc.state.trans.InputMethodStatusCnCj35;
import cjdict2356pc.state.trans.InputMethodStatusCnCj5;
import cjdict2356pc.state.trans.InputMethodStatusCnCj6;
import cjdict2356pc.state.trans.InputMethodStatusCnCjMacOsX105;
import cjdict2356pc.state.trans.InputMethodStatusCnCjMs;
import cjdict2356pc.state.trans.InputMethodStatusCnCjYhqm;
import cjdict2356pc.state.trans.InputMethodStatusCnElseJyutp;
import cjdict2356pc.state.trans.InputMethodStatusCnElseKorea;
import cjdict2356pc.state.trans.InputMethodStatusCnElsePy;
import cjdict2356pc.state.trans.InputMethodStatusCnElseSghm;
import cjdict2356pc.utils.StringUtils;

/**
 * 倉頡字典的相關查詢
 * 
 * 
 * @author 日月遞炤
 * @time 2017年9月26日 下午10:43:48
 */
public class SettingDictMbUtils {

    /**
     * 字典查询的輸入法
     */
    private static final List<InputMethodStatusCn> dictIms = new ArrayList<InputMethodStatusCn>();
    // 這裡本是讀取配置文件取出的
    private static final String cjConfig = "cj6,cj5,cjmacx,cj3,cjyhqm,cjms,cj2,sghm";
    
    static {
        init();
    }

    public static void init() {
        if (dictIms.isEmpty()) {
            try {

                String[] cjConfigArr = cjConfig.split(",");
                List<String> cjConfigList = new ArrayList<String>();
                for (String conf : cjConfigArr) {
                    cjConfigList.add(conf);
                }

                Map<String, Object> allCjIMsMap = new LinkedHashMap<String, Object>();
                InputMethodStatus im = new InputMethodStatusCnCj6();
                allCjIMsMap.put(im.getSubType(), im);
                im = new InputMethodStatusCnCj5();
                allCjIMsMap.put(im.getSubType(), im);
                im = new InputMethodStatusCnCjMacOsX105();
                allCjIMsMap.put(im.getSubType(), im);
                im = new InputMethodStatusCnCj35();
                allCjIMsMap.put(im.getSubType(), im);
                im = new InputMethodStatusCnCj3();
                allCjIMsMap.put(im.getSubType(), im);
                im = new InputMethodStatusCnCjMs();
                allCjIMsMap.put(im.getSubType(), im);
                im = new InputMethodStatusCnCjYhqm();
                allCjIMsMap.put(im.getSubType(), im);
                im = new InputMethodStatusCnCj2();
                allCjIMsMap.put(im.getSubType(), im);
                im = new InputMethodStatusCnElseSghm();
                allCjIMsMap.put(im.getSubType(), im);
                im = new InputMethodStatusCnElsePy();
                allCjIMsMap.put(im.getSubType(), im);
                im = new InputMethodStatusCnElseJyutp();
                allCjIMsMap.put(im.getSubType(), im);
                im = new InputMethodStatusCnElseKorea();
                allCjIMsMap.put(im.getSubType(), im);

                // for (String key : allCjIMsMap.keySet()) {
                if (null != cjConfigList && !cjConfigList.isEmpty()) {
                    for (String cfg : cjConfigList) {
                        if (null != allCjIMsMap.get(cfg)) {
                            dictIms.add((InputMethodStatusCn) allCjIMsMap.get(cfg));
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 初始化字典查詢展示
     * 
     * @author 日月遞炤
     * @time 2017年9月26日 下午10:44:46
     * @return ArrayList<Group> 輸入法分組結果
     */
    public static List<Group> initGroupDatas() {
        List<Group> gData = new ArrayList<Group>();
        for (int i = 0; i < dictIms.size(); i++) {
            gData.add(new Group(i, dictIms.get(i).getSubType(), dictIms.get(i).getInputMethodName()));
        }
        return gData;
    }

    /**
     * 按編碼查詢
     * 
     * @author fsz
     * @time 2017年9月27日上午9:42:40
     * @param query
     * @return
     */
    public static List<Group> selectDbByCode(String query) {
        List<Group> gData = new ArrayList<Group>();
        for (int i = 0; i < dictIms.size(); i++) {
            Group g = new Group(i, dictIms.get(i).getSubType(), dictIms.get(i).getInputMethodName());
            List<Item> items = dictIms.get(i).getCandidatesInfo(query, false);

            if (null != items && !items.isEmpty()) {
                for (Item it : items) {
                    if (StringUtils.hasText(it.getEncode())) {
                        it.setEncodeName(dictIms.get(i).translateCode2Name(it.getEncode()));
                    }
                }
                g.setItems(items);
            }
            gData.add(g);
        }
        return gData;
    }

    /**
     * 按字查詢
     * 
     * @author fsz
     * @time 2017年9月27日上午9:42:40
     * @param query
     * @return
     */
    public static List<Group> selectDbByChar(String[] chas) {
        List<Group> gData = new ArrayList<Group>();
        for (int i = 0; i < dictIms.size(); i++) {
            // 去重
            Set<String> queried = new HashSet<String>(); 
            Group g = new Group(i, dictIms.get(i).getSubType(),
                    dictIms.get(i).getInputMethodName());
            try {
                List<Item> items = new ArrayList<Item>();
                // 本分組查詢所有的字符，放入items中
                InputMethodStatusCn im = dictIms.get(i);
                for (String cha : chas) {
                    if (queried.contains(cha)) {
                        continue;
                    }
                    List<Item> items1 = im.getCandidatesInfoByChar(cha);
                    if (null != items1 && !items1.isEmpty()) {
                        for (Item it : items1) {
                            if (StringUtils.hasText(it.getEncode())) {
                                it.setEncodeName(im.translateCode2Name(it.getEncode()));
                            }
                        }
                        items.addAll(items1);
                    }
                    queried.add(cha);
                }

                if (null != items && !items.isEmpty()) {
                    g.setItems(items);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            gData.add(g);
        }
        return gData;
    }

}
