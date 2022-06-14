package com.xiaoyi.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.management.pojo.Admin;
import com.xiaoyi.management.pojo.LoginForm;
import com.xiaoyi.management.pojo.Student;
import com.xiaoyi.management.service.StudentService;
import com.xiaoyi.management.mapper.StudentMapper;
import com.xiaoyi.management.util.MD5;
import com.xiaoyi.management.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
* @author Chandler
* @description 针对表【tb_student】的数据库操作Service实现
* @createDate 2022-04-28 18:58:52
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
implements StudentService{

	@Autowired
	StudentMapper studentMapper;

	@Override
	public Student login(LoginForm loginForm) {
		QueryWrapper<Student> wrapper = new QueryWrapper<>();
		wrapper.eq("name",loginForm.getUsername());
		wrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
		return studentMapper.selectOne(wrapper);
	}

	@Override
	public Student getInfoById(Long userId) {
		return studentMapper.selectById(userId);
	}

	/**
	 * 根据学生姓名和班级名称分页查询学生信息
	 * @param pageNo
	 * @param pageSize
	 * @param student
	 * @return
	 */
	@Override
	public Page<Student> getStudentByOpr(Integer pageNo, Integer pageSize, Student student) {
		LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
		// 如果学生姓名不为空，则根据学生姓名模糊查询班级信息
		if (!StringUtils.isEmpty(student.getName())) {
			wrapper.like(Student::getName,student.getName());
		}
		// 如果班级名称不为空，则根据班级名称模糊查询学生信息
		if (!StringUtils.isEmpty(student.getName())) {
			wrapper.like(Student::getClazzName,student.getClazzName());
		}
		wrapper.orderByDesc(Student::getId);
		Page<Student> studentPage = new Page<>(pageNo, pageSize);
//		Page<Student> page = page(studentPage, wrapper);
		Page<Student> page = baseMapper.selectPage(studentPage, wrapper);
		return page;
	}

	/**
	 * 添加或修改学生信息
	 */
	@Override
	public void addOrUpdate(Student student) {
		saveOrUpdate(student);
	}

	/**
	 * 根据 id 删除、批量删除学生信息
	 * @param ids
	 */
	@Override
	public void delStudentByIds(List<Integer> ids) {
		removeByIds(ids);
	}

	@Override
	public Result updateStudentPwd(String oldPwd, String newPwd, Long userId) {
		// 学生
		// 查询旧密码输入是否正确
		LambdaQueryWrapper<Student> studentWrapper = new LambdaQueryWrapper<>();
		studentWrapper.eq(Student::getId,userId);
		studentWrapper.eq(Student::getPassword, oldPwd);
		Student student = getOne(studentWrapper);
		if (!student.getPassword().equals(oldPwd)) {
			return Result.fail().message("旧密码输入错误");
		}
		// 新密码与旧密码相同
		if (oldPwd.equals(newPwd)) {
			return Result.fail().message("旧密码不能与新密码相同");
		}
		// 新旧密码不相同,修改成功
		student.setPassword(newPwd);
		saveOrUpdate(student);
		return Result.ok();
	}
}
