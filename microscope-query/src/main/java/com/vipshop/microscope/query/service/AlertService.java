package com.vipshop.microscope.query.service;

import com.vipshop.microscope.storage.StorageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AlertService {

    private final StorageRepository storageRepository = StorageRepository.getStorageRepository();

    public List<Map<String, Object>> getQueryCondition() {
        return storageRepository.findExceptionIndex();
    }

}
