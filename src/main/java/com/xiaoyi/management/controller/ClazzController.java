package com.xiaoyi.management.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyi.management.pojo.Clazz;
import com.xiaoyi.management.service.ClazzService;
import com.xiaoyi.management.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api("班级管理控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

	@Autowired
	ClazzService clazzService;

	/**
	 * 分页查询所有班级信息
	 *
	 * @param pageNo
	 * @param pageSize
	 * @param clazz
	 * @return
	 */
	@ApiOperation("分页查询所有班级信息")
	@GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
	public Result getClazzsByOpr(@PathVariable("pageNo") Integer pageNo,
								 @PathVariable("pageSize") Integer pageSize,
								 Clazz clazz) {
		Page<Clazz> page = new Page<>(pageNo, pageSize);
		IPage<Clazz> clazzInfo = clazzService.getClazzInfo(page, clazz);
		return Result.ok(clazzInfo);
	}

	/**
	 * 保存或修改班级信息
	 * @param clazz
	 * @return
	 */
	@ApiOperation("保存或修改班级信息")
	@PostMapping("/saveOrUpdateClazz")
	public Result saveOrUpdateClazz(@ApiParam("JSON格式的clazz模型") @RequestBody Clazz clazz) {
		clazzService.saveOrUpdate(clazz);
		return Result.ok();
	}

	/**
	 * 删除、批量删除班级信息
	 * @param ids
	 * @return
	 */
	@ApiOperation("删除、批量删除班级信息")
	@DeleteMapping("/deleteClazz")
	public Result deleteClazz(@ApiParam("被删除的JSON格式的id数组") @RequestBody List<Integer> ids) {
		if (ids != null) {
			clazzService.removeByIds(ids);
			return Result.ok();
		}
		return Result.fail().message("请选择要删除的班级信息");
	}

	/**
	 * 根据名称模糊搜索班级信息并回显
	 * @return
	 */
	@GetMapping("/getClazzs")
	public Result getClazzs() {
		List<Clazz> clazzes = clazzService.getClazzs();
		if (clazzes != null) {
			return Result.ok(clazzes);
		}
		return Result.fail().message("查询的班级不存在");
	}


}
