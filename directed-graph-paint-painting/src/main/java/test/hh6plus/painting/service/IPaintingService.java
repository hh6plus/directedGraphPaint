package test.hh6plus.painting.service;

import test.hh6plus.painting.model.PaintingModel;

import java.util.Map;

/**
 * Created by huhong02 on 17/7/12.
 */
public interface IPaintingService {

    PaintingModel getModelTobePainted(String appkey, Map<String, PaintingModel> resultMap);

    void getPic(String appkey);
}
