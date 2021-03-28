package com.yinglongyhy.fang.rest.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.entity.Lease;
import com.yinglongyhy.fang.entity.Picture;
import com.yinglongyhy.fang.entity.Picture2houseInfo;
import com.yinglongyhy.fang.exception.RestApiException;
import com.yinglongyhy.fang.service.IPicture2houseInfoService;
import com.yinglongyhy.fang.service.IPictureService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 图片表 前端控制器
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Slf4j
@RestController
@RequestMapping("/admin/picture")
public class PictureTableRestController {

    @Autowired
    private IPictureService pictureService;

    @Autowired
    private IPicture2houseInfoService picture2houseInfoService;

    @GetMapping("page")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<Page<Picture>> page(
            @ModelAttribute @ApiParam(value = "params") Picture params,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") @ApiParam(value = "pageNumber", defaultValue = "1") Integer pageNumber
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return new ResponseEntity<>(pictureService.page(new Page<>(pageNumber, pageSize), new QueryWrapper<>(params)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<Picture> getById(@PathVariable("id") @ApiParam(value = "id") Long id) {
        return new ResponseEntity<>(pictureService.getOne(new QueryWrapper<Picture>().eq("id", id)), HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity save(@ModelAttribute @ApiParam(value = "picture") Picture picture) {
        pictureService.saveOrUpdate(picture);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity delete(@PathVariable("id") @ApiParam(value = "id") Long id) {
        if (picture2houseInfoService.count(new QueryWrapper<Picture2houseInfo>().eq("picture", id)) > 0) {
            log.warn("删除图片失败，当前图片正在被使用 : {}", id);
            throw new RestApiException("PictureTableRestController", "delete error", "删除图片失败，当前图片正在被使用");
        }
        pictureService.remove(new QueryWrapper<Picture>().eq("id", id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
