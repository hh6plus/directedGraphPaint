package test.hh6plus.saving.model;

import java.util.List;

/**
 * Created by huhong02 on 17/7/11.
 */
public class SavingModel extends BaseModel {

    private List<String> directingModelAppkeyList;

    public List<String> getDirectingModelAppkeyList() {
        return directingModelAppkeyList;
    }

    public void setDirectingModelAppkeyList(List<String> directingModelAppkeyList) {
        this.directingModelAppkeyList = directingModelAppkeyList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SavingModel)) return false;
        if (!super.equals(o)) return false;

        SavingModel that = (SavingModel) o;

        return !(directingModelAppkeyList != null ? !directingModelAppkeyList.equals(that.directingModelAppkeyList) : that.directingModelAppkeyList != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (directingModelAppkeyList != null ? directingModelAppkeyList.hashCode() : 0);
        return result;
    }
}
