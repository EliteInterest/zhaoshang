package com.zx.zsmarketmobile.ui.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.zx.zsmarketmobile.R;
import com.zx.zsmarketmobile.adapter.ChartTableAdapter;
import com.zx.zsmarketmobile.entity.KeyValueInfo;
import com.zx.zsmarketmobile.entity.QualitySampleEntity;
import com.zx.zsmarketmobile.entity.StatisticsItemInfo;
import com.zx.zsmarketmobile.http.ApiData;
import com.zx.zsmarketmobile.http.BaseHttpResult;
import com.zx.zsmarketmobile.listener.IChartListener;
import com.zx.zsmarketmobile.ui.base.BaseActivity;
import com.zx.zsmarketmobile.util.ChartUtil;
import com.zx.zsmarketmobile.util.DigitUtil;
import com.zx.zsmarketmobile.util.DrawChartUtil;
import com.zx.zsmarketmobile.util.Util;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create By Xiangb On 2017/5/17
 * 功能：统计月报界面
 */
public class MonthlyMagazineActivity extends BaseActivity implements IChartListener {

    private LinearLayout llChart;//图表
    private RecyclerView rvChart;
    public TextView tvKey, tvValue, tvOther, tvPercent;
    private TextView tvBack;
    private TextView mTvTimeContent, mTvQueryRiskByTime;
    private Spinner mQueryTypeSpinner, mQueryTypeSpinner1;
    private LinearLayout mQueryByTypeSpinnerLayout;//按类型选择日期的线性布局
    private LinearLayout mTimeSelectedLayout;
    private LinearLayout llChartYear;
    private Spinner spChartArea;
    private Spinner spChartyear;
    private Spinner spChartyear1;
    private TextView mTvStartTime, mTvEndTime, mTvQueryByTime;
    private StatisticsItemInfo mItemInfo;
    private ChartUtil mChartUtil;
    private ChartTableAdapter mAdapter;
    private List<KeyValueInfo> keyList = new ArrayList<>();
    private List<String> tableCodes = new ArrayList<>();
    private String mStartTime = DrawChartUtil.getDate(true, false), mEndTime = DrawChartUtil.getDate(false, true), mType = "";
    private String meld1 = "2017";
    private String meld2 = "2018";

    private TextView monthView1;
    private TextView monthView2;

    private enum mQueryType {
        year,
        month
    }

    private ApiData getChartInfo = new ApiData(ApiData.HTTP_ID_statistics_single_parameter);
    private ApiData countEntityType = new ApiData(ApiData.HTTP_ID_countEntityType);
    private ApiData deviceSecurityRiskData = new ApiData(ApiData.HTTP_ID_device_security_risk_parameter);
    private ApiData drugSamplingData = new ApiData(ApiData.HTTP_ID_drug_sample_parameter);
    private ApiData industrialProductData = new ApiData(ApiData.HTTP_ID_industrial_product_parameter);
    private ApiData countHzpList = new ApiData(ApiData.HTTP_ID_statistics_hzp);

    private ApiData caseDep = new ApiData(ApiData.HTTP_ID_statistics_case_queryDep);//根据部门统计
    private ApiData caseType = new ApiData(ApiData.HTTP_ID_statistics_case_queryType);//来源统计
    private ApiData caseIsCase = new ApiData(ApiData.HTTP_ID_statistics_case_queryIsCase);//立案统计
    private ApiData caseCloseCount = new ApiData(ApiData.HTTP_ID_statistics_case_queryClosedCount);//结案统计
    private ApiData caseRecordCount = new ApiData(ApiData.HTTP_ID_statistics_case_queryRecordCount);//案件记录趋势统计
    private ApiData casePunishCount = new ApiData(ApiData.HTTP_ID_statistics_case_queryPunishCount);//案件处罚趋势统计

    private ApiData compDepart = new ApiData(ApiData.HTTP_ID_statistics_comp_countDepartment);//受理部门统计
    private ApiData compType = new ApiData(ApiData.HTTP_ID_statistics_comp_countType);//投诉类别统计
    private ApiData compInfo = new ApiData(ApiData.HTTP_ID_statistics_comp_countInfo);//信息来源统计
    private ApiData compBussiniss = new ApiData(ApiData.HTTP_ID_statistics_comp_countBussiniss);//业务来源统计

