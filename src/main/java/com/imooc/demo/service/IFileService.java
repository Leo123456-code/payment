package com.imooc.demo.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: IFileService
 * Description: TODO文件处理服务
 * Author: Leo
 * Date: 2020/3/12-11:34
 * email 1437665365@qq.com
 */
public interface IFileService {
    //文件处理服务
    String upload(MultipartFile file, String path);
}
