package com.qys.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:518ad-ccn date:Dec 13, 2011
 * describe：24节气
 * 注：程序中使用到的计算节气公式、节气世纪常量等相关信息参照http://www.360doc.com/content/11/0106/22/5281066_84591519.shtml，
 * 程序的运行得出的节气结果绝大多数是正确的，有少数部份是有误差的
 */
public class _24SolarTerms {
    private static final double D = 0.2422;
    private final static Map<String, Integer[]> INCREASE_OFFSETMAP = new HashMap<String, Integer[]>();//+1偏移
    private final static Map<String, Integer[]> DECREASE_OFFSETMAP = new HashMap<String, Integer[]>();//-1偏移

    /**
     * 24节气
     **/
    private static enum SolarTermsEnum {
        LICHUN,//--立春
        YUSHUI,//--雨水
        JINGZHE,//--惊蛰
        CHUNFEN,//春分
        QINGMING,//清明
        GUYU,//谷雨
        LIXIA,//立夏
        XIAOMAN,//小满
        MANGZHONG,//芒种
        XIAZHI,//夏至
        XIAOSHU,//小暑
        DASHU,//大暑
        LIQIU,//立秋
        CHUSHU,//处暑
        BAILU,//白露
        QIUFEN,//秋分
        HANLU,//寒露
        SHUANGJIANG,//霜降
        LIDONG,//立冬
        XIAOXUE,//小雪
        DAXUE,//大雪
        DONGZHI,//冬至
        XIAOHAN,//小寒
        DAHAN;//大寒
    }

