package com.fec.view.addresswheel.bean;

import com.fec.view.addresswheel.widget.ISelectAble;

/**
 * Created by Administrator on 2017/12/1.
 */

public class SelectAbleBean implements ISelectAble {
    private String name;
    private int id;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Object getArg() {
        return null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
