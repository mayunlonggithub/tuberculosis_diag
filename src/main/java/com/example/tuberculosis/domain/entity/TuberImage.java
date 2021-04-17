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
@Table(name = "tuber_image")
public class TuberImage extends CreateModifyTime {
    private int id;
    private Integer doctorNum;
    private Integer patientNum;
    private String relativePath;
    private String imageName;
    private Integer delFlag;
    private String diagnosisRecord;
    private Integer status;

    @Id
    @Column(name = "id")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "tuberImage", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Basic
    @Column(name = "relative_path")
    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    @Basic
    @Column(name = "image_name")
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
    @Column(name = "diagnosis_record")
    public String getDiagnosisRecord() {
        return diagnosisRecord;
    }

    public void setDiagnosisRecord(String diagnosisRecord) {
        this.diagnosisRecord = diagnosisRecord;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
