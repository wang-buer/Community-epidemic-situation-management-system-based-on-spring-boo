package com.example.controller;

import com.example.common.Result;
import com.example.entity.RichtextInfoPraise;
import com.example.service.RichtextInfoPraiseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/richtextInfoPraise")
public class RichtextInfoPraiseController {
    @Resource
    private RichtextInfoPraiseService richtextInfoPraiseService;

    @PostMapping
    public Result<RichtextInfoPraise> add(@RequestBody RichtextInfoPraise info, HttpServletRequest request) {
        richtextInfoPraiseService.add(info, request);
        return Result.success(info);
    }

    @GetMapping("/count/{id}")
    public Result<Integer> count (@PathVariable Long id) {
        return Result.success(richtextInfoPraiseService.count(id));
    }
}
