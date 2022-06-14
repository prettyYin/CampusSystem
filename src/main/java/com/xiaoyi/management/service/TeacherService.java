package com.xiaoyi.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.management.pojo.LoginForm;
import com.xiaoyi.management.pojo.Teacher;
import com.xiaoyi.management.util.Result;

/**
* @author Chandler
* @description 针对表【tb_teacher】的数据库操作Service
* @createDate 2022-04-28 18:58:58
*/
public interface TeacherService extends IService<Teacher> {

	Teacher login(LoginForm loginForm);

	Teacher getInfoById(Long userId);

	Page<Teacher> getTeachers(Integer pageNo, Integer pageSize, Teacher teacher);

	Result updateTeacherPwd(String oldPwd, String newPwd, Long userId);
}
