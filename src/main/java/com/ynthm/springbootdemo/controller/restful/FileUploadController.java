package com.ynthm.springbootdemo.controller.restful;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynthm.springbootdemo.utils.HttpClientUtil;
import com.ynthm.springbootdemo.utils.ImgBase64Util;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author : Ynthm
 */
@RestController
@Slf4j
public class FileUploadController {

    private final String ROOT = "/upload";
    private final String PROJECT_RESOURCE = "./src/main/resources/images";

    @Value("${upload.dir}")
    private String path;

    @ApiOperation(value = "上传文件到本地")
    @ApiParam(name = "file", value = "文件", required = true)
    @PostMapping(value = "/upload/local")
    public void uploadLocal(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try (InputStream is = file.getInputStream()) {
                // 文件路径最好只建立一次
                if(Files.notExists(Paths.get(path)))
                {
                    Files.createDirectories(Paths.get(path));
                }
                log.info("path is {}", Paths.get(path, file.getOriginalFilename()));
                Files.copy(is, Paths.get(path, file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                log.error("copy file failed.", e);
            }
        }
    }

    @PostMapping(value = "/uploads")
    public void handleFileUpload(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                System.out.println("文件未上传");
            } else {
                try {
                    Files.copy(file.getInputStream(), Paths.get(path, file.getOriginalFilename()));
                } catch (IOException | RuntimeException e) {

                }
            }
        }
    }

    @GetMapping(value = "/files/{filename:.+}")
    public ResponseEntity<?> getFile(@PathVariable String filename) {

        try {
            return ResponseEntity.ok(Paths.get(path, filename).toString());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping(value = "/upload")
    public String handleFileUpload(@RequestParam String name, @RequestParam String idNumber, @RequestParam MultipartFile file) {
        if (StringUtils.isBlank(name)) {
            return "请输入姓名!";
        }
        if (file.isEmpty()) {
            return "请选择文件";
        } else {
            try {
                // Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));


                String url = "http://r.micropattern.com/micropatternImageRecognition/serviceAlgForBase64";
                Map<String, String> bodys = new HashMap<>();
                //Path path = Paths.get("src/test/resources/me.jpg");

                ImgBase64Util imgUtil = new ImgBase64Util();

                bodys.put("apiKey", "ffba201f-4644-4a7d-bb06-4bca77678c07");
                bodys.put("serviceType", "4003");
                bodys.put("responseType", "0");
                bodys.put("name", name);
                bodys.put("idCardNum", idNumber);
                bodys.put("imgBase64", imgUtil.base64Encoding(file.getBytes()));
                return HttpClientUtil.instance().httpPostForm(url, bodys);


            } catch (IOException | RuntimeException e) {

            }
        }

        return "some error happened.";
    }


    @ApiOperation(value = "身份证ORC", notes = "通过OCR识别身份证的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "身份证正面", required = true, dataType = "File"),
            @ApiImplicitParam(name = "file1", value = "身份证反面", required = false, dataType = "File")
    })
    @PostMapping(value = "/idocr")
    public String idCardOcr(@RequestParam("file") MultipartFile file, @RequestParam("file1") MultipartFile fileBack) {

        if (file.isEmpty()) {
            return "请至少上传身份证正面";
        } else {
            try {
                String url = "http://r.micropattern.com/micropatternImageRecognition/serviceAlgForBase64";
                Map<String, String> bodys = new HashMap<>();

                bodys.put("apiKey", "ffba201f-4644-4a7d-bb06-4bca77678c07");
                bodys.put("serviceType", "3");
                bodys.put("responseType", "0");

                bodys.put("imgName", "aaa");

                ImgBase64Util imgUtil = new ImgBase64Util();
                bodys.put("imgBase64", imgUtil.base64Encoding(file.getBytes()));
                if (!fileBack.isEmpty()) {
                    bodys.put("imgBase64_1", imgUtil.base64Encoding(fileBack.getBytes()));
                }

                return HttpClientUtil.instance().httpPostForm(url, bodys);


            } catch (IOException | RuntimeException e) {

            }
        }

        return "some error happened.";
    }


    @PostMapping(value = "/human")
    public String humanCheck(@RequestParam("files") MultipartFile[] files) throws IOException {

        ImgBase64Util imgUtil = new ImgBase64Util();
        List<String> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            } else {
                fileList.add(imgUtil.base64Encoding(file.getBytes()));
            }
        }

        if (fileList.size() < 0) {
            return "请上传至少一个文件.";
        }

        try {
            String url = "http://r.micropattern.com/micropatternImageRecognition/serviceAlgForBase64";
            Map<String, String> bodys = new HashMap<>();

            bodys.put("apiKey", "ffba201f-4644-4a7d-bb06-4bca77678c07");
            bodys.put("serviceType", "11");
            bodys.put("responseType", "0");

            bodys.put("imgBase64", new ObjectMapper().writeValueAsString(fileList));


            return HttpClientUtil.instance().httpPostForm(url, bodys);


        } catch (RuntimeException e) {

        }

        return "some error happened.";
    }

}
