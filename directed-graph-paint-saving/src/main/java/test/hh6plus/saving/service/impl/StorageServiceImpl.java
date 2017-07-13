package test.hh6plus.saving.service.impl;

import com.google.common.collect.Maps;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import test.hh6plus.saving.model.SavingModel;
import test.hh6plus.saving.service.IStorageService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StorageServiceImpl implements IStorageService {

    @Resource(name = "ehcacheManagerFactory")
    private CacheManager ehcacheManager;

    private Cache cache;

    @PostConstruct
    public void initCache() {
        cache = ehcacheManager.getCache("savingModelCache");
    }

    public boolean uploadSavingModel(SavingModel savingModel) {
        if (savingModel == null) {
            return false;
        }
        String key = savingModel.getAppkey();
        if (key == null) {
            return false;
        }
        Element element = new Element(key, savingModel);
        cache.put(element);
        return true;
    }

    public int batchUploadSavingModel(List<SavingModel> savingModelList) {
        if (CollectionUtils.isEmpty(savingModelList)) {
            return 0;
        }
        int sucCount = 0;
        for (SavingModel savingModel : savingModelList) {
            if (uploadSavingModel(savingModel)) {
                sucCount++;
            }
        }
        return sucCount;
    }

    public Map<String, SavingModel> batchQuerySavingModelByAppkey(List<String> appkeyList) {
        if (CollectionUtils.isEmpty(appkeyList)) {
            return new HashMap<String, SavingModel>();
        }
        Map<String, SavingModel> result = Maps.newHashMap();
        for (String appkey : appkeyList) {
            result.put(appkey, querySavingModelByAppkey(appkey));
        }
        return result;
    }

    public SavingModel querySavingModelByAppkey(String appkey) {
        if (appkey == null) {
            return null;
        }
        Element element = cache.get(appkey);
        if (element == null) {
            return null;
        }
        return (SavingModel) element.getObjectValue();
    }
}