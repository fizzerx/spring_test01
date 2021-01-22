package com.example.ims_20200716.service.impl;

import com.example.ims_20200716.dao.ImsCfgLableClassdao;
import com.example.ims_20200716.pojo.ImsCfgLableClass;
import com.example.ims_20200716.service.ImsCfgLableClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ImsCfgLableClassServiceImpl implements ImsCfgLableClassService {

    @Autowired
    ImsCfgLableClassdao imsCfgLableClassdao;

    @Override
    public ImsCfgLableClass getImsCfgLableClass() {
        return imsCfgLableClassdao.getImsCfgLableClass();
    }
}
