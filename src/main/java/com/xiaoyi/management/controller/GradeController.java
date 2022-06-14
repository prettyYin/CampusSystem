package com.xiaoyi.management.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyi.management.mapper.GradeMapper;
import com.xiaoyi.management.pojo.Clazz;
import com.xiaoyi.management.pojo.Grade;
import com.xiaoyi.management.pojo.Student;
import com.xiaoyi.management.service.GradeService;
import com.xiaoyi.management.util.Result;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

	@Autowired
	GradeService gradeService;

	/**
	 * 查询年级信息
	 * @param pageNo
	 * @param pageSize
	 * @param gradeName
	 * @return
	 */
	@GetMapping("/getGrades/{pageNo}/{pageSize}")
	public Result getGrades(@PathVariable("pageNo") Integer pageNo,
							@PathVariable("pageSize") Integer pageSize,
							String gradeName) {
		// 设置分页
		Page<Grade> page = new Page<>(pageNo, pageSize);
		// 传入分页信息和年级信息：查询年级
		IPage<Grade> gradePage = gradeService.getGradeInfo(page,gradeName);
		return Result.ok(gradePage);
	}

	/**
	 * 添加或修改年级信息
	 * @param grade
	 * @return
	 */
	@PostMapping("/saveOrUpdateGrade")
	public Result saveOrUpdateGrade(@RequestBody Grade grade) {
		gradeService.saveOrUpdate(grade);
		return Result.ok();
	}

	@DeleteMapping("/deleteGrade")
	public Result deleteGrade(@RequestBody ArrayList<Integer> ids) {
		gradeService.removeByIds(ids);
		return Result.ok().message("删除成功!");
	}

	@GetMapping("/getGrades")
	public Result getGrades() {
		List<Grade> grades = gradeService.getGrades();
		return Result.ok(grades);
	}


}
