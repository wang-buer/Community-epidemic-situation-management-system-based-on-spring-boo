package com.example.service;

import com.example.dao.SubmitInfoDao;
import com.example.entity.Account;
import com.example.entity.SubmitInfo;
import com.example.vo.SubmitInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubmitInfoService {

    @Resource
    private SubmitInfoDao submitInfoDao;

    public SubmitInfo add(SubmitInfo info) {
    submitInfoDao.insertSelective(info);
        return info;
    }

    public void delete(Long id) {
        submitInfoDao.deleteByPrimaryKey(id);
    }

    public void update(SubmitInfo info) {
        submitInfoDao.updateByPrimaryKeySelective(info);
    }

    public SubmitInfo findById(Long id) {
        return submitInfoDao.selectByPrimaryKey(id);
    }
    public List<SubmitInfoVo> findAll() {
        return submitInfoDao.findAll();
    }

    public PageInfo<SubmitInfoVo> findPage(Integer pageNum, Integer pageSize, HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("user");
        if (account == null) {
            return PageInfo.of(new ArrayList<>());
        }
        String name = account.getName();
        Integer level = account.getLevel();
        PageHelper.startPage(pageNum, pageSize);
        List<SubmitInfoVo> info;
        if (1 == level) {
            info = submitInfoDao.findByUserIdAndLevel(null, null, 1);
        } else {
            info = submitInfoDao.findByUserIdAndLevel(name, level, 0);
        }
        return PageInfo.of(info);
    }
}
