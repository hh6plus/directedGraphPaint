package test.hh6plus.saving.service;

import test.hh6plus.saving.model.SavingModel;

import java.util.List;
import java.util.Map;

public interface IStorageService {
    boolean uploadSavingModel(SavingModel savingModel);

    int batchUploadSavingModel(List<SavingModel> savingModelList);

    Map<String, SavingModel> batchQuerySavingModelByAppkey(List<String> appkey);

    SavingModel querySavingModelByAppkey(String appkey);
}