package com.xiaoyi.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.management.pojo.Clazz;
import com.xiaoyi.management.pojo.Student;
import com.xiaoyi.management.service.ClazzService;
import com.xiaoyi.management.mapper.ClazzMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
* @author Chandler
* @description 针对表【tb_clazz】的数据库操作Service实现
* @createDate 2022-04-28 18:57:18
*/
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz>
implements ClazzService{

	@Autowired
	ClazzMapper clazzMapper;

	@Autowired
	ClazzService clazzService;

	/**
	 * 分页查询学生信息
	 * @param page
	 * @param clazz
	 * @return
	 */
	@Override
	public IPage<Clazz> getClazzInfo(Page<Clazz> page, Clazz clazz) {
		LambdaQueryWrapper<Clazz> wrapper = new LambdaQueryWrapper<>();
		if (!StringUtils.isEmpty(clazz.getGradeName())) {
			wrapper.like(Clazz::getGradeName, clazz.getGradeName());
		}
		if (!StringUtils.isEmpty(clazz.getName())) {
			wrapper.like(Clazz::getName,clazz.getName());
		}
		wrapper.orderByAsc(Clazz::getName);
		Page<Clazz> clazzPage = clazzMapper.selectPage(page, wrapper);
		return clazzPage;
	}

	@Override
	public List<Clazz> getClazzs() {
		List<Clazz> clazzes = list();
		return clazzes;
	}


}
