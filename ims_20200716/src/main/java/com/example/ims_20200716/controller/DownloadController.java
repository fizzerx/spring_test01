package com.example.ims_20200716.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@WebServlet("report")
public class DownloadController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        List<MultipartFile> fileList = multipartRequest.getFiles("file");
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        if(fileMap == null || fileMap.size() == 0){
            System.out.println("请上传文件,注意文件的name属性为file");
        }
        Collection<MultipartFile> files = fileMap.values();
        for(MultipartFile file:files){
            /**
             注意这里获取的个数，是根据前端form表单里有几个，type=file来决定的，
             比如你虽然只点选了一个文件，上传，但这里实际上回有两个MultipartFile对 象，只不过其中一个MutipartFile的文件名会为空，这就是为什么下边会有个判断文件名
             为空的步骤**/
            String req= file.getOriginalFilename();

            File tempFile = getTmpFile(req);
            System.out.println(tempFile.getAbsolutePath());
            if(!tempFile.exists()){
                tempFile.getParentFile().mkdirs();
                try {
                    tempFile.createNewFile();
                    file.transferTo(tempFile); //到这里tempFile即是上传上来的文件。
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(req);
        }

    }
    public File getTmpFile(String fileName) {
        File tmpDir = FileUtils.getTempDirectory();
        System.out.println("========"+tmpDir.getAbsolutePath());
        String tmpFileName = (Math.random() * 10000 + "").replace(".", "")+"_"+fileName;
        return new File(tmpDir, tmpFileName);
    }
}
