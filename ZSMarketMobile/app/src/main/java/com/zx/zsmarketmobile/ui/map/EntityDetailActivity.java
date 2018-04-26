package com.zx.zsmarketmobile.ui.map;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zx.zsmarketmobile.R;
import com.zx.zsmarketmobile.R.id;
import com.zx.zsmarketmobile.entity.EntityDetail;
import com.zx.zsmarketmobile.entity.HttpZtEntity;
import com.zx.zsmarketmobile.http.ApiData;
import com.zx.zsmarketmobile.http.BaseHttpResult;
import com.zx.zsmarketmobile.ui.base.BaseActivity;
import com.zx.zsmarketmobile.ui.taskexcute.TaskDetailFragment;
import com.zx.zsmarketmobile.ui.taskexcute.TaskFlowFragment;
import com.zx.zsmarketmobile.util.ConstStrings;
import com.zx.zsmarketmobile.util.Util;
import com.zx.zsmarketmobile.util.ZXDialogUtil;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：主体详情
 */
public class EntityDetailActivity extends BaseActivity implements OnClickListener {


    private ApiData taskOpt = new ApiData(ApiData.HTTP_ID_taskOpt);
    private ViewPager mVpContent;
    private LinearLayout llClaimedLayout;
    private Button btnYes, btnNo;
    private TabLayout tb_entity;
    private EntityDetail mEntityDetail;
    private Location location = null;
    private LocationManager locationManager;
    private HttpZtEntity entity;
    private EntityImageFragment entityImageFragment;
    private String fEntityType, fEntityGuid;
    private int type = 0;//0纠正  1认领  2其他

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entitydetail);

        addToolBar(true);
        getRightImg().setOnClickListener(this);
        setMidText("主体详情");
        taskOpt.setLoadingListener(this);
        llClaimedLayout = (LinearLayout) findViewById(R.id.ll_claimedLayout);
        btnYes = (Button) findViewById(id.btn_excute_no);
        btnNo = (Button) findViewById(id.btn_excute_yes);
        tb_entity = (TabLayout) findViewById(id.tb_normal_layout);
        mVpContent = (ViewPager) findViewById(id.vp_normal_pager);
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        type = getIntent().getIntExtra("type", 0);
        entity = (HttpZtEntity) getIntent().getSerializableExtra("ztEntity");
        if (type == 0) {//查询
            llClaimedLayout.setVisibility(View.GONE);
        } else if (type == 1) {//审核
            llClaimedLayout.setVisibility(View.VISIBLE);
            btnNo.setOnClickListener(this);
        } else {
            llClaimedLayout.setVisibility(View.GONE);
        }
        mEntityDetail = (EntityDetail) getIntent().getSerializableExtra("entity");
        fEntityType = getIntent().getStringExtra("fEntityType");
        fEntityGuid = getIntent().getStringExtra("fEntityGuid");
        myPagerAdapter.addFragment(TaskDetailFragment.newInstance(mEntityDetail), "项目信息");
        myPagerAdapter.addFragment(TaskFlowFragment.newInstance(mEntityDetail.getProjGuid()), "报送轨迹");
//        myPagerAdapter.addFragment(GradeFragment.newInstance(1, mEntityDetail.getGrade()), "等级信息");
        mVpContent.setAdapter(myPagerAdapter);
        mVpContent.setOffscreenPageLimit(2);
        tb_entity.setupWithViewPager(mVpContent);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_taskOpt:
                showToast("操作成功");
                llClaimedLayout.setVisibility(View.GONE);
                Util.dialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.toolbar_right:
//                HttpZtEntity Ztentity = new HttpZtEntity();
//                Ztentity.setGuid(key.fEntityGuid);
//                Ztentity.setAddress(key.fAddress);
//                Ztentity.setEntityName(key.fEntityName);
//                Ztentity.setCreditLevel(key.fCreditLevel);
//                Ztentity.setLatitude(locationEntity.getfLatitude());
//                Ztentity.setLongitude(locationEntity.getfLongitude());
//                Ztentity.setContactInfo(key.fContactInfo);
//                Ztentity.setWkid(4490);
//                if (TextUtils.isEmpty(Ztentity.getLatitude()) ||
//                        TextUtils.isEmpty(Ztentity.getLongitude())) {
//                    Ztentity.setLongitude(entity.getLongitude());
//                    Ztentity.setLatitude(entity.getLatitude());
//                }
                if ((entity.getLatitude() == 0 || entity.getLongitude() == 0)
                        || (TextUtils.isEmpty(entity.getProjName()) && TextUtils.isEmpty(entity.getProjAddr()))) {
                    showToast("该主体暂无位置信息");
                    return;
                }
                Intent intent = new Intent(EntityDetailActivity.this, WorkInMapShowActivity.class);
                intent.putExtra("entity", entity);
                intent.putExtra("type", ConstStrings.MapType_ZtDetail);
                startActivity(intent);
                break;
            case id.btn_excute_yes://位置纠正
                ZXDialogUtil.showYesNoDialog(this, "提示", "是否执行审核通过操作?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskOpt.loadData(mEntityDetail.getProjGuid(), "审核通过");
                    }
                });
                break;
            case id.btn_excute_no://主体认领
                ZXDialogUtil.showYesNoDialog(this, "提示", "是否执行审核退回操作?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskOpt.loadData(mEntityDetail.getProjGuid(), "审核退回");
                    }
                });
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        entityImageFragment.mprvPhoto.onActivityResult(requestCode, resultCode, data);
    }


    //重写返回键监听返回事件，判断是否为查看详情状态
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

}
