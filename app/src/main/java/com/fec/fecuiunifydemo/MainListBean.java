package com.fec.fecuiunifydemo;

import java.util.List;

/**
 * Description :
 *
 * @author XQ Yang
 * @date 2018/7/12  10:25
 */
public class MainListBean {

    public MainListBean(String title,List<ChildListBean> childList) {
        this.title = title;
        this.childList = childList;
    }

    /**
     * title : 弹窗
     * childList : [{"title":"底部弹窗","command":"bottomDialog"}]
     */



    private String title;
    private List<ChildListBean> childList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChildListBean> getChildList() {
        return childList;
    }

    public void setChildList(List<ChildListBean> childList) {
        this.childList = childList;
    }

    public static class ChildListBean {

        public ChildListBean(String title,Class<? extends ICommand> commandKey) {
            this.title = title;
            this.command = commandKey;
        }

        /**
         * title : 底部弹窗
         * command : bottomDialog
         */


        private String title;
        private Class<? extends ICommand> command;

        public Class<? extends ICommand> getCommand() {
            return command;
        }

        public void setCommand(Class<? extends ICommand> command) {
            this.command = command;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


    }
}
