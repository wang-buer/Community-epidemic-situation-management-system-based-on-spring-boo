package com.example.service;

import cn.hutool.json.JSONUtil;
import com.example.dao.RichtextInfoDao;
import org.springframework.stereotype.Service;
import com.example.entity.RichtextInfo;
import com.example.entity.AuthorityInfo;
import com.example.entity.Account;
import com.example.vo.RichtextInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Value;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RichtextInfoService {

    @Resource
    private RichtextInfoDao richtextInfoDao;

    public RichtextInfo add(RichtextInfo info) {
        richtextInfoDao.insertSelective(info);
        return info;
    }

    public void delete(Long id) {
        richtextInfoDao.deleteByPrimaryKey(id);
    }

    public void update(RichtextInfo info) {
        richtextInfoDao.updateByPrimaryKeySelective(info);
    }

    public RichtextInfo findById(Long id) {
        return richtextInfoDao.selectByPrimaryKey(id);
    }

    public List<RichtextInfoVo> findAll() {
        return richtextInfoDao.findByName("all");
    }

    public PageInfo<RichtextInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<RichtextInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    public List<RichtextInfoVo> findAllPage(HttpServletRequest request, String name) {
		return richtextInfoDao.findByName(name);
    }

}
