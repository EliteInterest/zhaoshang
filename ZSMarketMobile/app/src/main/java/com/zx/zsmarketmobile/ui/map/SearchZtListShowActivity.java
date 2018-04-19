package com.zx.zsmarketmobile.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.zsmarketmobile.R;
import com.zx.zsmarketmobile.R.id;
import com.zx.zsmarketmobile.adapter.PaginationAdapter;
import com.zx.zsmarketmobile.adapter.SearchFilterAdapter;
import com.zx.zsmarketmobile.entity.EntityDetail;
import com.zx.zsmarketmobile.entity.HttpSearchZtEntity;
import com.zx.zsmarketmobile.entity.HttpZtEntity;
import com.zx.zsmarketmobile.entity.SearchFilterEntity;
import com.zx.zsmarketmobile.http.ApiData;
import com.zx.zsmarketmobile.http.BaseHttpResult;
import com.zx.zsmarketmobile.listener.LoadMoreListener;
import com.zx.zsmarketmobile.listener.MyItemClickListener;
import com.zx.zsmarketmobile.ui.base.BaseActivity;
import com.zx.zsmarketmobile.util.ConstStrings;
import com.zx.zsmarketmobile.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：主体搜索列表
 */
@SuppressWarnings("deprecation")
public class SearchZtListShowActivity extends BaseActivity implements MyItemClickListener, LoadMoreListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mTextViewCount;
    private PaginationAdapter mAdapter;
    private String mKeyword = "";
    public int mPageNo = 1;
    private TextView tvFilter;
    private LinearLayout llFilter;
    private RecyclerView rvFilter;
    private Button btnSearch, btnCancel;
    private boolean mIsCreated = false;
    public int mTotalNo = 0;
    private int currentIndex = -1;

    private ApiData ztsearchData = new ApiData(ApiData.HTTP_ID_searchzt);

    private ApiData taskData = new ApiData(ApiData.HTTP_ID_entity_detail);
    private HttpSearchZtEntity mSearchZtEntity = null;
    private List<HttpZtEntity> mTaskList = new ArrayList<>();
    private SearchFilterAdapter searchFilterAdapter;
    private String queryJson = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchzt_list_show);

        addToolBar(true);
        setMidText("查询结果");
        getRightImg().setOnClickListener(this);

        mIsCreated = true;
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(id.srl_normal_layout);
        mRecyclerView = (RecyclerView) findViewById(id.rv_normal_view);
        mTextViewCount = (TextView) findViewById(id.tv_searchzt_count);
        tvFilter = findViewById(id.tv_search_filter);
        llFilter = findViewById(id.ll_search_filter);
        rvFilter = findViewById(id.rv_search_filter);
        btnSearch = findViewById(id.btn_search_dosearch);
        btnCancel = findViewById(id.btn_search_cancel);

        ztsearchData.setLoadingListener(this);
        taskData.setLoadingListener(this);
        tvFilter.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mKeyword = bundle.getString("keyword");
                mSearchZtEntity = (HttpSearchZtEntity) bundle.getSerializable("entity");
                mTaskList = mSearchZtEntity.getZtList();
                mTotalNo = mSearchZtEntity.getTotal();
                mTextViewCount.setText(mSearchZtEntity.getTotal() + "");
            }
        }
        //设置适配器及监听
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new PaginationAdapter(this, mTaskList);
        mRecyclerView.setAdapter(mAdapter);
        mTextViewCount.setTextColor(getResources().getColor(R.color.white));
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //监听mRecyclerView是否滑动到最后一个
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //当RecyclerView不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        mAdapter.setStatus(0, mPageNo, mTotalNo);
                    }
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                if (mPageNo > 1) {
                    mPageNo--;
                }
                loadData();
            }
        });

        mRecyclerView.setVisibility(View.GONE);

        rvFilter.setLayoutManager(new LinearLayoutManager(this));
        searchFilterAdapter = new SearchFilterAdapter();
        rvFilter.setAdapter(searchFilterAdapter);

