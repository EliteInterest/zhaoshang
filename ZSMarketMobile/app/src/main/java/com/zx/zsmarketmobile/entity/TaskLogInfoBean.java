package com.zx.zsmarketmobile.entity;

import java.util.List;

/**
 * Created by Xiangb on 2018/4/23.
 * 功能：
 */

public class TaskLogInfoBean {

    /**
     * optUserName : lx02
     * optRemark : null
     * optGuid : 7919D5ED925248898217FC4FE30A05EC
     * projGuid : AFC43BFF2C1C4132B716B719D14F2252
     * optDate : 1500601162000
     * optUser : A19C826D302E44C292615B308A474C70
     * optType : 提交删除
     * detailLogs : []
     */

    private String optUserName;
    private String optRemark;
    private String optGuid;
    private String projGuid;
    private long optDate;
    private String optUser;
    private String optType;
    private List<?> detailLogs;

    public String getOptUserName() {
        return optUserName;
    }

    public void setOptUserName(String optUserName) {
        this.optUserName = optUserName;
    }

    public String getOptRemark() {
        return optRemark;
    }

    public void setOptRemark(String optRemark) {
        this.optRemark = optRemark;
    }

    public String getOptGuid() {
        return optGuid;
    }

    public void setOptGuid(String optGuid) {
        this.optGuid = optGuid;
    }

    public String getProjGuid() {
        return projGuid;
    }

    public void setProjGuid(String projGuid) {
        this.projGuid = projGuid;
    }

    public long getOptDate() {
        return optDate;
    }

    public void setOptDate(long optDate) {
        this.optDate = optDate;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public List<?> getDetailLogs() {
        return detailLogs;
    }

    public void setDetailLogs(List<?> detailLogs) {
        this.detailLogs = detailLogs;
    }
}
