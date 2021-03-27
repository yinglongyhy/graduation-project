package com.yinglongyhy.fang.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.dto.HouseInfoParamsDto;
import com.yinglongyhy.fang.dto.HouseInfoResponseDto;
import com.yinglongyhy.fang.entity.Address;
import com.yinglongyhy.fang.service.IAddressService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 地址表 前端控制器
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/address")
public class AddressRestController {
    @Autowired
    private IAddressService addressService;

    @GetMapping("/query")
    @ApiOperation(value = "分页查询", notes = "")
    public ResponseEntity<List<Address>> page(
            @RequestParam("name") @ApiParam(value = "name") String name) {
        return new ResponseEntity<>(addressService.list(new QueryWrapper<Address>().like("fullname", "%" + name + "%")), HttpStatus.OK);
    }

}
