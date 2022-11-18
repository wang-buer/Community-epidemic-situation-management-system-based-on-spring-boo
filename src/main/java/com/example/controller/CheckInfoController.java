package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.common.ResultCode;
import com.example.entity.CheckInfo;
import com.example.service.*;
import com.example.vo.CheckInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/checkInfo")
public class CheckInfoController {
    @Resource
    private CheckInfoService checkInfoService;

    @PostMapping
    public Result<CheckInfo> add(@RequestBody CheckInfoVo checkInfo) {
        checkInfoService.add(checkInfo);
        return Result.success(checkInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        checkInfoService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody CheckInfoVo checkInfo) {
        checkInfoService.update(checkInfo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<CheckInfo> detail(@PathVariable Long id) {
        CheckInfo checkInfo = checkInfoService.findById(id);
        return Result.success(checkInfo);
    }

    @GetMapping
    public Result<List<CheckInfoVo>> all() {
        return Result.success(checkInfoService.findAll());
    }

    @GetMapping("/page/{name}/{temperature}/{location}")
    public Result<PageInfo<CheckInfoVo>> page(@PathVariable String name,
                                                @PathVariable String temperature,
                                                @PathVariable String location,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                HttpServletRequest request) {
        return Result.success(checkInfoService.findPage(name, temperature, location, pageNum, pageSize, request));
    }

    @GetMapping("/check/temperature")
    public Result check() {
        if (checkInfoService.checkIsHigh()) {
            return Result.error();
        } else {
            return Result.success();
        }
    }

    @GetMapping("/check/location")
    public Result checkLocation() {
        if (checkInfoService.checkIsLocation()) {
            return Result.error();
        } else {
            return Result.success();
        }
    }

    /**
    * 批量通过excel添加信息
    * @param file excel文件
    * @throws IOException
    */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {

        List<CheckInfo> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(CheckInfo.class);
        if (!CollectionUtil.isEmpty(infoList)) {
            // 处理一下空数据
            List<CheckInfo> resultList = infoList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getName())).collect(Collectors.toList());
            for (CheckInfo info : resultList) {
                checkInfoService.add(info);
            }
        }
        return Result.success();
    }

    @GetMapping("/getExcelModel")
    public void getExcelModel(HttpServletResponse response) throws IOException {
        // 1. 生成excel
        Map<String, Object> row = new LinkedHashMap<>();
		row.put("name", "");
		row.put("age", "");
		row.put("temperature", "");
		row.put("phone", "");
		row.put("location", "");

        List<Map<String, Object>> list = CollUtil.newArrayList(row);

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=checkInfoModel.xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }
}
