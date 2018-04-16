package com.zs.marketmobile.ui.supervise.mytask;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zs.marketmobile.R;
import com.zs.marketmobile.adapter.supervise.SuperviseDetialFlowAdapter;
import com.zs.marketmobile.entity.supervise.MyTaskFlow;
import com.zs.marketmobile.entity.supervise.MyTaskListEntity;
import com.zs.marketmobile.http.ApiData;
import com.zs.marketmobile.http.BaseHttpResult;
import com.zs.marketmobile.manager.UserManager;
import com.zs.marketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/23.
 */

public class SuperviseMyTaskFlowFragment extends BaseFragment {

    private SuperviseDetialFlowAdapter superviseDetialFlowAdapter;
    private RecyclerView mRvInfo;
    private String fId = "";
    private ApiData getLcgj = new ApiData(ApiData.HTTP_ID_superviseGetAyLcgjPageInfo);
    private List<MyTaskFlow.DataBean> dataInfoList = new ArrayList<>();
    private MyTaskListEntity mEntity;

    public static SuperviseMyTaskFlowFragment newInstance(Context context, MyTaskListEntity rowsBean) {
        SuperviseMyTaskFlowFragment details = new SuperviseMyTaskFlowFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mEntity", rowsBean);
        details.mEntity = rowsBean;
        UserManager userManager = new UserManager();
        details.userInfo = userManager.getUser(context);
        details.setArguments(bundle);
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supervise_flow, container, false);
        mEntity = (MyTaskListEntity) getArguments().getSerializable("mEntity");

        mRvInfo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        getLcgj.setLoadingListener(this);
        mRvInfo.setLayoutManager(mLinearLayoutManager);
        fId = mEntity.getId();
        superviseDetialFlowAdapter = new SuperviseDetialFlowAdapter(getActivity(), dataInfoList);
        mRvInfo.setAdapter(superviseDetialFlowAdapter);
        getLcgj.loadData(fId, userInfo.getId());

        return view;
    }

    //当界面可见之后再进行数据加载（懒加载）
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && dataInfoList.size() == 0) {
            getLcgj.loadData(fId, userInfo.getId());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_superviseGetAyLcgjPageInfo:
                dataInfoList.clear();
                List<MyTaskFlow.DataBean> list = (List<MyTaskFlow.DataBean>) b.getEntry();
                dataInfoList.addAll(list);
                superviseDetialFlowAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

}
