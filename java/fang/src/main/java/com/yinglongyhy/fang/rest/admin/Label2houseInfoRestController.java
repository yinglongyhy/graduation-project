package com.yinglongyhy.fang.rest.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.entity.Label2houseInfo;
import com.yinglongyhy.fang.service.ILabel2houseInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yinglongyhy
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/admin/label2houseInfo")
public class Label2houseInfoRestController {

    @Autowired
    private ILabel2houseInfoService label2houseInfoService;

    @GetMapping("page")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<Page<Label2houseInfo>> page(
            @ModelAttribute @ApiParam(value = "params") Label2houseInfo params,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") @ApiParam(value = "pageNumber") Integer pageNumber
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "pageSize") Integer pageSize) {
        return new ResponseEntity<>(label2houseInfoService.page(new Page<>(pageNumber, pageSize), new QueryWrapper<>(params)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<Label2houseInfo> getById(@PathVariable("id") @ApiParam(value = "id") Long id) {
        return new ResponseEntity<>(label2houseInfoService.getOne(new QueryWrapper<Label2houseInfo>().eq("id", id)), HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity save(@ModelAttribute @ApiParam(value = "label2houseInfo") Label2houseInfo label2houseInfo) {
        label2houseInfoService.saveOrUpdate(label2houseInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity delete(@PathVariable("id") @ApiParam(value = "id") Long id) {
        label2houseInfoService.remove(new QueryWrapper<Label2houseInfo>().eq("id", id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
