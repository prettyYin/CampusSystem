package com.xiaoyi.management;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaoyi.management.pojo.Admin;
import com.xiaoyi.management.service.AdminService;
import com.xiaoyi.management.util.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CampusSystemApplicationTests {

	@Autowired
	AdminService adminService;

	@Test
	void testPassword() {
		Admin admin = adminService.getById(103);
		String password = admin.getPassword();
		String encryptPwd = MD5.encrypt(admin.getPassword());
		System.out.println(password);
		System.out.println(encryptPwd);
	}

	@Test
	void testGetAdmin() {
		LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
//		wrapper.eq(Admin::getName,"admin");
		String password = MD5.encrypt("admin");
		String password2 = MD5.encrypt(password);
//		wrapper.eq(Admin::getPassword,password);
//		Admin admin = adminService.getOne(wrapper);
		System.out.println(password);
		System.out.println(password2);
//		System.out.println(admin);
	}

}
