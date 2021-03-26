package com.yinglongyhy.fang.rest.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.entity.HouseInfo;
import com.yinglongyhy.fang.service.IHouseInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 房源信息表 前端控制器
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/admin/houseInfo")
public class HouseInfoTableRestController {

    @Autowired
    private IHouseInfoService houseInfoService;

    @GetMapping("page")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<Page<HouseInfo>> page(
            @ModelAttribute @ApiParam(value = "params") HouseInfo params,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") @ApiParam(value = "pageNumber") Integer pageNumber
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "pageSize") Integer pageSize) {
        return new ResponseEntity<>(houseInfoService.page(new Page<>(pageNumber, pageSize), new QueryWrapper<>(params)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<HouseInfo> getById(@PathVariable("id") @ApiParam(value = "id") Long id) {
        return new ResponseEntity<>(houseInfoService.getOne(new QueryWrapper<HouseInfo>().eq("id", id)), HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity save(@ModelAttribute @ApiParam(value = "houseInfo") HouseInfo houseInfo) {
        houseInfoService.saveOrUpdate(houseInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity delete(@PathVariable("id") @ApiParam(value = "id") Long id) {
        houseInfoService.remove(new QueryWrapper<HouseInfo>().eq("id", id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
