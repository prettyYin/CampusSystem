package com.xiaoyi.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.management.pojo.LoginForm;
import com.xiaoyi.management.pojo.Student;
import com.xiaoyi.management.pojo.Teacher;
import com.xiaoyi.management.service.TeacherService;
import com.xiaoyi.management.mapper.TeacherMapper;
import com.xiaoyi.management.util.MD5;
import com.xiaoyi.management.util.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Chandler
* @description 针对表【tb_teacher】的数据库操作Service实现
* @createDate 2022-04-28 18:58:58
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
implements TeacherService{

	@Autowired
	TeacherMapper teacherMapper;

	@Override
	public Teacher login(LoginForm loginForm) {
		QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
		wrapper.eq("name",loginForm.getUsername());
		wrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
		return teacherMapper.selectOne(wrapper);
	}

	@Override
	public Teacher getInfoById(Long userId) {
		return teacherMapper.selectById(userId);
	}

	/**
	 * 分页查询教师信息
	 * @param pageNo
	 * @param pageSize
	 * @param teacher
	 * @return
	 */
	@Override
	public Page<Teacher> getTeachers(Integer pageNo, Integer pageSize, Teacher teacher) {
		LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
		if (Strings.isEmpty(teacher.getName()) ) {
			wrapper.orderByDesc(Teacher::getId);
		}
		Page<Teacher> teacherPage = new Page<>(pageNo, pageSize);
		return teacherMapper.selectPage(teacherPage, wrapper);
	}

	/**
	 * 修改教师用户密码
	 * @param oldPwd
	 * @param newPwd
	 * @param userId
	 * @return
	 */
	@Override
	public Result updateTeacherPwd(String oldPwd, String newPwd, Long userId) {
		// 查询旧密码输入是否正确
		LambdaQueryWrapper<Teacher> teacherWrapper = new LambdaQueryWrapper<>();
		teacherWrapper.eq(Teacher::getId,userId);
		teacherWrapper.eq(Teacher::getPassword, oldPwd);
		Teacher teacher = getOne(teacherWrapper);
		if (!teacher.getPassword().equals(oldPwd)) {
			return Result.fail().message("旧密码输入错误");
		}
		// 新密码与旧密码相同
		if (oldPwd.equals(newPwd)) {
			return Result.fail().message("旧密码不能与新密码相同");
		}
		// 新旧密码不相同,修改成功
		teacher.setPassword(newPwd);
		saveOrUpdate(teacher);
		return Result.ok();
	}
}
