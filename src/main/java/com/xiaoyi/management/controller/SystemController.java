package com.xiaoyi.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaoyi.management.common.SystemConstants;
import com.xiaoyi.management.pojo.Admin;
import com.xiaoyi.management.pojo.LoginForm;
import com.xiaoyi.management.pojo.Student;
import com.xiaoyi.management.pojo.Teacher;
import com.xiaoyi.management.service.AdminService;
import com.xiaoyi.management.service.StudentService;
import com.xiaoyi.management.service.TeacherService;
import com.xiaoyi.management.util.*;
import io.jsonwebtoken.lang.Objects;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sms/system")
public class SystemController {
	@Autowired
	private AdminService adminService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private TeacherService teacherService;

	/**
	 * 获取验证码
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "/getVerifiCodeImage",method = RequestMethod.GET)
	public void getVerifiCodeImage(HttpSession session, HttpServletResponse response) {
		// 获取验证码图片
		BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
		// 获取验证码图片上的文字
		String verifiCode = String.valueOf(CreateVerifiCodeImage.getVerifiCode());
		// 验证码文字发送到session域中
		session.setAttribute("verifiCode",verifiCode);
		// 验证码图片通过输出流进行响应
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			ImageIO.write(verifiCodeImage,"JPEG",outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户登录验证
	 * @param session
	 * @param loginForm
	 * @return
	 */
	@PostMapping("login")
	public Result login(HttpSession session,
						@RequestBody LoginForm loginForm) {
		HashMap<String, Object> map = new HashMap<>();
		// 对比session域中的验证码和用户输入的验证码是否相同
		String sessionVerifiCode = (String) session.getAttribute("verifiCode");
		String userVerifiCode = loginForm.getVerifiCode();
		// 判断session域中的验证码是否失效，若失效，返回错误信息；若未失效，继续判断和用户输入验证码是否相同
		if ("".equals(sessionVerifiCode)) {
			// 验证码过期
			return Result.fail().message("验证码已失效，请刷新后重新输入");
		}
		if (!sessionVerifiCode.equalsIgnoreCase(userVerifiCode)) {
			return Result.fail().message("验证码输入错误，请重新输入");
		}
		// 验证码使用完毕，删除验证码
		session.removeAttribute("sessionVerifiCode");

		// 根据用户身份，校验登录信息
		int userType = loginForm.getUserType();
		switch (userType) {
			// 管理员身份
			case 1:
				//调用登录方法，根据用户输入的登录信息进行校验
				Admin admin = adminService.login(loginForm);
				if (null != admin) { // 校验通过
					// 用户ID和类型转为token指令响应给前端
					String token = JwtHelper.createToken(Long.valueOf(admin.getId()), userType);
					map.put("token", token);
					return Result.ok(map);
				} else { // 校验失败
					return Result.fail().message("用户名或者密码有误!");
				}
			case 2:
				//调用登录方法，根据用户输入的登录信息进行校验
				Student student = studentService.login(loginForm);
				if (null != student) { // 校验通过
					// 用户ID和类型转为token指令响应给前端
					String token = JwtHelper.createToken(Long.valueOf(student.getId()), userType);
					map.put("token", token);
					return Result.ok(map);
				} else { // 校验失败
					return Result.fail().message("用户名或者密码有误!");
				}
			case 3:
				//调用登录方法，根据用户输入的登录信息进行校验
				Teacher teacher = teacherService.login(loginForm);
				if (null != teacher) { // 校验通过
					// 用户ID和类型转为token指令响应给前端
					String token = JwtHelper.createToken(Long.valueOf(teacher.getId()), userType);
					map.put("token", token);
					return Result.ok(map);
				} else { // 校验失败
					return Result.fail().message("用户名或者密码有误!");
				}
		}
		// 响应失败，查无用户
		return Result.fail().message("响应失败，查无此用户");
	}

	/**
	 * 根据登录token判断登录人员信息
	 * @param token
	 * @return
	 */
	@GetMapping("/getInfo")
	public Result getInfo(@RequestHeader String token) {
		HashMap<String, Object> map = new HashMap<>();
		// 判断前端传过来的token是否失效
		boolean expiration = JwtHelper.isExpiration(token);
		if (expiration) { // 失效
			return Result.build("token已失效", ResultCodeEnum.TOKEN_ERROR);
		}
		// 未失效，判断用户id和用户类型,根据id和类型跳转至不同的首页
		Long userId = JwtHelper.getUserId(token);
		Integer userType = JwtHelper.getUserType(token);
		switch (userType) {
			//管理员
			case SystemConstants.ADMIN_IDENTITY:
				Admin admin = adminService.getInfoById(userId);
				map.put("user",admin);
				map.put("userType",userType);
				break;
			//学生
			case SystemConstants.STUDENT_IDENTITY:
				Student student = studentService.getInfoById(userId);
				map.put("user",student);
				map.put("userType",userType);
				break;
			//教师
			case SystemConstants.TEACHER_IDENTITY:
				Teacher teacher = teacherService.getInfoById(userId);
				map.put("user",teacher);
				map.put("userType",userType);
				break;
		}
		return Result.ok(map);
	}


	/**
	 * 头像上传
	 * @param multipartFile
	 * @return
	 */
	@PostMapping("/headerImgUpload")
	public Result headerImgUpload(@ApiParam("文件头像二进制数据") @RequestPart MultipartFile multipartFile) {
		String dirPath = "D:\\JavaCode\\workspace\\IDEA\\CampusSystem\\src\\main\\resources\\public\\upload\\";
		String portraitPath = "upload/";
		Map<String, Object> uploadResult = UploadFile.getUploadResult(multipartFile, dirPath, portraitPath);
		String headerImg = (String)uploadResult.get("portrait_path");
		return Result.ok(headerImg);
	}

	/**
	 * 修改密码
	 * @param oldPwd
	 * @param newPwd
	 * @param token
	 * @return
	 */
	@PostMapping("/updatePwd/{oldPwd}/{newPwd}")
	public Result updatePwd(@PathVariable("oldPwd") String oldPwd,
							@PathVariable("newPwd") String newPwd,
							@RequestHeader("token") String token) {
		if (JwtHelper.isExpiration(token)) {
			// token过期
			return Result.fail().message("token已失效，请重新登陆");
		}
		// token有效
		// 密码生成密文
		oldPwd = MD5.encrypt(oldPwd);
		newPwd = MD5.encrypt(newPwd);
		// 判断用户类型
		Long userId = JwtHelper.getUserId(token);
		Integer userType = JwtHelper.getUserType(token);
		switch (userType) {
			// 管理员
			case SystemConstants.ADMIN_IDENTITY:
				return adminService.updateAdminPwd(oldPwd,newPwd,userId);
			// 学生
			case SystemConstants.STUDENT_IDENTITY:
				return studentService.updateStudentPwd(oldPwd,newPwd,userId);
			// 教师
			case SystemConstants.TEACHER_IDENTITY:
				return teacherService.updateTeacherPwd(oldPwd,newPwd,userId);
		}
		return Result.fail().message("没有该用户类型");
	}

}

