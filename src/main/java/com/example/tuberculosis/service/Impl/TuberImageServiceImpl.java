package com.example.tuberculosis.service.Impl;

import com.example.tuberculosis.base.PageResult;
import com.example.tuberculosis.base.Paging;
import com.example.tuberculosis.common.FileAccessHelper;
import com.example.tuberculosis.dao.TuberImageDao;
import com.example.tuberculosis.dao.UserDao;
import com.example.tuberculosis.domain.dto.PatientUserForm;
import com.example.tuberculosis.domain.dto.TuberImageForm;
import com.example.tuberculosis.domain.entity.TuberImage;
import com.example.tuberculosis.domain.entity.User;
import com.example.tuberculosis.domain.enums.BaseValue;
import com.example.tuberculosis.service.TuberImageService;
import com.example.tuberculosis.utils.Constant;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/29 18:49
 */
@Service
public class TuberImageServiceImpl implements TuberImageService {

    private static Logger logger = LoggerFactory.getLogger(TuberImageServiceImpl.class);

    @Autowired
    private FileAccessHelper fileAccessHelper;
    @Autowired
    private TuberImageDao tuberImageDao;
    @Autowired
    private UserDao userDao;


    private final String path_cmd = "/home/pi/Documents/Unet-CT/testUnet1022.py" ;
    private final String path_save = "/home/pi/Documents/data/" ;


    @Override
    public void createOrUpdate(Integer doctorNum, Integer patientNum, MultipartFile file,String diagnosisRecord) throws IOException {
        Map<File, MultipartFile> toSave = new HashMap<>(1);
        String fileName = file.getOriginalFilename();
        if (null != fileName) {
                fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
            }
            TuberImage image = new TuberImage();
            image.setDoctorNum(doctorNum);
            image.setPatientNum(patientNum);
            image.setImageName(fileName);
            image.setDelFlag(1);
            image.setRelativePath(fileAccessHelper.buildRelativePath(doctorNum, patientNum, fileName));
            image.setDiagnosisRecord(diagnosisRecord);
            tuberImageDao.save(image);
            //本机数据保存
            File path = new File(fileAccessHelper.buildStorePath(image.getRelativePath()));
            if (!path.getParentFile().exists()) {
                path.getParentFile().mkdirs();
            }
            toSave.put(path, file);
        for (Map.Entry<File, MultipartFile> entry : toSave.entrySet()) {
            FileUtils.writeByteArrayToFile(entry.getKey(), entry.getValue().getBytes());
        }
        return;
    }

//    @Override
//    public void run(Integer id) {
//        Optional<TuberImageForm> opImage = tuberImageDao.findById(id);
//        TuberImageForm image=opImage.get();
//        GeneralExecutor general = null;
//        try {
//            general = DefaultExecutorFactory.getExecutor(GeneralExecutor.class);
//            String path = path_save + image.getRelativePath();
//            general.execute("python", path_cmd, path);
//        } catch (ExecutorNotFoundExecption | ExecuteException executorNotFoundExecption) {
//            logger.info("{}执行出错", general);
//        }
//    }

    @Override
    public List<BaseValue> getRecord(Integer tuberImageId) {
        List<BaseValue> list = new ArrayList<>();
        TuberImage image = tuberImageDao.getOne(tuberImageId);
        Integer id=image.getId();
        String url = fileAccessHelper.buildHttpUrl(image.getRelativePath(), image.getCreateTime());
        BaseValue baseValue=new BaseValue();
        baseValue.setKey(String.valueOf(id));
        baseValue.setValue(url);
        list.add(baseValue);
        return list;
    }


