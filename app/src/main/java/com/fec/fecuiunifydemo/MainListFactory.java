package com.fec.fecuiunifydemo;

import com.fec.fecuiunifydemo.command.AddressCommand;
import com.fec.fecuiunifydemo.command.AddressSelectorButton;
import com.fec.fecuiunifydemo.command.AmapButton;
import com.fec.fecuiunifydemo.command.BadgeCommand;
import com.fec.fecuiunifydemo.command.ButtonStyle;
import com.fec.fecuiunifydemo.command.CountdownButton;
import com.fec.fecuiunifydemo.command.DialogCommand;
import com.fec.fecuiunifydemo.command.EmojiButton;
import com.fec.fecuiunifydemo.command.EmptyLayout;
import com.fec.fecuiunifydemo.command.GetCodeButton;
import com.fec.fecuiunifydemo.command.HeaderButton;
import com.fec.fecuiunifydemo.command.LoopViewCommand;
import com.fec.fecuiunifydemo.command.MarqueeViewButton;
import com.fec.fecuiunifydemo.command.MessageBox;
import com.fec.fecuiunifydemo.command.NumberCommand;
import com.fec.fecuiunifydemo.command.PhotoViewButton;
import com.fec.fecuiunifydemo.command.PictureSelectorButton;
import com.fec.fecuiunifydemo.command.PopupView;
import com.fec.fecuiunifydemo.command.PopupWindow;
import com.fec.fecuiunifydemo.command.ProgressBar.ProgressBarStyle;
import com.fec.fecuiunifydemo.command.PromptBox;
import com.fec.fecuiunifydemo.command.RatingBar;
import com.fec.fecuiunifydemo.command.SearchHistoryCommand;
import com.fec.fecuiunifydemo.command.SwitchButton;
import com.fec.fecuiunifydemo.command.TabCommand;
import com.fec.fecuiunifydemo.command.ZxingButton;
import com.fec.fecuiunifydemo.command.refreshButton;
import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 *
 * @author XQ Yang
 * @date 2018/7/12  11:22
 */
public class MainListFactory {
    public static List<MainListBean> getMainList() {
        return new ArrayList<MainListBean>(){{
            add(new MainListBean("弹框",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("底部弹窗",DialogCommand.class));
                // TODO: 2018/7/18 加标题
                add(new MainListBean.ChildListBean("选择弹窗",DialogCommand.class));
               // add(new MainListBean.ChildListBean("中心弹窗",DialogCommand.class));
            }}));
            add(new MainListBean("页面顶部Tab",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("固定样式",TabCommand.class));
                add(new MainListBean.ChildListBean("可滑动",TabCommand.class));
            }}));
            add(new MainListBean("进度圈",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("转圈",ProgressBarStyle.class));
                add(new MainListBean.ChildListBean("转圈+标题",ProgressBarStyle.class));

            }}));
            add(new MainListBean("提示框",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("提示框",PromptBox.class));
                //add(new MainListBean.ChildListBean("发送成功",PromptBox.class));
                //add(new MainListBean.ChildListBean("发送失败",PromptBox.class));

            }}));
            add(new MainListBean("评星",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("评星",RatingBar.class));
            }}));
            add(new MainListBean("空页面",new ArrayList<MainListBean.ChildListBean>() {{
               // add(new MainListBean.ChildListBean("加载数据为空",EmptyLayout.class));
                add(new MainListBean.ChildListBean("加载数据失败",EmptyLayout.class));
            }}));
            add(new MainListBean("单选开关",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("switch开关",SwitchButton.class));

            }}));
            add(new MainListBean("分享",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("分享",PopupWindow.class));

            }}));
            add(new MainListBean("浮层弹框",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("浮层弹框",PopupView.class));

            }}));
            add(new MainListBean("倒计时",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("倒计时样式",CountdownButton.class));

            }}));
            add(new MainListBean("跑马灯效果",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("跑马灯效果", MarqueeViewButton.class));

            }}));
            add(new MainListBean("图片浏览",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("图片浏览", PhotoViewButton.class));

            }}));
            add(new MainListBean("图片选择器",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("图片选择器", PictureSelectorButton.class));

            }}));
            add(new MainListBean("地址时间选择器",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("地址选择器", AddressCommand.class));
                add(new MainListBean.ChildListBean("时间选择器-时分秒", AddressCommand.class));
                add(new MainListBean.ChildListBean("时间选择器-年月日", AddressCommand.class));
                add(new MainListBean.ChildListBean("时间选择器-年月日时分秒", AddressCommand.class));

            }}));
            add(new MainListBean("地址选择器",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("地址选择器", AddressSelectorButton.class));
                //add(new MainListBean.ChildListBean("时间选择器", AddressSelectorButton.class));
            }}));
            add(new MainListBean("高德地图地址",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("高德地图地址", AmapButton.class));
                //add(new MainListBean.ChildListBean("时间选择器", AddressSelectorButton.class));
            }}));
            add(new MainListBean("按钮样式",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("按钮样式", ButtonStyle.class));
            }}));
            add(new MainListBean("留言框",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("留言框", MessageBox.class));
            }}));
            add(new MainListBean("下拉加载",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("下拉加载", refreshButton.class));
            }}));
            add(new MainListBean("红点指示器",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("红点指示器", BadgeCommand.class));
            }}));
            add(new MainListBean("搜索历史",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("搜索历史页面", SearchHistoryCommand.class));
            }}));
            //add(new MainListBean("状态栏",new ArrayList<MainListBean.ChildListBean>() {{
            //    add(new MainListBean.ChildListBean("状态栏", StatusCommand.class));
            //    add(new MainListBean.ChildListBean("图片背景状态栏", StatusCommand.class));
            //}}));
            add(new MainListBean("验证码倒计时",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("验证码倒计时", GetCodeButton.class));
            }}));
            add(new MainListBean("二维码扫描",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("二维码扫描", ZxingButton.class));
            }}));
            add(new MainListBean("数量修改器",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("数量修改器", NumberCommand.class));
            }}));
            add(new MainListBean("轮播图",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("自动轮播图", LoopViewCommand.class));
            }}));
            add(new MainListBean("会员中心",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("头部视图拉伸放大效果", HeaderButton.class));
            }}));
            add(new MainListBean("表情键盘",new ArrayList<MainListBean.ChildListBean>() {{
                add(new MainListBean.ChildListBean("表情键盘", EmojiButton.class));
            }}));
        }};
    }
}
