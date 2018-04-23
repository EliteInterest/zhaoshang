package com.zx.zsmarketmobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.zsmarketmobile.R;
import com.zx.zsmarketmobile.entity.HttpZtEntity;

import java.util.List;

/**
 * Created by Xiangb on 2016/9/22.
 * 功能：搜索列表适配器
 */
public class TaskExcuteAdapter extends MyRecycleAdapter {

    private List<HttpZtEntity> ztItems;
    private Context context;
    private Holder myHolder;

    public TaskExcuteAdapter(Context c, List<HttpZtEntity> ztItems) {
        this.ztItems = ztItems;
        this.context = c;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_excute_list, parent, false);
            return new Holder(view);
        } else {//footer
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_foot_view, parent, false);
            return new FooterViewHolder(view);
        }
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            myHolder = (Holder) holder;
            final HttpZtEntity mEntity = ztItems.get(position);
            myHolder.tvZtName.setText(mEntity.getProjName());
//            setDrawable(mEntity, myHolder.imgCreditLevel);
            myHolder.tvZtAddress.setText(mEntity.getProjDept());
            myHolder.tvZtTel.setText(mEntity.getProjIndustry());
            if (mEntity.getProjStage() == 1){
                myHolder.tvStage.setText("洽谈");
            }else if (mEntity.getProjStage() == 2){
                myHolder.tvStage.setText("签约");
            }else if (mEntity.getProjStage() == 3){
                myHolder.tvStage.setText("开工");
            }else if (mEntity.getProjStage() == 4){
                myHolder.tvStage.setText("续建");
            }else if (mEntity.getProjStage() == 5){
                myHolder.tvStage.setText("投产");
            }else if (mEntity.getProjStage() == 6){
                myHolder.tvStage.setText("达产");
            }
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return ztItems == null ? 0 : ztItems.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvZtName, tvZtAddress, tvZtTel, tvStage;
        private ImageView imgCreditLevel;

        public Holder(View parent) {
            super(parent);
            tvZtName = (TextView) parent.findViewById(R.id.tv_zt_name);
            tvZtAddress = (TextView) parent.findViewById(R.id.tv_zt_address);
            tvZtTel = (TextView) parent.findViewById(R.id.tv_zt_tel);
            imgCreditLevel = (ImageView) parent.findViewById(R.id.iv_creditlevel);
            tvStage = parent.findViewById(R.id.tv_zt_stage);
            parent.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

}
