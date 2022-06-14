package com.xiaoyi.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.management.pojo.LoginForm;
import com.xiaoyi.management.pojo.Student;
import com.xiaoyi.management.util.Result;

import java.util.List;

/**
* @author Chandler
* @description 针对表【tb_student】的数据库操作Service
* @createDate 2022-04-28 18:58:52
*/
public interface StudentService extends IService<Student> {

	Student login(LoginForm loginForm);

	Student getInfoById(Long userId);
	Page<Student> getStudentByOpr(Integer pageNo, Integer pageSize, Student student);

	void addOrUpdate(Student student);

	void delStudentByIds(List<Integer> ids);

	Result updateStudentPwd(String oldPwd, String newPwd, Long userId);
}
