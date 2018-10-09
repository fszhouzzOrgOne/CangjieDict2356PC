package cjdict2356pc.mb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import cjdict2356pc.dto.Item;
import cjdict2356pc.utils.IOUtils;

/**
 * 加载碼表數據
 */
public class MbUtils {
    public static final String TYPE_CODE_CJGEN2 = "cj2";
    public static final String TYPE_CODE_CJGEN3 = "cj3";
    public static final String TYPE_CODE_CJGEN35 = "cj35"; // 三五代
    public static final String TYPE_CODE_CJGEN5 = "cj5";
    public static final String TYPE_CODE_CJGEN6 = "cj6";
    public static final String TYPE_CODE_ZYFH = "zyfh"; // 注音符號
    public static final String TYPE_CODE_KARINA = "karina"; // 日語oen假名
    public static final String TYPE_CODE_PINYIN = "pinyin"; // 官話拼音
    public static final String TYPE_CODE_JYUTPING = "jyutp"; // 粵語拼音
    public static final String TYPE_CODE_SIGOHAOMA = "sghm"; // 四角號碼
    public static final String TYPE_CODE_CJGENYAHOO = "cjyhqm"; // 雅虎奇摩
    public static final String TYPE_CODE_CJGENMS = "cjms"; // 微軟倉頡
    public static final String TYPE_CODE_CJGENKOREA = "korea"; // 朝鮮諺文
    public static final String TYPE_CODE_CJGENMANJU = "manju"; // 圈點滿文
    public static final String TYPE_CODE_CJGEN_IPA = "ipa"; // 國際音標
    public static final String TYPE_CODE_CJGEN_KOXHANH = "kohan"; // 中古漢語
    // 倉頡碼表的交集
    public static final String TYPE_CODE_CJINTERSECT = "cjcommon";

    private static String dbName = "cjmbdb.db";
    private static String genTbName = "t_mb_type"; // 碼表名表
    private static String genClNameId = "_id";
    private static String genClNameGen = "type_code";
    private static String genClNameName = "type_name";
    private static String mbTbName = "t_mb_content"; // 碼表
    private static String mbClNameId = "_id";
    private static String mbClNameGen = "type_code";
    private static String mbClNameCod = "mb_code";
    private static String mbClNameVal = "mb_char";
    private static String mbClNameOrder = "mb_order_no"; // 序號列
    private static String mbTbNameIntersect = "t_mb_content_intersect"; // 交集碼表
    private static String mbClNameIdIntersect = "_id";
    private static String mbClNameCodIntersect = "mb_code";
    private static String mbClNameValIntersect = "mb_char";
    private static String mbClNameOrderIntersect6 = "mb_order_6"; // 序號列6
    private static String mbClNameOrderIntersect5 = "mb_order_5"; // 序號列5
    private static String mbClNameOrderIntersect3 = "mb_order_3"; // 序號列3
    private static String mbClNameOrderIntersectyh = "mb_order_yh"; // 序號列yh
    private static String mbClNameOrderIntersectms = "mb_order_ms"; // 序號列ms

    private static Connection conct = null;
    private static Statement stmt = null;

    public static void main(String[] args) {
        String typeCode = "cj6";
        System.out.println(typeCode + "名字：" + getInputMethodName(typeCode));

        ArrayList<Item> itemsChar = selectDbByChar(typeCode, "頡");
        System.out.println("itemsChar: " + itemsChar);

        ArrayList<Item> itemsCode = selectDbByCode(typeCode, "hh", false, null, false);
        System.out.println("itemsCode: " + itemsCode);

        boolean existsDBLikeCode = existsDBLikeCode(typeCode, "hh");
        System.out.println("existsDBLikeCode: " + existsDBLikeCode);
    }

    /**
     * 獲取查詢Statement
     * 
     * @author fszhouzz@qq.com
     * @time 2017年12月21日下午5:34:55
     * @return
     */
    public static Statement getStatement() {
        if (null == stmt) {
            try {
                // 參見：https://www.cnblogs.com/zeciiii/p/4178824.html
                // 在內部會被拒絕訪問
                String outFileName = MbUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                outFileName = java.net.URLDecoder.decode(outFileName, "UTF-8");
                if (!outFileName.endsWith(File.separator)) {
                    outFileName = outFileName.substring(0, outFileName.lastIndexOf(File.separator) + 1);
                }
                outFileName += dbName;

                String inFileName = "/" + dbName;

                File destFile = new File(outFileName);
                boolean shouldCopy = true;
                if (destFile.exists()) {
                    InputStream isOut = new FileInputStream(destFile);
                    InputStream isInner = MbUtils.class.getResourceAsStream(inFileName);

                    if (IOUtils.isSameFile(isInner, isOut)) {
                        shouldCopy = false;
                    }
                    if (shouldCopy) {
                        destFile.delete();
                    }
                }

                if (shouldCopy) {
                    OutputStream isOut = new FileOutputStream(outFileName);
                    InputStream isInner = MbUtils.class.getResourceAsStream(inFileName);
                    IOUtils.copyFile(isInner, isOut);
                }

                Class.forName("org.sqlite.JDBC");
                conct = DriverManager.getConnection("jdbc:sqlite:" + outFileName);
                stmt = conct.createStatement();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                if (null != e.getCause()) {
                    JOptionPane.showMessageDialog(null, e.getCause().getMessage());
                }
            }
        }
        return stmt;
    }

