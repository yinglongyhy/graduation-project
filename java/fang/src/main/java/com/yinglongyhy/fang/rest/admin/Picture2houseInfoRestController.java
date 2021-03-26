package com.yinglongyhy.fang.rest.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.entity.Picture2houseInfo;
import com.yinglongyhy.fang.service.IPicture2houseInfoService;
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
@RequestMapping("/admin/picture2houseInfo")
public class Picture2houseInfoRestController {

    @Autowired
    private IPicture2houseInfoService picture2houseInfoService;

    @GetMapping("page")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<Page<Picture2houseInfo>> page(
            @ModelAttribute @ApiParam(value = "params") Picture2houseInfo params,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") @ApiParam(value = "pageNumber") Integer pageNumber
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "pageSize") Integer pageSize) {
        return new ResponseEntity<>(picture2houseInfoService.page(new Page<>(pageNumber, pageSize), new QueryWrapper<>(params)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<Picture2houseInfo> getById(@PathVariable("id") @ApiParam(value = "id") Long id) {
        return new ResponseEntity<>(picture2houseInfoService.getOne(new QueryWrapper<Picture2houseInfo>().eq("id", id)), HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity save(@ModelAttribute @ApiParam(value = "picture2houseInfo") Picture2houseInfo picture2houseInfo) {
        picture2houseInfoService.saveOrUpdate(picture2houseInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity delete(@PathVariable("id") @ApiParam(value = "id") Long id) {
        picture2houseInfoService.remove(new QueryWrapper<Picture2houseInfo>().eq("id", id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
