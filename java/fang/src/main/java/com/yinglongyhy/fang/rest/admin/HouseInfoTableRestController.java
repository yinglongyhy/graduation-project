package com.yinglongyhy.fang.rest.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.entity.HouseInfo;
import com.yinglongyhy.fang.entity.Label2houseInfo;
import com.yinglongyhy.fang.entity.Lease;
import com.yinglongyhy.fang.entity.Picture2houseInfo;
import com.yinglongyhy.fang.exception.RestApiException;
import com.yinglongyhy.fang.service.IHouseInfoService;
import com.yinglongyhy.fang.service.ILabel2houseInfoService;
import com.yinglongyhy.fang.service.ILeaseService;
import com.yinglongyhy.fang.service.IPicture2houseInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/admin/houseInfo")
public class HouseInfoTableRestController {

    @Autowired
    private IHouseInfoService houseInfoService;

    @Autowired
    private ILabel2houseInfoService label2houseInfoService;

    @Autowired
    private IPicture2houseInfoService picture2houseInfoService;

    @Autowired
    private ILeaseService leaseService;

    @GetMapping("page")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<Page<HouseInfo>> page(
            @ModelAttribute @ApiParam(value = "params") HouseInfo params,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") @ApiParam(value = "pageNumber", defaultValue = "1") Integer pageNumber
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
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
        if (leaseService.count(new QueryWrapper<Lease>().eq("house_info", id)) > 0) {
            log.warn("删除房源失败，当前房源正在被使用 : {}", id);
            throw new RestApiException("HouseInfoTableRestController", "delete error", "删除房源失败，当前房源正在被使用");
        }
        label2houseInfoService.remove(new QueryWrapper<Label2houseInfo>().eq("house_info", id));
        picture2houseInfoService.remove(new QueryWrapper<Picture2houseInfo>().eq("house_info", id));
        houseInfoService.remove(new QueryWrapper<HouseInfo>().eq("id", id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
