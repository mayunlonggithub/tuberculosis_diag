package com.example.tuberculosis.dao;

import com.example.tuberculosis.base.CustomRepostory;
import com.example.tuberculosis.domain.entity.PatientUser;
import com.example.tuberculosis.domain.entity.User;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/29 21:06
 */
public interface PatientUserDao extends CustomRepostory<PatientUser,Integer> {
    public PatientUser findByDoctorNumAndPatientNumAndDelFlag(Integer doctorNum, Integer patientNum,Integer delFlag);
}