    public static void closeStatement() {
        try {
            if (null != stmt) {
                stmt.close();
                stmt = null;
            }
            if (null != conct) {
                conct.close();
                conct = null;
            }
        } catch (SQLException e) {
        }
    }

    /**
     * 按字符查詢
     */
    public static ArrayList<Item> selectDbByChar(String typeCode, String cha) {
        return selectDbByChar(new String[] { typeCode }, cha);
    }

    /**
     * 按字符查詢2
     */
    public static ArrayList<Item> selectDbByChar(String[] typeCode, String cha) {
        if (null == getStatement() || null == cha || cha.trim().length() == 0) {
            return null;
        }
        cha = cha.trim();

        String sql = getQuerySql(typeCode, cha, null);

        ResultSet rs = null;
        try {
            rs = getStatement().executeQuery(sql.toString());
        } catch (SQLException e) {
        }
        ArrayList<Item> items = handleSelectResultCursor(rs);
        return items;
    }

    /**
     * 按編碼查詢
     * 
     * @param typeCode
     *            輸入法類型
     * @param code
     *            當前輸入
     * @param isPrompt
     *            是否模糊提示
     * @param promptCode
     *            模糊提示底查詢參數
     * @param extraResolve
     *            是否解析結果，如加入時間等
     * @author t
     * @time 2016-12-18下午1:03:35
     */
    public static ArrayList<Item> selectDbByCode(String typeCode, String code, boolean isPrompt, String promptCode,
            boolean extraResolve) {
        return selectDbByCode(new String[] { typeCode }, code, isPrompt, promptCode, extraResolve);
    }

