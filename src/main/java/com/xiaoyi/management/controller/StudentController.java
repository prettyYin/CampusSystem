package com.xiaoyi.management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyi.management.pojo.Student;
import com.xiaoyi.management.service.StudentService;
import com.xiaoyi.management.util.MD5;
import com.xiaoyi.management.util.Result;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
	@Autowired
	StudentService studentService;

	/**
	 * 根据学生姓名和班级名称分页查询学生信息
	 * @param pageNo
	 * @param pageSize
	 * @param student
	 * @return
	 */
	@GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
	public Result getStudentByOpr(@ApiParam("页码数")@PathVariable("pageNo") Integer pageNo,
								  @ApiParam("页大小") @PathVariable("pageSize") Integer pageSize,
								  @ApiParam("查询条件：1.学生姓名2.班级名称") Student student) {
		Page<Student> clazzPage = studentService.getStudentByOpr(pageNo,pageSize,student);
		return Result.ok(clazzPage);
	}

	/**
	 * 添加或修改学生信息
	 * @param student
	 * @return
	 */
	@PostMapping("/addOrUpdateStudent")
	public Result addOrUpdateStudent(@RequestBody Student student) {
		// 密码不为空时，进行加密储存
		if (!Strings.isEmpty(student.getPassword())) {
			student.setPassword(MD5.encrypt(student.getPassword()));
		}
		// 学生信息不为空，则进行修改或删除
		if (null != student) {
			studentService.addOrUpdate(student);
			return Result.ok();
		}
		return Result.fail().message("修改的学生信息不能为空");
	}

	/**
	 * 根据 id 删除、批量删除学生信息
	 * @param ids
	 * @return
	 */
	@DeleteMapping("/delStudentById")
	public Result delStudentById(@RequestBody List<Integer> ids) {
		if (ids.size() > 0) {
			studentService.delStudentByIds(ids);
			return Result.ok();
		}
		return Result.fail().message("请选择要删除的学生信息");
	}
}