//        loadData(true);

    }

    @Override
    public void LoadMore() {
        if (mPageNo * 10 <= mTotalNo) {
            mPageNo++;
            mAdapter.setStatus(1, mPageNo, mTotalNo);
            loadData();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        currentIndex = position;
        HttpZtEntity zt = mTaskList.get(position);
        taskData.loadData(zt.getProjGuid());
//        taskData.loadData(userInfo.getId(), zt.getGuid(), zt.getCreditLevel(), zt.getfEntityType());

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mPageNo = 1;
//        loadData();
    }

    public void loadData() {
        if (queryJson.length() == 0) {
            return;
        }
        ztsearchData.loadData(queryJson);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.toolbar_right:
                if (mSearchZtEntity!=null&&mSearchZtEntity.getZtList() != null && mSearchZtEntity.getZtList().size() > 0) {
                    Intent intent = new Intent(SearchZtListShowActivity.this, WorkInMapShowActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", ConstStrings.MapType_SearchZt);
                    bundle.putSerializable("entity", mSearchZtEntity);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Util.activity_In(this);
                } else {
                    showToast("无查询结果，不可在地图上查看主体！");
                }
                break;
            case R.id.tv_search_filter:
                if (llFilter.getVisibility() == View.GONE) {
                    llFilter.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
                    llFilter.setVisibility(View.VISIBLE);
                } else {
                    llFilter.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                    llFilter.setVisibility(View.GONE);
                }
                break;
            case id.btn_search_dosearch:
                List<SearchFilterEntity> datas = searchFilterAdapter.getDataList();
                JSONObject jsonObject = new JSONObject();
                mPageNo = 1;
                try {
                    jsonObject.put("pageNo", mPageNo);
                    jsonObject.put("pageSize", "10");
                    jsonObject.put("projName", datas.get(0).getValue());
                    jsonObject.put("projCode", datas.get(1).getValue());
                    jsonObject.put("projStage", datas.get(2).getValue());
                    jsonObject.put("isForeign", datas.get(3).getValue());
                    jsonObject.put("projType", datas.get(4).getValue());
                    jsonObject.put("projIndustry", datas.get(5).getValue());
                    jsonObject.put("projNewIns", datas.get(6).getValue());
                    jsonObject.put("investAgreementNum", datas.get(7).getValue());
                    jsonObject.put("supplementAgreementNum", datas.get(8).getValue());
                    jsonObject.put("zshRecordNum", datas.get(9).getValue());
                    jsonObject.put("BghRecordNum", datas.get(10).getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                queryJson = jsonObject.toString();
                loadData();
                break;
            case id.btn_search_cancel:
                llFilter.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                llFilter.setVisibility(View.GONE);
                break;

            default:
                break;
        }

    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        mSwipeRefreshLayout.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_searchzt:
                mRecyclerView.setVisibility(View.VISIBLE);
                mSearchZtEntity = (HttpSearchZtEntity) b.getEntry();
                mTotalNo = mSearchZtEntity.getTotal();
                mTextViewCount.setText(mTotalNo + "");
                mTaskList.clear();
                mTaskList.addAll(mSearchZtEntity.getZtList());
                mAdapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(0);
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                llFilter.setVisibility(View.GONE);
                break;

            case ApiData.HTTP_ID_entity_detail:
                HttpZtEntity zt = mTaskList.get(currentIndex);
                EntityDetail mEntityDetail = (EntityDetail) b.getEntry();
                Intent intent = new Intent(SearchZtListShowActivity.this, EntityDetailActivity.class);
                intent.putExtra("type", 2);
                intent.putExtra("ztEntity", zt);
                intent.putExtra("entity", mEntityDetail);
//                if (zt != null) {
//                    String fEntityType = zt.getfEntityType();
//                    String fEntityGuid = zt.getGuid();
//                    intent.putExtra("fEntityType", fEntityType);
//                    intent.putExtra("fEntityGuid", fEntityGuid);
//                }
                startActivity(intent);
                Util.activity_In(this);
                break;

            default:
                break;
        }

    }


}
