package com.zx.zsmarketmobile.ui.statistics;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zx.zsmarketmobile.R;
import com.zx.zsmarketmobile.adapter.ChartListAdapter;
import com.zx.zsmarketmobile.entity.NormalListEntity;
import com.zx.zsmarketmobile.http.ApiData;
import com.zx.zsmarketmobile.http.BaseHttpResult;
import com.zx.zsmarketmobile.listener.LoadMoreListener;
import com.zx.zsmarketmobile.listener.MyItemClickListener;
import com.zx.zsmarketmobile.ui.base.BaseActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2017/5/18
 * 功能：统计钻取列表
 */
public class ChartListActivity extends BaseActivity implements MyItemClickListener, SwipeRefreshLayout.OnRefreshListener, LoadMoreListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ChartListAdapter mAdapter;
    private int mPageSize = 20;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private String itemName = "", lineName = "", columnName = "";
    private List<JSONObject> dataList = new ArrayList<>();
    private ApiData getDataList = new ApiData(ApiData.HTTP_ID_getStatisticsList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_swipe_recycler_view);
        initView();
    }

    /**
     * 初始化地图
     */
    private void initView() {
        itemName = getIntent().getStringExtra("key");
        lineName = getIntent().getStringExtra("line");
        columnName = getIntent().getStringExtra("column");
        addToolBar(true);
        hideRightImg();
        setMidText(itemName + "-" + columnName);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_normal_layout);
        recyclerView = (RecyclerView) findViewById(R.id.rv_normal_view);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new ChartListAdapter(this, dataList);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        getDataList.setLoadingListener(this);

        mAdapter.notifyDataSetChanged();

        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        getDataList.loadData(mPageSize, mPageNo, itemName, lineName, columnName);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult baseHttpResult) {
        super.onLoadComplete(id, baseHttpResult);
        swipeRefreshLayout.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_getStatisticsList:
                recyclerView.scrollToPosition(0);
                NormalListEntity normalListEntity = (NormalListEntity) baseHttpResult.getEntry();
                mTotalNo = normalListEntity.getTotal();
                mAdapter.setStatus(0, mPageNo, mTotalNo, mPageSize);
                List<JSONObject> tempList = normalListEntity.getChartList();
                dataList.clear();
                dataList.addAll(tempList);
                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }

    /**
     * item点击事件
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        JSONObject jsonInfo = dataList.get(position);
        ChartDetailActivity.starAction(this, itemName, jsonInfo);
    }

    /**
     * 下拉刷新事件
     */
    @Override
    public void onRefresh() {
        if (mPageNo > 1) {
            mPageNo--;
        }
        loadData();
    }

    /**
     * 查看更多事件
     */
    @Override
    public void LoadMore() {
        if (mPageNo * mPageSize < mTotalNo) {
            mPageNo++;
            mAdapter.setStatus(1, mPageNo, mTotalNo, mPageSize);
            loadData();
        }
    }

}
