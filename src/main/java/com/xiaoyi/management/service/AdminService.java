package com.xiaoyi.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.management.pojo.Admin;
import com.xiaoyi.management.pojo.LoginForm;
import com.xiaoyi.management.util.Result;

import java.util.List;

/**
* @author Chandler
* @description 针对表【tb_admin】的数据库操作Service
* @createDate 2022-04-28 18:54:10
*/
public interface AdminService extends IService<Admin> {

	Admin login(LoginForm loginForm);

	Admin getInfoById(Long userId);

	IPage<Admin> getAllAdmin(Integer pageNo, Integer pageSize, String adminName);

	void saveOrUpdateAdmin(Admin admin);

	void deleteAdminByIds(List<Integer> ids);

	Result updateAdminPwd(String oldPwd, String newPwd,Long userId);
}
