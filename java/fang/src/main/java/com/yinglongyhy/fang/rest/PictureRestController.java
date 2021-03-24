package com.yinglongyhy.fang.rest;


import com.yinglongyhy.fang.FileUtil;
import com.yinglongyhy.fang.entity.Picture;
import com.yinglongyhy.fang.service.IPictureService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 图片表 前端控制器
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/picture")
public class PictureRestController {

    @Value("${web.upload-path}")
    private String path;

    @Value("${web.image-path}")
    private String imagePath;

    @Autowired
    private IPictureService pictureService;

    @PostMapping("upload")
    @ApiOperation(value = "上传图片接口", notes = "上传图片接口，返回新的图片名称")
    public ResponseEntity<String> upload(@RequestParam(value = "file") MultipartFile file) {
        String newFileName = FileUtil.upload(file, path, file.getOriginalFilename());

        pictureService.save(new Picture(newFileName, file.getOriginalFilename()));
        return new ResponseEntity<>(newFileName, HttpStatus.OK);
    }
}
