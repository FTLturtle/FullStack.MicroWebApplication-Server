package JavaLamps.ZipTube.services.implementations;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AmazonS3ClientServiceImplementationTest {

    private AmazonS3ClientServiceImplementation amazonS3ClientServiceImplementation;

    @Mock
    private AmazonS3 amazonS3;

    @Mock
    private TransferManager transferManager;

    private String awsS3AudioBucket = "awsS3BucketName";

    @Before
    public void before() {
        amazonS3ClientServiceImplementation = new AmazonS3ClientServiceImplementation(amazonS3, awsS3AudioBucket, transferManager);
    }

    @Test
    public void uploadFileToS3Bucket() {
        PutObjectResult putObjectResult = mock(PutObjectResult.class);
        Upload mockUpload = mock(Upload.class);

        when(amazonS3.putObject(any(PutObjectRequest.class))).thenReturn(putObjectResult);
        when(transferManager.upload(any(PutObjectRequest.class))).thenReturn(mockUpload);

        MockMultipartFile mockMultipartFile = new MockMultipartFile("some name", "some original file name", "text/plain", "some file data".getBytes());
        amazonS3ClientServiceImplementation.uploadFileToS3Bucket(mockMultipartFile, "some file name", true);

        verify(amazonS3, times(1)).putObject(any(PutObjectRequest.class));
        verify(transferManager, times(1)).upload(any(PutObjectRequest.class));
    }

    @Test
    public void deleteFileFromS3Bucket() {
        String fileName = "a filename";
        amazonS3ClientServiceImplementation.deleteFileFromS3Bucket(fileName);
        verify(amazonS3, times(1)).deleteObject(any(DeleteObjectRequest.class));
    }

    @Test
    public void getFileUrl() throws MalformedURLException {
        // Given
        String fileName = "a filename";
        String expectedUrlString = "https://someurl.com";
        URL url = new URL(expectedUrlString);
        when(amazonS3.getUrl(awsS3AudioBucket, fileName)).thenReturn(url);

        // When
        String actualUrlString = amazonS3ClientServiceImplementation.getFileUrl(fileName);

        // Then
        Assert.assertEquals(expectedUrlString, actualUrlString);
        verify(amazonS3, times(1)).getUrl(awsS3AudioBucket, fileName);
    }
}