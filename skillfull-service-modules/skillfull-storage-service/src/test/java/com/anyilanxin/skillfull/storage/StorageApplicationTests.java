// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.storage;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

@SpringBootTest
class StorageApplicationTests {
    @Autowired
    private OSSClient ossClient;

    @Test
    void contextLoads() throws FileNotFoundException {
        String bucketName = "skillfulcloud";
        String localFile = "/Users/zxiaozhou/Pictures/Wallbot-kUhy-JBPtmA.png";
        String fileKeyName = "coding_java.png";
        InputStream inputStream = new FileInputStream(localFile);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentDisposition("attachment; filename=coding_java.png");

        PutObjectResult putObjectResult = ossClient.putObject(bucketName, "lsjdfkdflsdkflsf.png", inputStream, meta);

        System.out.println("---putObjectResult---" + putObjectResult);
        ossClient.shutdown();
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(bucketName, "lsjdfkdflsdkflsf.png", expiration).toString();
        System.out.println("----url------" + url);
    }

}
