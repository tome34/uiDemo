package com.fec.fecuiunifydemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fec.fecuiunifydemo.R;
import java.util.List;

/**
 * @author tome
 * @date 2018/7/25  17:50
 * @describe ${刷新列表}
 */
public class RefreshAdapter extends RecyclerView.Adapter<RefreshAdapter.MyViewHolder>{
    private Context mContext;
    private List<String> mList;

    public RefreshAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mList = data ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview,parent,false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {
        holder.mTvName.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTvName ;
        public MyViewHolder (View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tv_name);

        }
    }
}
