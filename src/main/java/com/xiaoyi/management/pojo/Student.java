package com.xiaoyi.management.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName tb_student
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_student")
public class Student implements Serializable {
    /**
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String sno;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String gender;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String telephone;

    /**
     * 
     */
    private String address;

    /**
     * 
     */
    private String introducation;

    /**
     * 
     */
    private String portraitPath;

    /**
     * 
     */
    private String clazzName;

    private static final long serialVersionUID = 1L;
}