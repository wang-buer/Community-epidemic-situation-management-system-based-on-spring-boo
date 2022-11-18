package com.example.dao;

import com.example.entity.RichtextInfoComment;
import com.example.vo.RichtextInfoCommentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RichtextInfoCommentDao extends Mapper<RichtextInfoComment> {
    List<RichtextInfoCommentVo> findAllVo(@Param("name") String name);
    List<RichtextInfoCommentVo> findByForeignId (@Param("id") Long id, @Param("parentId") Long parentId);
}
