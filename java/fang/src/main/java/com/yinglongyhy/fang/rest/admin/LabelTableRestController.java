package com.yinglongyhy.fang.rest.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.entity.Label;
import com.yinglongyhy.fang.entity.Label2houseInfo;
import com.yinglongyhy.fang.entity.Lease;
import com.yinglongyhy.fang.exception.RestApiException;
import com.yinglongyhy.fang.service.IHouseInfoService;
import com.yinglongyhy.fang.service.ILabel2houseInfoService;
import com.yinglongyhy.fang.service.ILabelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Slf4j
@RestController
@RequestMapping("/admin/label")
public class LabelTableRestController {

    @Autowired
    private ILabelService labelService;

    @Autowired
    private ILabel2houseInfoService label2houseInfoService;

    @GetMapping("page")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<Page<Label>> page(
            @ModelAttribute @ApiParam(value = "params") Label params,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") @ApiParam(value = "pageNumber", defaultValue = "1") Integer pageNumber
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        QueryWrapper<Label> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(params.getName())) {
            wrapper = wrapper.like("name", "%" + params.getName() + "%");
        }
        return new ResponseEntity<>(labelService.page(new Page<>(pageNumber, pageSize), wrapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<Label> getById(@PathVariable("id") @ApiParam(value = "id") Long id) {
        return new ResponseEntity<>(labelService.getOne(new QueryWrapper<Label>().eq("id", id)), HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity save(@ModelAttribute @ApiParam(value = "label") Label label) {
        labelService.saveOrUpdate(label);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity delete(@PathVariable("id") @ApiParam(value = "id") Long id) {
        if (label2houseInfoService.count(new QueryWrapper<Label2houseInfo>().eq("label", id)) > 0) {
            log.warn("标签失败，当前标签正在被使用 : {}", id);
            throw new RestApiException("LabelTableRestController", "delete error", "删除标签失败，当前标签正在被使用");
        }
        labelService.remove(new QueryWrapper<Label>().eq("id", id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
