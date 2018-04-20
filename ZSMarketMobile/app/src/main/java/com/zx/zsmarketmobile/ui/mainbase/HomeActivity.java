package com.zx.zsmarketmobile.ui.mainbase;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zx.zsmarketmobile.R;
import com.zx.zsmarketmobile.R.id;
import com.zx.zsmarketmobile.entity.StatisticsInfo;
import com.zx.zsmarketmobile.entity.StatisticsItemInfo;
import com.zx.zsmarketmobile.ui.base.BaseActivity;
import com.zx.zsmarketmobile.ui.caselegal.CaseMonitorFragment;
import com.zx.zsmarketmobile.ui.caselegal.CaseMyListFragment;
import com.zx.zsmarketmobile.ui.caselegal.CaseSearchFragment;
import com.zx.zsmarketmobile.ui.complain.ComplainMonitorFragment;
import com.zx.zsmarketmobile.ui.complain.ComplainMyListFragment;
import com.zx.zsmarketmobile.ui.complain.ComplainSearchFragment;
import com.zx.zsmarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.zsmarketmobile.ui.statistics.StatisticsFragment;
import com.zx.zsmarketmobile.ui.supervise.mytask.SuperviseMyTaskFragment;
import com.zx.zsmarketmobile.ui.system.HelpActivity;
import com.zx.zsmarketmobile.ui.system.SettingsActivity;
import com.zx.zsmarketmobile.util.ConstStrings;
import com.zx.zsmarketmobile.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2016/9/19
 * 功能：主界面
 */
public class HomeActivity extends BaseActivity implements OnClickListener {
    private static String TAG = "HomeActivity";
    public ViewPager mVpContent;
    private TabLayout homeTabLayout;
    private TaskNumFragment mComplaintFragment;// 投诉举报
    private TaskNumFragment mSuperviseFragment;// 监管任务
    private int index;
    private StatisticsFragment mStatisticsFragment;// 统计分析
    private SharedPreferences mSharePreferences;
    private TextView mTextViewBottomMessage;/* 消息中心-消息通知 */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addToolBar(true);
        showMidPic();

        getRightImg().setOnClickListener(this);

