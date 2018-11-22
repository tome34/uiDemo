package com.bigkoo.pickerview.sync;

import android.util.Log;
import com.bigkoo.pickerview.adapter.NumericWheelAdapter;
import com.contrarywind.view.WheelView;

/**
 * Description :
 *
 * @author XQ Yang
 * @date 7/16/2018  3:18 PM
 */
public class SyncBean implements Syncable{
    public WheelView mView;
    public int start;
    public int end;
    private static final String TAG = "SyncBean";

    public SyncBean(WheelView view, int start, int end) {
        mView = view;
        this.start = start;
        this.end = end;
    }

    @Override
    public void sync() {
        if (mView.getIntAdapter().getStart()!=start||mView.getIntAdapter().getEnd()!=end) {
            mView.setAdapter(new NumericWheelAdapter(start,end));
            Log.i(TAG, "onSync: item = "+this+" start = "+start+" end= "+end);
        }
    }
}
