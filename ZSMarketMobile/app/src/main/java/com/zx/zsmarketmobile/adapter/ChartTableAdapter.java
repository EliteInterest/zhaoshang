package com.zx.zsmarketmobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.zsmarketmobile.R;
import com.zx.zsmarketmobile.entity.KeyValueInfo;
import com.zx.zsmarketmobile.entity.StatisticsItemInfo;
import com.zx.zsmarketmobile.listener.IChartListener;
import com.zx.zsmarketmobile.ui.statistics.ChartActivity;
import com.zx.zsmarketmobile.util.DigitUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Xiangb on 2017/5/17.
 * 功能：统计图表适配器
 */

public class ChartTableAdapter extends MyRecycleAdapter {
    private List<KeyValueInfo> mDataList;
    private Context mContext;
    private double sum = 0;
    private IChartListener mListener;
    private DecimalFormat df = new DecimalFormat("######0.00");
    private StatisticsItemInfo statisticsItemInfo;

    public ChartTableAdapter(Context c, StatisticsItemInfo mItemInfo, List<KeyValueInfo> keyList) {
        this.mDataList = keyList;
        this.mContext = c;
        this.statisticsItemInfo = mItemInfo;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new Holder(view);
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder myHolder = (Holder) holder;
        KeyValueInfo mEntity = mDataList.get(position);

        Log.i("wangwansheng", "statisticsItemInfo.name is " + statisticsItemInfo.name);
        if (statisticsItemInfo.name.contains("1-X月"))
            if (statisticsItemInfo.name.equals("1-X月签约项目情况表") ||
                    statisticsItemInfo.name.equals("1-X月开工项目情况表") ||
                    statisticsItemInfo.name.equals("1-X月投产项目情况表")) {
                Log.i("wangwansheng", "X月签约项目情况表...");
                myHolder.parent.findViewById(R.id.layout1).setVisibility(View.GONE);
                myHolder.parent.findViewById(R.id.layout2).setVisibility(View.VISIBLE);
                myHolder.parent.findViewById(R.id.layout3).setVisibility(View.VISIBLE);
                myHolder.parent.findViewById(R.id.layout4).setVisibility(View.VISIBLE);
                myHolder.parent.findViewById(R.id.layout5).setVisibility(View.VISIBLE);

                myHolder.parent.findViewById(R.id.view2).setVisibility(View.VISIBLE);
                myHolder.parent.findViewById(R.id.view3).setVisibility(View.VISIBLE);
                myHolder.parent.findViewById(R.id.view4).setVisibility(View.VISIBLE);
                myHolder.parent.findViewById(R.id.view5).setVisibility(View.VISIBLE);


                TextView textView = myHolder.parent.findViewById(R.id.include1).findViewById(R.id.layout2_name1);
                textView.setText("内资");
                textView = myHolder.parent.findViewById(R.id.include2).findViewById(R.id.layout2_name1);
                textView.setText("内资");
                textView = myHolder.parent.findViewById(R.id.include11).findViewById(R.id.layout2_name1);
                textView.setText("工业");
                textView = myHolder.parent.findViewById(R.id.include22).findViewById(R.id.layout2_name1);
                textView.setText("服务业");
                textView = myHolder.parent.findViewById(R.id.include3).findViewById(R.id.layout2_name1);
                textView.setText("江北片区");
                textView = myHolder.parent.findViewById(R.id.include4).findViewById(R.id.layout2_name1);
                textView.setText("北碚片区");
                textView = myHolder.parent.findViewById(R.id.include5).findViewById(R.id.layout2_name1);
                textView.setText("渝北片区");
                textView = myHolder.parent.findViewById(R.id.include6).findViewById(R.id.layout2_name1);
                textView.setText("直管区");
                textView = myHolder.parent.findViewById(R.id.include7).findViewById(R.id.layout2_name1);
                textView.setText("直属区");
                textView = myHolder.parent.findViewById(R.id.include8).findViewById(R.id.layout2_name1);
                textView.setText("两江工业开发区");
                textView = myHolder.parent.findViewById(R.id.include9).findViewById(R.id.layout2_name1);
                textView.setText("保税港区");
                textView = myHolder.parent.findViewById(R.id.include10).findViewById(R.id.layout2_name1);
                textView.setText("江北嘴");
                textView = myHolder.parent.findViewById(R.id.include12).findViewById(R.id.layout2_name1);
                textView.setText("悦来");

                myHolder.layoutName.setText("按内外资分");
                myHolder.layoutName1.setText("按产业分");
                myHolder.layoutName2.setText("按地域分");

                return;
            } else {
//                myHolder.parent.findViewById(R.id.layout6).setVisibility(View.VISIBLE);
            }

        sum = 0;
        if (!statisticsItemInfo.name.equals("状态变化") || !statisticsItemInfo.name.equals("状态排行"))
            for (KeyValueInfo keyValueInfo : mDataList) {
                if (!"合计".equals(keyValueInfo.key) && !"总数".equals(keyValueInfo.key) && !"总计".equals(keyValueInfo.key)) {
                    double value = DigitUtil.StringToDouble(keyValueInfo.value);
                    sum += value;
                }
            }
        if (sum == 0) {
            sum = 1;
        }

        if (statisticsItemInfo.name.equals("投资总额") || statisticsItemInfo.name.equals("纳税总额") ||
                statisticsItemInfo.name.equals("增长率排行") || statisticsItemInfo.name.equals("数量变化")
                || statisticsItemInfo.name.equals("投资金额") || statisticsItemInfo.name.equals("纳税金额")) {
            myHolder.tvPercent.setVisibility(View.GONE);
            myHolder.tvPercent.setVisibility(View.GONE);
            myHolder.tvOther.setVisibility(View.GONE);
        }

        if (statisticsItemInfo.name.equals("状态变化")) {
            myHolder.tvOther.setVisibility(View.VISIBLE);
            myHolder.tvOther1.setVisibility(View.VISIBLE);
            myHolder.tvOther2.setVisibility(View.VISIBLE);
            myHolder.tvOther3.setVisibility(View.VISIBLE);

        }

        if (statisticsItemInfo.name.equals("状态排行")) {
            myHolder.tvKey.setText(mEntity.key);
            myHolder.tvValue.setText(mEntity.value.split(";")[0]);
            myHolder.tvPercent.setText(mEntity.value.split(";")[1]);
            myHolder.tvOther.setText(mEntity.value.split(";")[2]);
            myHolder.tvOther1.setText(mEntity.value1.split(";")[0]);
            myHolder.tvOther2.setText(mEntity.value1.split(";")[1]);
            myHolder.tvOther3.setText(mEntity.value1.split(";")[2]);
            myHolder.tvOther.setVisibility(View.VISIBLE);
            myHolder.tvOther1.setVisibility(View.VISIBLE);
            myHolder.tvOther2.setVisibility(View.VISIBLE);
            myHolder.tvOther3.setVisibility(View.VISIBLE);
            return;
        }

        if (statisticsItemInfo.chartType == 1) {//折线图
            myHolder.tvKey.setText(mEntity.key);
            if (statisticsItemInfo.name.equals("状态变化")) {
                myHolder.tvValue.setText(mEntity.value.split(";")[0]);
                myHolder.tvPercent.setText(mEntity.value.split(";")[1]);
                myHolder.tvOther.setText(mEntity.value.split(";")[2]);
                myHolder.tvOther1.setText(mEntity.value1.split(";")[0]);
                myHolder.tvOther2.setText(mEntity.value1.split(";")[1]);
                myHolder.tvOther3.setText(mEntity.value1.split(";")[2]);
            } else
                myHolder.tvValue.setText(mEntity.value);
        } else if (mEntity.value1 != null || mEntity.value2 != null) {//此时不是简单的名称-数量-比重的类型
            myHolder.tvKey.setText(mEntity.key);
            myHolder.tvValue.setText(mEntity.value);
            myHolder.tvPercent.setText(mEntity.value1);
            myHolder.tvOther.setText(mEntity.value2);
            myHolder.tvOther.setVisibility(mEntity.value2 == null ? View.GONE : View.VISIBLE);

            if (statisticsItemInfo.tableTitle.equals("历史累计")) {
                myHolder.tvOther.setVisibility(View.VISIBLE);
                myHolder.tvOther1.setVisibility(View.VISIBLE);
                myHolder.tvOther2.setVisibility(View.VISIBLE);
                myHolder.tvOther.setText("80");
                myHolder.tvOther1.setText("2016");
                myHolder.tvOther2.setText("5");
            }
        } else {
            double value = DigitUtil.StringToDouble(mEntity.value);
            myHolder.tvKey.setText(mEntity.key);
            myHolder.tvValue.setText(mEntity.value);
            if (value == 0 || sum == 0) {
                myHolder.tvPercent.setText(0 + "%");
            } else if ("合计".equals(mEntity.key) || "总数".equals(mEntity.key) || "总计".equals(mEntity.key)) {
                myHolder.tvPercent.setText(100.00 + "%");
            } else {
                myHolder.tvPercent.setText(df.format(value * 100 / sum) + "%");
            }
        }
    }