        mVpContent = (ViewPager) findViewById(R.id.vp_normal_pager);
        homeTabLayout = (TabLayout) findViewById(R.id.tb_normal_layout);
        index = getIntent().getIntExtra("item", 0);
        initialViewPager();
        findViewById(id.tvActHome_setting).setOnClickListener(this);
        findViewById(id.tvActHome_message).setOnClickListener(this);
        findViewById(id.tvActHome_help).setOnClickListener(this);
        mSharePreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mTextViewBottomMessage = (TextView) findViewById(id.tv_bottom_messsage_msg);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (index != 3) {
            loadData();
        }
    }

    public void loadData() {
//        taskData.loadData(userInfo.getId(), userInfo.getAuthority(), userInfo.getDepartment());
    }

    public HomeActivity() {
    }

    public void initialViewPager() {
        switch (index) {
            case 0://监管任务
                setMidText("监管任务");
                String departName = "";
                if (userInfo != null && !TextUtils.isEmpty(userInfo.getDepartment())) {
                    departName = userInfo.getDepartment();
                }
                Log.i(TAG, "departName is " + departName);
//                if (!departName.equals("管理员")) {
//                    myPagerAdapter.addFragment(SuperviseMyTaskFragment.newInstance(), "我的任务");
//                }

                myPagerAdapter.addFragment(SuperviseMyTaskFragment.newInstance(), "我的任务");
//                myPagerAdapter.addFragment(TaskNumFragment.newInstance(1), "状态监控");

//                myPagerAdapter.addFragment(SuperviseClaimedFragment.newInstance(0), "坐标纠正");
//                myPagerAdapter.addFragment(SuperviseClaimedFragment.newInstance(1), "主体认领");
                break;
            case 1://数据分析
                setMidText("数据分析");
                getRightImg().setVisibility(View.GONE);
                StatisticsInfo superviseInfo = createSuperviseData();
                myPagerAdapter.addFragment(StatisticsFragment.newInstance(superviseInfo), "结构分析");
                StatisticsInfo staticInfo = createStatisticeData();
                myPagerAdapter.addFragment(StatisticsFragment.newInstance(staticInfo), "对比分析");
                StatisticsInfo compInfo = createCompData();
                myPagerAdapter.addFragment(StatisticsFragment.newInstance(compInfo), "榜单分析");
                StatisticsInfo caseInfo = createCaseData();
                myPagerAdapter.addFragment(StatisticsFragment.newInstance(caseInfo), "趋势分析");

                break;
            case 2://案件执法
                setMidText("统计月报");
                getRightImg().setVisibility(View.GONE);
                superviseInfo = createyuebaoData1();
                myPagerAdapter.addFragment(StatisticsFragment.newInstance(superviseInfo), "总体概括");
                staticInfo = createyuebaoData2();
                myPagerAdapter.addFragment(StatisticsFragment.newInstance(staticInfo), "今年累计");
                compInfo = createyuebaoData3();
                myPagerAdapter.addFragment(StatisticsFragment.newInstance(compInfo), "历史累计");
                break;
            case 3://信息管理
//                toActivity(InfoManagerActivity.class);
                break;

            case 4://统计分析
//                setMidText("统计分析");
//                getRightImg().setVisibility(View.GONE);
//                StatisticsInfo superviseInfo = createSuperviseData();
//                myPagerAdapter.addFragment(StatisticsFragment.newInstance(superviseInfo), "监管任务");
//                StatisticsInfo compInfo = createCompData();
//                myPagerAdapter.addFragment(StatisticsFragment.newInstance(compInfo), "投诉举报");
//                StatisticsInfo caseInfo = createCaseData();
//                myPagerAdapter.addFragment(StatisticsFragment.newInstance(caseInfo), "案件执法");
//                List<StatisticsInfo> mDataList = createStatisticeData();
//                for (int i = 0; i < mDataList.size(); i++) {
//                    myPagerAdapter.addFragment(StatisticsFragment.newInstance(mDataList.get(i)), mDataList.get(i).labelName);
//                }
//                if (mDataList.size() == 0) {
//                    homeTabLayout.setVisibility(View.GONE);
//                }
                break;

            case 5://帮助

                break;

            case 6://设置

                break;
        }
        if (myPagerAdapter.fragmentList.size() < 2) {
            homeTabLayout.setVisibility(View.GONE);
        }
        mVpContent.setOffscreenPageLimit(myPagerAdapter.getCount());
        mVpContent.setAdapter(myPagerAdapter);
        homeTabLayout.setupWithViewPager(mVpContent);
        Util.dynamicSetTabLayoutMode(homeTabLayout);
        mVpContent.setCurrentItem(0);
    }

    private StatisticsInfo createyuebaoData1() {
        StatisticsInfo dataInfo = new StatisticsInfo();
        dataInfo.labelName = "总体概括";
        dataInfo.itemList.add(new StatisticsItemInfo("推进情况", 0, "月报", R.mipmap.statis_bmtj));
        dataInfo.itemList.add(new StatisticsItemInfo("项目状态", 1, "月报", R.mipmap.statis_lytj));
        dataInfo.itemList.add(new StatisticsItemInfo("签约项目情况", 1, "月报", R.mipmap.statis_latj));
        dataInfo.itemList.add(new StatisticsItemInfo("开工项目情况", 1, "月报", R.mipmap.statis_tsjl));
        dataInfo.itemList.add(new StatisticsItemInfo("投资项目情况", 1, "月报", R.mipmap.statis_rwlx));
        dataInfo.itemList.add(new StatisticsItemInfo("在谈项目情况", 1, "月报", R.mipmap.statis_rwzt));
        dataInfo.itemList.add(new StatisticsItemInfo("新区成立至X月末项目状况", 1, "月报", R.mipmap.statis_tzsb));
        return dataInfo;
    }

    private StatisticsInfo createyuebaoData2() {
        StatisticsInfo dataInfo = new StatisticsInfo();
        dataInfo.labelName = "今年累计";
        dataInfo.itemList.add(new StatisticsItemInfo("1-X月签约项目情况表", 1, "月报", R.mipmap.statis_bmtj));
        dataInfo.itemList.add(new StatisticsItemInfo("1-X月签约工业项目", 1, "月报", R.mipmap.statis_lytj));
        dataInfo.itemList.add(new StatisticsItemInfo("1-X月签服务业项目", 1, "月报", R.mipmap.statis_latj));
        dataInfo.itemList.add(new StatisticsItemInfo("1-X月开工项目情况表", 1, "月报", R.mipmap.statis_tsjl));
        dataInfo.itemList.add(new StatisticsItemInfo("1-X月开工工业项目", 1, "月报", R.mipmap.statis_rwlx));
        dataInfo.itemList.add(new StatisticsItemInfo("1-X月开工服务业项目", 1, "月报", R.mipmap.statis_rwzt));
        dataInfo.itemList.add(new StatisticsItemInfo("1-X月投产项目情况表", 1, "月报", R.mipmap.statis_tzsb));
        dataInfo.itemList.add(new StatisticsItemInfo("1-X月投产工業項目", 1, "月报", R.mipmap.statis_tzsb));
        dataInfo.itemList.add(new StatisticsItemInfo("1-X月投产服務業項目", 1, "月报", R.mipmap.statis_tzsb));


        return dataInfo;
    }

    private StatisticsInfo createyuebaoData3() {
        StatisticsInfo dataInfo = new StatisticsInfo();
        dataInfo.labelName = "历史累计";
        dataInfo.itemList.add(new StatisticsItemInfo("已签约未开工工业项目前10位", 1, "月报", R.mipmap.statis_bmtj));
        dataInfo.itemList.add(new StatisticsItemInfo("已签约未开工服务业项目前10位", 1, "月报", R.mipmap.statis_lytj));
        dataInfo.itemList.add(new StatisticsItemInfo("已开工未投产工业项目前10位", 1, "月报", R.mipmap.statis_latj));
        dataInfo.itemList.add(new StatisticsItemInfo("已开工未投产服务业项目前10位", 1, "月报", R.mipmap.statis_tsjl));
        return dataInfo;
    }

    /**
     * 案件执法
     *
     * @return
     */
    private StatisticsInfo createCaseData() {
        StatisticsInfo dataInfo = new StatisticsInfo();
        dataInfo.labelName = "趋势分析";
        dataInfo.itemList.add(new StatisticsItemInfo("数量变化", 1, "时间", R.mipmap.statis_bmtj));
        dataInfo.itemList.add(new StatisticsItemInfo("状态变化", 1, "时间", R.mipmap.statis_lytj));
        dataInfo.itemList.add(new StatisticsItemInfo("投资金额", 1, "时间", R.mipmap.statis_latj));
        dataInfo.itemList.add(new StatisticsItemInfo("纳税金额", 1, "时间", R.mipmap.statis_tsjl));
        return dataInfo;
    }

    /**
     * 投诉举报
     */
    private StatisticsInfo createCompData() {
        StatisticsInfo dataInfo = new StatisticsInfo();
        dataInfo.labelName = "榜单分析";
        dataInfo.itemList.add(new StatisticsItemInfo("状态排行", 0, "板块", R.mipmap.statis_slbm));
        dataInfo.itemList.add(new StatisticsItemInfo("投资总额", 0, "板块", R.mipmap.statis_tslx));
        dataInfo.itemList.add(new StatisticsItemInfo("纳税总额", 0, "板块", R.mipmap.statis_xxly));
        dataInfo.itemList.add(new StatisticsItemInfo("增长率排行", 0, "类别", R.mipmap.statis_ywly));
        return dataInfo;
    }

    /**
     * 监管任务
     */
    private StatisticsInfo createSuperviseData() {
        StatisticsInfo dataInfo = new StatisticsInfo();
        dataInfo.labelName = "结构分析";
//        dataInfo.itemList.add(new StatisticsItemInfo("任务主体", 0, "类别", R.mipmap.statistic_qyfb));
        dataInfo.itemList.add(new StatisticsItemInfo("资金来源", 2, "资金来源", R.mipmap.statis_rwlx));
        dataInfo.itemList.add(new StatisticsItemInfo("所属板块", 0, "板块名称", R.mipmap.statis_jczt));
        dataInfo.itemList.add(new StatisticsItemInfo("产业分布", 2, "所属行业", R.mipmap.statis_zxrws));
        dataInfo.itemList.add(new StatisticsItemInfo("项目状态", 2, "项目状态", R.mipmap.statis_xbwq));
        return dataInfo;
    }

    /**
     * 统计分析
     *
     * @return
     */
    private StatisticsInfo createStatisticeData() {
        StatisticsInfo info = new StatisticsInfo();
        info.labelName = "对比分析";
        info.itemList.add(new StatisticsItemInfo("数量对比", 0, "所属板块", R.mipmap.statis_zxrws));
        info.itemList.add(new StatisticsItemInfo("状态对比", 0, "项目状态", R.mipmap.statis_rwlx));
        info.itemList.add(new StatisticsItemInfo("资金对比", 0, "资金来源", R.mipmap.statis_tzsb));
        info.itemList.add(new StatisticsItemInfo("增长对比", 0, "所属板块", R.mipmap.statis_xbwq));
        return info;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.toolbar_right:
                Intent mapIntent = new Intent(HomeActivity.this, WorkInMapShowActivity.class);
                mapIntent.putExtra("type", ConstStrings.MapType_Main);
                startActivity(mapIntent);
                break;
            case id.tvActHome_setting:
                toActivity(SettingsActivity.class);
                break;
            case id.tvActHome_message:
                toActivity(MessageCenterActivity.class);
                break;
            case id.tvActHome_help:
                Intent helpIntent = new Intent(HomeActivity.this, HelpActivity.class);
                helpIntent.putExtra("isToLogin", false);
                startActivity(helpIntent);
                break;
            default:
                break;
        }
    }

    private void toActivity(Class<? extends Activity> c) {
        Intent intent = new Intent(HomeActivity.this, c);
        startActivity(intent);
        Util.activity_In(this);
    }
}