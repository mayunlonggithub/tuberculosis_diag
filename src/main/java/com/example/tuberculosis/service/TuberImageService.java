package com.example.tuberculosis.service;

import com.example.tuberculosis.base.PageResult;
import com.example.tuberculosis.base.Paging;
import com.example.tuberculosis.domain.dto.TuberImageForm;
import com.example.tuberculosis.domain.dto.UserForm;
import com.example.tuberculosis.domain.entity.TuberImage;
import com.example.tuberculosis.domain.entity.User;
import com.example.tuberculosis.domain.enums.BaseValue;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/29 18:38
 */
public interface TuberImageService {
    /**
     * 上传图片
     * @param doctorNum 医生账号
     * @param patientNum 病人账号
     * @param file 上传的文件
     * @return 成功的id
     * @throws IOException
     */
    void createOrUpdate(Integer doctorNum, Integer patientNum, MultipartFile file,String diagnosisRecord) throws IOException;

    /**
     * 模型运行，调用python
     * @param id 图片id
     */
//    void run(Integer id);

    /**
     * 根据医生账号和病患账号获取患者记录
     * @param doctorNum 医生账号
     * @param patientNum 病患账号
     * @return url的列表
     */
    List<BaseValue> getRecord(Integer tuberImageId);

    public void deleteImage(Integer imageId);

    /**
     * 返回结果图片url
     * @param doctorNum 医生账号
     * @param patientNum 病人账号
     * @return url的列表
     */
//    List<String> getResultRecords(Integer doctorNum, Integer patientNum);

    /**
     * @Title getList
     * @Description 获取用户分页列表
     * @return
     */
    public PageResult<TuberImageForm.User> getList(Paging paging, List<String> queryString, List<String> orderBys,User kUser);

    public void updateTuberImage(TuberImageForm.UpdateTuberImage updateTuberImage);

    public Integer getAllTuberImage();

    public TuberImageForm.User getDetail(Integer tuberImageId) ;

}
