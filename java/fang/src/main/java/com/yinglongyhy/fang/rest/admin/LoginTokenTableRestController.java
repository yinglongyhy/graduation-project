package com.yinglongyhy.fang.rest.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.entity.LoginToken;
import com.yinglongyhy.fang.service.ILoginTokenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 登录令牌表 前端控制器
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/admin/loginToken")
public class LoginTokenTableRestController {

    @Autowired
    private ILoginTokenService loginTokenService;

    @GetMapping("page")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<Page<LoginToken>> page(
            @ModelAttribute @ApiParam(value = "params") LoginToken params,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") @ApiParam(value = "pageNumber", defaultValue = "1") Integer pageNumber
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return new ResponseEntity<>(loginTokenService.page(new Page<>(pageNumber, pageSize), new QueryWrapper<>(params)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<LoginToken> getById(@PathVariable("id") @ApiParam(value = "id") Long id) {
        return new ResponseEntity<>(loginTokenService.getOne(new QueryWrapper<LoginToken>().eq("id", id)), HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity save(@ModelAttribute @ApiParam(value = "loginToken") LoginToken loginToken) {
        loginTokenService.saveOrUpdate(loginToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity delete(@PathVariable("id") @ApiParam(value = "id") Long id) {
        loginTokenService.remove(new QueryWrapper<LoginToken>().eq("id", id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
