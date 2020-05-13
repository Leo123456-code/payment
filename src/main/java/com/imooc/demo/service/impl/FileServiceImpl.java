package com.imooc.demo.service.impl;

import com.google.common.collect.Lists;
import com.imooc.demo.service.IFileService;
import com.imooc.demo.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * ClassName: FileServiceImpl
 * Description: TODO文件处理
 * Author: Leo
 * Date: 2020/3/12-11:35
 * email 1437665365@qq.com
 */
@Slf4j
@Service
public class FileServiceImpl implements IFileService {
    
    @Override
    public String upload(MultipartFile file, String path) {
        /**
         * @Description //TODO 文件处理
           @Author Leo
         * @Date 14:10 2020/3/12
         * @Param [file, path]
         * @return java.lang.String
        */
        String fileName = file.getOriginalFilename();
        //拓展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //uuid+.+拓展名
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        log.info("开始上传文件,上传文件名:{},上传路径:{},新文件名:{}",fileName,path,uploadFileName);
        //文件夹操作
        File fileDir = new File(path);
        if(!fileDir.exists()){
            //可写
            fileDir.setWritable(true);
            //可创建多级文件夹
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            //文件已经上传成功了
            //todo 将targetFile 上传到我们的FTP服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //todo 上传完成之后,删除upload 下面的文件
//            targetFile.delete();
        } catch (IOException e) {
            log.error("上传文件异常",e);
            e.printStackTrace();
        }
        //A:abc.jpg
        //B:abc.jpg
        return targetFile.getName();
    }

}
