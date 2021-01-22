package com.example.ims_20200716.dao;

import com.example.ims_20200716.pojo.ImsCfgLableClass;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ImsCfgLableClassdao {
    @Select("select * from ims_cfg_lable_info")
    public ImsCfgLableClass getImsCfgLableClass();
}
