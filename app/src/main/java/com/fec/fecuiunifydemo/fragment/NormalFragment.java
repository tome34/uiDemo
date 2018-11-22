package com.fec.fecuiunifydemo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fec.fecuiunifydemo.R;
import com.fec.fecuiunifydemo.adapter.RefreshAdapter;
import java.util.Arrays;
import java.util.List;

/**
 * @author tome
 * @date 2018/8/3  14:30
 * @describe ${TODO}
 */
public class NormalFragment extends Fragment{

    private RecyclerView mRecyclerView ;
    private List<String> mDatas;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_normal_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        init();
        return view;

    }

    private void init() {
        mDatas= Arrays.asList("第1条数据","第2数据","第3条数据","第4条数据","第5条数据","第6条数据","第7条数据","第8条数据","第9条数据","第10条数据","第11条数据","第12条数据","第13条数据","第14条数据","第15条数据","第16条数据","第17条数据","第18条数据","第19条数据","第20条数据");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RefreshAdapter mAdapter = new RefreshAdapter(getActivity(), mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }
}
