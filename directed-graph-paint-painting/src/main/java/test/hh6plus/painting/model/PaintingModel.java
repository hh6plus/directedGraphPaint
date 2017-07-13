package test.hh6plus.painting.model;

import test.hh6plus.saving.model.BaseModel;

import java.util.Set;

/**
 * Created by huhong02 on 17/7/12.
 */
public class PaintingModel extends BaseModel {
    private StateEnum state;
    private Set<PaintingModel> paintingModelSet;

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public Set<PaintingModel> getPaintingModelSet() {
        return paintingModelSet;
    }

    public void setPaintingModelSet(Set<PaintingModel> paintingModelSet) {
        this.paintingModelSet = paintingModelSet;
    }

}
