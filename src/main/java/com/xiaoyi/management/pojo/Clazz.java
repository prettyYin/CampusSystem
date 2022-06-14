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
 * @TableName tb_clazz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_clazz")
public class Clazz implements Serializable {
    /**
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Integer number;

    /**
     * 
     */
    private String introducation;

    /**
     * 
     */
    private String headmaster;

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
    private String gradeName;

    private static final long serialVersionUID = 1L;
}