package com.yinglongyhy.fang.rest;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yinglongyhy.fang.dto.LeaseAddRequestDto;
import com.yinglongyhy.fang.entity.Lease;
import com.yinglongyhy.fang.service.ILeaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 租房信息（协议） 前端控制器
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/lease")
public class LeaseRestController {

    @Autowired
    private ILeaseService leaseService;

    @PostMapping
    @ApiOperation(value = "添加租约", notes = "")
    public ResponseEntity add(@RequestBody LeaseAddRequestDto leaseAddRequestDto) {
        return new ResponseEntity(leaseService.add(leaseAddRequestDto), HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "更新租约", notes = "")
    public ResponseEntity update(@RequestBody LeaseAddRequestDto leaseAddRequestDto) {
        leaseService.update(new UpdateWrapper<Lease>()
                .set("rent", leaseAddRequestDto.getRent())
                .eq("id", leaseAddRequestDto.getLeaseId()));
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "删除租约", notes = "")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        leaseService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
