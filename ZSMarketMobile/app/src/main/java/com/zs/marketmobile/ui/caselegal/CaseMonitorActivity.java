package com.zs.marketmobile.ui.caselegal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zs.marketmobile.R;
import com.zs.marketmobile.adapter.CaseListAdapter;
import com.zs.marketmobile.entity.CaseInfoEntity;
import com.zs.marketmobile.entity.NormalListEntity;
import com.zs.marketmobile.http.ApiData;
import com.zs.marketmobile.http.BaseHttpResult;
import com.zs.marketmobile.listener.LoadMoreListener;
import com.zs.marketmobile.listener.MyItemClickListener;
import com.zs.marketmobile.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2017/3/17
 * 功能：案件监控fragment
 */
public class CaseMonitorActivity extends BaseActivity implements LoadMoreListener, MyItemClickListener, View.OnClickListener {

    private RecyclerView rvSearch;
    private SwipeRefreshLayout srlSearch;
    private CaseListAdapter mAdapter;
    private List<CaseInfoEntity> dataList = new ArrayList<>();
    private int mPageSize = 10;
    private int mPageNo = 1;
    private int mTotalNo = 0;
    private String type = "", yqtype = "";
    private ApiData pageLcjk = new ApiData(ApiData.HTTP_ID_casePageAyxx);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_swipe_recycler_view);
        initView();
    }

    private void initView() {
        addToolBar(true);
        setMidText("案件列表");
        hideRightImg();

        type = getIntent().getStringExtra("type");
        yqtype = getIntent().getStringExtra("yqtype");
        rvSearch = (RecyclerView) findViewById(R.id.rv_normal_view);
        srlSearch = (SwipeRefreshLayout) findViewById(R.id.srl_normal_layout);
        rvSearch.setLayoutManager(mLinearLayoutManager);
        mAdapter = new CaseListAdapter(this, dataList, false);
        rvSearch.setAdapter(mAdapter);
        pageLcjk.setLoadingListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
        srlSearch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPageNo > 1) {
                    mPageNo--;
                }
                loadData();
            }
        });
    }

    //加载更多
    @Override
    public void LoadMore() {
        if (mPageNo * mPageSize < mTotalNo) {
            mPageNo++;
            mAdapter.setStatus(1, mPageNo, mTotalNo);
            loadData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    //item点击事件
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, CaseDetailActivity.class);
        intent.putExtra("entity", dataList.get(position));
        intent.putExtra("monitor", true);
        startActivity(intent);
    }

    //数据加载
    private void loadData() {
        pageLcjk.loadData(mPageNo, mPageSize, yqtype, "", "", type);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        srlSearch.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_casePageAyxx:
                NormalListEntity normalListEntity = (NormalListEntity) b.getEntry();
                mTotalNo = normalListEntity.getTotal();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                List<CaseInfoEntity> entityList = normalListEntity.getCaseInfoEntityList();
                dataList.clear();
                dataList.addAll(entityList);
                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }

}
