package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 通用街口
 */
@RestController
@RequestMapping("/admin/common")
@Api("通用接口")
@Slf4j
public class CommonController {

    @Value("${project.path}")
    private String path;

    @PostMapping("/upload")
    @ApiOperation("文件上傳 ")
    public Result<String> upload(MultipartFile file) {
        //MultipartFile為springboot 封裝appache的common-io的lib
        //此文件近來為一臨時文件，需要轉存到指定位子
        log.info("文件上傳", file.toString());

        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        //使用uuid防止文件名稱重複

        File dir = new File(path);
        //判斷當前目錄是否存在
        if (!dir.exists()) {
            //目錄不存在需要創目錄
            dir.mkdirs();
        }

        String newFilename = UUID.randomUUID().toString() + suffix;
        try {
            //將臨時文件轉存到以下路徑
            file.transferTo(new File(path + newFilename));

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        return Result.success(newFilename);
    }
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        //輸入流，通過輸入流讀取文件內容
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            fileInputStream= new FileInputStream(new File(path+name));
            //輸出流，通過輸出流將文件寫回瀏覽器，在瀏覽器展示圖片
            outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len=0;
            byte[] bytes = new byte[1024];

            while ((len = fileInputStream.read(bytes))!= -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                fileInputStream.close();
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //輸出流，通過輸出流將文件寫回瀏覽器，在瀏覽器展示圖片

    }


}


