package com.zx.zsmarketmobile.ui.taskexcute;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.zsmarketmobile.R;
import com.zx.zsmarketmobile.adapter.TaskInfoAdapter;
import com.zx.zsmarketmobile.entity.EntityDetail;
import com.zx.zsmarketmobile.entity.KeyValueInfo;
import com.zx.zsmarketmobile.ui.base.BaseFragment;
import com.zx.zsmarketmobile.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2018/4/22.
 * 功能：
 */

public class TaskDetailFragment extends BaseFragment {
    private EntityDetail mEntity;
    private RecyclerView rvTask;

    public static TaskDetailFragment newInstance(EntityDetail entity) {
        TaskDetailFragment details = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("entity", entity);
        details.setArguments(args);
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        mEntity = (EntityDetail) getArguments().getSerializable("entity");
        view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        rvTask = view.findViewById(R.id.rv_task_detail);
        rvTask.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<KeyValueInfo> taskList = new ArrayList<>();
        taskList.add(new KeyValueInfo("项目编号", mEntity.getProjCode()));
        taskList.add(new KeyValueInfo("所属板块", mEntity.getProjDept()));
        taskList.add(new KeyValueInfo("项目名称", mEntity.getProjName()));
        taskList.add(new KeyValueInfo("所属大类", mEntity.getProjType()));
        taskList.add(new KeyValueInfo("所属行业", mEntity.getProjIndustry()));
        taskList.add(new KeyValueInfo("类别名称", ""));
        taskList.add(new KeyValueInfo("新兴行业", mEntity.getProjNewIns()));
        taskList.add(new KeyValueInfo("是否外资", mEntity.getIsForeign() == 1 ? "是" : "否"));
        taskList.add(new KeyValueInfo("投资主体（中方）", mEntity.getInvestorEntityChina()));
        taskList.add(new KeyValueInfo("注册资本（中方）", mEntity.getRegMoneyNational() + ""));
        taskList.add(new KeyValueInfo("合同号", mEntity.getContractNum()));
        taskList.add(new KeyValueInfo("合同投资额", mEntity.getContractAmountNational() + ""));
        taskList.add(new KeyValueInfo("到位投资额", mEntity.getRealAmountNational() + ""));
        taskList.add(new KeyValueInfo("预计达产产值", mEntity.getExpectedOutput() + ""));
        taskList.add(new KeyValueInfo("开工日期", DateUtil.getDateFromMillis(mEntity.getRealStartDate())));
        taskList.add(new KeyValueInfo("税收", mEntity.getTaxTotal() + ""));
        taskList.add(new KeyValueInfo("签约日期", DateUtil.getDateFromMillis(mEntity.getSignDate()) + ""));
        taskList.add(new KeyValueInfo("最后修改时间", DateUtil.getDateFromMillis(mEntity.getLastmodifyDate()) + ""));
        taskList.add(new KeyValueInfo("企业统一社会信用代码", mEntity.getUniscid() + ""));
        taskList.add(new KeyValueInfo("是否涉密", mEntity.getIsClassified() == 1 ? "是" : "否"));
        taskList.add(new KeyValueInfo("项目所属部门", mEntity.getProjZsDept() + ""));
        taskList.add(new KeyValueInfo("外资国别", mEntity.getForeignCountry() + ""));
        taskList.add(new KeyValueInfo("现代服务业局行业分类", mEntity.getProjServiceType() + ""));
        taskList.add(new KeyValueInfo("投资协议编号", mEntity.getInvestAgreementNum()));
        taskList.add(new KeyValueInfo("招商会纪要编号", mEntity.getZshRecordNum()));
        taskList.add(new KeyValueInfo("办公会纪要编号", mEntity.getBghRecordNum()));
        taskList.add(new KeyValueInfo("时候阅读", mEntity.getIsRead() == 1 ? "是" : "否"));
        taskList.add(new KeyValueInfo("用地类型", ""));
        taskList.add(new KeyValueInfo("楼宇面积", mEntity.getBuildingArea() + ""));
        taskList.add(new KeyValueInfo("项目地址", mEntity.getProjAddr()));
        taskList.add(new KeyValueInfo("招商负责人姓名", mEntity.getAttractInvestmentPerson()));
        taskList.add(new KeyValueInfo("招商负责人联系方式", mEntity.getAttractInvestmentInfo()));
        taskList.add(new KeyValueInfo("企业联系人姓名", mEntity.getBusinessContactPerson()));
        taskList.add(new KeyValueInfo("企业联系人联系方式", mEntity.getBusinessContactInfo()));
        taskList.add(new KeyValueInfo("下次提醒日期", mEntity.getNextRemindDate() + ""));
        taskList.add(new KeyValueInfo("项目内容简介", mEntity.getProjDescrible()));
        taskList.add(new KeyValueInfo("项目备注", mEntity.getRemark()));
        rvTask.setAdapter(new TaskInfoAdapter(getActivity(), taskList));
        return view;
    }
}
