package test.hh6plus.painting.model;

import test.hh6plus.saving.model.BaseModel;

import java.util.Set;

/**
 * Created by huhong02 on 17/7/12.
 */
public class PaintModel extends BaseModel {
    private StateEnum state;
    private Set<PaintModel> paintModelSet;

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public Set<PaintModel> getPaintModelSet() {
        return paintModelSet;
    }

    public void setPaintModelSet(Set<PaintModel> paintModelSet) {
        this.paintModelSet = paintModelSet;
    }

}
