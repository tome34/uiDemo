package com.fec.fecuiunifydemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
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

public class QDTabSegmentScrollableModeActivity extends AppCompatActivity {
    @SuppressWarnings("FieldCanBeLocal") private final int TAB_COUNT = 10;

    @BindView(R.id.topbar)
    TopBar     mTopBar;
    @BindView(R.id.tabSegment)
    TabSegment mTabSegment;
    @BindView(R.id.contentViewPager)
    ViewPager      mContentViewPager;

    private Map<ContentPage, View> mPageMap = new HashMap<>();
    private ContentPage mDestPage = ContentPage.Item1;
    private List<Fragment> mFragmentList;
    private List<String> mTabTitle;
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



    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        mTopBar.setTitle("多个tab");
    }

    private void initData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new NormalFragment());
        mFragmentList.add(new NormalFragment());
        mFragmentList.add(new NormalFragment());
        mFragmentList.add(new NormalFragment());
        mFragmentList.add(new NormalFragment());
        mFragmentList.add(new NormalFragment());
        mFragmentList.add(new NormalFragment());
        mFragmentList.add(new NormalFragment());
        mFragmentList.add(new NormalFragment());
        mFragmentList.add(new NormalFragment());

        mTabTitle = new ArrayList<>();

        mPagerAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragmentList, mTabTitle);

    }

    private void initTabAndPager() {
        mContentViewPager.setAdapter(mPagerAdapter);
        mContentViewPager.setCurrentItem(mDestPage.getPosition(), false);
        for (int i = 0; i < TAB_COUNT; i++) {
            mTabSegment.addTab(new TabSegment.Tab("Item " + (i + 1)));
        }
        int space = DisplayHelper.dp2px(this, 16);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setMode(TabSegment.MODE_SCROLLABLE);
        mTabSegment.setDefaultNormalColor(getResources().getColor(R.color.primary_bg));
        mTabSegment.setDefaultSelectedColor(getResources().getColor(R.color.red_ff));
        mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setupWithViewPager(mContentViewPager, false);
        mTabSegment.setPadding(space, 0, space, 0);
    }

    private View getPageView(ContentPage page) {
        View view = mPageMap.get(page);
        if (view == null) {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setTextColor(Color.BLACK);
            textView.setText("这是第 " + (page.getPosition() + 1) + " 个 Item 的内容区");
            view = textView;
            mPageMap.put(page, view);
        }
        return view;
    }

    public enum ContentPage {
        Item1(0),
        Item2(1),
        Item3(2),
        Item4(3),
        Item5(4),
        Item6(5),
        Item7(6),
        Item8(7),
        Item9(8),
        Item10(9);
        public static final int SIZE = 10;
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
                case 2:
                    return Item3;
                case 3:
                    return Item4;
                case 4:
                    return Item5;
                case 5:
                    return Item6;
                case 6:
                    return Item7;
                case 7:
                    return Item8;
                case 8:
                    return Item9;
                case 9:
                    return Item10;
                default:
                    return Item1;
            }
        }

        public int getPosition() {
            return position;
        }
    }
}
