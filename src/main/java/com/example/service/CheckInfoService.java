package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.example.dao.CheckInfoDao;
import org.springframework.stereotype.Service;
import com.example.entity.CheckInfo;
import com.example.entity.AuthorityInfo;
import com.example.entity.Account;
import com.example.vo.CheckInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class CheckInfoService {

    @Resource
    private CheckInfoDao checkInfoDao;

    public CheckInfo add(CheckInfo checkInfo) {
        checkInfoDao.insertSelective(checkInfo);
        return checkInfo;
    }

    public void delete(Long id) {
        checkInfoDao.deleteByPrimaryKey(id);
    }

    public void update(CheckInfo checkInfo) {
        checkInfoDao.updateByPrimaryKeySelective(checkInfo);
    }

    public CheckInfo findById(Long id) {
        return checkInfoDao.selectByPrimaryKey(id);
    }

    public List<CheckInfoVo> findAll() {
        return checkInfoDao.findByName("all", "all", "all");
    }

    public PageInfo<CheckInfoVo> findPage(String name, String temperature, String location,
                                          Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<CheckInfoVo> all = findAllPage(request, name, temperature, location);
        return PageInfo.of(all);
    }

    public List<CheckInfoVo> findAllPage(HttpServletRequest request, String name, String temperature, String location) {
		return checkInfoDao.findByName(name, temperature, location);
    }

    public boolean checkIsHigh() {
        Example example = new Example(CheckInfo.class);
        example.createCriteria().andGreaterThanOrEqualTo("temperature", "37.5");
        List<CheckInfo> list = checkInfoDao.selectByExample(example);
        if (!CollectionUtil.isEmpty(list)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIsLocation() {
        Example example = new Example(CheckInfo.class);
        example.createCriteria().andNotEqualTo("location", "九江");
        List<CheckInfo> list = checkInfoDao.selectByExample(example);
        if (!CollectionUtil.isEmpty(list)) {
            return true;
        } else {
            return false;
        }
    }

}