    @Override
    @Transactional
    public void deleteImage(Integer imageId) {
        Optional<TuberImage> optionalTuberImage = tuberImageDao.findById(imageId);
        TuberImage tuberImage=optionalTuberImage .get();
        tuberImage.setDelFlag(0);
        tuberImageDao.save(tuberImage);
    }
//
//    @Override
//    public List<String> getResultRecords(Integer doctorNum, Integer patientNum) {
//        List<String> list = new ArrayList<>();
//        List<TuberImageForm> images =tuberImageDao.findByDoctorNumAndPatientNum(doctorNum, patientNum);
//        for (TuberImageForm  image : images) {
//            String name = image.getRelativePath().substring(0, image.getRelativePath().lastIndexOf("."));
//            String jpg = image.getRelativePath().substring(image.getRelativePath().lastIndexOf("."));
//            String relativePath = name + "_result" + jpg;
//            String url = fileAccessHelper.buildHttpUrl(relativePath, image.getCreateTime());
//            list.add(url);
//        }
//        return list;
//    }

    public PageResult<TuberImageForm.User> getList(Paging paging, List<String> queryString, List<String> orderBys,User kUser) {
        queryString.add("delFlag~Eq~1");
        if(kUser.getRoleType()== Constant.DOCTOR) {
            queryString.add("doctorNum~Eq~"+kUser.getId());
        }
        if(kUser.getRoleType()== Constant.PATIENT) {
            queryString.add("patientNum~Eq~"+kUser.getId());
        }
        PageResult<TuberImage> tuberImagePage = tuberImageDao.findAll(paging,queryString,orderBys);
        List<TuberImage> tuberImageList = tuberImagePage.getContent();
        List<TuberImageForm.User> userList = new ArrayList<>();
        for(TuberImage tuberImage1 : tuberImageList) {
            TuberImageForm.User tuberUser = new TuberImageForm.User();
            User doctorUser = userDao.getOne(tuberImage1.getDoctorNum());
            User patientUser = userDao.getOne(tuberImage1.getPatientNum());
            tuberUser.setId(tuberImage1.getId());
            tuberUser.setCreateTime(tuberImage1.getCreateTime());
            tuberUser.setEmployeeId(doctorUser.getEmployeeId());
            tuberUser.setNickname(patientUser.getNickname());
            tuberUser.setAccount(patientUser.getAccount());
            tuberUser.setDiagnosisRecord(tuberImage1.getDiagnosisRecord());
            userList.add(tuberUser);
        }
        PageResult<TuberImageForm.User> tuberPage = new PageResult<>();
        tuberPage.setTotal(tuberImagePage.getTotal());
        tuberPage.setPageIndex(tuberImagePage.getPageIndex());
        tuberPage.setLimit(tuberImagePage.getLimit());
        tuberPage.setContent(userList);
        return tuberPage;
    }

    public TuberImageForm.User getDetail(Integer tuberImageId) {
        TuberImage tuberImage1 = tuberImageDao.getOne(tuberImageId);
        TuberImageForm.User tuberUser = new TuberImageForm.User();
        User doctorUser = userDao.getOne(tuberImage1.getDoctorNum());
        User patientUser = userDao.getOne(tuberImage1.getPatientNum());
        tuberUser.setId(tuberImage1.getId());
        tuberUser.setCreateTime(tuberImage1.getCreateTime());
        tuberUser.setEmployeeId(doctorUser.getEmployeeId());
        tuberUser.setNickname(patientUser.getNickname());
        tuberUser.setAccount(patientUser.getAccount());
        tuberUser.setDiagnosisRecord(tuberImage1.getDiagnosisRecord());
        return tuberUser;

    }

    public void updateTuberImage(TuberImageForm.UpdateTuberImage updateTuberImage) {
        Integer tuberId = updateTuberImage.getId();
        TuberImage tuberImage = tuberImageDao.getOne(tuberId);
        tuberImage.setDiagnosisRecord(updateTuberImage.getDiagnosisRecord());
        tuberImageDao.save(tuberImage);
    }

    public Integer getAllTuberImage() {
        List<TuberImage> tuberImageList =tuberImageDao.findByDelFlag(1);
        return tuberImageList.size();
    }
}
