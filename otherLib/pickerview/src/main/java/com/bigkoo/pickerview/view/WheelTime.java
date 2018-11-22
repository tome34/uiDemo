package com.bigkoo.pickerview.view;

import android.view.View;
import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.adapter.NumericWheelAdapter;
import com.bigkoo.pickerview.listener.ISelectTimeCallback;
import com.bigkoo.pickerview.sync.SyncBean;
import com.bigkoo.pickerview.utils.ChinaDate;
import com.bigkoo.pickerview.utils.LunarCalendar;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class WheelTime {
    public static final  List<String> LIST_BIG            = Arrays.asList("1", "3", "5", "7", "8", "10", "12");
    public static final  List<String> LIST_LITTLE         = Arrays.asList("4", "6", "9", "11");
    private static final String       TAG                 = "WheelTime";
    private static final int          DEFAULT_START_YEAR  = 1900;
    private static final int          DEFAULT_END_YEAR    = 2100;
    private static final int          DEFAULT_START_MONTH = 1;
    private static final int          DEFAULT_END_MONTH   = 12;
    private static final int          DEFAULT_START_DAY   = 1;
    private static final int          DEFAULT_END_DAY     = 31;
    private static final int          DEFAULT_END_H       = 23;
    private static final int          DEFAULT_START_H     = 0;
    private static final int          DEFAULT_START_M_S   = 0;
    private static final int          DEFAULT_END_M_S     = 59;
    private static final int[]        ALLOWENDARRAY       = new int[] { DEFAULT_END_YEAR, DEFAULT_END_MONTH, DEFAULT_END_DAY, DEFAULT_END_H, DEFAULT_END_M_S, DEFAULT_END_M_S };
    private static final int[]        ALLOWSTARTARRAY     =
        new int[] { DEFAULT_START_YEAR, DEFAULT_START_MONTH, DEFAULT_START_DAY, DEFAULT_START_H, DEFAULT_START_M_S, DEFAULT_START_M_S };
    public static        DateFormat   dateFormat          = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private View      view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_minutes;
    private WheelView wv_seconds;
    private int       gravity;
    private boolean[] type;
    private int startYear  = DEFAULT_START_YEAR;
    private int endYear    = DEFAULT_END_YEAR;
    private int startMonth = DEFAULT_START_MONTH;
    private int endMonth   = DEFAULT_END_MONTH;
    private int startDay   = DEFAULT_START_DAY;
    private int endDay     = DEFAULT_END_DAY; //表示31天的
    private int startH     = DEFAULT_START_H;
    private int endH       = DEFAULT_END_H;
    private int startM     = DEFAULT_START_M_S;
    private int endM       = DEFAULT_END_M_S;
    private int startS     = DEFAULT_START_M_S;
    private int endS       = DEFAULT_END_M_S;
    private int                   currentYear;
    private int                   textSize;
    //文字的颜色和分割线的颜色
    private int                   textColorOut;
    private int                   textColorCenter;
    private int                   dividerColor;
    private float                 lineSpacingMultiplier;
    private WheelView.DividerType dividerType;
    private boolean isLunarCalendar = false;
    private ISelectTimeCallback mSelectChangeCallback;
    private int                 currentDay;
    private int                 currentHour;
    private int                 currentMinutes;
    private int                 currentMonth;
    private int                 currentSecond;
    private List<SyncBean> mSyncBeanList = new ArrayList<>();
    private int[]          startArray    = new int[] { DEFAULT_START_YEAR, DEFAULT_START_MONTH, DEFAULT_START_DAY, DEFAULT_START_H, DEFAULT_START_M_S, DEFAULT_START_M_S };
    private int[]          endArray      = new int[] { DEFAULT_END_YEAR, DEFAULT_END_MONTH, DEFAULT_END_DAY, DEFAULT_END_H, DEFAULT_END_M_S, DEFAULT_END_M_S };
    private int[]          currentArray  = new int[] { DEFAULT_END_YEAR, DEFAULT_END_MONTH, DEFAULT_END_DAY, DEFAULT_END_H, DEFAULT_END_M_S, DEFAULT_END_M_S };

    public WheelTime(View view, boolean[] type, int gravity, int textSize) {
        super();
        this.view = view;
        this.type = type;
        this.gravity = gravity;
        this.textSize = textSize;
        setView(view);
    }

    public boolean isLunarMode() {
        return isLunarCalendar;
    }

    public void setLunarMode(boolean isLunarCalendar) {
        this.isLunarCalendar = isLunarCalendar;
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0, 0);
    }

    public void setPicker(int year, final int month, int day, int h, int m, int s) {
        if (isLunarCalendar) {
            int[] lunar = LunarCalendar.solarToLunar(year, month + 1, day);
            setLunar(lunar[0], lunar[1] - 1, lunar[2], lunar[3] == 1, h, m, s);
        } else {
            setSolar(year, month, day, h, m, s);
        }
    }

    /**
     * 设置农历
     */
    private void setLunar(int year, final int month, int day, boolean isLeap, int h, int m, int s) {
        // 年
        wv_year = (WheelView)view.findViewById(R.id.year);
        wv_year.setAdapter(new ArrayWheelAdapter(ChinaDate.getYears(startYear, endYear)));// 设置"年"的显示数据
        wv_year.setLabel("");// 添加文字
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据
        wv_year.setGravity(gravity);

        // 月
        wv_month = (WheelView)view.findViewById(R.id.month);
        wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year)));
        wv_month.setLabel("");

        int leapMonth = ChinaDate.leapMonth(year);
        if (leapMonth != 0 && (month > leapMonth - 1 || isLeap)) { //选中月是闰月或大于闰月
            wv_month.setCurrentItem(month + 1);
        } else {
            wv_month.setCurrentItem(month);
        }

        wv_month.setGravity(gravity);

        // 日
        wv_day = (WheelView)view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (ChinaDate.leapMonth(year) == 0) {
            wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year, month))));
        } else {
            wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year))));
        }
        wv_day.setLabel("");
        wv_day.setCurrentItem(day - 1);
        wv_day.setGravity(gravity);

        wv_hours = (WheelView)view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        //wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
        wv_hours.setCurrentItem(h);
        wv_hours.setGravity(gravity);

        wv_minutes = (WheelView)view.findViewById(R.id.min);
        wv_minutes.setAdapter(new NumericWheelAdapter(0, 59));
        //wv_minutes.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_minutes.setCurrentItem(m);
        wv_minutes.setGravity(gravity);

        wv_seconds = (WheelView)view.findViewById(R.id.second);
        wv_seconds.setAdapter(new NumericWheelAdapter(0, 59));
        //wv_seconds.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_seconds.setCurrentItem(m);
        wv_seconds.setGravity(gravity);

        // 添加"年"监听
        wv_year.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + startYear;
                // 判断是不是闰年,来确定月和日的选择
                wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year_num)));
                if (ChinaDate.leapMonth(year_num) != 0 && wv_month.getCurrentItem() > ChinaDate.leapMonth(year_num) - 1) {
                    wv_month.setCurrentItem(wv_month.getCurrentItem() + 1);
                } else {
                    wv_month.setCurrentItem(wv_month.getCurrentItem());
                }

                int maxItem = 29;
                if (ChinaDate.leapMonth(year_num) != 0 && wv_month.getCurrentItem() > ChinaDate.leapMonth(year_num) - 1) {
                    if (wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
                        maxItem = ChinaDate.leapDays(year_num);
                    } else {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, wv_month.getCurrentItem()))));
                        maxItem = ChinaDate.monthDays(year_num, wv_month.getCurrentItem());
                    }
                } else {
                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, wv_month.getCurrentItem() + 1))));
                    maxItem = ChinaDate.monthDays(year_num, wv_month.getCurrentItem() + 1);
                }

                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        // 添加"月"监听
        wv_month.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index;
                int year_num = wv_year.getCurrentItem() + startYear;
                int maxItem = 29;
                if (ChinaDate.leapMonth(year_num) != 0 && month_num > ChinaDate.leapMonth(year_num) - 1) {
                    if (wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
                        maxItem = ChinaDate.leapDays(year_num);
                    } else {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, month_num))));
                        maxItem = ChinaDate.monthDays(year_num, month_num);
                    }
                } else {
                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, month_num + 1))));
                    maxItem = ChinaDate.monthDays(year_num, month_num + 1);
                }

                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        setChangedListener(wv_day);
        setChangedListener(wv_hours);
        setChangedListener(wv_minutes);
        setChangedListener(wv_seconds);

        if (type.length != 6) {
            throw new RuntimeException("type[] length is not 6");
        }
        wv_year.setVisibility(type[0] ? View.VISIBLE : View.GONE);
        wv_month.setVisibility(type[1] ? View.VISIBLE : View.GONE);
        wv_day.setVisibility(type[2] ? View.VISIBLE : View.GONE);
        wv_hours.setVisibility(type[3] ? View.VISIBLE : View.GONE);
        wv_minutes.setVisibility(type[4] ? View.VISIBLE : View.GONE);
        wv_seconds.setVisibility(type[5] ? View.VISIBLE : View.GONE);
        setContentTextSize();
    }

    /**
     * 设置公历
     */
    private void setSolar(int year, final int month, int day, int h, int m, int s) {

        currentYear = year;
        currentArray[0] = year;
        // 年
        wv_year = (WheelView)view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        mSyncBeanList.add(new SyncBean(wv_year, startYear, endYear));
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据
        wv_year.setGravity(gravity);
        // 月
        wv_month = (WheelView)view.findViewById(R.id.month);
        if (startYear == endYear) {//开始年等于终止年
            wv_month.setAdapter(new NumericWheelAdapter(startMonth, endMonth));
            wv_month.setCurrentItem(month + 1 - startMonth);
        } else if (year == startYear) {
            //起始日期的月份控制
            wv_month.setAdapter(new NumericWheelAdapter(startMonth, 12));
            wv_month.setCurrentItem(month + 1 - startMonth);
        } else if (year == endYear) {
            //终止日期的月份控制
            wv_month.setAdapter(new NumericWheelAdapter(1, endMonth));
            wv_month.setCurrentItem(month);
        } else {
            wv_month.setAdapter(new NumericWheelAdapter(1, 12));
            wv_month.setCurrentItem(month);
        }
        mSyncBeanList.add(new SyncBean(wv_month, wv_month.getIntAdapter().getStart(), wv_month.getIntAdapter().getEnd()));
        wv_month.setGravity(gravity);
        // 日
        wv_day = (WheelView)view.findViewById(R.id.day);

        if (startYear == endYear && startMonth == endMonth) {
            if (LIST_BIG.contains(String.valueOf(month + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else if (LIST_LITTLE.contains(String.valueOf(month + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
                }
            }
            wv_day.setCurrentItem(day - startDay);
        } else if (year == startYear && month + 1 == startMonth) {
            // 起始日期的天数控制
            if (LIST_BIG.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(startDay, 31));
            } else if (LIST_LITTLE.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(startDay, 30));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {

                    wv_day.setAdapter(new NumericWheelAdapter(startDay, 29));
                } else {

                    wv_day.setAdapter(new NumericWheelAdapter(startDay, 28));
                }
            }
            wv_day.setCurrentItem(day - startDay);
        } else if (year == endYear && month + 1 == endMonth) {
            // 终止日期的天数控制
            if (LIST_BIG.contains(String.valueOf(month + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
            } else if (LIST_LITTLE.contains(String.valueOf(month + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
                }
            }
            wv_day.setCurrentItem(day - 1);
        } else {
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (LIST_BIG.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (LIST_LITTLE.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {

                    wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                } else {

                    wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
            wv_day.setCurrentItem(day - 1);
        }

        mSyncBeanList.add(new SyncBean(wv_day, wv_day.getIntAdapter().getStart(), wv_day.getIntAdapter().getEnd()));

        wv_day.setGravity(gravity);

        // 添加"年"监听
        wv_year.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + startYear;
                currentYear = year_num;
                onChanged(0, currentYear);
                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        // 添加"月"监听
        wv_month.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index + 1;
                currentMonth = month_num;
                onChanged(1, currentMonth);
                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        //时
        wv_hours = (WheelView)view.findViewById(R.id.hour);
        currentHour = h;
        currentMinutes = m;
        currentSecond = s;
        int sh = DEFAULT_START_H, eh = DEFAULT_END_H;
        if (year == endYear && month + 1 == endMonth && day == endDay) {
            eh = endH;
        } else if (year == startYear && month + 1 == startMonth && day == startDay) {
            sh = startH;
        }
        wv_hours.setAdapter(new NumericWheelAdapter(sh, eh));
        mSyncBeanList.add(new SyncBean(wv_hours, sh, eh));
        wv_hours.setCurrentItem(h);
        wv_hours.setGravity(gravity);

        //分
        wv_minutes = (WheelView)view.findViewById(R.id.min);
        int sm = DEFAULT_START_M_S, em = DEFAULT_END_M_S;
        if (year == endYear && month + 1 == endMonth && day == endDay && h == endH) {
            em = endM;
        } else if (year == startYear && month + 1 == startMonth && day == startDay && h == startH) {
            sm = startM;
        }
        wv_minutes.setAdapter(new NumericWheelAdapter(sm, em));
        mSyncBeanList.add(new SyncBean(wv_minutes, sm, em));
        wv_minutes.setCurrentItem(m);
        wv_minutes.setGravity(gravity);
        //秒
        wv_seconds = (WheelView)view.findViewById(R.id.second);

        int ss = DEFAULT_START_M_S, es = DEFAULT_END_M_S;
        if (year == endYear && month + 1 == endMonth && day == endDay && h == endH && m == endM) {
            es = endS;
        } else if (year == startYear && month + 1 == startMonth && day == startDay && h == startH && m == startM) {
            ss = startS;
        }
        wv_seconds.setAdapter(new NumericWheelAdapter(ss, es));
        mSyncBeanList.add(new SyncBean(wv_seconds, ss, es));
        wv_seconds.setCurrentItem(s);
        wv_seconds.setGravity(gravity);

        //setChangedListener(wv_day);
        wv_day.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentDay = index + 1;
                onChanged(2, currentDay);
                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });
        wv_hours.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentHour = index;
                onChanged(3, currentHour);
                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });
        wv_minutes.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentMinutes = index;
                onChanged(4, index);
                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        wv_seconds.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentSecond = index;
                onChanged(5, index);
                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        if (type.length != 6) {
            throw new IllegalArgumentException("type[] length is not 6");
        }
        wv_year.setVisibility(type[0] ? View.VISIBLE : View.GONE);
        wv_month.setVisibility(type[1] ? View.VISIBLE : View.GONE);
        wv_day.setVisibility(type[2] ? View.VISIBLE : View.GONE);
        wv_hours.setVisibility(type[3] ? View.VISIBLE : View.GONE);
        wv_minutes.setVisibility(type[4] ? View.VISIBLE : View.GONE);
        wv_seconds.setVisibility(type[5] ? View.VISIBLE : View.GONE);
        setContentTextSize();
    }



    private void setChangedListener(WheelView wheelView) {
        if (mSelectChangeCallback != null) {
            wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            });
        }
    }

    private void setContentTextSize() {
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_minutes.setTextSize(textSize);
        wv_seconds.setTextSize(textSize);
    }



    private void onChanged(int pos, int now) {
        if (pos + 1 >= mSyncBeanList.size()) {
            return;
        }
        currentArray[pos] = now;
        int downCount = 0;
        int upCount = 0;
        //检查前面项目达到上界和下届的数量
        for (int i = 0; i <= pos; i++) {
            if (currentArray[i] == startArray[i]) {
                downCount++;
            } else if (currentArray[i] == endArray[i]) {
                upCount++;
            }
        }
        //如果全部达到边界
        boolean hasDown = downCount == pos + 1;
        boolean hasUp = upCount == pos + 1;
        //向后扫描后面的item,根据是否达到边界设置边界
        for (int i = pos + 1; i < mSyncBeanList.size(); i++) {
            SyncBean syncBean = mSyncBeanList.get(i);
            if (hasDown) {
                syncBean.start = startArray[i];
            } else {
                syncBean.start = ALLOWSTARTARRAY[i];
            }
            if (hasUp) {
                syncBean.end = endArray[i];
            } else {
                //是日
                if (i == 2) {
                    //是二月
                    if (currentArray[1] == 2) {
                        int year_num = currentArray[0];
                        //是闰年
                        if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0) {
                            syncBean.end = 29;
                        } else {
                            syncBean.end = 28;
                        }
                    } else {
                        if (LIST_BIG.contains(String.valueOf(now))) {
                            syncBean.end = 31;
                        } else {
                            syncBean.end = 30;
                        }
                    }
                } else {
                    syncBean.end = ALLOWENDARRAY[i];
                }
            }
            //修正选中项和上下界
            if (currentArray[i] < syncBean.start) {
                currentArray[i] = syncBean.start;
            } else if (currentArray[i] > syncBean.end) {
                currentArray[i] = syncBean.end;
            }
            if (syncBean.end < syncBean.start) {
                syncBean.end = syncBean.start;
            }
            syncBean.sync();
        }
    }


    public void setLabels(String label_year, String label_month, String label_day, String label_hours, String label_mins, String label_seconds) {
        if (isLunarCalendar) {
            return;
        }

        if (label_year != null) {
            wv_year.setLabel(label_year);
        } else {
            wv_year.setLabel(view.getContext().getString(R.string.pickerview_year));
        }
        if (label_month != null) {
            wv_month.setLabel(label_month);
        } else {
            wv_month.setLabel(view.getContext().getString(R.string.pickerview_month));
        }
        if (label_day != null) {
            wv_day.setLabel(label_day);
        } else {
            wv_day.setLabel(view.getContext().getString(R.string.pickerview_day));
        }
        if (label_hours != null) {
            wv_hours.setLabel(label_hours);
        } else {
            wv_hours.setLabel(view.getContext().getString(R.string.pickerview_hours));
        }
        if (label_mins != null) {
            wv_minutes.setLabel(label_mins);
        } else {
            wv_minutes.setLabel(view.getContext().getString(R.string.pickerview_minutes));
        }
        if (label_seconds != null) {
            wv_seconds.setLabel(label_seconds);
        } else {
            wv_seconds.setLabel(view.getContext().getString(R.string.pickerview_seconds));
        }
    }

    public void setTextXOffset(int x_offset_year, int x_offset_month, int x_offset_day, int x_offset_hours, int x_offset_minutes, int x_offset_seconds) {
        wv_day.setTextXOffset(x_offset_year);
        wv_month.setTextXOffset(x_offset_month);
        wv_year.setTextXOffset(x_offset_day);
        wv_hours.setTextXOffset(x_offset_hours);
        wv_minutes.setTextXOffset(x_offset_minutes);
        wv_seconds.setTextXOffset(x_offset_seconds);
    }

    /**
     * 设置是否循环滚动
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_minutes.setCyclic(cyclic);
        wv_seconds.setCyclic(cyclic);
    }

    public String getTime() {
        if (isLunarCalendar) {
            //如果是农历 返回对应的公历时间
            return getLunarTime();
        }
        StringBuilder sb = new StringBuilder();
        if (currentYear == startYear) {
           /* int i = wv_month.getCurrentItem() + startMonth;
            System.out.println("i:" + i);*/
            if ((wv_month.getCurrentItem() + startMonth) == startMonth) {
                sb.append((wv_year.getCurrentItem() + startYear)).append("-").append((wv_month.getCurrentItem() + startMonth)).append("-").append(
                    (wv_day.getCurrentItem() + startDay)).append(" ").append(wv_hours.getCurrentItem()).append(":").append(wv_minutes.getCurrentItem()).append(":").append(
                    wv_seconds.getCurrentItem());
            } else {
                sb.append((wv_year.getCurrentItem() + startYear))
                  .append("-")
                  .append((wv_month.getCurrentItem() + startMonth))
                  .append("-")
                  .append((wv_day.getCurrentItem() + 1))
                  .append(" ")
                  .append(wv_hours.getCurrentItem())
                  .append(":")
                  .append(wv_minutes.getCurrentItem())
                  .append(":")
                  .append(wv_seconds.getCurrentItem());
            }
        } else {
            sb.append((wv_year.getCurrentItem() + startYear))
              .append("-")
              .append((wv_month.getCurrentItem() + 1))
              .append("-")
              .append((wv_day.getCurrentItem() + 1))
              .append(" ")
              .append(wv_hours.getCurrentItem())
              .append(":")
              .append(wv_minutes.getCurrentItem())
              .append(":")
              .append(wv_seconds.getCurrentItem());
        }

        return sb.toString();
    }

    /**
     * 农历返回对应的公历时间
     */
    private String getLunarTime() {
        StringBuilder sb = new StringBuilder();
        int year = wv_year.getCurrentItem() + startYear;
        int month = 1;
        boolean isLeapMonth = false;
        if (ChinaDate.leapMonth(year) == 0) {
            month = wv_month.getCurrentItem() + 1;
        } else {
            if ((wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) <= 0) {
                month = wv_month.getCurrentItem() + 1;
            } else if ((wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) == 1) {
                month = wv_month.getCurrentItem();
                isLeapMonth = true;
            } else {
                month = wv_month.getCurrentItem();
            }
        }
        int day = wv_day.getCurrentItem() + 1;
        int[] solar = LunarCalendar.lunarToSolar(year, month, day, isLeapMonth);

        sb.append(solar[0])
          .append("-")
          .append(solar[1])
          .append("-")
          .append(solar[2])
          .append(" ")
          .append(wv_hours.getCurrentItem())
          .append(":")
          .append(wv_minutes.getCurrentItem())
          .append(":")
          .append(wv_seconds.getCurrentItem());
        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
        startArray[0] = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setRangDate(Calendar startDate, Calendar endDate) {

        if (startDate == null && endDate != null) {
            int year = endDate.get(Calendar.YEAR);
            int month = endDate.get(Calendar.MONTH) + 1;
            int day = endDate.get(Calendar.DAY_OF_MONTH);
            if (year > startYear) {
                this.endYear = year;
                this.endMonth = month;
                this.endDay = day;
            } else if (year == startYear) {
                if (month > startMonth) {
                    this.endYear = year;
                    this.endMonth = month;
                    this.endDay = day;
                } else if (month == startMonth) {
                    if (day > startDay) {
                        this.endYear = year;
                        this.endMonth = month;
                        this.endDay = day;
                    }
                }
            }
            endH = endDate.get(Calendar.HOUR_OF_DAY);
            endM = endDate.get(Calendar.MINUTE);
            endS = endDate.get(Calendar.SECOND);
        } else if (startDate != null && endDate == null) {
            int year = startDate.get(Calendar.YEAR);
            int month = startDate.get(Calendar.MONTH) + 1;
            int day = startDate.get(Calendar.DAY_OF_MONTH);
            if (year < endYear) {
                this.startMonth = month;
                this.startDay = day;
                this.startYear = year;
            } else if (year == endYear) {
                if (month < endMonth) {
                    this.startMonth = month;
                    this.startDay = day;
                    this.startYear = year;
                } else if (month == endMonth) {
                    if (day < endDay) {
                        this.startMonth = month;
                        this.startDay = day;
                        this.startYear = year;
                    }
                }
            }
            startH = startDate.get(Calendar.HOUR_OF_DAY);
            startM = startDate.get(Calendar.MINUTE);
            startS = startDate.get(Calendar.SECOND);
        } else if (startDate != null) {
            this.startYear = startDate.get(Calendar.YEAR);
            this.endYear = endDate.get(Calendar.YEAR);
            this.startMonth = startDate.get(Calendar.MONTH) + 1;
            this.endMonth = endDate.get(Calendar.MONTH) + 1;
            this.startDay = startDate.get(Calendar.DAY_OF_MONTH);
            this.endDay = endDate.get(Calendar.DAY_OF_MONTH);
            endH = endDate.get(Calendar.HOUR_OF_DAY);
            endM = endDate.get(Calendar.MINUTE);
            endS = endDate.get(Calendar.SECOND);
            startH = startDate.get(Calendar.HOUR_OF_DAY);
            startM = startDate.get(Calendar.MINUTE);
            startS = startDate.get(Calendar.SECOND);
        }
        startArray[0] = startYear;
        startArray[1] = startMonth;
        startArray[2] = startDay;
        startArray[3] = startH;
        startArray[4] = startM;
        startArray[5] = startS;
        endArray[0] = endYear;
        endArray[1] = endMonth;
        endArray[2] = endDay;
        endArray[3] = endH;
        endArray[4] = endM;
        endArray[5] = endS;
    }

    /**
     * 设置间距倍数,但是只能在1.0-4.0f之间
     */
    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        this.lineSpacingMultiplier = lineSpacingMultiplier;
        setLineSpacingMultiplier();
    }

    private void setLineSpacingMultiplier() {
        wv_day.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_month.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_year.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_hours.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_minutes.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_seconds.setLineSpacingMultiplier(lineSpacingMultiplier);
    }

    /**
     * 设置分割线的颜色
     */
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        setDividerColor();
    }

    private void setDividerColor() {
        wv_day.setDividerColor(dividerColor);
        wv_month.setDividerColor(dividerColor);
        wv_year.setDividerColor(dividerColor);
        wv_hours.setDividerColor(dividerColor);
        wv_minutes.setDividerColor(dividerColor);
        wv_seconds.setDividerColor(dividerColor);
    }

    /**
     * 设置分割线的类型
     */
    public void setDividerType(WheelView.DividerType dividerType) {
        this.dividerType = dividerType;
        setDividerType();
    }

    private void setDividerType() {

        wv_day.setDividerType(dividerType);
        wv_month.setDividerType(dividerType);
        wv_year.setDividerType(dividerType);
        wv_hours.setDividerType(dividerType);
        wv_minutes.setDividerType(dividerType);
        wv_seconds.setDividerType(dividerType);
    }

    /**
     * 设置分割线之间的文字的颜色
     */
    public void setTextColorCenter(int textColorCenter) {
        this.textColorCenter = textColorCenter;
        setTextColorCenter();
    }

    private void setTextColorCenter() {
        wv_day.setTextColorCenter(textColorCenter);
        wv_month.setTextColorCenter(textColorCenter);
        wv_year.setTextColorCenter(textColorCenter);
        wv_hours.setTextColorCenter(textColorCenter);
        wv_minutes.setTextColorCenter(textColorCenter);
        wv_seconds.setTextColorCenter(textColorCenter);
    }

    /**
     * 设置分割线以外文字的颜色
     */
    public void setTextColorOut(int textColorOut) {
        this.textColorOut = textColorOut;
        setTextColorOut();
    }

    private void setTextColorOut() {
        wv_day.setTextColorOut(textColorOut);
        wv_month.setTextColorOut(textColorOut);
        wv_year.setTextColorOut(textColorOut);
        wv_hours.setTextColorOut(textColorOut);
        wv_minutes.setTextColorOut(textColorOut);
        wv_seconds.setTextColorOut(textColorOut);
    }

    /**
     * @param isCenterLabel 是否只显示中间选中项的
     */
    public void isCenterLabel(boolean isCenterLabel) {
        wv_day.isCenterLabel(isCenterLabel);
        wv_month.isCenterLabel(isCenterLabel);
        wv_year.isCenterLabel(isCenterLabel);
        wv_hours.isCenterLabel(isCenterLabel);
        wv_minutes.isCenterLabel(isCenterLabel);
        wv_seconds.isCenterLabel(isCenterLabel);
    }

    public void setSelectChangeCallback(ISelectTimeCallback mSelectChangeCallback) {
        this.mSelectChangeCallback = mSelectChangeCallback;
    }
}
