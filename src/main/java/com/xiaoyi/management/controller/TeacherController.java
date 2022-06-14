package com.xiaoyi.management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyi.management.pojo.Teacher;
import com.xiaoyi.management.service.TeacherService;
import com.xiaoyi.management.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

	@Autowired
	TeacherService teacherService;

	/**
	 * 分页查询教师信息
	 * @param pageNo
	 * @param pageSize
	 * @param teacher
	 * @return
	 */
	@GetMapping("/getTeachers/{pageNo}/{pageSize}")
	public Result getTeachers(@PathVariable("pageNo") Integer pageNo,
							  @PathVariable("pageSize") Integer pageSize,
							  Teacher teacher) {
		Page<Teacher> teacherPage = teacherService.getTeachers(pageNo,pageSize,teacher);
		return Result.ok(teacherPage);
	}
}
