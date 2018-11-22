package com.fec.fecuiunifydemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fec.fecuiunifydemo.R;
import com.fec.view.common.tab.TabSegment;
import com.fec.view.common.topbar.TopBar;
import com.fec.view.common.utils.DisplayHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cginechen
 * @date 2017-04-28
 */

public class QDTabSegmentFixModeActivity extends AppCompatActivity {
    @BindView(R.id.topbar)
    TopBar     mTopBar;
    @BindView(R.id.tabSegment)
    TabSegment mTabSegment;
    @BindView(R.id.contentViewPager)
    ViewPager      mContentViewPager;

    private Map<ContentPage, Fragment> mPageMap = new HashMap<>();
    private List<Fragment> mFragmentList;
    private List<String> mTabTitle;
    private ContentPage mDestPage = ContentPage.Item1;
    private int position = 0;
    private BaseFragmentAdapter mPagerAdapter;
    //private PagerAdapter mPagerAdapter = new PagerAdapter() {
    //    @Override
    //    public boolean isViewFromObject(View view, Object object) {
    //        return view == object;
    //    }
    //
    //    @Override
    //    public int getCount() {
    //        return ContentPage.SIZE;
    //    }
    //
    //    @Override
    //    public Object instantiateItem(final ViewGroup container, int position) {
    //        ContentPage page = ContentPage.getPage(position);
    //        View view = getPageView(page);
    //        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    //        container.addView(view, params);
    //        return view;
    //    }
    //
    //    @Override
    //    public void destroyItem(ViewGroup container, int position, Object object) {
    //        container.removeView((View) object);
    //    }
    //
    //};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_viewpager_layout);
        ButterKnife.bind(this);
        initTopBar();
        initData();
        initTabAndPager();
    }

    private void initData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new NormalFragment());
        mFragmentList.add(new NormalFragment());

        mTabTitle = new ArrayList<>();

        mPagerAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragmentList, mTabTitle);

    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        mTopBar.setTitle("固定宽度");

        //mTopBar.addRightTextButton("切换", R.id.topbar_right_change_button)
        //        .setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                showBottomSheetList();
        //            }
        //        });
    }

    private void showBottomSheetList() {
        switch (position%9) {
            case 0:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(false);
                mTabSegment.addTab(new TabSegment.Tab("一页"));
                mTabSegment.addTab(new TabSegment.Tab("二页"));
                break;
            case 1:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(true);
                mTabSegment.setIndicatorPosition(false);
                mTabSegment.setIndicatorWidthAdjustContent(true);
                mTabSegment.addTab(new TabSegment.Tab("一页"));
                mTabSegment.addTab(new TabSegment.Tab("二页"));
                break;
            case 2:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(true);
                mTabSegment.setIndicatorPosition(true);
                mTabSegment.setIndicatorWidthAdjustContent(true);
                mTabSegment.addTab(new TabSegment.Tab("一页"));
                mTabSegment.addTab(new TabSegment.Tab("二页"));
                break;
            case 3:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(true);
                mTabSegment.setIndicatorPosition(false);
                mTabSegment.setIndicatorWidthAdjustContent(false);
                mTabSegment.addTab(new TabSegment.Tab("一页"));
                mTabSegment.addTab(new TabSegment.Tab("二页"));
                break;
            case 4:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(false);
                TabSegment.Tab component = new TabSegment.Tab(
                    ContextCompat.getDrawable(this, R.mipmap.ic_launcher),
                    null,
                    "Components", true
                );
                TabSegment.Tab util = new TabSegment.Tab(
                    ContextCompat.getDrawable(this, R.mipmap.ic_launcher),
                    null,
                    "Helper", true
                );
                mTabSegment.addTab(component);
                mTabSegment.addTab(util);
                break;
            case 5:
                TabSegment.Tab tab = mTabSegment.getTab(0);
                tab.setSignCountMargin(0, -DisplayHelper.dp2px(this, 4));
                tab.showSignCountView(this, 1);
                break;
            case 6:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(false);
                TabSegment.Tab component2 = new TabSegment.Tab(
                    ContextCompat.getDrawable(this, R.mipmap.ic_launcher),
                    ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round),
                    "Components", false
                );
                TabSegment.Tab util2 = new TabSegment.Tab(
                    ContextCompat.getDrawable(this, R.mipmap.ic_launcher),
                    ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round),
                    "Helper", false
                );
                mTabSegment.addTab(component2);
                mTabSegment.addTab(util2);
                break;
            case 7:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(true);
                mTabSegment.setIndicatorWidthAdjustContent(true);
                mTabSegment.setIndicatorPosition(false);
                TabSegment.Tab component3 = new TabSegment.Tab(
                    ContextCompat.getDrawable(this, R.mipmap.ic_launcher),
                    null,
                    "Components", true
                );
                component3.setTextColor(Color.BLUE,
                    Color.RED);
                TabSegment.Tab util3 = new TabSegment.Tab(
                    ContextCompat.getDrawable(this, R.mipmap.icon_notify_done),
                    null,
                    "Helper", true
                );
                util3.setTextColor(Color.BLUE,
                    Color.RED);
                mTabSegment.addTab(component3);
                mTabSegment.addTab(util3);
                break;
            case 8:
                mTabSegment.updateTabText(0, "动态更新文案");
                break;
            case 9:
                TabSegment.Tab component4 = new TabSegment.Tab(
                    ContextCompat.getDrawable(this, R.mipmap.icon_notify_done),
                    null,
                    "动态更新", true
                );
                mTabSegment.replaceTab(0, component4);
                break;

            default:
                break;
        }
        position++;
        mTabSegment.notifyDataChanged();
    }

    private void initTabAndPager() {
        mContentViewPager.setAdapter(mPagerAdapter);
        mContentViewPager.setCurrentItem(mDestPage.getPosition(), false);
        mTabSegment.addTab(new TabSegment.Tab("item1"));
        mTabSegment.addTab(new TabSegment.Tab("item2"));
        mTabSegment.setDefaultNormalColor(getResources().getColor(R.color.primary_bg));
        mTabSegment.setDefaultSelectedColor(getResources().getColor(R.color.red_ff));
        mTabSegment.setupWithViewPager(mContentViewPager, false);
        mTabSegment.setMode(TabSegment.MODE_FIXED);
        mTabSegment.addOnTabSelectedListener(new TabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                mTabSegment.hideSignCountView(index);
            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {
                mTabSegment.hideSignCountView(index);
            }

            @Override
            public void onDoubleTap(int index) {

            }
        });
    }

/*    private Fragment getPageView(ContentPage page) {
        Fragment view = mPageMap.get(page);
        if (view == null) {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

            if (page == ContentPage.Item1) {
                textView.setText("page1");
            } else if (page == ContentPage.Item2) {
                textView.setText("page2");
            }

            view = textView;
            mPageMap.put(page, view);
        }
        return view;
    }*/

    public enum ContentPage {
        Item1(0),
        Item2(1);
        public static final int SIZE = 2;
        private final int position;

        ContentPage(int pos) {
            position = pos;
        }

        public static ContentPage getPage(int position) {
            switch (position) {
                case 0:
                    return Item1;
                case 1:
                    return Item2;
                default:
                    return Item1;
            }
        }

        public int getPosition() {
            return position;
        }
    }
}