    private ApiData enterType = new ApiData(ApiData.HTTP_ID_statistics_entity_enterpriseType);//主体
    private ApiData enterIndustry = new ApiData(ApiData.HTTP_ID_statistics_entity_enterpriseIndustry);
    private ApiData enterEquipType = new ApiData(ApiData.HTTP_ID_statistics_entity_equipmentType);
    private ApiData enterComplain = new ApiData(ApiData.HTTP_ID_statistics_entity_enterpriseComplain);
    private ApiData enterDev = new ApiData(ApiData.HTTP_ID_statistics_entity_enterpriseDev);
    private ApiData enterAnn = new ApiData(ApiData.HTTP_ID_statistics_entity_enterpriseAnn);
    private ApiData enterWarning = new ApiData(ApiData.HTTP_ID_statistics_entity_enterpriseWarning);

    private ApiData superCountTask = new ApiData(ApiData.HTTP_ID_statistics_super_countTask);
    private ApiData superCountType = new ApiData(ApiData.HTTP_ID_statistics_super_countType);
    private ApiData superEnterprise = new ApiData(ApiData.HTTP_ID_statistics_super_countEnterprise);
    private ApiData superDoTask = new ApiData(ApiData.HTTP_ID_statistics_super_countDoTask);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);
        addToolBar(true);
        hideRightImg();

        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        llChart = (LinearLayout) findViewById(R.id.month_chart);
        rvChart = (RecyclerView) findViewById(R.id.rvChart_table);
        tvKey = (TextView) findViewById(R.id.tvChart_key);
        tvValue = (TextView) findViewById(R.id.tvChart_value);
        tvOther = (TextView) findViewById(R.id.tvChart_other);
        tvPercent = (TextView) findViewById(R.id.tvChart_percent);
        tvBack = (TextView) findViewById(R.id.tv_reback);
        mTvTimeContent = (TextView) findViewById(R.id.tv_statistics_content_time);
        mTvQueryRiskByTime = (TextView) findViewById(R.id.tv_statistics_query);
        mQueryByTypeSpinnerLayout = (LinearLayout) findViewById(R.id.time_type_select_layout);
        mTimeSelectedLayout = (LinearLayout) findViewById(R.id.time_select_layout);
        mTvStartTime = (TextView) findViewById(R.id.tvActStatistics_starttime);
        mTvEndTime = (TextView) findViewById(R.id.tvActStatistics_endtime);
        mTvQueryByTime = (TextView) findViewById(R.id.tvActStatistics_query);
        llChartYear = findViewById(R.id.ll_chart_year);
        spChartyear = findViewById(R.id.sp_chart_year);
        spChartyear1 = findViewById(R.id.sp_chart_year1);
        spChartArea = findViewById(R.id.sp_chart_area);
        monthView1 = findViewById(R.id.month_message);
        monthView2 = findViewById(R.id.month_message_content);

        mItemInfo = (StatisticsItemInfo) getIntent().getSerializableExtra("task");
        setMidText(mItemInfo.name);

        if (mItemInfo.name.equals("推进情况")) {
            llChart.setVisibility(View.VISIBLE);
        }
        mChartUtil = new ChartUtil(mContext);
        loadData();
    }

    /**
     * 加载数据
     */
    public void loadData() {
        switch (mItemInfo.name) {
            case "推进情况":
                monthView1.setText("推进情况");
                Log.i("wangwansheng", "set text!!");
                monthView2.setText("    1-X月，两江新区全域招商引资签约项目329个，投资1153.5亿元，同比下降40.0%，其中外资6.94亿美元，下降92.7%，内资1071.5亿元，下降20.0%；开工项目187个，投资674.9亿元，下降51.4%，其中外资14.71亿美元，下降82.7%，内资550.5亿元，下降36.4%；投产项目168个，投资363.5亿元，下降67.0%，其中外资12.55亿美元，下降81.0%，内资254.3亿元，下降63.5%。\n" +
                        "\n" +
                        "    两江直管区方面：1-X月签约项目共303个，投资994.8亿元，占两江全域的86.2%，同比下降33.8%；开工项目177个，投资637.7亿元，占两江全域的94.5%，同比下降41.7%；投产项目157个，投资328.7亿元，占两江全域的90.4%，同比下降62.7%。");
                List<KeyValueInfo> tempList = new ArrayList<>();
                KeyValueInfo info = new KeyValueInfo();
                info.key = "签约";
                info.value = "100";
                info.value1 = "100";
                tempList.add(info);
                Log.i("wangwansheng", "add 111");

                info = new KeyValueInfo();
                info.key = "开工";
                info.value = "100";
                info.value1 = "100";
                tempList.add(info);
                Log.i("wangwansheng", "add 222");

                info = new KeyValueInfo();
                info.key = "投产";
                info.value = "100";
                info.value1 = "200";
                tempList.add(info);
                Log.i("wangwansheng", "add 333");

                initChart(tempList);
                break;
            case "项目状态":
                monthView1.setText("项目状态");
                monthView2.setText("新区成立至今，有632个项目处于已签约尚未开工状态，投资2284.1亿元，其中外资24.9亿美元，该项目状态中直管区有485个项目，投资1443.0亿元，占比63.2%。有300个项目处于已开工尚未投产状态，签约项目开工率为79.5%，投资3888.8亿元，其中外资59.5亿美元，该项目状态中直管区有173个项目，投资2738.4亿元，占比70.4%。已投产2154个项目，开工项目投产率为87.8%，投资5392.7亿元，每个项目平均投资2.5亿元，其中外资160.9亿美元，该项目状态中直管区有1665个项目，投资3941.4亿元，占比73.1%。");
                //deviceSecurityRiskData.loadData("");
                break;
            case "签约项目情况":
                monthView1.setText("签约项目情况");
                monthView2.setText("资金来源：内资项目301个，投资1071.5亿元，下降11.9%；外资项目28个，投资82.0亿元，下降88.4%。\n"
                + "\n" +
                "产业分布：工业项目67个，主要集中在汽车及新能源汽车、电子核心部件和3D打印等领域，投资270.9亿元，下降15.9%。服务业项目260个，主要集中在房地产、金融、文创旅游等领域，投资882.4亿元，下降44.9%。\n"
                + "\n" +
                "地域分布：主要分布于直属区、两江工业开发区、渝北片区、江北嘴等地，其中直属区66个项目，投资383.8亿元，占比33.3%；两江工业开发区67个项目，投资372.5亿元，占比32.3%；渝北片区9个项目，投资152.7亿元，占比13.2%；江北嘴49个项目，投资114.4亿元，占比9.9%。");
                //industrialProductData.loadData("");
                break;
            case "开工项目情况":
                monthView1.setText("开工项目情况");
                monthView2.setText("资金来源：内资项目166个，投资550.5亿元，下降31.3%；外资项目21个，投资124.39亿元，下降78.8%。\n" +
                        "\n" +
                "产业分布：工业项目30个，行业主要集中在航空航天、电子核心部件、汽车及新能源汽车和生物医药制造业，合同投资338.5亿元，增长56.5%；服务业项目156个，行业主要集中在房地产、金融、大数据和贸易等行业，投资336.2亿元，下降71.3%。\n" +
                        "\n" +
                "地域分布：主要集中在直属区、两江工业开发区、江北嘴、保税港区等地。其中，两江工业开发区新开工42个项目，投资472.4亿元，占70.0%；直属区35个项目，投资136.8亿元，占20.3%；江北嘴48个项目，投资14.42亿元，占2.1%；保税港区52个项目，投资14.07亿元，占2.1%。");
                //countHzpList.loadData("");
                break;
            case "投产项目情况":
                monthView1.setText("投产项目情况");
                monthView2.setText("资金来源：内资项目146个，投资254.3亿元，下降62.9%；外资项目22个，投资109.2亿元，下降73.8%。\n" +
                        "\n" +
                "产业分布：工业项目25个，主要集中在电子核心部件、通用航空、汽车及新能源汽车制造业，投资142.5亿元，增长24.3%；服务业142个，主要集中在金融、贸易和租赁业，投资220.8亿元，下降77.7%。\n" +
                        "\n" +
                "地域分布：主要集中在直属区、两江工业开发区、江北嘴、保税港区等地。其中，两江工业开发区新投产37个项目，投资170.5亿元，占46.9%；直属区19个项目，投资127.2亿元，占35.0%；江北嘴48个项目，投资14.42亿元，占4.0%；保税港区52个项目，投资14.07亿元，占3.9%。");
                //caseDep.loadData(meld1 + "," + meld2);
                break;
            case "在谈项目情况":
                monthView1.setText("在谈项目情况");
                monthView2.setText("截至8月末，共有在谈项目151个，预计投资961.1亿元。其中：内资项目144个，预计投资860.3亿元，占比89.5%，外资项目7个，预计投资100.78亿元，占比10.5%；工业项目110个，预计投资594.2亿元，占比61.8%，服务业项目40个，预计投资358.90亿元，占比37.3%，农林牧渔业项目1个，预计投资8亿元。\n" +
                        "\n" +
                "地域分布方面，直属区和两江工业开发区共有在谈项目121个，预计投资560.8亿元，占比58.3%；北碚片区8个，预计投资199.04亿元，占比20.7%，；渝北片区20个，预计投资114.3亿元，占比11.9%；悦来1个，预计投资80亿元，占比8.3%。");
                //caseType.loadData(meld1 + "," + meld2);
                break;
            case "新区成立至X月末项目状态":
                monthView1.setText("新区成立至X月末项目状态");
                monthView2.setText("1-X月，两江新区全域招商引资签约项目329个，投资1153.5亿元，同比下降40.0%，其中外资6.94亿美元，下降92.7%，内资1071.5亿元，下降20.0%；开工项目187个，投资674.9亿元，下降51.4%，其中外资14.71亿美元，下降82.7%，内资550.5亿元，下降36.4%；投产项目168个，投资363.5亿元，下降67.0%，其中外资12.55亿美元，下降81.0%，内资254.3亿元，下降63.5%。\n" +
                        "\n" +
                "两江直管区方面：1-X月签约项目共303个，投资994.8亿元，占两江全域的86.2%，同比下降33.8%；开工项目177个，投资637.7亿元，占两江全域的94.5%，同比下降41.7%；投产项目157个，投资328.7亿元，占两江全域的90.4%，同比下降62.7%。");
                //caseIsCase.loadData(meld1 + "," + meld2);
                break;
            case "增长对比":
                caseIsCase.loadData(spChartyear.getSelectedItem());
                break;
            case "状态排行":
                caseRecordCount.loadData("");
                break;
            case "投资总额":
                tvPercent.setVisibility(View.GONE);
                casePunishCount.loadData("");
                break;
            case "纳税总额":
                tvPercent.setVisibility(View.GONE);
                compDepart.loadData("");
                break;
            case "增长率排行":
                compType.loadData("", "");
                break;
            case "数量变化":
                String area = mQueryTypeSpinner.getSelectedItemPosition() == 0 ? "全部" : Util.area[mQueryTypeSpinner.getSelectedItemPosition()];
                String queryType = "year";
                switch (mQueryTypeSpinner1.getSelectedItemPosition()) {
                    case 0:
                        queryType = "year";
                        break;
                    case 1:
                        queryType = "quarter";
                        break;
                    case 2:
                        queryType = "month";
                        break;

                }
                area = "";
                compInfo.loadData(area, queryType, mTvStartTime.getText().toString(), mTvEndTime.getText().toString());
                break;
            case "状态变化":
                area = mQueryTypeSpinner.getSelectedItemPosition() == 0 ? "全部" : Util.area[mQueryTypeSpinner.getSelectedItemPosition()];
                queryType = "year";
                switch (mQueryTypeSpinner1.getSelectedItemPosition()) {
                    case 0:
                        queryType = "year";
                        break;
                    case 1:
                        queryType = "quarter";
                        break;
                    case 2:
                        queryType = "month";
                        break;

                }
                area = "";
                compBussiniss.loadData(area, queryType, mTvStartTime.getText().toString(), mTvEndTime.getText().toString());
                break;
            case "投资金额":
                tvValue.setText(mItemInfo.name);
                area = mQueryTypeSpinner.getSelectedItemPosition() == 0 ? "全部" : Util.area[mQueryTypeSpinner.getSelectedItemPosition()];
                queryType = "year";
                switch (mQueryTypeSpinner1.getSelectedItemPosition()) {
                    case 0:
                        queryType = "year";
                        break;
                    case 1:
                        queryType = "quarter";
                        break;
                    case 2:
                        queryType = "month";
                        break;

                }
                area = "";
                getChartInfo.loadData(area, queryType, mTvStartTime.getText().toString(), mTvEndTime.getText().toString());
                break;
            case "纳税金额":
                tvValue.setText(mItemInfo.name);
                tempList = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    info = new KeyValueInfo();
                    info.key = String.valueOf(i + 2013);
                    switch (i) {
                        case 0:
                            info.value = "100";
                            break;
                        case 1:
                            info.value = "150";
                            break;
                        case 2:
                            info.value = "120";
                            break;
                        case 3:
                            info.value = "130";
                            break;
                    }
                    tempList.add(info);
                }
                initChart(tempList);
                keyList.clear();
                keyList.addAll(tempList);
                mAdapter.notifyDataSetChanged();
                if (tableCodes.size() > 0) {
                    tvBack.setVisibility(View.VISIBLE);
                } else {
                    tvBack.setVisibility(View.GONE);
                }
//                enterIndustry.loadData();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reback:
                tableCodes.remove(tableCodes.size() - 1);
                if (mItemInfo.name.equals("主体类别")) {
                    countEntityType.loadData();
                } else if (mItemInfo.name.equals("行业分类")) {
                    getChartInfo.loadData(mItemInfo.name, tableCodes.size() == 0 ? "" : tableCodes.get(tableCodes.size() - 1));
                }
                break;
            case R.id.tv_statistics_query:
                loadData();
                break;
            case R.id.tvActStatistics_starttime:
                Util.setDateIntoText(MonthlyMagazineActivity.this, v);
                break;
            case R.id.tvActStatistics_endtime:
                Util.setDateIntoText(MonthlyMagazineActivity.this, v);
                break;
            case R.id.tvActStatistics_query:
//                String startTime = mTvStartTime.getText().toString();
//                String endTime = mTvEndTime.getText().toString();
//                if (!startTime.equals("开始时间")) {
//                    if (!endTime.equals("结束时间")) {
//                        boolean isRight = Util.compareTime(startTime, endTime);
//                        if (isRight) {
                loadData();
//                        } else {
//                            showToast("开始时间应该早于结束时间！");
//                        }
//                    } else {
//                        showToast("请输入结束时间！");
//                    }
//                } else {
//                    showToast("请输入开始时间！");
//                }
                break;
            case R.id.tv_statistics_content_time:
                Util.setDateYearIntoText(this, v, mType);
                break;
            default:
                break;
        }
    }

    //图表name的点击事件
    @Override
    public void OnKeyClick(int position) {
        KeyValueInfo mEntity = keyList.get(position);
        if ("合计".equals(mEntity.key) || "总数".equals(mEntity.key) || "总计".equals(mEntity.key)) {
            return;
        }
        switch (mItemInfo.name) {
            case "主体类别":
                if (mEntity.key.equals("企业")) {
                    tableCodes.add(mEntity.code);
                    getChartInfo.loadData(mItemInfo.name, mEntity.code);
                }
                break;
            case "行业分类":
                tableCodes.add(mEntity.code);
                getChartInfo.loadData(mItemInfo.name, mEntity.code);
                break;
            default:
                break;
        }
    }

    //图表num的点击事件
    @Override
    public void onValueClick(String columnString, String lineString) {
        String[] hasList = new String[]{"商标", "微企", "食品隐患", "药品隐患", "特种设备隐患", "主体数量", "主体类别", "行业分类"};
        if (Arrays.asList(hasList).contains(mItemInfo.name)) {
            return;
        }
        Intent intent = new Intent(this, ChartListActivity.class);
        intent.putExtra("key", mItemInfo.name);
        intent.putExtra("line", lineString);
        intent.putExtra("column", columnString);
        startActivity(intent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_statistics_hzp:
            case ApiData.HTTP_ID_countEntityType:
            case ApiData.HTTP_ID_statistics_single_parameter:
            case ApiData.HTTP_ID_statistics_case_queryDep:
            case ApiData.HTTP_ID_statistics_case_queryType:
            case ApiData.HTTP_ID_statistics_case_queryClosedCount:
            case ApiData.HTTP_ID_statistics_case_queryIsCase:
            case ApiData.HTTP_ID_statistics_case_queryPunishCount:
            case ApiData.HTTP_ID_statistics_comp_countDepartment:
            case ApiData.HTTP_ID_statistics_comp_countType:
            case ApiData.HTTP_ID_statistics_comp_countInfo:
            case ApiData.HTTP_ID_statistics_entity_enterpriseType:
            case ApiData.HTTP_ID_statistics_entity_enterpriseDev:
            case ApiData.HTTP_ID_statistics_entity_enterpriseIndustry:
            case ApiData.HTTP_ID_statistics_entity_enterpriseAnn:
            case ApiData.HTTP_ID_statistics_entity_enterpriseComplain:
            case ApiData.HTTP_ID_statistics_entity_equipmentType:
            case ApiData.HTTP_ID_statistics_super_countTask:
            case ApiData.HTTP_ID_statistics_super_countType:
            case ApiData.HTTP_ID_statistics_super_countEnterprise:
            case ApiData.HTTP_ID_statistics_super_countDoTask:
            case ApiData.HTTP_ID_statistics_entity_enterpriseWarning:
            case ApiData.HTTP_ID_device_security_risk_parameter://特种设备隐患
            case ApiData.HTTP_ID_industrial_product_parameter:
                List<KeyValueInfo> tempList = (List<KeyValueInfo>) b.getEntry();
                initChart(tempList);
                keyList.clear();
                keyList.addAll(tempList);
                mAdapter.notifyDataSetChanged();
                if (tableCodes.size() > 0) {
                    tvBack.setVisibility(View.VISIBLE);
                } else {
                    tvBack.setVisibility(View.GONE);
                }
                break;
            case ApiData.HTTP_ID_statistics_comp_countBussiniss:
            case ApiData.HTTP_ID_statistics_case_queryRecordCount:
                tempList = (List<KeyValueInfo>) b.getEntry();
                drawLineChart1(tempList);
                keyList.clear();
                keyList.addAll(tempList);
                mAdapter.notifyDataSetChanged();
                if (tableCodes.size() > 0) {
                    tvBack.setVisibility(View.VISIBLE);
                } else {
                    tvBack.setVisibility(View.GONE);
                }
                break;
            case ApiData.HTTP_ID_drug_sample_parameter://药品检验
//            case ApiData.HTTP_ID_industrial_product_parameter://工业产品检验
                List<QualitySampleEntity> qualitySampleEntityList = (List<QualitySampleEntity>) b.getEntry();
                List<KeyValueInfo> sampleList = new ArrayList<>();
                for (int i = 0; i < qualitySampleEntityList.size(); i++) {
                    QualitySampleEntity mSample = qualitySampleEntityList.get(i);
                    sampleList.add(new KeyValueInfo(mSample.getType(), mSample.getCheckoutTotal(), mSample.getCheckoutPass()));
                }
                initChart(sampleList);
                keyList.clear();
                keyList.addAll(sampleList);
                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }

    /**
     * 初始化图表
     *
     * @param keyList
     */
    private void initChart(List<KeyValueInfo> keyList) {
        llChart.removeAllViews();
        if (keyList.size() == 0) {
            return;
        }
        switch (mItemInfo.chartType) {
            case 0://柱状图
                drawBarChart(keyList);
                break;
            case 1://折线图
                drawLineChart(keyList);
                break;
            case 2://饼状图
                drawPieChart(keyList);
                break;
        }
    }

    /**
     * 画饼状图
     *
     * @param keyList
     */
    private void drawPieChart(List<KeyValueInfo> keyList) {
        List<KeyValueInfo> tempList = new ArrayList<>();
        for (int i = 0; i < keyList.size(); i++) {
            KeyValueInfo keyValueInfo = keyList.get(i);
            if ("合计".equals(keyValueInfo.key) || "总数".equals(keyValueInfo.key)
                    || "0".equals(keyValueInfo.value) || "总计".equals(keyValueInfo.key)) {
            } else {
                tempList.add(keyList.get(i));
            }
        }
        int[] colors = new int[]{getMyColor(R.color.chart8), getMyColor(R.color.chart7),
                getMyColor(R.color.chart6), getMyColor(R.color.chart4), getMyColor(R.color.chart5),
                getMyColor(R.color.chart3), getMyColor(R.color.chart2), getMyColor(R.color.chart1)};
        mChartUtil.setColors(colors);
        GraphicalView categoryView = mChartUtil.createCategoryView("", "", tempList);
        llChart.removeAllViews();
        llChart.addView(categoryView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    private void drawLineChart1(List<KeyValueInfo> keyList) {
        List<double[]> values = new ArrayList<>();
        int size = 0;
        for (int i = 0; i < keyList.size(); i++) {
            KeyValueInfo key = keyList.get(i);
            if (!"合计".equals(key.key) && !"总数".equals(key.key) && !"总计".equals(key.key)) {
                size++;
            }
        }
        double[] value = new double[size];
        double[] value1 = new double[size];
        double[] value2 = new double[size];
        double[] value3 = new double[size];
        double[] value4 = new double[size];
        double[] value5 = new double[size];
        String[] names = new String[size];
        for (int i = 0; i < keyList.size(); i++) {
            KeyValueInfo key = keyList.get(i);
            value[i] = DigitUtil.StringToDouble(key.value.split(";")[0]);
            names[i] = key.key;
            value1[i] = DigitUtil.StringToDouble(key.value.split(";")[1]);
            value2[i] = DigitUtil.StringToDouble(key.value.split(";")[2]);
            value3[i] = DigitUtil.StringToDouble(key.value1.split(";")[0]);
            value4[i] = DigitUtil.StringToDouble(key.value1.split(";")[1]);
            value5[i] = DigitUtil.StringToDouble(key.value1.split(";")[2]);

        }

        values.add(value);
        values.add(value1);
        values.add(value2);
        values.add(value3);
        values.add(value4);
        values.add(value5);

        int[] colors = new int[]{getMyColor(R.color.chart9), getMyColor(R.color.chart1), getMyColor(R.color.chart2),
                getMyColor(R.color.chart3), getMyColor(R.color.chart4), getMyColor(R.color.chart5), getMyColor(R.color.chart6),
                getMyColor(R.color.chart7), getMyColor(R.color.chart8)};
        mChartUtil.setColors(colors);
        mChartUtil.setColors(colors);

        String[] titles = new String[6];
        for (int i = 0; i < 6; i++) {
            titles[i] = Util.status1[i];
        }

        GraphicalView graphicalView;
        if (mItemInfo.name.equals("状态变化"))
            graphicalView = mChartUtil.createLineChartView(titles, names, "", values);
        else {
            graphicalView = mChartUtil.createBarChartView(titles, names, "", values);
        }
        llChart.removeAllViews();
        llChart.addView(graphicalView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

    }

    /**
     * 画折线图
     *
     * @param keyList
     */
    private void drawLineChart(List<KeyValueInfo> keyList) {
        List<double[]> values = new ArrayList<>();
        Map<String, Object> map = getChartData(keyList);
        double[] value = (double[]) map.get("value");
        String[] names = (String[]) map.get("name");
        values.add(value);
        if (map.get("value1") != null) {
            double[] barValue1 = (double[]) map.get("value1");
            values.add(barValue1);
        }
        if (map.get("value2") != null) {
            double[] barValue2 = (double[]) map.get("value2");
            values.add(barValue2);
        }
        if (map.get("value3") != null) {
            double[] barValue1 = (double[]) map.get("value1");
            values.add(barValue1);
        }
        int[] colors = new int[]{getMyColor(R.color.chart9), getMyColor(R.color.chart1), getMyColor(R.color.chart2),
                getMyColor(R.color.chart3), getMyColor(R.color.chart4), getMyColor(R.color.chart5), getMyColor(R.color.chart6),
                getMyColor(R.color.chart7), getMyColor(R.color.chart8)};
        mChartUtil.setColors(colors);
        mChartUtil.setColors(colors);
        GraphicalView graphicalView = mChartUtil.createLineChartView(new String[]{mItemInfo.name}, names, "", values);
        llChart.removeAllViews();
        llChart.addView(graphicalView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * 画柱状图
     *
     * @param keyList
     */
    private void drawBarChart(List<KeyValueInfo> keyList) {
        List<double[]> barValues = new ArrayList<>();
        Map<String, Object> map = getChartData(keyList);
        double[] barValue = (double[]) map.get("value");
        String[] barNames = (String[]) map.get("name");
        if (map.get("value1") != null) {
            double[] barValue1 = (double[]) map.get("value1");
            barValues.add(barValue1);
        }
        if (map.get("value2") != null) {
            double[] barValue2 = (double[]) map.get("value2");
            barValues.add(barValue2);
        }
        barValues.add(barValue);
        int[] colors = new int[]{getMyColor(R.color.chart9), getMyColor(R.color.chart1), getMyColor(R.color.chart2),
                getMyColor(R.color.chart3), getMyColor(R.color.chart4), getMyColor(R.color.chart5), getMyColor(R.color.chart6),
                getMyColor(R.color.chart7), getMyColor(R.color.chart8)};
        mChartUtil.setColors(colors);

        int size = barValues.size();
        String[] titles = new String[size];
        for (int i = 0; i < size; i++) {
            titles[i] = mItemInfo.name;
        }

        if (mItemInfo.name.equals("特种设备隐患")) {
            titles = new String[]{"安全附件", "安保合同", "下次检修"};
        } else if (mItemInfo.name.equals("药品检查") || mItemInfo.name.equals("工业产品检查")) {
            titles = new String[]{"检查数", "通过数"};
        } else if (mItemInfo.name.equals("化妆品")) {
            titles = new String[]{"美容", "美发"};
        }
        GraphicalView barView = mChartUtil.createBarChartView(titles, barNames, "", barValues);
        llChart.removeAllViews();
        llChart.addView(barView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * 获取数据map
     *
     * @param keyList
     * @return
     */
    private Map<String, Object> getChartData(List<KeyValueInfo> keyList) {
        Map<String, Object> map = new HashMap<>();
        int size = 0;
        for (int i = 0; i < keyList.size(); i++) {
            KeyValueInfo key = keyList.get(i);
            if (!"合计".equals(key.key) && !"总数".equals(key.key) && !"总计".equals(key.key)) {
                size++;
            }
        }
        double[] value = new double[size];
        double[] value1 = new double[size];
        double[] value2 = new double[size];
        double[] value3 = new double[size];
        String[] name = new String[size];
        for (int i = 0; i < keyList.size(); i++) {
            KeyValueInfo key = keyList.get(i);
            if (!"合计".equals(key.key) && !"总数".equals(key.key) && !"总计".equals(key.key)) {
                value[i] = DigitUtil.StringToDouble(key.value);
                name[i] = key.key;
                value1[i] = DigitUtil.StringToDouble(key.value1);
//                value2[i] = DigitUtil.StringToDouble(key.value2);
//                value3[i] = DigitUtil.StringToDouble(key.code);
            }
        }
        map.put("value", value);
        map.put("name", name);
        map.put("value1", keyList.get(0).value1 == null ? null : value1);
        map.put("value2", keyList.get(0).value2 == null ? null : value2);
        map.put("value3", keyList.get(0).code == null ? null : value3);
        return map;
    }

    /**
     * 获取颜色
     *
     * @param id
     * @return
     */
    public int getMyColor(int id) {
        return ContextCompat.getColor(mContext, id);
    }

}
