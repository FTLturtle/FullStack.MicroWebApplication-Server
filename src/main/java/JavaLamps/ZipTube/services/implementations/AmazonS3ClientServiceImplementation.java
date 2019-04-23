package JavaLamps.ZipTube.services.implementations;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import JavaLamps.ZipTube.services.AmazonS3ClientService;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class AmazonS3ClientServiceImplementation implements AmazonS3ClientService {
    private String awsS3AudioBucket;
    private AmazonS3 amazonS3;
    private static final Logger logger = LoggerFactory.getLogger(AmazonS3ClientServiceImplementation.class);
    private TransferManager transferManager;

    @Autowired
    public AmazonS3ClientServiceImplementation(AmazonS3 amazonS3, String awsS3AudioBucket, TransferManager transferManager) {
        this.amazonS3 = amazonS3;
        this.awsS3AudioBucket = awsS3AudioBucket;
        this.transferManager = transferManager;
    }

    public void uploadFileToS3Bucket(MultipartFile multipartFile, String fileName, boolean enablePublicReadAccess) {
        try {
            //creating the file in the server (temporarily)
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

            //uploading file to amazon s3 bucket
            PutObjectRequest putObjectRequest = new PutObjectRequest(awsS3AudioBucket, fileName, file);
            if (enablePublicReadAccess) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            amazonS3.putObject(putObjectRequest);

            // wait for completion so that the url will be available for saving to the database
            Upload upload = transferManager.upload(putObjectRequest);
            upload.waitForCompletion();

            //removing the file created in the server
            file.delete();
        } catch (IOException | AmazonServiceException | InterruptedException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
        }
    }

    @Async
    public void deleteFileFromS3Bucket(String fileName) {
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, fileName));
        } catch (AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + fileName + "] ");
        }
    }

    public String getFileUrl(String key) {
        return amazonS3.getUrl(awsS3AudioBucket, key).toString();
    }
}