package test.hh6plus.test;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.hh6plus.painting.service.IPaintingService;
import test.hh6plus.saving.model.SavingModel;
import test.hh6plus.saving.model.Steward;
import test.hh6plus.saving.service.IStorageService;

import java.util.Map;


public class CacheTest extends FullContextTest {
    @Autowired
    private IStorageService storageService;

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


}