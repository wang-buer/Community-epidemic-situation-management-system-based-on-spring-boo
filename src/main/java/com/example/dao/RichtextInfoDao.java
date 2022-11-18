package com.example.dao;

import com.example.entity.RichtextInfo;
import com.example.vo.RichtextInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RichtextInfoDao extends Mapper<RichtextInfo> {
    List<RichtextInfoVo> findByName(@Param("name") String name);
    
    
    
}
