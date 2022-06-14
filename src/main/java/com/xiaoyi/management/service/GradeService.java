package com.xiaoyi.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.management.pojo.Grade;

import java.util.List;

/**
* @author Chandler
* @description 针对表【tb_grade】的数据库操作Service
* @createDate 2022-04-28 18:58:42
*/
public interface GradeService extends IService<Grade> {

	IPage getGradeInfo(Page<Grade> page, String gradeName);

	List<Grade> getGrades();
}
