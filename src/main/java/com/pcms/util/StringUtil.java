package com.pcms.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作的工具类，所有方法均是静态方法
 *
 * @author Chen.Biao
 * @version V1.0.0
 * @Title: StringUtil.java
 * @Package com.sand.util
 * @date 2015-3-29
 */
public class StringUtil {
    /**
     * 分号
     */
    public static final String SEMICOLON = ";";
    /**
     * 冒号
     */
    public static final String COLON = ":";
    /**
     * 逗号
     */
    public static final String COMMA = ",";
    /**
     * '/'
     */
    public static final String SPLIT = "/";
    /**
     * 下划线
     */
    public static final String UNDERLINE = "_";

    public static final int FRONT = 0;

    public static final int BACK = 1;

    static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();

    /**
     * 拆分字符串
     *
     * @param split--->间隔符
     * @param sourceStr--->要拆分的字符 备注：一些特殊字符的参数split必须转义，例如像="!@#$%^&*()"等传入时必须在字符前加上\\
     * @return
     */
    public static String[] split(String split, String sourceStr) {
        int n = getExistCountInStr(split, sourceStr);
        ++n;// // 已split拆分的字符的数组长度(包括空的字符也算是一个数组元素)(例如'a/aa//'对应的n就是4)
        String[] wholeStrs = sourceStr.split(split);
        if (wholeStrs.length != n) {
            String[] aWholeStrs = new String[n];
            System.arraycopy(wholeStrs, 0, aWholeStrs, 0, wholeStrs.length);
            Arrays.fill(aWholeStrs, wholeStrs.length, aWholeStrs.length, "");
            return aWholeStrs;
        }
        return wholeStrs;
    }

    /**
     * 得到字符在在目标字符串中出现的次数
     *
     * @param sourceStr
     * @return
     */
    public static int getExistCountInStr(String str, String sourceStr) {
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(sourceStr);
        int n = 0;
        while (matcher.find()) {// 统计str在sourceStr的个数
            n++;
        }
        return n;
    }

    /**
     * 用指定的字符填充指定的字符串达到指定的长度，并返回填充之后的字符串 <br>
     *
     * @param p_scr    待填充的字符串
     * @param p_fill   填充的字符
     * @param p_length 填充之后的字符串总长度
     * @return String 填充之后的字符串
     */
    public static String fill(String p_scr, char p_fill, int p_length) {
        return fill(p_scr, p_fill, p_length, FRONT);
    }

    /**
     * 用指定的字符填充指定的字符串达到指定的长度，并返回填充之后的字符串<br>
     *
     * @param p_scr     待填充的字符串
     * @param p_fill    填充的字符
     * @param p_length  填充之后的字符串总长度
     * @param direction 填充方向，SerialPart.FRONT 前面，SerialPart.BACK后面
     * @return String 填充之后的字符串
     */
    public static String fill(String p_scr, char p_fill, int p_length,
                              int direction) {
        /* 如果待填充字符串的长度等于填充之后字符串的长度，则无需填充直接返回 */
        if (p_scr.length() == p_length) {
            return p_scr;
        }
        /* 初始化字符数组 */
        char[] fill = new char[p_length - p_scr.length()];
        /* 填充字符数组 */
        Arrays.fill(fill, p_fill);
        /* 根据填充方向，将填充字符串与源字符串进行拼接 */
        switch (direction) {
            case FRONT:
                return String.valueOf(fill).concat(p_scr);
            case BACK:
                return p_scr.concat(String.valueOf(fill));
            default:
                return p_scr;
        }
    }

    public static List<Integer> toInteger(List<String> convert) {
        List<Integer> converted = new ArrayList<Integer>();
        for (String id : convert) {
            converted.add(new Integer(id));
        }
        return converted;
    }

    /**
     * 将String类型的id转换为Interger的id
     *
     * @param strIds
     */
    public static Collection<Integer> toIntegerIds(Collection<String> strIds) {
        Collection<Integer> setIds = new HashSet<Integer>();
        for (Iterator<String> iterator = strIds.iterator(); iterator.hasNext(); ) {
            Integer id = Integer.parseInt(iterator.next());
            setIds.add(id);
        }
        return setIds;
    }

