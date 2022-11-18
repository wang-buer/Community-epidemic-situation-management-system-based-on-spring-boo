package com.example.controller;

import com.example.common.Result;
import com.example.entity.RichtextInfoComment;
import com.example.vo.RichtextInfoCommentVo;
import com.example.service.RichtextInfoCommentService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/richtextInfoComment")
public class RichtextInfoCommentController {
    @Resource
    private RichtextInfoCommentService richtextInfoCommentService;

    @PostMapping
    public Result<RichtextInfoComment> add(@RequestBody RichtextInfoComment commentInfo, HttpServletRequest request) {
        richtextInfoCommentService.add(commentInfo, request);
        return Result.success(commentInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        richtextInfoCommentService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody RichtextInfoComment commentInfo) {
        richtextInfoCommentService.update(commentInfo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<RichtextInfoComment> detail(@PathVariable Long id) {
        RichtextInfoComment commentInfo = richtextInfoCommentService.findById(id);
        return Result.success(commentInfo);
    }

    @GetMapping
    public Result<List<RichtextInfoCommentVo>> all() {
        return Result.success(richtextInfoCommentService.findAll());
    }

    @GetMapping("/page/{name}")
    public Result<PageInfo<RichtextInfoCommentVo>> page(@PathVariable String name,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                HttpServletRequest request) {
        return Result.success(richtextInfoCommentService.findPage(name, pageNum, pageSize));
    }

    @GetMapping("/findByForeignId/{id}")
    public Result<List<RichtextInfoCommentVo>> findByForeignId (@PathVariable Long id) {
        return Result.success(richtextInfoCommentService.findByForeignId(id));
    }
}
