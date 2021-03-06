package com.zx.zsmarketmobile.ui.infomanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.zsmarketmobile.R;
import com.zx.zsmarketmobile.adapter.infomanager.MeasureLiebiaoAdapter;
import com.zx.zsmarketmobile.entity.infomanager.InfoManagerMeasureLiebiao;
import com.zx.zsmarketmobile.http.ApiData;
import com.zx.zsmarketmobile.http.BaseHttpResult;
import com.zx.zsmarketmobile.listener.LoadMoreListener;
import com.zx.zsmarketmobile.listener.MyItemClickListener;
import com.zx.zsmarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/23.
 */

public class MeasureLiebiaoFragment extends BaseFragment implements LoadMoreListener, MyItemClickListener {
    private static final String TAG = "StandardMessageSelectFragment";
    private RecyclerView rvTodo;
    private SwipeRefreshLayout srlTodo;
    private MeasureLiebiaoAdapter mAdapter;
    private String msg = "";
    private List<InfoManagerMeasureLiebiao.RowsBean> dataList = new ArrayList<>();
    private int mPageSize = 10;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private ApiData getInfoStandar = new ApiData(ApiData.HTTP_ID_info_manager_measuring_instruments_liebiao);

    public static MeasureLiebiaoFragment newInstance() {
        MeasureLiebiaoFragment fragment = new MeasureLiebiaoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_to_do_list, container, false);

        rvTodo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        srlTodo = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        rvTodo.setLayoutManager(mLinearLayoutManager);
        getInfoStandar.setLoadingListener(this);
        mAdapter = new MeasureLiebiaoAdapter(getActivity(), dataList, true);
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
        Intent intent = new Intent(getActivity(), MeasureDetailActivity.class);
        intent.putExtra("entity", dataList.get(position));
        intent.putExtra("type", 3);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    //数据加载
    @SuppressLint("LongLogTag")
    private void loadData() {
        if (msg.length() != 0)
        {
            String[] value = msg.split(";");
            if (value.length == 2) {
                getInfoStandar.loadData(mPageNo, mPageSize, value[0], value[1]);
            } else {
                getInfoStandar.loadData(mPageNo, mPageSize, value[0]);
            }
        }
        else
            getInfoStandar.loadData(mPageNo, mPageSize, "t_trading_tools");
    }

    public void load(final String msg) {
        this.msg = msg;
        String[] value = msg.split(";");
        if (value.length == 2) {
            getInfoStandar.loadData(mPageNo, mPageSize, value[0], value[1]);
        } else {
            getInfoStandar.loadData(mPageNo, mPageSize, value[0]);
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        srlTodo.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_info_manager_measuring_instruments_liebiao:
                InfoManagerMeasureLiebiao myTaskListEntity = (InfoManagerMeasureLiebiao) b.getEntry();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                List<InfoManagerMeasureLiebiao.RowsBean> entityList = myTaskListEntity.getList();
                dataList.clear();
                if (entityList != null) {
                    dataList.addAll(entityList);
                }
                mAdapter.notifyDataSetChanged();

                InfoManagerMeasureLiebiao.RowsBean bean = null;
                for (int j = 0; j < dataList.size(); j++) {
                    bean = dataList.get(j);
                    Log.i(TAG, "bean is " + bean.getEnterpriseName());
                }

                break;

            default:
                break;
        }
    }
}
