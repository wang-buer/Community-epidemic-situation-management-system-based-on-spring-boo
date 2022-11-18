package com.example.dao;

import com.example.entity.BusinessInfo;
import com.example.vo.BusinessInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface BusinessInfoDao extends Mapper<BusinessInfo> {
    List<BusinessInfoVo> findByName(@Param("name") String name);
    
    int checkRepeat(@Param("column") String column, @Param("value") String value, @Param("id") Long id);
    BusinessInfoVo findByUsername(String username);
    Integer count();
}
