package com.xiaoyi.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.management.common.SystemConstants;
import com.xiaoyi.management.pojo.Admin;
import com.xiaoyi.management.pojo.LoginForm;
import com.xiaoyi.management.pojo.Student;
import com.xiaoyi.management.pojo.Teacher;
import com.xiaoyi.management.service.AdminService;
import com.xiaoyi.management.mapper.AdminMapper;
import com.xiaoyi.management.util.JwtHelper;
import com.xiaoyi.management.util.MD5;
import com.xiaoyi.management.util.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Chandler
* @description 针对表【tb_admin】的数据库操作Service实现
* @createDate 2022-04-28 18:54:10
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
implements AdminService{

	@Autowired
	AdminMapper adminMapper;

	@Override
	public Admin login(LoginForm loginForm) {
		LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Admin::getName,loginForm.getUsername());
		wrapper.eq(Admin::getPassword, MD5.encrypt(loginForm.getPassword()));
		Admin admin = getOne(wrapper);
		//Admin admin = adminMapper.selectOne(wrapper);
		return admin;
	}

	@Override
	public Admin getInfoById(Long userId) {
		return adminMapper.selectById(userId);
	}

	/**
	 * 分页查询管理员信息
	 * @param pageNo
	 * @param pageSize
	 * @param adminName
	 * @return
	 */
	@Override
	public IPage<Admin> getAllAdmin(Integer pageNo, Integer pageSize, String adminName) {
		LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
		if (!Strings.isEmpty(adminName)) {
			wrapper.orderByDesc(Admin::getId);
		}
		Page<Admin> adminPage = new Page<>(pageNo, pageSize);
		return adminMapper.selectPage(adminPage, wrapper);
	}

	/**
	 * 添加或修改Admin信息
	 * @param admin
	 */
	@Override
	public void saveOrUpdateAdmin(Admin admin) {
		if (!Strings.isEmpty(admin.getPassword())) {
			admin.setPassword(MD5.encrypt(admin.getPassword()));
			saveOrUpdate(admin);
		}
	}

	@Override
	public void deleteAdminByIds(List<Integer> ids) {
		if (ids.size() > 0) {
			removeByIds(ids);
		}
	}

	/**
	 * 修改管理员密码
	 * @param oldPwd
	 * @param newPwd
	 * @param
	 * @return
	 */
	@Override
	public Result updateAdminPwd(String oldPwd, String newPwd, Long userId) {
		LambdaQueryWrapper<Admin> adminWrapper = new LambdaQueryWrapper<>();
		adminWrapper.eq(Admin::getId,userId);
		adminWrapper.eq(Admin::getPassword, oldPwd);
		Admin admin = getOne(adminWrapper);
		if (!admin.getPassword().equals(oldPwd)) {
			return Result.fail().message("旧密码输入错误");
		}
		// 新密码与旧密码相同
		if (oldPwd.equals(newPwd)) {
			return Result.fail().message("旧密码不能与新密码相同");
		}
		// 新旧密码不相同,修改成功
		admin.setPassword(newPwd);
		saveOrUpdate(admin);

		return Result.ok();
	}
}
