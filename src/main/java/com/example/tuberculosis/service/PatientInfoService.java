package com.example.tuberculosis.service;

import com.example.tuberculosis.base.PageResult;
import com.example.tuberculosis.base.Paging;
import com.example.tuberculosis.domain.dto.PatientUserForm;
import com.example.tuberculosis.domain.entity.PatientUser;
import com.example.tuberculosis.domain.entity.User;

import java.util.List;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/29 21:30
 */
public interface PatientInfoService {
   void addPatientInfo(PatientUserForm.AddPatient addPatient, Integer uId);
   void deletePatientInfo(Integer patientId);
   void updatePatientInfo(PatientUserForm.UpdatePatient updatePatient);
   PageResult<PatientUserForm.Patient> getList(Paging paging, List<String> queryString, List<String> orderBys,User kUser);
   PatientUserForm.Patient getDetail(Integer patientId);

}
