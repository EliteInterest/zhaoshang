package com.zs.marketmobile.adapter.supervise;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zs.marketmobile.R;
import com.zs.marketmobile.adapter.MyRecycleAdapter;
import com.zs.marketmobile.entity.supervise.MyTaskPageEntity;
import com.zs.marketmobile.listener.MyItemClickListener;

import java.util.List;

/**
 * Created by zhouzq on 2017/4/6.
 */

public class SuperviseDetialPackageAdapter extends MyRecycleAdapter {
    private List<MyTaskPageEntity.DataBean> dataList;
    private Context mContext;

    public SuperviseDetialPackageAdapter(Context c, List<MyTaskPageEntity.DataBean> dataBeanList) {
        this.dataList = dataBeanList;
        this.mContext = c;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_package, parent, false);
        return new Holder(view);
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder myHolder = (Holder) holder;
        MyTaskPageEntity.DataBean mEntify = dataList.get(position);
        myHolder.tvTime.setText(mEntify.getFCreateDate());
        myHolder.tvName.setText(mEntify.getFTaskName());
        myHolder.tvDepartment.setText(mEntify.getFCreateDepartment());
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mListener = listener;
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView tvName, tvDepartment, tvTime;

        public Holder(View parent) {
            super(parent);
            tvTime = (TextView) parent.findViewById(R.id.tvItemPackage_time);
            tvName = (TextView) parent.findViewById(R.id.tvItemPackage_name);
            tvDepartment = (TextView) parent.findViewById(R.id.tvItemPackage_department);
        }
    }
}
