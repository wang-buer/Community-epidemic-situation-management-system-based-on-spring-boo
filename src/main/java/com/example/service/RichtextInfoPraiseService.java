package com.example.service;

import com.example.dao.RichtextInfoPraiseDao;
import com.example.entity.Account;
import com.example.entity.RichtextInfoPraise;
import com.example.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RichtextInfoPraiseService {

    @Resource
    private RichtextInfoPraiseDao richtextInfoPraiseDao;

    public RichtextInfoPraise add(RichtextInfoPraise info, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("1001", "请先登录！");
        }
        // 先判断一下该用户是否已经点过赞了
        RichtextInfoPraise findInfo = richtextInfoPraiseDao.getInfoByUserIdAndLevelAndId(info.getUserId(), info.getLevel(), info.getForeignId());
        if (findInfo != null) {
            throw new CustomException("1001", "您已经点过赞了，请不要重复点击");
        }
        info.setUserId(user.getId());
        info.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        richtextInfoPraiseDao.insertSelective(info);
        return info;
    }
    public Integer count (Long id) {
        return richtextInfoPraiseDao.count(id);
    }
}
