package com.vipshop.microscope.query.service;

import com.vipshop.microscope.storage.StorageRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final StorageRepository storageRepository = StorageRepository.getStorageRepository();

}