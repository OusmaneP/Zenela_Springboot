package com.podosoft.zenela.Services.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.podosoft.zenela.Services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
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
//            BufferedImage originalImage = ImageIO.read(file.getInputStream());
//            BufferedImage thumbnail = Thumbnails.of(originalImage)
//                    .size(100, 100)
//                    .keepAspectRatio(false)
//                    .asBufferedImage();
//
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//
//            assert extension != null;
//            ImageIO.write(thumbnail, extension, os);

//            InputStream is = new ByteArrayInputStream(os.toByteArray());

//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            Thumbnails.of(file.getInputStream())
//                            .size(200, 200)
//                                    .outputFormat(extension)
//                                            .toOutputStream(outputStream);


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
    public String uploadThumb(MultipartFile file) {

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
}
