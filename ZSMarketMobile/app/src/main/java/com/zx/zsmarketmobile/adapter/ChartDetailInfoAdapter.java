package com.zx.zsmarketmobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.zsmarketmobile.R;
import com.zx.zsmarketmobile.entity.KeyValueInfo;
import com.zx.zsmarketmobile.listener.MyItemClickListener;

import java.util.List;

/**
 * Create By Stanny On 2017/3/13
 * 功能：统计详情适配器
 */
public class ChartDetailInfoAdapter extends MyRecycleAdapter {

    private List<KeyValueInfo> dataList;
    private Context mContext;

    public ChartDetailInfoAdapter(Context c, List<KeyValueInfo> complainList) {
        this.dataList = complainList;
        this.mContext = c;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complain, parent, false);
        return new Holder(view);
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder myHolder = (Holder) holder;
        KeyValueInfo mEntify = dataList.get(position);
        myHolder.tvName.setText(mEntify.key);
        myHolder.tvRemark.setText(mEntify.value == null ? "" : mEntify.value);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mListener = listener;
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView tvName, tvRemark;

        public Holder(View parent) {
            super(parent);
            tvName = (TextView) parent.findViewById(R.id.tvItemComplain_name);
            tvRemark = (TextView) parent.findViewById(R.id.tvItemComplain_value);
        }
    }

}