package com.yinglongyhy.fang.rest;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.dto.HouseInfoDto;
import com.yinglongyhy.fang.dto.HouseInfoParamsDto;
import com.yinglongyhy.fang.dto.HouseInfoResponseDto;
import com.yinglongyhy.fang.exception.RestApiException;
import com.yinglongyhy.fang.service.IHouseInfoService;
import com.yinglongyhy.fang.threadlocal.UserThreadLocal;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping("/houseInfo")
public class HouseInfoRestController {

    @Autowired
    private IHouseInfoService houseInfoService;

    @GetMapping("page")
    @ApiOperation(value = "分页查询", notes = "")
    public ResponseEntity<Page<HouseInfoResponseDto>> page(
            @ModelAttribute @ApiParam(value = "params") HouseInfoParamsDto params,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") @ApiParam(value = "pageNumber", defaultValue = "1") Integer pageNumber
          , @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        if (UserThreadLocal.get() == null && (StringUtils.isNotBlank(params.getTenant()) || (StringUtils.isNotBlank(params.getOwner())))) {
            log.warn("权限不足, params: {}", params);
            throw new RestApiException("HouseInfoRestController.page", "no permission", "权限不足");
        }
        return new ResponseEntity<>(houseInfoService.page(params, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "单个查询", notes = "")
    public ResponseEntity<HouseInfoResponseDto> getByid(@PathVariable @ApiParam(value = "params") Long id) {
        return new ResponseEntity<>(houseInfoService.getOneById(id), HttpStatus.OK);
    }

    @PostMapping("save")
    @ApiOperation(value = "保存房屋信息", notes = "保存房屋信息，新增或修改")
    public ResponseEntity save(@RequestBody HouseInfoDto houseInfoDto) {
        houseInfoService.save(houseInfoDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "保存房屋信息", notes = "保存房屋信息，新增或修改")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        houseInfoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
