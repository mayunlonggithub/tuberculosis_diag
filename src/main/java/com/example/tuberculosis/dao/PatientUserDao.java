package com.example.tuberculosis.dao;

import com.example.tuberculosis.base.CustomRepostory;
import com.example.tuberculosis.domain.entity.PatientUser;
import com.example.tuberculosis.domain.entity.User;

import java.util.List;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/29 21:06
 */
public interface PatientUserDao extends CustomRepostory<PatientUser,Integer> {
    public PatientUser findByDoctorNumAndPatientNumAndDelFlag(Integer doctorNum, Integer patientNum,Integer delFlag);
    public List<PatientUser> findByDoctorNumAndDelFlag(Integer doctorNum,Integer delFlag);
}
