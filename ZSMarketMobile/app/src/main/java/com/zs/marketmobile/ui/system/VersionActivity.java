package com.zs.marketmobile.ui.system;

import android.os.Bundle;
import android.widget.TextView;

import com.zs.marketmobile.R;
import com.zs.marketmobile.ui.base.BaseActivity;
import com.zs.marketmobile.entity.VersionInfo;

/**
 *
 * Create By Xiangb On 2016/9/19
 * 功能：版本说明界面
 *
 */

public class VersionActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_version);
		addToolBar(true);
		hideRightImg();
		setMidText("版本说明");
		TextView tvCode = (TextView) findViewById(R.id.tvActVersion_code);
		TextView tvRemark = (TextView) findViewById(R.id.tvActVersion_remark);
		VersionInfo info = (VersionInfo) getIntent().getSerializableExtra("version");
		tvCode.setText("版本号：" + info.versionCode);
		tvRemark.setText(info.versionRemark);
	}
	
}
