package com.zx.zsmarketmobile.ui.taskexcute;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.zsmarketmobile.R;
import com.zx.zsmarketmobile.adapter.TaskExcuteAdapter;
import com.zx.zsmarketmobile.entity.EntityDetail;
import com.zx.zsmarketmobile.entity.HttpSearchZtEntity;
import com.zx.zsmarketmobile.entity.HttpZtEntity;
import com.zx.zsmarketmobile.http.ApiData;
import com.zx.zsmarketmobile.http.BaseHttpResult;
import com.zx.zsmarketmobile.listener.LoadMoreListener;
import com.zx.zsmarketmobile.listener.MyItemClickListener;
import com.zx.zsmarketmobile.ui.base.BaseFragment;
import com.zx.zsmarketmobile.ui.caselegal.CaseMyListFragment;
import com.zx.zsmarketmobile.ui.map.EntityDetailActivity;
import com.zx.zsmarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.zsmarketmobile.util.ConstStrings;
import com.zx.zsmarketmobile.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2017/3/10
 * 功能：案件待办已办fragment
 */
public class TaskExcuteFragment extends BaseFragment implements LoadMoreListener, MyItemClickListener {

    private RecyclerView rvTodo;
    private SwipeRefreshLayout srlTodo;
    private TaskExcuteAdapter mAdapter;
    private List<HttpZtEntity> dataList = new ArrayList<>();
    private int mPageSize = 10;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private int currentIndex = -1;
    private CaseMyListFragment caseMyListFragment;
    private ApiData getToDoPage = new ApiData(ApiData.HTTP_ID_searchzt);
    private ApiData taskData = new ApiData(ApiData.HTTP_ID_entity_detail);

    public static TaskExcuteFragment newInstance() {
        TaskExcuteFragment fragment = new TaskExcuteFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_to_do_list, container, false);
        rvTodo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        srlTodo = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        rvTodo.setLayoutManager(mLinearLayoutManager);
        getToDoPage.setLoadingListener(this);
        taskData.setLoadingListener(this);
        mAdapter = new TaskExcuteAdapter(getActivity(), dataList);
        rvTodo.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
        srlTodo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPageNo > 1) {
                    mPageNo--;
                }
                loadData();
            }
        });
        return view;
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

    //item点击事件
    @Override
    public void onItemClick(View view, int position) {
//        Intent intent = new Intent(getActivity(), CaseDetailActivity.class);
//        intent.putExtra("entity", dataList.get(position));
//        intent.putExtra("showExcute", index == 0 ? true : false);
//        startActivity(intent);
        currentIndex = position;
        HttpZtEntity zt = dataList.get(position);
        taskData.loadData(zt.getProjGuid());
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    //数据加载
    private void loadData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNo", mPageNo);
            jsonObject.put("pageSize", 10);
            jsonObject.put("infoStatus", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getToDoPage.loadData(jsonObject.toString());
    }

    HttpSearchZtEntity mSearchZtEntity;

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        srlTodo.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_searchzt:
                mSearchZtEntity = (HttpSearchZtEntity) b.getEntry();
                mTotalNo = mSearchZtEntity.getTotal();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                List<HttpZtEntity> entityList = mSearchZtEntity.getZtList();
                dataList.clear();
                dataList.addAll(entityList);
                mAdapter.notifyDataSetChanged();
                break;
            case ApiData.HTTP_ID_entity_detail:
                HttpZtEntity zt = dataList.get(currentIndex);
                EntityDetail mEntityDetail = (EntityDetail) b.getEntry();
                Intent intent = new Intent(getActivity(), EntityDetailActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("ztEntity", zt);
                intent.putExtra("entity", mEntityDetail);
//                if (zt != null) {
//                    String fEntityType = zt.getfEntityType();
//                    String fEntityGuid = zt.getGuid();
//                    intent.putExtra("fEntityType", fEntityType);
//                    intent.putExtra("fEntityGuid", fEntityGuid);
//                }
                startActivityForResult(intent, 0);
                Util.activity_In(getActivity());
                break;
            default:
                break;
        }
    }

    public void onTooBarMapClick() {
        if (mSearchZtEntity != null && mSearchZtEntity.getZtList() != null && mSearchZtEntity.getZtList().size() > 0) {
            Intent intent = new Intent(getActivity(), WorkInMapShowActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("type", ConstStrings.MapType_SearchZt);
            bundle.putSerializable("entity", mSearchZtEntity);
            intent.putExtras(bundle);
            startActivity(intent);
            Util.activity_In(getActivity());
        } else {
            showToast("无查询结果，不可在地图上查看主体！");
        }
    }
}
