package JavaLamps.ZipTube.services;

import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

public interface AmazonS3ClientService
{
    void uploadFileToS3Bucket(MultipartFile multipartFile, String fileName, boolean enablePublicReadAccess);

    void deleteFileFromS3Bucket(String fileName);

    String getFileUrl(String key);
}