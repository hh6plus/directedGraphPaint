package test.hh6plus.painting.service;

import test.hh6plus.painting.model.PaintModel;

import java.io.File;
import java.util.Map;

/**
 * Created by huhong02 on 17/7/12.
 */
public interface IPaintingService {

    PaintModel getModelTobePainted(String appkey, Map<String, PaintModel> resultMap);

    File getPic(String appkey);
}
