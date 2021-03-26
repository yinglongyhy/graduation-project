package com.yinglongyhy.fang.rest.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.dto.AddressParamDto;
import com.yinglongyhy.fang.entity.Address;
import com.yinglongyhy.fang.service.IAddressService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 地址表 前端控制器
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/admin/address")
public class AddressTableRestController {
    @Autowired
    private IAddressService addressService;

    @GetMapping("page")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<Page<Address>> page(
            @ModelAttribute @ApiParam(value = "params") AddressParamDto params,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") @ApiParam(value = "pageNumber") Integer pageNumber
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "pageSize") Integer pageSize) {
        return new ResponseEntity<>(addressService.page(new Page<>(pageNumber, pageSize), params), HttpStatus.OK);
    }

    @GetMapping("/{code}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<Address> getByCode(@PathVariable("code") @ApiParam(value = "code") String code) {
        return new ResponseEntity<>(addressService.getOne(new QueryWrapper<Address>().eq("code", code)), HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity save(@ModelAttribute @ApiParam(value = "address") Address address) {
        addressService.saveOrUpdate(address, new UpdateWrapper<Address>().eq("code", address.getCode()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{code}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity delete(@PathVariable("code") @ApiParam(value = "code") String code) {
        addressService.remove(new QueryWrapper<Address>().eq("code", code));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}






















