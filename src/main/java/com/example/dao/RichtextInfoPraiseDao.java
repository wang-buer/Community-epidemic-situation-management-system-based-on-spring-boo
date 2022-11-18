package com.example.dao;

import com.example.entity.RichtextInfoPraise;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


@Repository
public interface RichtextInfoPraiseDao extends Mapper<RichtextInfoPraise> {
    @Select("select count(*) from praise_richtext_info where foreignId = #{id}")
    Integer count(Long id);

    @Select("select * from praise_richtext_info where userId = #{userId} and level = #{level} and foreignId = #{foreignId}")
    RichtextInfoPraise getInfoByUserIdAndLevelAndId(@Param("userId") Long userId,
                                                @Param("level") Integer level,
                                                @Param("foreignId") Long foreignId);
}
