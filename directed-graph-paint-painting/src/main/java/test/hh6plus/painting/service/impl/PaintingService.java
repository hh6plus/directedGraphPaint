package test.hh6plus.painting.service.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.hh6plus.painting.enums.GraphTypeEnum;
import test.hh6plus.painting.enums.RepresentTypeEnum;
import test.hh6plus.painting.helper.GraphViz;
import test.hh6plus.painting.model.PaintModel;
import test.hh6plus.painting.model.StateEnum;
import test.hh6plus.painting.service.IPaintingService;
import test.hh6plus.saving.model.SavingModel;
import test.hh6plus.saving.model.Steward;
import test.hh6plus.saving.service.IStorageService;

import java.io.File;
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
     *
     * @param appkey
     * @param resultMap
     * @return
     */
    public PaintModel getModelTobePainted(String appkey, Map<String, PaintModel> resultMap) {
        if (resultMap.containsKey(appkey)) {
            return resultMap.get(appkey);
        }
        PaintModel paintModel = new PaintModel();
        paintModel.setAppkey(appkey);
        resultMap.put(appkey, paintModel);

        SavingModel savingModel = storageService.querySavingModelByAppkey(appkey);

        if (savingModel == null) {
            paintModel.setState(StateEnum.NOT_UPDATED);
            return paintModel;
        }
        filterPaintingModelBySavingModel(savingModel, paintModel);

        List<String> subAppkeyList = savingModel.getDirectingModelAppkeyList();
        if (CollectionUtils.isEmpty(subAppkeyList)) {
            paintModel.setState(StateEnum.UPDATED_NO_SON);
            return paintModel;
        }

        Set<PaintModel> subPaintModelSet = Sets.newHashSet();
        for (String subAppkey : subAppkeyList) {
            subPaintModelSet.add(this.getModelTobePainted(subAppkey, resultMap));
        }
        paintModel.setState(StateEnum.UPDATED);
        paintModel.setPaintModelSet(subPaintModelSet);
        return paintModel;

    }

    private PaintModel filterPaintingModelBySavingModel(SavingModel savingModel, PaintModel paintModel) {
        if (savingModel == null) {
            return null;
        }
        paintModel.setStewardList(savingModel.getStewardList());
        return paintModel;
    }

    public File getPic(String appkey) {
        Map<String, PaintModel> appkeyToModelMap = Maps.newHashMap();
        this.getModelTobePainted(appkey, appkeyToModelMap);
        File result = printDirectedGraph(appkey, appkeyToModelMap);
        return result;
    }

    private File printDirectedGraph(String picName, Map<String, PaintModel> appkeyToModelMap) {
        GraphViz gv = getGraphVizByPaintMap(appkeyToModelMap);
        System.out.println(gv.getDotSource());
        String type = GraphTypeEnum.PNG.getType();
        File out = new File("/tmp/out_" + picName + "." + type);   // Linux
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, RepresentTypeEnum.DOT.getType()), out);
        return out;
    }

    private GraphViz getGraphVizByPaintMap(Map<String, PaintModel> appkeyToModelMap) {
        GraphViz gv = new GraphViz();
        gv.increaseDpi();   // 106 dpi

        gv.addln(gv.start_graph());
        gv.addln("graph [rankdir = \"LR\"];");
        gv.addln("node [fontsize = \"16\" shape = \"ellipse\"]; \nedge [];");
/*
        gv.addln("node1[label = \"Mr.a | aa | appA\"]\n");
        gv.addln("node2[label = \"{Mr.b, Mr.c} | {bb, cc} | appB\"]\n");
        gv.addln("node3[label = \"Mr.d | dd | appC\"]\n");
        gv.addln("node1 -> node2");
        gv.addln("node1 -> node3");
*/
        //节点信息
        for (Map.Entry<String, PaintModel> entry : appkeyToModelMap.entrySet()) {
            String appkey = entry.getKey();
            PaintModel paintModel = entry.getValue();
            gv.add("\"" + appkey + "\"" + "[label = \"");
            gv.add("<f0> " + appkey + "|");
            gv.add("<f1> " + getStewardListString(paintModel.getStewardList()));
            gv.add("\" shape = \"record\" ");
            if (StateEnum.NOT_UPDATED.equals(paintModel.getState())) {
                gv.add("color = lemonchiffon2");
            } else {
                gv.add("color = greenyellow");
            }
            gv.addln("];");
        }

        //链接信息
        for (Map.Entry<String, PaintModel> entry : appkeyToModelMap.entrySet()) {

            String appkey = entry.getKey();
            PaintModel paintModel = entry.getValue();
            Set<PaintModel> subModels = paintModel.getPaintModelSet();
            if (CollectionUtils.isEmpty(subModels)) {
                continue;
            }
            for (PaintModel subModel : subModels) {
                gv.addln("\"" + appkey + "\"" + ":f0 ->" + "\"" + subModel.getAppkey() + "\"" + ":f0;");
            }
        }

        gv.addln(gv.end_graph());
        return gv;
    }
    private String getStewardListString(List<Steward> list) {
        StringBuilder result = new StringBuilder();
//        result.append("{");
        if (CollectionUtils.isNotEmpty(list)) {
            for (Steward steward : list) {
                result.append("name:").append(steward.getName()).append(",misId:").append(steward.getMisId()).append("\\;");
            }
        }
//        result.append("}");
        return result.toString();
    }
}
