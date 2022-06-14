package com.xiaoyi.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.management.pojo.Grade;
import com.xiaoyi.management.service.GradeService;
import com.xiaoyi.management.mapper.GradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
* @author Chandler
* @description 针对表【tb_grade】的数据库操作Service实现
* @createDate 2022-04-28 18:58:42
*/
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
implements GradeService{

	@Autowired
	GradeMapper gradeMapper;

	/**
	 * 分页查询年级信息
	 * @param page
	 * @param gradeName
	 * @return
	 */
	@Override
	public IPage<Grade> getGradeInfo(Page<Grade> page, String gradeName) {
		QueryWrapper<Grade> wrapper = new QueryWrapper<>();
		if ( !StringUtils.isEmpty(gradeName) ) {
			wrapper.like("name",gradeName);
		}
		wrapper.orderByAsc("id");
		Page<Grade> pageResult = gradeMapper.selectPage(page, wrapper);
		return pageResult;
	}

	@Override
	public List<Grade> getGrades() {
		return gradeMapper.selectList(null);
	}
}
