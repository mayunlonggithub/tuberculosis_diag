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

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/22 19:07
 */
@Entity
@Table(name = "patient_user")
public class PatientUser extends CreateModifyTime {
    private int id;
    private String description;
    private String judge;
    private Integer doctorNum;
    private Integer patientNum;
    private Integer delFlag;

    @Id
    @Column(name = "id")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "patientInf", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "judge")
    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    @Basic
    @Column(name = "del_flag")
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Basic
    @Column(name = "doctor_num")
    public Integer getDoctorNum() {
        return doctorNum;
    }

    public void setDoctorNum(Integer doctorNum) {
        this.doctorNum = doctorNum;
    }

    @Basic
    @Column(name = "patient_num")
    public Integer getPatientNum() {
        return patientNum;
    }

    public void setPatientNum(Integer patientNum) {
        this.patientNum = patientNum;
    }
}
