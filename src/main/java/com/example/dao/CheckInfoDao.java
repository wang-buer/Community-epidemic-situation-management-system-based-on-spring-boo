package com.example.dao;

import com.example.entity.CheckInfo;
import com.example.vo.CheckInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CheckInfoDao extends Mapper<CheckInfo> {
    List<CheckInfoVo> findByName(@Param("name") String name,
                                 @Param("temperature") String temperature,
                                 @Param("location") String location);
    
    
    
    Integer count();
}