    public void setChartListener(IChartListener chartListener) {
        mListener = chartListener;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView tvKey, tvValue, tvPercent, tvOther, tvOther1, tvOther2, tvOther3,
                layoutName, layoutName1, layoutName2, layoutName3;
        private View parent;

        public Holder(View parent) {
            super(parent);
            this.parent = parent;
            tvKey = (TextView) parent.findViewById(R.id.tvItemTable_name);
            tvValue = (TextView) parent.findViewById(R.id.tvItemTable_first);
            tvPercent = (TextView) parent.findViewById(R.id.tvItemTable_second);
            tvOther = (TextView) parent.findViewById(R.id.tvItemTable_three);
            tvOther1 = (TextView) parent.findViewById(R.id.tvItemTable_four);
            tvOther2 = (TextView) parent.findViewById(R.id.tvItemTable_five);
            tvOther3 = (TextView) parent.findViewById(R.id.tvItemTable_six);

            layoutName = (TextView) parent.findViewById(R.id.layout_name1);
            layoutName1 = (TextView) parent.findViewById(R.id.layout_name11);
            layoutName2 = (TextView) parent.findViewById(R.id.layout_name2);
            layoutName3 = (TextView) parent.findViewById(R.id.layout_name3);

            if (statisticsItemInfo.name.equals("1-X月签约项目情况表")) {
                Log.i("wangwansheng", "X月签约项目情况表");
            }

//            tvKey.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mListener != null) {
//                        mListener.OnKeyClick(getAdapterPosition());
//                    }
//                }
//            });
//            tvValue.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mListener != null && !mDataList.get(getAdapterPosition()).key.equals("总计")
//                            && !mDataList.get(getAdapterPosition()).key.equals("总数") && !mDataList.get(getAdapterPosition()).key.equals("合计")) {
//                        if (mDataList.get(getAdapterPosition()).value == null) {
//                            return;
//                        }
//                        if (mDataList.get(getAdapterPosition()).value.equals("0")) {
//                            ((ChartActivity) mContext).showToast("该项暂无数据");
//                            return;
//                        }
//                        String line = ((ChartActivity) mContext).tvValue.getText().toString();
//                        if (line.equals("数量")) {
//                            line = "";
//                        }
//                        mListener.onValueClick(mDataList.get(getAdapterPosition()).key, line);
//                    }
//                }
//            });
//            tvPercent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mListener != null && !mDataList.get(getAdapterPosition()).key.equals("总计")
//                            && !mDataList.get(getAdapterPosition()).key.equals("总数") && !mDataList.get(getAdapterPosition()).key.equals("合计")) {
//                        if (mDataList.get(getAdapterPosition()).value1 == null) {
//                            return;
//                        }
//                        if (mDataList.get(getAdapterPosition()).value1.equals("0")) {
//                            ((ChartActivity) mContext).showToast("该项暂无数据");
//                            return;
//                        }
//                        String line = ((ChartActivity) mContext).tvPercent.getText().toString();
//                        if (!line.equals("所占比重")) {
//                            mListener.onValueClick(mDataList.get(getAdapterPosition()).key, line);
//                        }
//                    }
//                }
//            });
//            tvOther.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mListener != null && !mDataList.get(getAdapterPosition()).key.equals("总计")
//                            && !mDataList.get(getAdapterPosition()).key.equals("总数") && !mDataList.get(getAdapterPosition()).key.equals("合计")) {
//                        if (mDataList.get(getAdapterPosition()).value2 == null) {
//                            return;
//                        }
//                        if (mDataList.get(getAdapterPosition()).value2.equals("0")) {
//                            ((ChartActivity) mContext).showToast("该项暂无数据");
//                            return;
//                        }
//                        String line = ((ChartActivity) mContext).tvOther.getText().toString();
//                        if (((ChartActivity) mContext).tvOther.getVisibility() == View.VISIBLE) {
//                            mListener.onValueClick(mDataList.get(getAdapterPosition()).key, line);
//                        }
//                    }
//                }
//            });
        }
    }
}
