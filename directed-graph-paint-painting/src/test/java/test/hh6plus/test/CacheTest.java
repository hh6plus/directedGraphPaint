package test.hh6plus.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.hh6plus.painting.model.PaintingModel;
import test.hh6plus.painting.service.IPaintingService;
import test.hh6plus.saving.model.SavingModel;
import test.hh6plus.saving.model.Steward;
import test.hh6plus.saving.service.IStorageService;

import java.util.List;
import java.util.Map;


public class CacheTest extends FullContextTest {
    @Autowired
    private IStorageService storageService;

    @Autowired
    private IPaintingService paintingService;

    String appkeyA = "a", appkeyB = "b", appkeyC = "c", appkeyD = "d", appkeyE = "e";
    Steward stewardA = new Steward("Mr.a", "aa");
    Steward stewardBC = new Steward("Mr.bc", "bc");
    Steward stewardCD = new Steward("Mr.cd", "cd");

    @Test
    public void testSaveAndPut() {
        String appkey = "a";
        Steward steward = new Steward("Mr.a", "aa13");
        SavingModel savingModel = new SavingModel();
        savingModel.setAppkey(appkey);
        savingModel.setStewardList(Lists.newArrayList(steward));

        storageService.uploadSavingModel(savingModel);

        Map<String, SavingModel> result = storageService.batchQuerySavingModelByAppkey(Lists.newArrayList(appkey));

        Assert.assertTrue(result.containsKey(appkey));
        Assert.assertTrue(savingModel.equals(result.get(appkey)));
    }

    /**
     * a->b,b->e(未上报)
     * a->c,c->d,d->a(环)
     */
    @Test
    public void getModelTobePaintedTest() {
        SavingModel savingModelA = getSavingModel(appkeyA, Lists.newArrayList(stewardA), appkeyB, appkeyC);
        SavingModel savingModelB = getSavingModel(appkeyB, Lists.newArrayList(stewardBC), appkeyE);
        SavingModel savingModelC = getSavingModel(appkeyC, Lists.newArrayList(stewardBC, stewardCD), appkeyD);
        SavingModel savingModelD = getSavingModel(appkeyD, Lists.newArrayList(stewardCD), appkeyA);
        storageService.batchUploadSavingModel(Lists.newArrayList(savingModelA, savingModelB, savingModelC, savingModelD));
        Map<String, PaintingModel> result = Maps.newHashMap();
        paintingService.getModelTobePainted(appkeyA, result);
        Assert.assertTrue(result.containsKey(appkeyA));
        Assert.assertTrue(result.containsKey(appkeyB));
        Assert.assertTrue(result.containsKey(appkeyC));
        Assert.assertTrue(result.containsKey(appkeyD));
        Assert.assertTrue(result.containsKey(appkeyE));
    }

    private SavingModel getSavingModel(String appkey, List<Steward> stewards, String... subAppkeys) {
        SavingModel savingModel = new SavingModel();
        savingModel.setAppkey(appkey);
        savingModel.setStewardList(stewards);
        savingModel.setDirectingModelAppkeyList(Lists.newArrayList(subAppkeys));
        return savingModel;
    }

}