package test.hh6plus.saving.model;

import java.util.List;

/**
 * Created by huhong02 on 17/7/12.
 */
public class BaseModel {

    private String appkey;
    private List<Steward> stewardList;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public List<Steward> getStewardList() {
        return stewardList;
    }

    public void setStewardList(List<Steward> stewardList) {
        this.stewardList = stewardList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseModel)) return false;

        BaseModel baseModel = (BaseModel) o;

        if (appkey != null ? !appkey.equals(baseModel.appkey) : baseModel.appkey != null) return false;
        return !(stewardList != null ? !stewardList.equals(baseModel.stewardList) : baseModel.stewardList != null);

    }

    @Override
    public int hashCode() {
        int result = appkey != null ? appkey.hashCode() : 0;
        result = 31 * result + (stewardList != null ? stewardList.hashCode() : 0);
        return result;
    }
}
