package com.example.ims_20200716.controller;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@RestController
public class UploadFileController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("reportTTT")
    public String uploadFile(HttpServletRequest request){
        System.out.println(request.getHttpServletMapping());
        //创建一个解析器工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //得到解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("utf-8");
        try {
            List<FileItem> list=upload.parseRequest(request);
            System.out.println("1111");
            System.out.println(list.size());
            for(FileItem item : list){
                System.out.println("2222");
                //判断输入的类型
                if (item.isFormField()) {
                    System.out.println("3333");
                    //普通输入项
                    String inputName = item.getFieldName();
                    String inputValue = item.getString();
                    System.out.println(inputName);
                    System.out.println(inputValue);
                }else {
                    System.out.println("4444");
                    //上传文件输入项
                    String filename = item.getName();//上传文件的文件名
                    System.out.println(filename);
                    InputStream is=item.getInputStream();
                    //拼装输出路径
                    String outpath = request.getSession().getServletContext().getRealPath("/")+"img/user/"+filename;

                    FileOutputStream fos=new FileOutputStream(outpath);
                    byte[] buff=new byte[1024];
                    while((is.read(buff))>0){
                        fos.write(buff);
                    }
                    is.close();
                    fos.close();
                }
            }
        } catch (FileUploadException | IOException e) {

        }


        return "666";

    }

    /*
    public String uploadFile(@RequestParam(value = "files") List<MultipartFile> files, @RequestParam(value = "inputGroupName") String inputGroupName){
      try{
        if (files.isEmpty()){
          return "File is empty";
        }

        for(MultipartFile multipartFile : files){
            String fileName = multipartFile.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String filePath = "D:\\IMS\\file\\";
            String path = filePath + fileName;
            File dest = new File(path);
            System.gc();
            if (dest.isFile() && dest.exists()) { //判断文件是否存在
                Boolean a = dest.delete();
                if (a) {
                    System.out.println("删除成功！");
                } else {
                    System.out.println("删除失败！");
                }
            }
            // 检测路径是否存在
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            // 文件写入
            multipartFile.transferTo(dest);


            // 文件入库

            try {
                String encoding = "GBK";
                File file = new File("D:\\IMS\\file\\定向客群.txt");
                if (file.isFile() && file.exists()) { //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                            new FileInputStream(file), encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    String sql = "insert into ";
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        //System.out.println(lineTxt);
                    }
                    read.close();
                    bufferedReader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return "upload success";
      } catch (Exception e) {
        e.printStackTrace();
      }

        return "666";
    }*/

}
