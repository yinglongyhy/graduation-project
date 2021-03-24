package com.yinglongyhy.fang.rest;


import com.yinglongyhy.fang.FileUtil;
import com.yinglongyhy.fang.dto.HouseInfoDto;
import com.yinglongyhy.fang.entity.Picture;
import com.yinglongyhy.fang.service.IHouseInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 房源信息表 前端控制器
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/houseInfo")
public class HouseInfoRestController {

    @Autowired
    private IHouseInfoService houseInfoService;

    @PostMapping("save")
    @ApiOperation(value = "保存房屋信息", notes = "保存房屋信息，新增或修改")
    public ResponseEntity save(@RequestBody HouseInfoDto houseInfoDto) {
        houseInfoService.save(houseInfoDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
