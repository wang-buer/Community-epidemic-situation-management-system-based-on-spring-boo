package com.example.service;

import com.example.dao.BusinessInfoDao;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.example.entity.BusinessInfo;
import com.example.exception.CustomException;
import com.example.common.ResultCode;
import com.example.vo.BusinessInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.crypto.SecureUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class BusinessInfoService {

    @Resource
    private BusinessInfoDao businessInfoDao;

    public BusinessInfo add(BusinessInfo businessInfo) {
        // 唯一校验
        int count = businessInfoDao.checkRepeat("name", businessInfo.getName(), null);
        if (count > 0) {
            throw new CustomException("1001", "用户名\"" + businessInfo.getName() + "\"已存在");
        }
        if (StringUtils.isEmpty(businessInfo.getPassword())) {
            // 默认密码123456
            businessInfo.setPassword(SecureUtil.md5("123456"));
        } else {
            businessInfo.setPassword(SecureUtil.md5(businessInfo.getPassword()));
        }
        businessInfoDao.insertSelective(businessInfo);
        return businessInfo;
    }

    public void delete(Long id) {
        businessInfoDao.deleteByPrimaryKey(id);
    }

    public void update(BusinessInfo businessInfo) {
        businessInfoDao.updateByPrimaryKeySelective(businessInfo);
    }

    public BusinessInfo findById(Long id) {
        return businessInfoDao.selectByPrimaryKey(id);
    }

    public List<BusinessInfoVo> findAll() {
        return businessInfoDao.findByName("all");
    }

    public PageInfo<BusinessInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessInfoVo> all = businessInfoDao.findByName(name);
        return PageInfo.of(all);
    }

    public BusinessInfoVo findByUserName(String name) {
        return businessInfoDao.findByUsername(name);
    }

    public BusinessInfo login(String username, String password) {
        BusinessInfo businessInfo = businessInfoDao.findByUsername(username);
        if (businessInfo == null) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        if (!SecureUtil.md5(password).equalsIgnoreCase(businessInfo.getPassword())) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        return businessInfo;
    }

}