    static {
        DECREASE_OFFSETMAP.put(SolarTermsEnum.YUSHUI.name(), new Integer[]{2026});//雨水
        INCREASE_OFFSETMAP.put(SolarTermsEnum.CHUNFEN.name(), new Integer[]{2084});//春分
        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOMAN.name(), new Integer[]{2008});//小满
        INCREASE_OFFSETMAP.put(SolarTermsEnum.MANGZHONG.name(), new Integer[]{1902});//芒种
        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAZHI.name(), new Integer[]{1928});//夏至
        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOSHU.name(), new Integer[]{1925, 2016});//小暑
        INCREASE_OFFSETMAP.put(SolarTermsEnum.DASHU.name(), new Integer[]{1922});//大暑
        INCREASE_OFFSETMAP.put(SolarTermsEnum.LIQIU.name(), new Integer[]{2002});//立秋
        INCREASE_OFFSETMAP.put(SolarTermsEnum.BAILU.name(), new Integer[]{1927});//白露
        INCREASE_OFFSETMAP.put(SolarTermsEnum.QIUFEN.name(), new Integer[]{1942});//秋分
        INCREASE_OFFSETMAP.put(SolarTermsEnum.SHUANGJIANG.name(), new Integer[]{2089});//霜降
        INCREASE_OFFSETMAP.put(SolarTermsEnum.LIDONG.name(), new Integer[]{2089});//立冬
        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOXUE.name(), new Integer[]{1978});//小雪
        INCREASE_OFFSETMAP.put(SolarTermsEnum.DAXUE.name(), new Integer[]{1954});//大雪
        DECREASE_OFFSETMAP.put(SolarTermsEnum.DONGZHI.name(), new Integer[]{1918, 2021});//冬至

        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOHAN.name(), new Integer[]{1982});//小寒
        DECREASE_OFFSETMAP.put(SolarTermsEnum.XIAOHAN.name(), new Integer[]{2019});//小寒

        INCREASE_OFFSETMAP.put(SolarTermsEnum.DAHAN.name(), new Integer[]{2082});//大寒
    }

    //定义一个二维数组，第一维数组存储的是20世纪的节气C值，第二维数组存储的是21世纪的节气C值,0到23个，依次代表立春、雨水...大寒节气的C值
    private static final double[][] CENTURY_ARRAY =
            {{4.6295, 19.4599, 6.3826, 21.4155, 5.59, 20.888, 6.318, 21.86, 6.5, 22.2, 7.928, 23.65, 8.35,
                    23.95, 8.44, 23.822, 9.098, 24.218, 8.218, 23.08, 7.9, 22.6, 6.11, 20.84}
                    , {3.87, 18.73, 5.63, 20.646, 4.81, 20.1, 5.52, 21.04, 5.678, 21.37, 7.108, 22.83,
                    7.5, 23.13, 7.646, 23.042, 8.318, 23.438, 7.438, 22.36, 7.18, 21.94, 5.4055, 20.12}};

    private static int LICHUN,//--立春
            YUSHUI,//--雨水
            JINGZHE,//--惊蛰
            CHUNFEN,//春分
            QINGMING,//清明
            GUYU,//谷雨
            LIXIA,//立夏
            XIAOMAN,//小满
            MANGZHONG,//芒种
            XIAZHI,//夏至
            XIAOSHU,//小暑
            DASHU,//大暑
            LIQIU,//立秋
            CHUSHU,//处暑
            BAILU,//白露
            QIUFEN,//秋分
            HANLU,//寒露
            SHUANGJIANG,//霜降
            LIDONG,//立冬
            XIAOXUE,//小雪
            DAXUE,//大雪
            DONGZHI,//冬至
            XIAOHAN,//小寒
            DAHAN;//大寒


    /**
     * @param year 年份
     * @param name 节气的名称
     * @return 返回节气是相应月份的第几天
     */
    public static int getSolarTermNum(int year, String name) {

        double centuryValue = 0;//节气的世纪值，每个节气的每个世纪值都不同
        name = name.trim().toUpperCase();
        int ordinal = SolarTermsEnum.valueOf(name).ordinal();

        int centuryIndex = -1;
        if (year >= 1901 && year <= 2000) {//20世纪
            centuryIndex = 0;
        } else if (year >= 2001 && year <= 2100) {//21世纪
            centuryIndex = 1;
        } else {
            throw new RuntimeException("不支持此年份：" + year + "，目前只支持1901年到2100年的时间范围");
        }
        centuryValue = CENTURY_ARRAY[centuryIndex][ordinal];
        int dateNum = 0;
        /**
         * 计算 num =[Y*D+C]-L这是传说中的寿星通用公式
         * 公式解读：年数的后2位乘0.2422加C(即：centuryValue)取整数后，减闰年数
         */
        int y = year % 100;//步骤1:取年分的后两位数
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {//闰年
            if (ordinal == SolarTermsEnum.XIAOHAN.ordinal() || ordinal == SolarTermsEnum.DAHAN.ordinal()
                    || ordinal == SolarTermsEnum.LICHUN.ordinal() || ordinal == SolarTermsEnum.YUSHUI.ordinal()) {
                //注意：凡闰年3月1日前闰年数要减一，即：L=[(Y-1)/4],因为小寒、大寒、立春、雨水这两个节气都小于3月1日,所以 y = y-1
                y = y - 1;//步骤2
            }
        }
        dateNum = (int) (y * D + centuryValue) - (int) (y / 4);//步骤3，使用公式[Y*D+C]-L计算
        dateNum += specialYearOffset(year, name);//步骤4，加上特殊的年分的节气偏移量
        return dateNum;
    }

    /**
     * 特例,特殊的年分的节气偏移量,由于公式并不完善，所以算出的个别节气的第几天数并不准确，在此返回其偏移量
     *
     * @param year 年份
     * @param name 节气名称
     * @return 返回其偏移量
     */
    public static int specialYearOffset(int year, String name) {
        int offset = 0;
        offset += getOffset(DECREASE_OFFSETMAP, year, name, -1);
        offset += getOffset(INCREASE_OFFSETMAP, year, name, 1);

        return offset;
    }

    public static int getOffset(Map<String, Integer[]> map, int year, String name, int offset) {
        int off = 0;
        Integer[] years = map.get(name);
        if (null != years) {
            for (int i : years) {
                if (i == year) {
                    off = offset;
                    break;
                }
            }
        }
        return off;
    }

    public static String solarTermToString(int year) {
        StringBuffer sb = new StringBuffer();
        sb.append("---").append(year);
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {//闰年
            sb.append(" 闰年");
        } else {
            sb.append(" 平年");
        }

        sb.append("\n")
                .append("立春：2月").append(getSolarTermNum(year, SolarTermsEnum.LICHUN.name()))
                .append("日,雨水：2月").append(getSolarTermNum(year, SolarTermsEnum.YUSHUI.name()))
                .append("日,惊蛰:3月").append(getSolarTermNum(year, SolarTermsEnum.JINGZHE.name()))
                .append("日,春分:3月").append(getSolarTermNum(year, SolarTermsEnum.CHUNFEN.name()))
                .append("日,清明:4月").append(getSolarTermNum(year, SolarTermsEnum.QINGMING.name()))
                .append("日,谷雨:4月").append(getSolarTermNum(year, SolarTermsEnum.GUYU.name()))
                .append("日,立夏:5月").append(getSolarTermNum(year, SolarTermsEnum.LIXIA.name()))
                .append("日,小满:5月").append(getSolarTermNum(year, SolarTermsEnum.XIAOMAN.name()))
                .append("日,芒种:6月").append(getSolarTermNum(year, SolarTermsEnum.MANGZHONG.name()))
                .append("日,夏至:6月").append(getSolarTermNum(year, SolarTermsEnum.XIAZHI.name()))
                .append("日,小暑:7月").append(getSolarTermNum(year, SolarTermsEnum.XIAOSHU.name()))
                .append("日,大暑:7月").append(getSolarTermNum(year, SolarTermsEnum.DASHU.name()))
                .append("日,\n立秋:8月").append(getSolarTermNum(year, SolarTermsEnum.LIQIU.name()))
                .append("日,处暑:8月").append(getSolarTermNum(year, SolarTermsEnum.CHUSHU.name()))
                .append("日,白露:9月").append(getSolarTermNum(year, SolarTermsEnum.BAILU.name()))
                .append("日,秋分:9月").append(getSolarTermNum(year, SolarTermsEnum.QIUFEN.name()))
                .append("日,寒露:10月").append(getSolarTermNum(year, SolarTermsEnum.HANLU.name()))
                .append("日,霜降:10月").append(getSolarTermNum(year, SolarTermsEnum.SHUANGJIANG.name()))
                .append("日,立冬:11月").append(getSolarTermNum(year, SolarTermsEnum.LIDONG.name()))
                .append("日,小雪:11月").append(getSolarTermNum(year, SolarTermsEnum.XIAOXUE.name()))
                .append("日,大雪:12月").append(getSolarTermNum(year, SolarTermsEnum.DAXUE.name()))
                .append("日,冬至:12月").append(getSolarTermNum(year, SolarTermsEnum.DONGZHI.name()))
                .append("日,小寒:1月").append(getSolarTermNum(year, SolarTermsEnum.XIAOHAN.name()))
                .append("日,大寒:1月").append(getSolarTermNum(year, SolarTermsEnum.DAHAN.name()));

        return sb.toString();
    }

    public static void init(int year) {
        LICHUN = getSolarTermNum(year, SolarTermsEnum.LICHUN.name());
        YUSHUI = getSolarTermNum(year, SolarTermsEnum.YUSHUI.name());
        JINGZHE = getSolarTermNum(year, SolarTermsEnum.JINGZHE.name());
        CHUNFEN = getSolarTermNum(year, SolarTermsEnum.CHUNFEN.name());
        QINGMING = getSolarTermNum(year, SolarTermsEnum.QINGMING.name());
        GUYU = getSolarTermNum(year, SolarTermsEnum.GUYU.name());
        LIXIA = getSolarTermNum(year, SolarTermsEnum.LIXIA.name());
        XIAOMAN = getSolarTermNum(year, SolarTermsEnum.XIAOMAN.name());
        MANGZHONG = getSolarTermNum(year, SolarTermsEnum.MANGZHONG.name());
        XIAZHI = getSolarTermNum(year, SolarTermsEnum.XIAZHI.name());
        XIAOSHU = getSolarTermNum(year, SolarTermsEnum.XIAOSHU.name());
        DASHU = getSolarTermNum(year, SolarTermsEnum.DASHU.name());
        LIQIU = getSolarTermNum(year, SolarTermsEnum.LIQIU.name());
        CHUSHU = getSolarTermNum(year, SolarTermsEnum.CHUSHU.name());
        BAILU = getSolarTermNum(year, SolarTermsEnum.BAILU.name());
        QIUFEN = getSolarTermNum(year, SolarTermsEnum.QIUFEN.name());
        HANLU = getSolarTermNum(year, SolarTermsEnum.HANLU.name());
        SHUANGJIANG = getSolarTermNum(year, SolarTermsEnum.SHUANGJIANG.name());
        LIDONG = getSolarTermNum(year, SolarTermsEnum.LIDONG.name());
        XIAOXUE = getSolarTermNum(year, SolarTermsEnum.XIAOXUE.name());
        DAXUE = getSolarTermNum(year, SolarTermsEnum.DAXUE.name());
        DONGZHI = getSolarTermNum(year, SolarTermsEnum.DONGZHI.name());
        XIAOHAN = getSolarTermNum(year, SolarTermsEnum.XIAOHAN.name());
        DAHAN = getSolarTermNum(year, SolarTermsEnum.DAHAN.name());
        return;
    }

    public static String getSolarTerm(int month,int day){
        String SolarTerm="";
        if(month==2) {
            if (day == LICHUN) {
                return "立春";
            } else if (day == YUSHUI) {
                return "雨水";
            }
        }else if(month==3) {
            if (day == JINGZHE) {
                return "惊蛰";
            } else if (day == CHUNFEN) {
                return "春分";
            }
        }else if(month==4) {
            if (day == QINGMING) {
                return "清明";
            } else if (day == GUYU) {
                return "谷雨";
            }
        }else if(month==5){
            if(day==LIXIA){
                return "立夏";
            }else if(day==XIAOMAN){
                return "小满";
            }
        }else if(month==6){
            if(day==MANGZHONG){
                return "芒种";
            }else if(day==XIAZHI){
                return "夏至";
            }
        }else if(month==7){
            if(day==XIAOSHU){
                return "小暑";
            }else if(day==DASHU){
                return "大暑";
            }
        }else if(month==8){
            if(day==LIQIU){
                return "立秋";
            }else if(day==CHUSHU){
                return "处暑";
            }
        }else if(month==9){
            if(day==BAILU){
                return "白露";
            }else if(day==QIUFEN){
                return "秋分";
            }
        }else if(month==10){
            if(day==HANLU){
                return "寒露";
            }else if(day==SHUANGJIANG){
                return "霜降";
            }
        }else if(month==11){
            if(day==LIDONG){
                return "立冬";
            }else if(day==XIAOXUE){
                return "小雪";
            }
        }else if(month==12){
            if(day==DAXUE){
                return "大雪";
            }else if(day==DONGZHI){
                return "冬至";
            }
        }else if(month==1){
            if(day==XIAOHAN){
                return "小寒";
            }else if(day==DAHAN){
                return "大寒";
            }
        }
        return "";
    }

    public static int getLICHUN() {
        return LICHUN;
    }

    public static int getYUSHUI() {
        return YUSHUI;
    }

    public static int getJINGZHE() {
        return JINGZHE;
    }

    public static int getCHUNFEN() {
        return CHUNFEN;
    }

    public static int getQINGMING() {
        return QINGMING;
    }

    public static int getGUYU() {
        return GUYU;
    }

    public static int getLIXIA() {
        return LIXIA;
    }

    public static int getXIAOMAN() {
        return XIAOMAN;
    }

    public static int getMANGZHONG() {
        return MANGZHONG;
    }

    public static int getXIAZHI() {
        return XIAZHI;
    }

    public static int getXIAOSHU() {
        return XIAOSHU;
    }

    public static int getDASHU() {
        return DASHU;
    }

    public static int getLIQIU() {
        return LIQIU;
    }

    public static int getCHUSHU() {
        return CHUSHU;
    }

    public static int getBAILU() {
        return BAILU;
    }

    public static int getQIUFEN() {
        return QIUFEN;
    }

    public static int getHANLU() {
        return HANLU;
    }

    public static int getSHUANGJIANG() {
        return SHUANGJIANG;
    }

    public static int getLIDONG() {
        return LIDONG;
    }

    public static int getXIAOXUE() {
        return XIAOXUE;
    }

    public static int getDAXUE() {
        return DAXUE;
    }

    public static int getDONGZHI() {
        return DONGZHI;
    }

    public static int getXIAOHAN() {
        return XIAOHAN;
    }

    public static int getDAHAN() {
        return DAHAN;
    }
}



