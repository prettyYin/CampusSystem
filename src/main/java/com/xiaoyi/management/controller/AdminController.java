package com.xiaoyi.management.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoyi.management.pojo.Admin;
import com.xiaoyi.management.service.AdminService;
import com.xiaoyi.management.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

	@Autowired
	AdminService adminService;

	/**
	 * 分页查询管理员信息
	 * @param pageNo
	 * @param pageSize
	 * @param adminName
	 * @return
	 */
	@GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
	public Result getAllAdmin(@PathVariable("pageNo") Integer pageNo,
							  @PathVariable("pageSize")Integer pageSize,
							  String adminName) {
		IPage<Admin> adminPage = adminService.getAllAdmin(pageNo,pageSize,adminName);
		return Result.ok(adminPage);
	}

	/**
	 * 添加或修改Admin信息
	 * @param admin
	 * @return
	 */
	@ApiOperation("添加或修改Admin信息")
	@PostMapping("/saveOrUpdateAdmin")
	public Result saveOrUpdateAdmin(@RequestBody Admin admin){
		adminService.saveOrUpdateAdmin(admin);
		return Result.ok();
	}

	@ApiOperation("删除Admin信息")
	@DeleteMapping("/deleteAdmin")
	public Result deleteAdmin(@RequestBody List<Integer> ids){
		adminService.deleteAdminByIds(ids);
		return Result.ok();
	}
}
