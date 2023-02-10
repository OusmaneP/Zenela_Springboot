package com.podosoft.zenela.Services.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.podosoft.zenela.Services.FileService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final AmazonS3Client awsS3Client;

    public FileServiceImpl(AmazonS3Client awsS3Client) {
        this.awsS3Client = awsS3Client;
    }

    private final String bucketName = "zenelabucketv1";

    @Override
    public String uploadFile(MultipartFile file) {

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        String key = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "." + extension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try{
            awsS3Client.putObject(bucketName, key, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occured while uploading the file");
        }

        awsS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

        return awsS3Client.getResourceUrl(bucketName, key);
    }

    @Override
    public boolean deleteFile(String url) {
        String[] tabString = url.split("s3.amazonaws.com/");
        if (tabString[1] != null){
            String key = tabString[1];
            try{
                awsS3Client.deleteObject(bucketName, key);
                return true;
            } catch (SdkClientException e) {
                return false;
            }
        }
        else {
            return false;
        }

    }

    @Override
    public String uploadVideoThumb(MultipartFile file) {

        String key = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + ".png";

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try{
            awsS3Client.putObject(bucketName, key, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occured while uploading the file");
        }

        awsS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

        return awsS3Client.getResourceUrl(bucketName, key);
    }

    @Override
    public String createUploadThumbnail(MultipartFile multipartFile) {
        String name = multipartFile.getName();
        try {
            InputStream is = multipartFile.getInputStream();

            // Create thumbnail from original image
            BufferedImage thumb;
            thumb = Thumbnails.of(is).size(200, 200).asBufferedImage();

            // Write the thumbnail data to a byte array that will be stored in S3 bucket as an object
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumb, "jpg", baos);

            // Build metadata for the file object to be sent to the S3 bucket
            ObjectMetadata omData = new ObjectMetadata();
            Map<String, String> userMetaDataOfThumb = new HashMap<>();

            int fileSizeInBytesOfThumb = baos.toByteArray().length;  //  size of your file in bytes

            userMetaDataOfThumb.put("thumbnail", "true");      //Optional key value pairs to store with thumnail object

            omData.setUserMetadata(userMetaDataOfThumb);   //Add optional meta data to the request params

            omData.setContentLength(fileSizeInBytesOfThumb);    //Set Content Length of the file

            //perform Upload to AWS S3 Bucket and return URL location

            String key = "thumb_" + UUID.randomUUID() + "_" + System.currentTimeMillis() + ".jpg";

//                metadata.setContentType(file.getContentType());

            awsS3Client.putObject(bucketName, key, new ByteArrayInputStream(baos.toByteArray()), omData);

            awsS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

            return awsS3Client.getResourceUrl(bucketName, key);


//                AWSCredentials credentials = new BasicAWSCredentials("MyKeyID", "MySecretKey");
//
//                AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion("us-west-1").build();
//                s3client.putObject("myS3BucketName", name + ".jpg ", baos, omData);
//                System.out.println("------Uploaded successfully---------");
//                S3Object o = s3client.getObject("myS3BucketName", name + ".jpg");
//                System.out.println("Downloading an object " + o);
//                System.out.println("Retrieved URL-----" + o.getObjectContent().getHttpRequest().getURI().toURL());
//                String thumbnailUrl = o.getObjectContent().



        }catch (Exception e){
            System.out.println("Error Creating thumbnail");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String uploadPhotoFile(MultipartFile multipartFile) {

        String name = multipartFile.getName();

        try {
            InputStream is = multipartFile.getInputStream();

            // Reduce the original image
            BufferedImage thumb;
            thumb = Thumbnails.of(is).scale(1).asBufferedImage();

            // Write the thumbnail data to a byte array that will be stored in S3 bucket as an object
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumb, "jpg", baos);

            // Build metadata for the file object to be sent to the S3 bucket
            ObjectMetadata omData = new ObjectMetadata();
            Map<String, String> userMetaDataOfThumb = new HashMap<>();

            int fileSizeInBytesOfThumb = baos.toByteArray().length;  //  size of your file in bytes

            userMetaDataOfThumb.put("image", "true");      //Optional key value pairs to store with thumnail object

            omData.setUserMetadata(userMetaDataOfThumb);   //Add optional meta data to the request params

            omData.setContentLength(fileSizeInBytesOfThumb);    //Set Content Length of the file

            String key = "img_zen_" + UUID.randomUUID() + "_" + System.currentTimeMillis() + ".jpg";

            //perform Upload to AWS S3 Bucket and return URL location
            awsS3Client.putObject(bucketName, key, new ByteArrayInputStream(baos.toByteArray()), omData);

            awsS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

            return awsS3Client.getResourceUrl(bucketName, key);

        }catch (Exception e){
            System.out.println("Error Reducing image size");
            e.printStackTrace();
        }

        return null;
    }
}