    /**
     * 将String Integer,String [],Integer []类型的id转化为类型为Integer的集合
     *
     * @param objIds
     * @return
     */
    public static Collection<Integer> toIntegerIds(Object objIds) {
        Collection<Integer> setIds = new HashSet<Integer>();
        if (objIds instanceof String && !"".equals(objIds)) {
            setIds.add(Integer.valueOf(objIds.toString()));
        } else if (objIds instanceof String[]) {
            String[] ids = (String[]) objIds;
            setIds.addAll(toIntegerIds(Arrays.asList(ids)));
        } else if (objIds instanceof Integer) {
            setIds.add((Integer) objIds);
        } else if (objIds instanceof Integer[]) {
            setIds.addAll(Arrays.asList((Integer[]) objIds));
        }
        return setIds;
    }


    /**
     * 转换为String
     *
     * @param obj
     * @return String
     * @Title: toString
     */
    public static String toString(Object obj) {
        try {
            if (obj != null)
                return obj.toString();
            else
                return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 转换为Integer
     *
     * @param str
     * @return Integer
     * @Title: toInteger
     */
    public static Integer toInteger(String str) {
        try {
            if (str != null)
                return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
        return null;
    }


    /**
     * 转换为Integer
     *
     * @param obj
     * @return Integer
     * @Title: toInteger
     */
    public static Integer toInteger(Object obj) {
        try {
            if (obj != null)
                return Integer.parseInt(String.valueOf(obj));
        } catch (NumberFormatException e) {
            return null;
        }
        return null;
    }

    public static String[] toStringArray(Object obj) {
        if (obj == null)
            return new String[]{};
        else if (obj instanceof String[])
            return (String[]) obj;
        else {
            String str = String.valueOf(obj);
            return StringUtils.isNotBlank(str) ? new String[]{str}
                    : new String[]{};
        }
    }

    public static String toStringForNull(String str) {
        if (str == null)
            return "";
        else
            return str;
    }

    /**
     * 判断当前值是否在当前的范围值中 (例: 1 在 {1,2,3} 中 ,2 不在 {1,3,4} 中 )
     *
     * @param targetStr
     * @param chkStr
     * @return
     */
    public static boolean checkValueInRangeStr(String targetStr, String chkStr) {
        if (targetStr.replaceAll("\\{", COMMA).replaceAll("\\}", COMMA)
                .indexOf(chkStr) > -1)
            return true;
        else
            return false;
    }

    /**
     * 将对象转换成字符串类型,如果是null,则为""
     *
     * @param obj
     * @return
     */
    public static String toStringForNull(Object obj) {
        if (obj == null)
            return "";
        else
            return obj.toString();
    }

    /**
     * 比较两个对象,如果相同(null和""是相同的)返回true,不同返回false
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean checkStringsEqual(Object str1, Object str2) {
        if (!toStringForNull(str1).equals("")) {
            if (str1.equals(str2))
                return true;
        } else {
            if (toStringForNull(str1).equals("")
                    && toStringForNull(str2).equals(""))
                return true;
        }
        return false;
    }

    /**
     * 返回单子的异常单号
     *
     * @param preString
     * @param oldNumber
     * @return
     */
    public static String getBillNumber(String preString, String oldNumber) {
        StringBuffer sb = new StringBuffer();
        sb.append(preString);
        sb.append(DateUtil.getDate("yyyyMMdd"));
        if (oldNumber == null || oldNumber.equals(""))
            sb.append("0001");
        else {
            int sequence = Integer.parseInt(oldNumber.substring(preString
                    .length() + 8));
            String targetString = Integer.toString(sequence + 1);
            for (int i = 0; i < oldNumber.substring(preString.length() + 8)
                    .length()
                    - targetString.length(); i++)
                sb.append("0");
            sb.append(targetString);
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否是空串或者NULL
     *
     * @param str
     * @return 空串和NULL时 true,否则 false
     */
    public static boolean checkNullString(String str) {
        if (str == null || str.trim().equals(""))
            return true;
        else
            return false;
    }

    /**
     * 转化对象类型为java.sql.Timestamp
     *
     * @param p_Obj 待转化对象
     * @return 转化后对象
     */
    public static Timestamp converTimestamp(Object p_Obj) {
        String DATE_Pattern = "\\d{4}[-/]\\d{1,2}[-/]\\d{1,2}(\\s\\d{1,2}:\\d{1,2}(:\\d{1,2})?)?";
        if (p_Obj == null || !Pattern.matches(DATE_Pattern, p_Obj.toString())) {
            throw new RuntimeException("无法转换 " + p_Obj.toString()
                    + " 至TIMESTAMP类型");
        }
        StringBuffer temp = new StringBuffer();
        StringTokenizer st = new StringTokenizer(p_Obj.toString(), "-,/,:, ");
        if (st.countTokens() == 3) // 如2002-12-21
        {
            temp.append(p_Obj.toString());
            temp.append(" 00:00:00.0");
        } else if (st.countTokens() == 5) // 如2003-12-21 22:33
        {
            temp.append(p_Obj.toString());
            temp.append(":00.0");
        } else // 如2003-12-21 22:33:00
        {
            temp.append(p_Obj.toString());
            temp.append(".0");
        }
        return Timestamp.valueOf(temp.toString().replace('/', '-'));
    }


    /**
     * 通过struts 2.0的checkboxlist标签上传到后台的字符串通过该方法转换成系统需要的格式 例如:上传的是1, 2,
     * 3这样的字符串,需要变成{1,2,3}
     *
     * @param busiType
     * @return
     */
    public static String getBusiTypeStr(String busiType) {
        String tmpStr = busiType.replaceAll(" ", "");
        return "{" + tmpStr + "}";
    }


    /**
     * 字符串转List
     *
     * @param p_Param String 待转字符串
     * @param p_Delim String 字符串分隔符
     * @return List
     */
    @SuppressWarnings("rawtypes")
    public static List String2List(String p_Param, String p_Delim) {
        if (p_Param == null || p_Param.trim().equals("")) {
            return null;
        }
        StringTokenizer st;
        if (p_Delim != null) {
            st = new StringTokenizer(p_Param, p_Delim);
        } else {
            st = new StringTokenizer(p_Param);
        }
        List<String> result = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            result.add(st.nextToken());
        }
        return result;
    }

    public static String null2Blank(Object obj) {
        if (obj != null)
            return obj.toString().replaceAll(" ", "").trim();
        else
            return "";
    }

    /**
     * list转字符串
     *
     * @param p_Param List 待转list
     * @param p_Delim String 字符串分隔符
     * @return String
     */
    @SuppressWarnings("rawtypes")
    public static String list2String(List p_Param, String p_Delim) {
        if (p_Param == null || p_Param.size() <= 0) {
            return null;
        }
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < p_Param.size(); i++) {
            temp.append(p_Delim);
            temp.append(p_Param.get(i));
            temp.append(p_Delim);
        }
        return temp.toString();
    }

    /**
     * 唯一数组中的重复值并删除空值, 返回值[0]为更改后的数组, [1]为新数组对应老数组的下标位置
     *
     * @param s
     * @return Object[]
     */
    public static Object[] uniqueArrayValue(String[] s) {
        if (s == null) {
            return new Object[2];
        }
        List<String> list = new ArrayList<String>();
        List<String> posList = new ArrayList<String>();
        for (int i = 0; i < s.length; i++) {
            if (!s[i].trim().equals("") && !list.contains(s[i].trim())) {
                list.add(s[i]);
                posList.add(String.valueOf(i));
            }
        }
        Object[] objs = new Object[2];
        objs[0] = list.toArray(new String[list.size()]);
        objs[1] = posList.toArray(new String[posList.size()]);
        return objs;
    }

    /**
     * 转换Double类型
     *
     * @param dbStr
     * @return
     */
    public static Double parseDouble(String dbStr) {
        if (dbStr != null && !dbStr.equals("")) {
            return Double.parseDouble(dbStr);
        } else {
            return null;
        }
    }

    /**
     * 将源串中最后一个老子串换成新子串
     *
     * @param source
     * @param oldString
     * @param newString
     * @return
     */
    public static String replaceLast(String source, String oldString,
                                     String newString) {
        StringBuffer sb = new StringBuffer();
        int index = source.lastIndexOf(oldString);
        for (int i = 0; i < index; i++)
            sb.append(source.charAt(i));
        sb.append(newString);
        for (int i = index + oldString.length(); i < source.length(); i++)
            sb.append(source.charAt(i));
        return sb.toString();
    }

    /**
     * 转义字符
     *
     * @param str
     * @return
     */
    public static String convertString(String str, String sourceSplit,
                                       String targetSplit) {
        String[] strs = str.split("");
        int isSplit = 0;
        String returnStr = "";
        for (String s : strs) {
            if ("\"".equals(s) && isSplit == 0) {
                isSplit = 1;
            } else if ("\"".equals(s) && isSplit == 1) {
                isSplit = 0;
            }
            if (isSplit == 1) {
                if (sourceSplit.equals(s)) {
                    s = targetSplit;
                }
                returnStr += s;
                continue;
            }
            returnStr += s;
        }
        return returnStr;
    }

    /**
     * 反转字符串
     *
     * @param strs
     * @return
     */
    public static String[] convertInverseString(String[] strs,
                                                String sourceSplit, String targetSplit) {
        String[] inverseStrs = new String[strs.length];
        for (int i = 0; i < strs.length; i++) {
            inverseStrs[i] = strs[i].replaceAll(sourceSplit, targetSplit);
        }
        return inverseStrs;
    }

    /**
     * 得到字符串中的数字
     *
     * @return
     */
    public static String getNumberFromString(String targetStr) {
        Pattern p = Pattern.compile("\\d+");
        Matcher matcher = p.matcher(targetStr);
        String str = "";
        if (matcher.find()) {
            str = targetStr.substring(matcher.start());
        }
        return str;
    }

    /**
     * 将指定的字符填充到目标字符中
     * 例如：targetStr="aaaa{0}bbbbb{1}",fillStrs=new Object[]{"你","好"}
     * 返回的字符串则是"aaaa你bbbbb好"
     * 特注：1:如果你fillStrs中有int或Integer的数字的话，会已科学计数法去填充值，
     * 建议fillStrs数组中都是字符串String型的;2:如果targetStr中有'字符'则需要转义''字符''
     */
    public static String fillString(String targetStr, Object... fillStrs) {
        String wholeStr = MessageFormat.format(targetStr, fillStrs);
        return wholeStr;
    }

    /**
     * 得到数据库字符串字段的值
     */
    public static String getSQLString(String str) {
        if (str == null) {
            return null;
        }
        return "'".concat(str).concat("'");
    }

    /**
     * 得到数据库字符串字段的值(非空)
     */
    public static String getNotNullSQLStr(String str) {
        if (str == null) {
            return "''";
        }
        return "'".concat(str).concat("'");
    }

    /**
     * 得到Integer类型的字符型值(此方法主要使用与sql 格式化注入值的时候)
     *
     * @param num
     * @return
     */
    public static String getSQLIntegerForFormat(Integer num) {
        if (num == null)
            return null;
        return num.toString();
    }

    /**
     * 转换String成Base64编码
     **/
    public static String base64Encode(String str) {
        if (str != null && !"".equals(str)) {
            byte[] data = str.getBytes();
            char[] out = new char[((data.length + 2) / 3) * 4];

            for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
                boolean quad = false;
                boolean trip = false;
                int val = (0xFF & (int) data[i]);
                val <<= 8;
                if ((i + 1) < data.length) {
                    val |= (0xFF & (int) data[i + 1]);
                    trip = true;
                }
                val <<= 8;
                if ((i + 2) < data.length) {
                    val |= (0xFF & (int) data[i + 2]);
                    quad = true;
                }
                out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 1] = alphabet[val & 0x3F];
                val >>= 6;
                out[index + 0] = alphabet[val & 0x3F];
            }
            str = new String(out);
        }
        return str;
    }

    /**
     * 中文字符进行UrlCoder转码
     *
     * @param str
     * @return String
     * @Title: urlEncode
     */
    public static String urlEncode(String str) {
        if (str != null && !"".equals(str)) {

            try {
                str = URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    /**
     * 中文解码
     *
     * @param str
     * @return String
     * @Title: urlDecode
     */
    public static String urlDecode(String str) {
        if (str != null && !"".equals(str)) {

            try {
                str = URLDecoder.decode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    /**
     * 转换Base64成String编码
     *
     * @param str
     * @return String
     * @Title: base64Decode
     */
    public static String base64Decode(String str) {
        if (str != null && !"".equals(str)) {
            byte[] data = str.getBytes();
            int len = ((data.length + 3) / 4) * 3;
            if (data.length > 0 && data[data.length - 1] == '=')
                --len;
            if (data.length > 1 && data[data.length - 2] == '=')
                --len;
            byte[] out = new byte[len];
            int shift = 0;
            int accum = 0;
            int index = 0;
            for (int ix = 0; ix < data.length; ix++) {
                int value = codes[data[ix] & 0xFF];
                if (value >= 0) {
                    accum <<= 6;
                    shift += 6;
                    accum |= value;
                    if (shift >= 8) {
                        shift -= 8;
                        out[index++] = (byte) ((accum >> shift) & 0xff);
                    }
                }
            }

            if (index != out.length) {
                throw new Error("miscalculated data length!");
            }
            str = new String(out);
        }
        return str;
    }

    static private byte[] codes = new byte[256];

    static {
        for (int i = 0; i < 256; i++)
            codes[i] = -1;
        for (int i = 'A'; i <= 'Z'; i++)
            codes[i] = (byte) (i - 'A');
        for (int i = 'a'; i <= 'z'; i++)
            codes[i] = (byte) (26 + i - 'a');
        for (int i = '0'; i <= '9'; i++)
            codes[i] = (byte) (52 + i - '0');
        codes['+'] = 62;
        codes['/'] = 63;
    }

    /**
     * 用","分隔的字符串转换成数组
     *
     * @return String[]
     * @Title: getArrayBySplitStr
     */
    public static String[] getArrayBySplitStr(Object obj) {
        String[] objArray = new String[]{};
        String str = null;
        if (obj != null) {
            str = obj.toString();
        }
        if (StringUtils.isNotEmpty(str)) {
            if (str.indexOf(",") != -1) {
                objArray = str.split(",");
            } else {
                objArray = new String[]{str};
            }
        }
        return objArray;
    }

    /**
     * Map里的key转换成 字符串      IN  查询条件使用
     *
     * @param key
     * @param paraMap
     * @param jointFlg 拼接方式：    true: 数组以","拼接;      false:数组以"','"格式拼接;
     * @return String
     * @Title: getStringByKey
     */
    public static String getStringByKey(String key, Map<String, Object> paraMap, boolean jointFlg) {
        String resultStr = "";
        Object obj = paraMap.get(key);
        if (jointFlg) {//","格式拼接
            if (obj instanceof String) {
                resultStr = (String) paraMap.get(key);
            } else if (obj instanceof String[]) {
                String[] objArr = (String[]) paraMap.get(key);
                for (int i = 0; i < objArr.length; i++) {
                    if (StringUtils.isNotEmpty(resultStr)) {
                        resultStr = resultStr.concat("," + objArr[i]);
                    } else {
                        resultStr = objArr[i];
                    }
                }
            }
        } else {//'',''格式拼接
            if (obj instanceof String) {
                String tmpStr = (String) paraMap.get(key);
                if (StringUtils.isNotEmpty(tmpStr)) {
                    resultStr = "'".concat(tmpStr).concat("'");
                }
            } else if (obj instanceof String[]) {
                String[] objArr = (String[]) paraMap.get(key);
                for (int i = 0; i < objArr.length; i++) {
                    if (StringUtils.isNotEmpty(resultStr)) {
                        resultStr = resultStr.concat("','").concat(objArr[i]);
                    } else {
                        resultStr = "'".concat(objArr[i]);
                    }
                }
                if (StringUtils.isNotEmpty(resultStr)) {
                    resultStr = resultStr.concat("'");
                }
            }
        }
        return resultStr;
    }

    /**
     * 追加字符空格
     *
     * @param optType 字符类型      2：数字       其他为String
     * @param sb      导出字符串
     * @param k       长度
     * @return void
     * @Title: appendSpacing
     */
    public static void appendSpacing(Integer optType, StringBuilder sb, int k) {
//		String flg = "\0";//默认空格
        String flg = " ";//默认空格
        if (optType != null && optType == 2) {
            flg = "0";
        }
        for (int i = 0; i < k; i++) {
            sb.append(flg);
        }
    }

    /**
     * 根据类型追加
     *
     * @param str     字符
     * @param optType 字符类型      2：数字       其他为String
     * @param sb      导出字符串
     * @param l       长度
     * @return void
     * @throws UnsupportedEncodingException
     * @Title: appendStr
     */
    public static void appendStr(String str, Integer optType, StringBuilder sb, int l) throws UnsupportedEncodingException {
        if (StringUtils.isNotEmpty(str)) {
            if (str.getBytes("GBK").length <= l) {
                if (optType != null && optType == 2) {//N类型
                    appendSpacing(optType, sb, l - str.getBytes("GBK").length);
                    sb.append(str);
                } else {
                    sb.append(str);
                    appendSpacing(optType, sb, l - str.getBytes("GBK").length);
                }
            } else {
                appendSpacing(optType, sb, l);
            }
        } else {
            appendSpacing(optType, sb, l);
        }
        //sb.append("|");
    }

    public static String getFullStr(String source, Integer optType, int length) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(source)) {
            if (source.getBytes().length <= length) {
                if (optType != null && optType == 2) {//N类型
                    appendSpacing(optType, sb, length - source.getBytes().length);
                    sb.append(source);
                } else {
                    sb.append(source);
                    appendSpacing(optType, sb, length - source.getBytes().length);
                }
            } else {
                appendSpacing(optType, sb, length);
            }
        } else {
            appendSpacing(optType, sb, length);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getFullStr("1", 1, 10));
    }


    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断是否是Integer类型
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (str != null && str.length() < 11 && !"".equals(str.trim())) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);
            Long number = 0l;
            if (isNum.matches()) {
                number = Long.parseLong(str);
            } else {
                return false;
            }
            if (number > 2147483647) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }


    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public static String formatText(String s, Integer length) {
        StringBuffer sb = new StringBuffer(s);
        while (sb.toString().getBytes().length < length) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static String formatTextOpposite(String s, Integer length) {
        StringBuffer sb = new StringBuffer("");
        while (sb.toString().getBytes().length + s.getBytes().length < length) {
            sb.append("0");
        }
        sb.append(s);
        return sb.toString();
    }

    /**
     * 判断是不是全数字
     *
     * @param str
     * @return boolean
     * @Title: isNumeric
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");  //原始正则表达式
        //Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是不是Decimal类型
     *
     * @param str
     * @return boolean
     * @Title: isDecimal
     */
    public static boolean isDecimal(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 转换为Decimal类型
     *
     * @param obj
     * @return BigDecimal
     * @Title: toDecimal
     */
    public static BigDecimal toDecimal(Object obj) {
        try {
            if (obj != null)
                return new BigDecimal(obj.toString());
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param newCharset 目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * List 转换为以,分隔的字符串
     *
     * @param strList
     * @return String
     * @Title: listToString
     */
    public static String listToString(List<String> strList) {
        if (strList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String str : strList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(str);
        }
        return result.toString();
    }

    /**
     * 判断oldObj如果为null 则被新newObj替换,否则返回原有值
     *
     * @param oldObj
     * @param newObj
     * @return
     */
    public static Object ifNull(Object oldObj, Object newObj) {
        if (oldObj != null) {
            return oldObj;
        } else {
            return newObj;
        }
    }

    /**
     * 判断oldObj如果为null 则被新newObj替换,否则返回原有值  并转换为String类型
     *
     * @param oldObj
     * @param newObj
     * @return Object
     * @Title: ifNullStr
     */
    public static String ifNullStr(Object oldObj, Object newObj) {
        if (oldObj != null) {
            return toString(oldObj);
        } else {
            return toString(newObj);
        }
    }


    /**
     * 判断targetObj如果为空(包含null和"") 则被新newObj替换,否则返回原有值  并转换为String类型
     *
     * @param targetObj
     * @param replaceObj
     * @return
     * @author eager 2017年7月31日下午7:04:20
     */
    public static String ifBlankStr(Object targetObj, Object replaceObj) {
        String targetStr = toString(targetObj);
        if (StringUtils.isBlank(targetStr)) {
            return toString(replaceObj);
        }
        return targetStr;
    }

    /**
     * 判断oldObj如果为null或者""  则被新newObj替换,否则返回原有值
     *
     * @param oldObj
     * @param newObj
     * @return Object
     * @Title: ifBank
     */
    public static Object ifBank(Object oldObj, Object newObj) {
        if (oldObj != null && StringUtils.isNotBlank(StringUtil.toString(oldObj))) {
            return oldObj;
        } else {
            return newObj;
        }
    }


    /**
     * 取出字符串前面的0，不包括中间和后面的0
     *
     * @param s
     * @return
     */
    public static String subZero(String s) {
        if (StringUtils.isNotBlank(s)) {
            int index = 0;
            char[] sArry = s.toCharArray();
            for (int i = 0; i < sArry.length; i++) {
                if ('0' != sArry[i]) {
                    index = i;
                    break;
                }
            }
            String sLast = s.substring(index, sArry.length);
            return sLast;
        }
        return null;
    }

    /**
     * 判断str是否包含searchStr
     * <p>  contains("abc", "")      &nbsp;&nbsp;    false
     * <p>  contains("abc", null)    &nbsp;&nbsp;    false
     * <p>  contains("abc", "a")     &nbsp;&nbsp;    true
     *
     * @param str
     * @param searchStr
     * @return boolean
     * @Title: contains
     */
    public static boolean contains(String str, String searchStr) {
        if (str == null || StringUtils.isBlank(searchStr)) {
            return false;
        }
        return str.indexOf(searchStr) >= 0;
    }

}
