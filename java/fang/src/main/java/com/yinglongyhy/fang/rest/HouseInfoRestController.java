package com.yinglongyhy.fang.rest;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.entity.HouseInfo;
import com.yinglongyhy.fang.entity.User;
import com.yinglongyhy.fang.service.IHouseInfoService;
import com.yinglongyhy.fang.threadlocal.UserThreadLocal;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Page<HouseInfo> page(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber
          , @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        User user = UserThreadLocal.get();
        return houseInfoService.page(new Page<>(pageNumber, pageSize));
    }

    @PostMapping("")
    @ApiOperation(value = "发布房源", notes = "")
    public Page<HouseInfo> add(@RequestBody HouseInfo houseInfo) {
        User user = UserThreadLocal.get();
        return houseInfoService.page(new Page<>(pageNumber, pageSize));
    }


}
