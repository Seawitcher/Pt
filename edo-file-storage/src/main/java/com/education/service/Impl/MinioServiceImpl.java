package com.education.service.Impl;

import com.education.utils.MinioUtil;
import com.education.service.MinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
@Log4j2
public class MinioServiceImpl implements MinioService {

    private MinioClient minioClient;
    private MinioUtil util;

    /**
     * Method creates bucket named "my-bucketname" if it not exists
     * and set versioning akso.
     */
    @PostConstruct
    public void existBucket() {
        try {
            log.info("creating new bucket", LocalTime.now());
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(util.getMyBucketname()).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(util.getMyBucketname()).build());
            }
            log.info("my-bucketname is created successfully", LocalTime.now());
        } catch (MinioException e) {
            System.out.println("checking for bucket`s existence : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteObjects(String objectNumber) {
        try {
            //** Lists objects information. */
            minioClient.removeObject(RemoveObjectArgs.
                    builder()
                    .bucket(util.getMyBucketname())
                    .object(objectNumber)
                    .build());
        } catch (MinioException e) {
            System.out.println("Delete failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void checkConnection() {
        try {
            log.info("Starting connection to MINIO server", LocalTime.now());
            List<Bucket> blist = minioClient.listBuckets();
            log.info("Connection success, total buckets: " + blist.size());
        } catch (MinioException e) {
            System.out.println("Connectionfailed for AKhmed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //** Upload 'my-objectname' from 'targetfolder' to 'sourcefolder' */ 
    @Override
    public void uploadOneFile(String objectName) {
        try {
            log.info("Starting to uploading", LocalTime.now());
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(util.getMyBucketname())
                            .object(objectName)
                            .filename(util.getForUploadFolder() + objectName)
                            .build());
            log.info("File is successfully uploaded to " + util.getForUploadFolder());
        } catch (MinioException e) {
            System.out.println("Upload failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //** Download 'my-objectname' from 'my-bucketname' to 'targetfolder' */
    @Override
    public void downloadOneFile(String objectName) {

        try {
            log.info("Starting to download file: " + objectName);
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket(util.getMyBucketname())
                            .object(objectName)
                            .filename(util.getForDownloadingFolder() + objectName)
                            .build());
            log.info("File is successfully downloaded to " + util.getForDownloadingFolder());
        } catch (MinioException e) {
            System.out.println("Upload failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