    /**
     * 按編碼查詢2
     */
    public static ArrayList<Item> selectDbByCode(String[] typeCode, String code, boolean isPrompt, String promptCode,
            boolean extraResolve) {
        if (null == getStatement() || null == code || code.trim().length() == 0) {
            return null;
        }
        code = code.trim();

        String sqls = getQuerySql(typeCode, null, code);

        ResultSet rs = null;
        try {
            rs = getStatement().executeQuery(sqls.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Item> items = handleSelectResultCursor(rs);

        // 如果沒有找到，按模糊查詢，再來一次
        // 倉頡不模糊查詢，先不管交集表了
        if (isPrompt && (null == items || items.isEmpty())) {
            // 輸入法類型條件
            List<String> types = new ArrayList<String>(Arrays.asList(typeCode));
            for (int i = types.size() - 1; i >= 0; i--) {
                if (TYPE_CODE_CJINTERSECT.equals(types.get(i))) {
                    types.remove(i);
                }
            }
            String typeCodeSql = " and " + mbClNameGen + " = '" + types.get(0) + "' ";
            // 排序
            String orderSql = " order by " + mbClNameCod + " asc, " + mbClNameOrder + " desc ";

            String promptCodeSql = " and " + mbClNameCod + " like '";
            promptCodeSql += (promptCode == null) ? code + "%" : promptCode + "%";
            promptCodeSql += "' ";

            StringBuilder sql = new StringBuilder();
            sql.append(" select ");
            sql.append(mbClNameId + ", ");
            sql.append(mbClNameGen + ", ");
            sql.append(mbClNameCod + ", ");
            sql.append(mbClNameVal + ", ");
            sql.append(mbClNameOrder);
            sql.append(" from ");
            sql.append(mbTbName);
            sql.append(" where 1=1 ");
            sql.append(typeCodeSql);
            sql.append(promptCodeSql);
            sql.append(orderSql);

            try {
                rs = getStatement().executeQuery(sql.toString());
            } catch (SQLException e) {
            }
            items = handleSelectResultCursor(rs);
        }
        return items;
    }

    /**
     * 游標的動作
     * 
     * @author fsz
     * @time 2017年9月27日上午11:17:49
     * @param rs
     *            結果集
     * @return
     */
    private static ArrayList<Item> handleSelectResultCursor(ResultSet rs) {
        ArrayList<Item> items = null;
        if (null != rs) {
            items = new ArrayList<Item>();
            try {
                while (rs.next()) {
                    int idVal = rs.getInt(mbClNameId);
                    String genVal = rs.getString(mbClNameGen);
                    String codeVal = rs.getString(mbClNameCod);
                    String charVal = rs.getString(mbClNameVal);
                    Item item = new Item(idVal, genVal, codeVal, charVal);
                    items.add(item);
                }
                rs.close();
            } catch (Exception e) {
            }
        }
        return items;
    }

    /**
     * 按編碼模糊查詢統計，是否還可以繼續鍵入
     * 
     * @author t
     * @time 2017-1-8下午10:11:02
     */
    public static boolean existsDBLikeCode(String typeCode, String code) {
        return existsDBLikeCode(new String[] { typeCode }, code);
    }

    // 2
    public static boolean existsDBLikeCode(String[] typeCode, String code) {
        // 輸入法類型條件
        List<String> types = new ArrayList<String>(Arrays.asList(typeCode));
        boolean unionIntersect = false;
        for (int i = types.size() - 1; i >= 0; i--) {
            if (TYPE_CODE_CJINTERSECT.equals(types.get(i))) {
                unionIntersect = true;
                types.remove(i);
            }
        }
        String typeCodeSql = " and " + mbClNameGen + " = '" + types.get(0) + "' ";
        // 當前輸入條件
        String codeLikeSql = " and " + mbClNameCod + " like '" + code + "%' ";

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT 1 ");
        sql.append(" where exists ( ");
        sql.append("     select 1 from ");
        sql.append(mbTbName);
        sql.append("     where 1=1 ");
        sql.append(typeCodeSql);
        sql.append(codeLikeSql);
        if (unionIntersect) {
            sql.append(" union all select 1 ");
            sql.append("     from ");
            sql.append(mbTbNameIntersect);
            sql.append("     where 1=1 ");
            sql.append(codeLikeSql);
        }
        sql.append(" ) ");

        boolean res = false;
        try {
            ResultSet rs = getStatement().executeQuery(sql.toString());
            while (rs.next()) {
                res = true;
            }
            rs.close();
        } catch (Exception e) {
        }
        return res;
    }

    /**
     * 查詢輸入法的名字
     */
    public static String getInputMethodName(String typeCode) {
        String resultName = null;

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(genClNameName);
        sql.append("     as thename, ");
        sql.append(genClNameId);
        sql.append(" FROM ");
        sql.append(genTbName);
        sql.append(" WHERE ");
        sql.append(genClNameGen);
        sql.append(" = '" + typeCode + "' ");

        try {
            ResultSet rs = getStatement().executeQuery(sql.toString());
            while (rs.next()) {
                resultName = rs.getString("thename");
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultName;
    }

    /**
     * 得到查询語句字串
     * 
     * @author fszhouzz@qq.com
     * @time 2018年10月8日 下午10:58:18
     * @param typeArr
     * @param cha
     * @param code
     * @return
     */
    private static String getQuerySql(String[] typeArr, String cha, String code) {
        // 輸入法類型條件
        List<String> types = new ArrayList<String>(Arrays.asList(typeArr));
        boolean unionIntersect = false;
        for (int i = types.size() - 1; i >= 0; i--) {
            if (TYPE_CODE_CJINTERSECT.equals(types.get(i))) {
                unionIntersect = true;
                types.remove(i);
            }
        }

        String typeCodeSql = " and " + mbClNameGen + " = '" + types.get(0) + "' ";
        // 當前輸入條件
        String chaSql = "";
        if (null != cha && !"".equals(cha.trim())) {
            chaSql = " and " + mbClNameVal + " = '" + cha + "' ";
        }
        String codeSql = "";
        if (null != code && !"".equals(code.trim())) {
            codeSql = " and " + mbClNameCod + " = '" + code + "' ";
        }
        // 排序
        String orderSql = " order by " + mbClNameCod + " asc, " + mbClNameOrder + " desc ";

        StringBuilder sql = new StringBuilder();
        sql.append(" select ");
        sql.append(mbClNameId + ", ");
        sql.append(mbClNameGen + ", ");
        sql.append(mbClNameCod + ", ");
        sql.append(mbClNameVal + ", ");
        sql.append(mbClNameOrder);
        sql.append(" from ");
        sql.append(mbTbName);
        sql.append(" where 1=1 ");
        sql.append(typeCodeSql);
        sql.append(chaSql);
        sql.append(codeSql);
        if (unionIntersect) {
            sql.append(" union all select ");
            sql.append(mbClNameIdIntersect + ", ");
            sql.append("'" + TYPE_CODE_CJINTERSECT + "' as " + mbClNameGen + ", ");
            sql.append(mbClNameCodIntersect + ", ");
            sql.append(mbClNameValIntersect + ", ");
            String colName = getColOrderName(types.get(0));
            sql.append(colName + " " + mbClNameOrder);
            sql.append(" from ");
            sql.append(mbTbNameIntersect);
            sql.append(" where 1=1 ");
            sql.append(chaSql);
            sql.append(codeSql);
        }
        return "select * from (" + sql.toString() + ") t " + orderSql;
    }

    private static String getColOrderName(String typeName) {
        String colName = mbClNameOrder;
        if (TYPE_CODE_CJGEN6.equals(typeName)) {
            colName = mbClNameOrderIntersect6;
        } else if (TYPE_CODE_CJGEN5.equals(typeName)) {
            colName = mbClNameOrderIntersect5;
        } else if (TYPE_CODE_CJGEN3.equals(typeName)) {
            colName = mbClNameOrderIntersect3;
        } else if (TYPE_CODE_CJGENYAHOO.equals(typeName)) {
            colName = mbClNameOrderIntersectyh;
        } else if (TYPE_CODE_CJGENMS.equals(typeName)) {
            colName = mbClNameOrderIntersectms;
        }
        return colName;
    }

}
