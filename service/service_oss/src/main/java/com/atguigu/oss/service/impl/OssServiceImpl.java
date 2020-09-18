package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;

@Service
public class OssServiceImpl implements OssService {

    //上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // 1、工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try{
            // 2、创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 3、获取上传文件输入流。
            InputStream inputStream = file.getInputStream();

            // 5、获取文件名称
            String fileName = file.getOriginalFilename();

            // 4、调用oss方法实现上传
            // 第一个参数，Bucket名称
            // 第二个参数，上传到oss文件路径和文件名称  /aa/bb/1.jpg
            // 第三个参数，上传文件输入流
            ossClient.putObject(bucketName, fileName, inputStream);

            // 6、关闭OSSClient。
            ossClient.shutdown();

            // 把上传之后文件路径返回
            // 需要把上传到阿里云oss路径手动拼接出来
            // https://edu-guli-1010.oss-us-west-1.aliyuncs.com/01.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;

        }catch (Exception e){
            return null;
        }
    }
}
