package com.zx.zsmarketmobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.zx.zsmarketmobile.R;
import com.zx.zsmarketmobile.entity.SearchFilterEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2018/4/19.
 * 功能：
 */

public class SearchFilterAdapter extends RecyclerView.Adapter<SearchFilterAdapter.MyHolder> {

    private List<SearchFilterEntity> dataList = new ArrayList<>();
    private Context context;

    public SearchFilterAdapter() {
        dataList.add(new SearchFilterEntity("项目名称"));
        dataList.add(new SearchFilterEntity("项目代码"));
        dataList.add(new SearchFilterEntity("所属阶段", new String[]{"请选择", "洽谈", "签约", "开工", "投产", "达产"}));
        dataList.add(new SearchFilterEntity("是否外资", new String[]{"请选择", "是", "否"}));
        dataList.add(new SearchFilterEntity("所属大类", new String[]{"请选择", "工业", "服务业", "其他"}));
        dataList.add(new SearchFilterEntity("所属行业", new String[]{"请先选择所属大类"}));
        dataList.add(new SearchFilterEntity("新兴行业", new String[]{"请先选择所属大类"}));
        dataList.add(new SearchFilterEntity("投资协议编号"));
        dataList.add(new SearchFilterEntity("投资补充协议编号"));
        dataList.add(new SearchFilterEntity("招商会纪要编号"));
        dataList.add(new SearchFilterEntity("办公会纪要便要"));
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_filter, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        SearchFilterEntity entity = dataList.get(position);
        holder.tvName.setText(entity.getName());
        if (entity.getSpinnerData() != null) {
            holder.llSpinner.setVisibility(View.VISIBLE);
            holder.etContent.setVisibility(View.GONE);
            setSpinnerValue(entity);
            holder.spValue.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, entity.getSpinnerData()));
            if (entity.getSpinnerData().length > entity.getSelectPositoin()) {
                holder.spValue.setSelection(entity.getSelectPositoin());
            } else {
                holder.spValue.setSelection(0);
            }
        } else {
            holder.llSpinner.setVisibility(View.GONE);
            holder.etContent.setVisibility(View.VISIBLE);
            holder.etContent.setText(entity.getValue());
        }
    }

    private void setSpinnerValue(SearchFilterEntity entity) {
        if (dataList.get(4).getSelectPositoin() == 0) {
            if ("所属行业".contains(entity.getName()) || "新兴行业".contains(entity.getName())) {
                entity.setSpinnerData(new String[]{"请先选择所属大类"});
            }
        } else if (dataList.get(4).getSelectPositoin() == 1) {
            if ("所属行业".contains(entity.getName())) {
                entity.setSpinnerData(new String[]{
                        "请选择", "采矿业", "农副食品加工业", "食品制造业", "酒、饮料和精制茶制造业", "烟草制品业",
                        "纺织业", "纺织服装、服饰业", "皮革、毛皮、羽毛及其制品和制鞋业", "木材加工和木、竹、藤、棕、草制品业", "家具制造业",
                        "造纸和纸制品业", "印刷和记录媒介复制业", "文教、工美、体育和娱乐用品制造业", "石油加工、炼焦和核燃料加工业", "化学原料和化学制品制造业",
                        "医药制造业", "化学纤维制造业", "橡胶和塑料制品业", "非金属矿物制品业", "黑色金属冶炼和压延加工业",
                        "有色金属冶炼和压延加工业", "金属制品业", "通用设备制造业", "专用设备制造业", "汽车制造业",
                        "铁路、船舶、航空航天和其他运输设备制造业", "电气机械和器材制造业", "计算机、通信和其他电子设备制造业", "仪器仪表制造业", "其他制造业",
                        "电力、热力、燃气及水生产和供应业"});
            } else if ("新兴行业".contains(entity.getName())) {
                entity.setSpinnerData(new String[]{
                        "请选择", "新能源及智能汽车", "电子核心部件", "云计算及物联网", "可穿戴设备及智能终端", "通用航空",
                        "生物医药及医疗器械", "机器人及智能装备", "能源装备", "节能环保", "新材料", "无法判断"});
            }
        } else if (dataList.get(4).getSelectPositoin() == 2) {
            if ("所属行业".contains(entity.getName())) {
                entity.setSpinnerData(new String[]{
                        "请选择", "电子商务", "现代物流", "房地产", "商贸流通", "教育和卫生",
                        "金融", "文化创意", "信息服务", "科技服务", "商务服务",
                        "其他"});
            } else if ("新兴行业".contains(entity.getName())) {
                entity.setSpinnerData(new String[]{
                        "请选择", "新兴金融", "国际物流", "大数据及信息服务", "软件设计及服务外包", "跨境电子商务及结算",
                        "保税商品展示及保税贸易", "总部贸易和转口贸易", "专业服务", "健康医疗", "文创旅游", "无法判断"});
            }

        } else if (dataList.get(4).getSelectPositoin() == 3) {
            if ("所属行业".contains(entity.getName())) {
                entity.setSpinnerData(new String[]{"请选择", "农、林、牧、渔业", "建筑业"});
            } else if ("新兴行业".contains(entity.getName())) {
                entity.setSpinnerData(new String[]{"请选择", "无法判断"});
            }
        }
    }

    public List<SearchFilterEntity> getDataList() {
        return dataList;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public EditText etContent;
        public LinearLayout llSpinner;
        public Spinner spValue;

        public MyHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_searchFilter_name);
            etContent = itemView.findViewById(R.id.et_searchFilter_content);
            llSpinner = itemView.findViewById(R.id.ll_searFilter_sp);
            spValue = itemView.findViewById(R.id.sp_searchFilter_value);
            etContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    dataList.get(getAdapterPosition()).setValue(etContent.getText().toString().trim());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            spValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dataList.get(getAdapterPosition()).setSelectPositoin(position);
                    String name = dataList.get(getAdapterPosition()).getName();
                    if ("所属阶段".contains(name)) {
                        if (position == 0) {
                            dataList.get(getAdapterPosition()).setValue("");
                        } else {
                            dataList.get(getAdapterPosition()).setValue(position + "");
                        }
                    } else if ("是否外资".contains(name)) {
                        if (position == 1) {
                            dataList.get(getAdapterPosition()).setValue(1 + "");
                        } else if (position == 2) {
                            dataList.get(getAdapterPosition()).setValue(0 + "");
                        } else {
                            dataList.get(getAdapterPosition()).setValue("");
                        }
                    } else if ("所属大类".contains(name)) {
                        notifyItemChanged(5);
                        notifyItemChanged(6);
                        if (position == 0) {
                            dataList.get(getAdapterPosition()).setValue("");
                        } else {
                            dataList.get(getAdapterPosition()).setValue(dataList.get(getAdapterPosition()).getSpinnerData()[position]);
                        }
                    } else if ("所属行业".contains(name)) {
                        if (position == 0) {
                            dataList.get(getAdapterPosition()).setValue("");
                        } else {
                            dataList.get(getAdapterPosition()).setValue(dataList.get(getAdapterPosition()).getSpinnerData()[position]);
                        }
                    } else if ("新兴行业".contains(name)) {
                        if (position == 0) {
                            dataList.get(getAdapterPosition()).setValue("");
                        } else {
                            dataList.get(getAdapterPosition()).setValue(dataList.get(getAdapterPosition()).getSpinnerData()[position]);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

}
