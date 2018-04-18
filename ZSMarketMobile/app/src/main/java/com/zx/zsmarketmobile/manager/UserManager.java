package com.zx.zsmarketmobile.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.zx.zsmarketmobile.entity.HttpLoginEntity;
import com.zx.zsmarketmobile.http.HttpConstant;

import java.util.Arrays;


/**
 * 用户管理
 *
 * @author zx-wt
 */
public class UserManager {
    private HttpLoginEntity user;

    public UserManager() {
        user = new HttpLoginEntity();
    }

    public HttpLoginEntity getUser(Context context) {
        init(context);
        return user;
    }

    /***
     *
     * @return 是否登录成功过 true 登录过
     */
    public boolean isLogin() {
        if (user == null)
            return false;
        return user.isLogin();
    }

    public void setNoLogin(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean("isLogin", false).commit();
        user.setIsLogin(false);
    }

    public void init(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        HttpConstant.AppCode = sp.getString("loginCode", "first");
        user = new HttpLoginEntity();
        user.setUserId(sp.getString("id", null));
        user.setGender(sp.getInt("gender", 0));
        user.setAge(sp.getInt("age", 0));
        user.setUserName(sp.getString("username", null));
        user.setDepartment(sp.getString("department", null));
        user.setTelephone(sp.getString("telephone", null));
        user.setPassword(sp.getString("password", null));
        user.setIsLogin(sp.getBoolean("isLogin", false));
        user.setRole(Arrays.asList(sp.getString("role", "").split(",")));
        user.setOfficeTel(sp.getString("officeTel", null));
        user.setRegion(sp.getString("region", null));
        user.setRegionCode(sp.getString("regionCode", null));
        user.setStatus(sp.getInt("status", 0));
        user.setUserHigher(sp.getString("userHigher", null));
        user.setToken(sp.getString("token", null));
    }

    public void setUser(Context context, HttpLoginEntity userinfo) {
        user = userinfo;
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("id", user.getUserId());
        editor.putInt("gender", user.getGender());
        editor.putInt("age", user.getAge());
        editor.putString("username", user.getUserName());
        editor.putString("department", user.getDepartment());
        editor.putString("telephone", user.getTelephone());
        editor.putString("password", user.getPassword());
        editor.putBoolean("isLogin", user.isLogin());
        editor.putString("role", user.getRoleString());

        editor.putString("officeTel", user.getOfficeTel());
        editor.putString("region", user.getRegion());
        editor.putString("regionCode", user.getRegionCode());
        editor.putInt("status", user.getStatus());
        editor.putString("userHigher", user.getUserHigher());
        editor.putString("token", user.getToken());


        editor.commit();
    }

}
