package com.xiaoyi.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.management.pojo.Clazz;
import com.xiaoyi.management.pojo.Student;

import java.util.List;

/**
* @author Chandler
* @description 针对表【tb_clazz】的数据库操作Service
* @createDate 2022-04-28 18:57:18
*/
public interface ClazzService extends IService<Clazz> {


	IPage<Clazz> getClazzInfo(Page<Clazz> page, Clazz clazz);

	List<Clazz> getClazzs();

}
