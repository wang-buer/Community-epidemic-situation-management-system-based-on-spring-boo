package com.example.dao;

import com.example.entity.SubmitInfo;
import com.example.vo.SubmitInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SubmitInfoDao extends Mapper<SubmitInfo> {
    List<SubmitInfoVo> findByUserIdAndLevel(@Param("name") String name,
                                           @Param("level") Integer level,
                                           @Param("status") Integer status);
    List<SubmitInfoVo> findAll();
}
