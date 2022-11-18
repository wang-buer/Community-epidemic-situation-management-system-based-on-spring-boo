package com.example.service;

import com.example.exception.CustomException;
import com.example.dao.RichtextInfoCommentDao;
import org.springframework.stereotype.Service;
import com.example.entity.RichtextInfoComment;
import com.example.vo.RichtextInfoCommentVo;
import com.example.entity.Account;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RichtextInfoCommentService {

    @Resource
    private RichtextInfoCommentDao richtextInfoCommentDao;

    public RichtextInfoComment add(RichtextInfoComment commentInfo, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("1001", "请先登录！");
        }
        commentInfo.setName(user.getName());
        commentInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        richtextInfoCommentDao.insertSelective(commentInfo);
        return commentInfo;
    }

    public void delete(Long id) {
        richtextInfoCommentDao.deleteByPrimaryKey(id);
    }

    public void update(RichtextInfoComment commentInfo) {
        commentInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        richtextInfoCommentDao.updateByPrimaryKeySelective(commentInfo);
    }

    public RichtextInfoComment findById(Long id) {
        return richtextInfoCommentDao.selectByPrimaryKey(id);
    }

    public List<RichtextInfoCommentVo> findAll() {
        return richtextInfoCommentDao.findAllVo(null);
    }

    public PageInfo<RichtextInfoCommentVo> findPage(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<RichtextInfoCommentVo> all = richtextInfoCommentDao.findAllVo(name);
        return PageInfo.of(all);
    }

    public List<RichtextInfoCommentVo> findByForeignId (Long id) {
        List<RichtextInfoCommentVo> all = richtextInfoCommentDao.findByForeignId(id, 0L);
        for (RichtextInfoCommentVo reserveInfoVo : all) {
            Long parentId = reserveInfoVo.getId();
            List<RichtextInfoCommentVo> children = new ArrayList<>(richtextInfoCommentDao.findByForeignId(id, parentId));
            reserveInfoVo.setChildren(children);
        }
        return all;
    }
}
