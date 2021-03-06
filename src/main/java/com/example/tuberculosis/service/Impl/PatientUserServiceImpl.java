package com.example.tuberculosis.service.Impl;

import com.example.tuberculosis.base.BeanPropertyCopyUtils;
import com.example.tuberculosis.base.PageResult;
import com.example.tuberculosis.base.Paging;
import com.example.tuberculosis.dao.PatientUserDao;
import com.example.tuberculosis.dao.UserDao;
import com.example.tuberculosis.domain.dto.PatientUserForm;
import com.example.tuberculosis.domain.entity.PatientUser;
import com.example.tuberculosis.domain.entity.User;
import com.example.tuberculosis.service.PatientInfoService;
import com.example.tuberculosis.service.UserService;
import com.example.tuberculosis.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/29 21:31
 */
@Service
public class PatientUserServiceImpl implements PatientInfoService {
    @Autowired
    private PatientUserDao patientInfDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Override
    @Transactional
    public void addPatientInfo(PatientUserForm.AddPatient addPatient, Integer uId) {
        PatientUser patientUser = patientInfDao.findByDoctorNumAndPatientNumAndDelFlag(uId,addPatient.getPatientNum(),1);
        Assert.isNull(patientUser,"该病人已添加");
        PatientUser patientInf = BeanPropertyCopyUtils.copy(addPatient, PatientUser.class);
        patientInf.setDoctorNum(uId);
        patientInf.setDelFlag(1);
        patientInfDao.save(patientInf);
    }

    @Override
    @Transactional
    public void deletePatientInfo(Integer patientId) {
        Optional<PatientUser> opPatientInf = patientInfDao.findById(patientId);
        PatientUser patientInf = opPatientInf.get();
        patientInf.setDelFlag(0);
        patientInfDao.save(patientInf);
    }

    @Override
    @Transactional
    public void updatePatientInfo(PatientUserForm.UpdatePatient updatePatient) {
        Optional<PatientUser> OpatientInf=patientInfDao.findById(updatePatient.getId());
        PatientUser patientInf1=OpatientInf.get();
        PatientUser patientInf = BeanPropertyCopyUtils.copy(updatePatient, PatientUser.class);
        patientInf.setDelFlag(1);
        patientInf.setPatientNum(patientInf1.getPatientNum());
        patientInf.setDoctorNum(patientInf1.getDoctorNum());
        patientInfDao.save(patientInf);
    }

    @Override
    public PageResult<PatientUserForm.Patient> getList(Paging paging, List<String> queryString, List<String> orderBys, User kUser ){
        queryString.add("delFlag~Eq~1");
        if(kUser.getRoleType()== Constant.DOCTOR) {
            queryString.add("doctorNum~Eq~"+kUser.getId());
        }
        PageResult<PatientUser> patientInfList = patientInfDao.findAll(paging,queryString,orderBys);
        List<PatientUser> patientUserList = patientInfList.getContent();
        List<PatientUserForm.Patient> patients = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(PatientUser patientUser : patientUserList) {
            PatientUserForm.Patient patient = new PatientUserForm.Patient();
            User user = userDao.getOne(patientUser.getPatientNum());
            patient.setId(patientUser.getId());
            patient.setNicekName(user.getNickname());
            patient.setAge(user.getAge());
            patient.setGender(user.getGender());
            patient.setDescription(patientUser.getDescription());
            patient.setJudge(patientUser.getJudge());
            patient.setEmployeeId(user.getEmployeeId());
            patient.setCreateTime(patientUser.getCreateTime());
            patient.setEmail(user.getEmail());
            patient.setPhone(user.getPhone());
            patient.setAccount(user.getAccount());
            patients.add(patient);
        }
        PageResult<PatientUserForm.Patient> patientPage = new PageResult<>();
        patientPage.setTotal(patientInfList.getTotal());
        patientPage.setPageIndex(patientInfList.getPageIndex());
        patientPage.setLimit(patientInfList.getLimit());
        patientPage.setContent(patients);
        return patientPage;
    }

    @Override
    public PatientUserForm.Patient getDetail(Integer patientId) {
        PatientUser patientUser = patientInfDao.getOne(patientId);
        PatientUserForm.Patient patient = new PatientUserForm.Patient();
        User user = userDao.getOne(patientUser.getPatientNum());
        patient.setId(patientUser.getId());
        patient.setNicekName(user.getNickname());
        patient.setAge(user.getAge());
        patient.setGender(user.getGender());
        patient.setDescription(patientUser.getDescription());
        patient.setJudge(patientUser.getJudge());
        patient.setEmployeeId(user.getEmployeeId());
        patient.setCreateTime(patientUser.getCreateTime());
        patient.setEmail(user.getEmail());
        patient.setPhone(user.getPhone());
        patient.setAccount(user.getAccount());
        return patient;
    }

}
