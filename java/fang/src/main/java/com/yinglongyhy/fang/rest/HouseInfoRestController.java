package com.yinglongyhy.fang.rest;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.dto.HouseInfoDto;
import com.yinglongyhy.fang.dto.HouseInfoParamsDto;
import com.yinglongyhy.fang.dto.HouseInfoResponseDto;
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
@RequestMapping("/houseInfo")
public class HouseInfoRestController {

    @Autowired
    private IHouseInfoService houseInfoService;

    @GetMapping("page")
    @ApiOperation(value = "分页查询", notes = "")
    public ResponseEntity<Page<HouseInfoResponseDto>> page(
            @ModelAttribute @ApiParam(value = "params") HouseInfoParamsDto params,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") @ApiParam(value = "pageNumber") Integer pageNumber
          , @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "pageSize") Integer pageSize) {
        return new ResponseEntity<>(houseInfoService.page(params, pageNumber, pageSize), HttpStatus.OK);
    }

    @PostMapping("save")
    @ApiOperation(value = "保存房屋信息", notes = "保存房屋信息，新增或修改")
    public ResponseEntity save(@RequestBody HouseInfoDto houseInfoDto) {
        houseInfoService.save(houseInfoDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
