package com.zx.zsmarketmobile.ui.taskexcute;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.zx.zsmarketmobile.R;
import com.zx.zsmarketmobile.adapter.TaskFlowAdapter;
import com.zx.zsmarketmobile.entity.TaskLogInfoBean;
import com.zx.zsmarketmobile.http.ApiData;
import com.zx.zsmarketmobile.http.BaseHttpResult;
import com.zx.zsmarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Stanny On 2017/3/14
 * 功能：案件轨迹Fragment
 */
public class TaskFlowFragment extends BaseFragment {

    private TaskFlowAdapter mCaseAdapter;
    private RecyclerView mRvInfo;
    private String fId = "";
    private String fType = "常规流程";
    private RadioGroup rgCaseFlow;
    private ApiData getLcgj = new ApiData(ApiData.HTTP_ID_taskLogInfo);
    private List<TaskLogInfoBean> dataInfoList = new ArrayList<>();

    public static TaskFlowFragment newInstance(String fId) {
        TaskFlowFragment details = new TaskFlowFragment();
        details.fId = fId;
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_flow, container, false);
        rgCaseFlow = (RadioGroup) view.findViewById(R.id.rg_case_flow);
        mRvInfo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        getLcgj.setLoadingListener(this);
        mRvInfo.setLayoutManager(mLinearLayoutManager);
        mCaseAdapter = new TaskFlowAdapter(getActivity(), dataInfoList);
        mRvInfo.setAdapter(mCaseAdapter);
        getLcgj.loadData(fId);
        return view;
    }

//    //当界面可见之后再进行数据加载（懒加载）
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser && dataInfoList.size() == 0) {
//            getLcgj.loadData(fId, fType);
//        }
//    }

    @Override
    @SuppressWarnings("unchecked")
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_taskLogInfo:
                dataInfoList.clear();
                List<TaskLogInfoBean> logInfoBeans = (List<TaskLogInfoBean>) b.getEntry();
                dataInfoList.addAll(logInfoBeans);
                mCaseAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

}
