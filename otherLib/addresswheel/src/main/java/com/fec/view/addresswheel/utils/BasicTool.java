package com.fec.view.addresswheel.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.Time;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author liubp
 */
public class BasicTool {

    /**
     * 检测字符串是否为空，
     *
     * @param str
     * @return 是空 返回 <code>false</code> 否则返回 <code>true</code>
     */
    public static boolean isNotEmpty(CharSequence str) {
        if (null == str)
            return false;
        if ((str.toString()).trim().equalsIgnoreCase("null"))
            return false;
        return (str.toString()).trim().length() > 0;
    }

    public static boolean isEmpty(CharSequence str) {
        return !isNotEmpty(str);
    }


    /**
     * 去掉"-"号
     * @param str
     * @return
     */
    public static String toAbs(String str) {

        return str.replaceAll("-", "");
    }

    public static boolean isEmptyForCart(JSONObject keyObj)
            throws JSONException {
        if (null == keyObj || "{}".equals(keyObj.toString())
                || !BasicTool.isNotEmpty(keyObj.getString("value")))
            return false;
        else
            return true;
    }

    /**
     * @param str
     * @return
     * @function 手机号的验证, 验证通过则返回ture，否则为false
     */
    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("((13[0-9])|(15[0-9])|(14[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(str);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getCurString() {
        long msg = System.currentTimeMillis();

        return Long.toString(msg);

    }


    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        //如果银行卡位数是15位，就不做验证，直接通过
        if (cardId != null && cardId.length() == 15) {
            return true;
        }
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }


    /**
     * 验证银行卡长度
     */
    public static boolean getBankLength(String cartNumber) {
        if (cartNumber == null) {
            return false;
        }
        return cartNumber.matches("^(\\\\d{15}||\\\\d{16}||\\\\d{17}||\\\\d{18}||\\\\d{19})$");
    }


    /**
     * 将单个list转成json字符串
     *
     * @param list
     * @return
     * @throws Exception
     */
    public static String listToJsonString(List<Map<String, Object>> list)
            throws Exception {
        String jsonL = "";
        StringBuffer temp = new StringBuffer();
        temp.append("[");
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> m = list.get(i);
            if (i == list.size() - 1) {
                temp.append(mapToJsonObj(m));
            } else {
                temp.append(mapToJsonObj(m) + ",");
            }
        }
        if (temp.length() > 1) {
            temp = new StringBuffer(temp.substring(0, temp.length()));
        }
        temp.append("]");
        jsonL = temp.toString();
        return jsonL;
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            //注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分
            verCode = context.getPackageManager().getPackageInfo(
                    "com.fecmobile.electronicfive", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verCode;
    }


    /**
     * 区号+座机号码+分机号码
     *
     * @param fixedPhone
     * @return
     */
    public static boolean isFixedPhone(String fixedPhone) {
        String reg = "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
        return Pattern.matches(reg, fixedPhone);
    }


    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verName;
    }

    /**
     * 对比本地与线上的版本号
     * */
    public static boolean needUpdateV2(String local, String online) {


        boolean need = false;

        if (local != null && online != null) {

            String[] onlines = online.split("\\.");

            String[] locals = local.split("\\.");



            // 2.0.0-1.0.0
            // 2.1.0-2.0.0
            // 2.1.1-2.1.0


            //3.0.0-4.0.0
            //3.1.0-3.2.0
            //3.1.1-3.2.0

            if (Integer.parseInt(onlines[0]) > Integer.parseInt(locals[0])) {
                need = true;
//                Logger.e("1-1");

            } else if (Integer.parseInt(onlines[0]) == Integer.parseInt(locals[0])) {
//                Logger.e("1-2");
                if (Integer.parseInt(onlines[1]) > Integer.parseInt(locals[1])) {
                    need = true;
//                    Logger.e("1-2-1");

                } else if (Integer.parseInt(onlines[1]) == Integer.parseInt(locals[1])) {
//                    Logger.e("1-2-2");
                    if (Integer.parseInt(onlines[2]) > Integer.parseInt(locals[2])) {
//                        Logger.e("1-2-2-1");
                        need = true;
                    } else {
//                        Logger.e("1-2-2-2");
                        need = false;
                    }

                } else if (Integer.parseInt(onlines[1]) < Integer.parseInt(locals[1])) {
                    need = false;
//                    Logger.e("1-2-3");
                }


            } else if (Integer.parseInt(onlines[0]) < Integer.parseInt(locals[0])) {
                need = false;
//                Logger.e("1-3");
            }


//            if (Integer.parseInt(onlines[0]) > Integer.parseInt(locals[0])) {
//                return true;
//            } else if (Integer.parseInt(onlines[1]) > Integer.parseInt(locals[1])) {
//                return true;
//            } else if (Integer.parseInt(onlines[2]) > Integer.parseInt(locals[2])) {
//                return true;
//            } else {
//                return false;
//            }

//            for (int i = 0; i < onlines.length; i++) {
//
//                if (Integer.parseInt(onlines[i]) > Integer.parseInt(locals[i])) {
//                    return true;
//                }
//            }

        }
        return need;


    }

    /**
     * 获取合适型两个时间差
     *
     * @param millis0   毫秒时间戳1
     * @param millis1   毫秒时间戳2
     * @param precision 精度
     *                  <p>precision = 0，返回null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</p>
     * @return 合适型两个时间差
     */
    public static int getFitTimeSpanV2(long millis0, long millis1,int precision) {
        return millis2FitTimeSpanV2(Math.abs(millis0 - millis1),precision);
    }

    @SuppressLint("DefaultLocale")
    public static int millis2FitTimeSpanV2(long millis, int precision) {
        if (millis <= 0) return 0;
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        long mode = 0;
        precision = Math.min(precision, 5);
        mode = millis / unitLen[precision];
        return (int) mode;
    }


    /**
     * 返回今天、昨天、2019-10-10 三种格式
     *
     * @param date
     * @return
     */
    public static String isNow(String date) {
        if (BasicTool.isEmpty(date)) {
            return "";
        }

        String dateF = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateF);
        String nowDay = "";
        try {
            nowDay = sdf.format(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //今天
        Calendar cal = Calendar.getInstance();
        String yday = sdf.format(cal.getTime());
        if (yday.equals(nowDay)) {
            return "今天";
        }

        //昨天
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        yday = sdf.format(cal.getTime());
        if (yday.equals(nowDay)) {
            return "昨天";
        }

        return nowDay;
    }

    /**
     * 将map数据解析出来，并拼接成json字符串
     *
     * @param map
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static JSONObject mapToJsonObj(Map map) throws Exception {
        JSONObject json = null;
        StringBuffer temp = new StringBuffer();
        if (!map.isEmpty()) {
            temp.append("{");
            // 遍历map
            Set set = map.entrySet();
            Iterator i = set.iterator();
            while (i.hasNext()) {
                Map.Entry entry = (Map.Entry) i.next();
                String key = (String) entry.getKey();

                Object value = entry.getValue();

                temp.append("\"" + key + "\":");

                if (null == value || "".equals(value)) {
                    temp.append("\"\"" + ", ");
                } else if (value instanceof Map<?, ?>) {
                    temp.append(mapToJsonObj((Map<String, Object>) value) + ",");
                } else if (value instanceof List<?>) {
                    temp.append(listToJsonString((List<Map<String, Object>>) value)
                            + ",");
                } else if (value instanceof String) {
                    temp.append("\"" + String.valueOf(value) + "\",");
                } else {
                    temp.append(String.valueOf(value) + ",");
                }

            }
            if (temp.length() > 1) {
                String mString = temp.toString();
                mString = mString.trim();

                temp = new StringBuffer(mString.substring(0,
                        mString.length() - 1));
            }

            temp.append("}");

            json = new JSONObject(temp.toString());
        }
        return json;
    }

    /**
     * 将json 对象转换为Map 对象
     *
     * @param jsonString
     * @return
     */
    public static Map<String, Object> jsonToMap(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = (String) keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把json 转换为 ArrayList 形式
     *
     * @return
     */
    public static List<Map<String, Object>> jsonArrToList(String jsonString) {
        List<Map<String, Object>> list = null;

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            JSONObject jsonObject;

            list = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                list.add(jsonToMap(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 把json 转换为 ArrayList 形式
     *
     * @return
     */
    public static List<Map<String, Object>> jsonArrToList(JSONArray jsonArray) {
        List<Map<String, Object>> list = null;

        try {
            JSONObject jsonObject;
            list = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                list.add(jsonToMap(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param gap 获取时间的间隔，要获取之前的日期则为负，如获取前一周的时间，则为-7；反之当前日期之后的则为正
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateBefore(int gap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar now = Calendar.getInstance();

        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) + gap);

        return sdf.format(now.getTime());
    }

    /**
     * 获取当前日期，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCruDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(new Date());
    }


    /**
     * 获取当前日期，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getOrderTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        return sdf.format(new Date());
    }

    /**
     * 日期时间字符串转换为日期字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateTimeToDate(String datatime, int flag) {
        if (BasicTool.isEmpty(datatime)) {
            return "";
        }
        SimpleDateFormat dataTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = dataTime.parse(datatime);
//            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            String format = "";
            if (flag == 1) {
                format = "MM/dd HH:mm";
            } else if (flag == 2) {
                format = "yyyy-MM-dd HH:mm";
            } else if (flag == 3) {
                format = "yyyy-MM-dd";
            } else if (flag == 4) {
                format = "MM-dd HH:mm";
            }
            SimpleDateFormat sdf2 = new SimpleDateFormat(format);

            return sdf2.format(date);
        } catch (ParseException e) {

        }
        return null;
    }

    private static final double EARTH_RADIUS = 6378137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2,
                                     double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 缩放Bitmap图片
     **/
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {

        int w = bitmap.getWidth();

        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();

        float scaleWidth = ((float) width / w);

        float scaleHeight = ((float) height / h);

        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出

        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

        return newbmp;

    }

    /**
     * 把Bitmap用Base64编码并返回生成的字符串
     */
    public static String bitmaptoString(Bitmap bitmap) {
        String string = null;

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        bitmap.compress(CompressFormat.JPEG, 100, bStream);

        byte[] bytes = bStream.toByteArray();

        string = Base64.encodeToString(bytes, Base64.DEFAULT);

        return string;
    }


    /**
     * 根据Object obj以及key获取对应的value值
     *
     * @param obj 目标对象
     * @param key 待取值的key
     * @return
     */
    public static String getValue(JSONObject obj, String key) {
        String value = "";

        if (obj.has(key)) {
            try {
                value = obj.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return value;
    }

    public static Drawable getDrawable(String urlpath) {
        Drawable d = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            d = Drawable.createFromStream(in, "abc.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return d;
    }


    /**
     * 日期时间比较
     *
     * @param DATE 输入的日期、时间
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean compare_date(String DATE, boolean hasHour) {
        Time time = new Time();
        time.setToNow();
        String DATE2;
        SimpleDateFormat df;
        if (hasHour) {
            DATE2 = time.year + "-" + (time.month + 1) + "-" + time.monthDay
                    + " " + time.hour + ":" + time.minute;
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else {
            DATE2 = time.year + "-" + (time.month + 1) + "-" + time.monthDay;
            df = new SimpleDateFormat("yyyy-MM-dd");
        }
        //L.e("DATE2当前系统时间", DATE2);


        try {
            Date dt1 = df.parse(DATE);
            Date dt2 = df.parse(DATE2);

            if (hasHour) {
                if (dt1.getTime() >= dt2.getTime()) {
                    return true;
                } else if (dt1.getTime() < dt2.getTime()) {
                    return false;
                }

            } else {
                if (dt1.getTime() > dt2.getTime()) {
                    return true;
                } else if (dt1.getTime() <= dt2.getTime()) {
                    return false;
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }


    /**
     * 日期时间比较
     *
     * @param DATE 输入的日期、时间
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean compare_date(String DATE, String DATE2, boolean hasHour) {
        Time time = new Time();
        time.setToNow();

        SimpleDateFormat df;
        if (hasHour) {
//            DATE2 = time.year + "-" + (time.month + 1) + "-" + time.monthDay
//                    + " " + time.hour + ":" + time.minute;
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else {
//            DATE2 = time.year + "-" + (time.month + 1) + "-" + time.monthDay;
            df = new SimpleDateFormat("yyyy-MM-dd");
        }
        //L.e("DATE2", DATE2);


        try {
            Date dt1 = df.parse(DATE);
            Date dt2 = df.parse(DATE2);

            if (hasHour) {
                if (dt1.getTime() >= dt2.getTime()) {
                    return true;
                } else if (dt1.getTime() < dt2.getTime()) {
                    return false;
                }

            } else {
                if (dt1.getTime() > dt2.getTime()) {
                    return true;
                } else if (dt1.getTime() <= dt2.getTime()) {
                    return false;
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    public static String getEncryptionMobile(String realmobile) {
        if (realmobile.length() > 7) {
            String str = realmobile.substring(0, 3) + "****" + realmobile.substring(7, realmobile.length());
        }
        return realmobile;
    }

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 获取字符串，过滤空、NULL
     *
     * @param object
     * @return
     */
    public static String valueOf(Object object) {
        String str = String.valueOf(object);
        if (isNotEmpty(str)) {
            return str;
        } else {
            return "";
        }
    }

    /**
     * 判断字符串是否包含正数或小数
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) return false;
        Pattern pattern = Pattern.compile("^[0-9]+([.]{1}[0-9]+){0,1}$");
        return pattern.matcher(str).matches();
    }

    public static boolean isNotNumeric(String str) {
        return !isNumeric(str);
    }

    /**
     * 字符串转int 非法返回0
     *
     * @param str
     * @return
     */
    public static int transToInt(String str) {
        if (isNumeric(str)) {
            return Integer.valueOf(str);
        }
        return 0;
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {

        long time = System.currentTimeMillis();

        long timeD = time - lastClickTime;

        if (0 < timeD && timeD < 800) {

            return true;

        }

        lastClickTime = time;

        return false;

    }


    private static String reverseArray(String[] Array) {
        String[] new_array = new String[Array.length];
        String str = "";
        for (int i = 0; i < Array.length; i++) {
            // 反转后数组的第一个元素等于源数组的最后一个元素：
            new_array[i] = Array[Array.length - i - 1];
        }
        for (int i = 0; i < new_array.length; i++) {
            str += new_array[i] + ",";
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }


    public static String getNewString(String oldStr) {
        String newStr = oldStr;
        if (BasicTool.isNotEmpty(oldStr)) {
            if (oldStr.contains(" ")) {
                newStr = oldStr.replaceAll(" ", "");
            }
        }
        return newStr;
    }


    /**
     * 检测程序运行状态
     */
    public static boolean checkAppRunState(Context context, String packageName) {

        boolean isAppRunning = false;

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> list = activityManager.getRunningTasks(20);

        for (ActivityManager.RunningTaskInfo info : list) {
            String name = info.topActivity.getPackageName();
            if (name.equals(packageName)) {

                isAppRunning = true;
                break;
            } else {
                //L.e("name: " + name);
            }
        }
       // L.e("isAppRunning: " + isAppRunning);
        return isAppRunning;
    }


    //通过反射设置Tablayout下划线的长度
    public static void setIndicator(TableLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }

    /**
     * @param remark
     * @param content 给content这个内容设置颜色color
     * @param end
     * @param color
     * @return
     */
    public static SpannableString setTextPartColor(String remark, String content, String end, int color) {
        SpannableString spannableSale = new SpannableString(remark + content + end);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableSale.setSpan(colorSpan, remark.length(), remark.length() + content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableSale;
    }


}