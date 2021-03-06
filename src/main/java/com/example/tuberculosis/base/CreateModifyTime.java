package com.example.tuberculosis.base;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 需要创建时间和修改时间的实体继承该类
 * created date：2017-09-20
 * @author niezhegang
 */

/**
 * 需要创建时间和修改时间的实体继承该类
 * created date：2017-09-20
 * @author niezhegang
 */
@MappedSuperclass
public abstract class CreateModifyTime {
    /**创建时间*/
    private Date createTime;
    /**修改时间*/
    private Date modifyTime;

    @Basic
    @Column(name = "create_time",updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modify_time")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
