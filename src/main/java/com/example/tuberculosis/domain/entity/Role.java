package com.example.tuberculosis.domain.entity;

import com.example.tuberculosis.base.CreateModifyTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/29 16:55
 */
@Entity
@Table(name = "t_role")
public class Role extends CreateModifyTime {
    private int id;
    private String name;
    private String description;
    private Integer delFlag;

    @Id
    @Column(name = "id")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "role", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "del_flag")
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

}
