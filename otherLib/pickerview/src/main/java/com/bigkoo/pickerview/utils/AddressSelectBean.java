package com.bigkoo.pickerview.utils;

import com.contrarywind.interfaces.IPickerViewData;
import java.util.List;

/**
 * 类描述    :
 * 包名      : com.fecmobile.dididingshuimo.bean.login
 * 创建人    : zhangh
 * 创建时间  : 2018/1/15  20:49
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class AddressSelectBean implements IPickerViewData {
    private String                  id;
    private String                  name;
    private List<AddressSelectBean> chindren;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AddressSelectBean> getChindren() {
        return chindren;
    }

    public void setChindren(List<AddressSelectBean> chindren) {
        this.chindren = chindren;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
