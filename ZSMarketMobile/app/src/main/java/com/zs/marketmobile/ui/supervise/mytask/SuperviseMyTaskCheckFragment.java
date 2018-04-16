package com.zs.marketmobile.ui.supervise.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zs.marketmobile.R;
import com.zs.marketmobile.adapter.supervise.SuperviseMyTaskCheckListAdapter;
import com.zs.marketmobile.entity.NormalListEntity;
import com.zs.marketmobile.entity.supervise.MyTaskCheckEntity;
import com.zs.marketmobile.entity.supervise.MyTaskListEntity;
import com.zs.marketmobile.http.ApiData;
import com.zs.marketmobile.http.BaseHttpResult;
import com.zs.marketmobile.listener.LoadMoreListener;
import com.zs.marketmobile.listener.MyItemClickListener;
import com.zs.marketmobile.ui.base.BaseFragment;
import com.zs.marketmobile.view.MyTaskAddEntityView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/23.
 */

public class SuperviseMyTaskCheckFragment extends BaseFragment implements LoadMoreListener, MyItemClickListener {


    private RecyclerView rvTodo;
    private SwipeRefreshLayout srlTodo;
    private Button addEntity;
    private SuperviseMyTaskCheckListAdapter mAdapter;
    private int index;//0待办  1已办
    private int type;//0我的任务 1任务监控
    private List<MyTaskCheckEntity> dataList = new ArrayList<>();
    private int mPageSize = 10;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private boolean isResult = false;
    private MyTaskListEntity mEntity;
    public MyTaskAddEntityView addEntityView;

    private ApiData getTaskCheckEmtity = new ApiData(ApiData.HTTP_ID_SuperviseTaskCheckEntity);

    public static SuperviseMyTaskCheckFragment newInstance(MyTaskListEntity rowsBean, int index, int type) {
        SuperviseMyTaskCheckFragment fragment = new SuperviseMyTaskCheckFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putInt("type", type);
        args.putSerializable("mEntity", rowsBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_to_do_list, container, false);

        mEntity = (MyTaskListEntity) getArguments().getSerializable("mEntity");
        index = getArguments().getInt("index");
        type = getArguments().getInt("type");

        rvTodo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        addEntity = (Button) view.findViewById(R.id.btn_myTask_addEntity);
        srlTodo = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        rvTodo.setLayoutManager(mLinearLayoutManager);
        getTaskCheckEmtity.setLoadingListener(this);
        mAdapter = new SuperviseMyTaskCheckListAdapter(dataList, (index == 0 && type == 0) ? true : false);
        rvTodo.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
        addEntityView = new MyTaskAddEntityView(this, mEntity);
        srlTodo.setOnRefreshListener(() -> {
            if (mPageNo > 1) {
                mPageNo--;
            }
            loadData();
        });
        addEntity.setOnClickListener(view1 -> addEntityView.show());
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
        Intent intent = new Intent(getActivity(), SuperviseMyTaskCheckActivity.class);
        intent.putExtra("entity", dataList.get(position));
        intent.putExtra("myToDo", mEntity);
        intent.putExtra("index", index);
        intent.putExtra("type", type);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            isResult = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    //数据加载
    public void loadData() {
//            getTaskCheckEmtity.loadData(mEntity.getFTaskId(), mPageSize, 1, mPageNo, userInfo.getId());
        getTaskCheckEmtity.loadData(mPageNo, mPageSize, mEntity.getId());

    }

    @Override
    public void onLoadStart(int id) {
        if (id != ApiData.HTTP_ID_searchzt) {
            super.onLoadStart(id);
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_SuperviseTaskCheckEntity:
                srlTodo.setRefreshing(false);
                NormalListEntity myTaskListEntity = (NormalListEntity) b.getEntry();
                mTotalNo = myTaskListEntity.getTotal();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                List<MyTaskCheckEntity> entityList = myTaskListEntity.getTaskCheckEntities();
                dataList.clear();
                dataList.addAll(entityList);
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

}