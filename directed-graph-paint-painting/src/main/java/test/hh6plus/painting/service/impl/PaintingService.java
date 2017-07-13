package test.hh6plus.painting.service.impl;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import test.hh6plus.painting.model.PaintingModel;
import test.hh6plus.painting.model.StateEnum;
import test.hh6plus.painting.service.IPaintingService;
import test.hh6plus.saving.model.SavingModel;
import test.hh6plus.saving.service.IStorageService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by huhong02 on 17/7/12.
 */
@Service
public class PaintingService implements IPaintingService {

    @Autowired
    private IStorageService storageService;

    /**
     * 获取输入的appkey对应的PaintingModel，其中的list将覆盖可达的全部appkey/遍历获得有向图
     * 1. 从此appkey获取对应SM，并获取它指向的全部appkey。
     * 2. 将此appkey生成PM，指向下游appkey递归执行得到的PM。
     * 3. 如果此appkey生成过PM，不再生成，get过来放入指向即可。
     * @param appkey
     * @param resultMap
     * @return
     */
    public PaintingModel getModelTobePainted(String appkey, Map<String, PaintingModel> resultMap) {
        //todo::待测
        if (resultMap.containsKey(appkey)) {
            return resultMap.get(appkey);
        }
        PaintingModel paintingModel = new PaintingModel();
        paintingModel.setAppkey(appkey);
        resultMap.put(appkey, paintingModel);

        SavingModel savingModel = storageService.querySavingModelByAppkey(appkey);

        if (savingModel == null) {
            paintingModel.setState(StateEnum.NOT_UPDATED);
            return paintingModel;
        }
        filterPaintingModelBySavingModel(savingModel, paintingModel);

        List<String> subAppkeyList = savingModel.getDirectingModelAppkeyList();
        if (CollectionUtils.isEmpty(subAppkeyList)) {
            paintingModel.setState(StateEnum.UPDATED_NO_SON);
            return paintingModel;
        }

        Set<PaintingModel> subPaintingModelSet = Sets.newHashSet();
        for (String subAppkey : subAppkeyList) {
            subPaintingModelSet.add(this.getModelTobePainted(subAppkey, resultMap));
        }
        paintingModel.setState(StateEnum.UPDATED);
        paintingModel.setPaintingModelSet(subPaintingModelSet);
        return paintingModel;

    }

    private PaintingModel filterPaintingModelBySavingModel(SavingModel savingModel, PaintingModel paintingModel) {
        if (savingModel == null) {
            return null;
        }
        paintingModel.setStewardList(savingModel.getStewardList());
        return paintingModel;
    }

    public void getPic(String appkey) {
        throw new IllegalArgumentException();
    }
}
