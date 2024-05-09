package com.yss.cad.web.sevice;

import com.yss.cad.common.Dwg2Dxf;
import com.yss.cad.web.dto.ParseDto;
import com.yss.cad.web.storage.DrillResultStorage;
import com.yss.cad.web.vo.ParseVO;
import com.yss.common.core.utils.FileUtils;
import com.yss.common.core.utils.ZipUtils;
import com.yss.drill.Drill;
import com.yss.drill.entity.IParamConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author 杨森森
 * @Data 2024/5/1  20:47
 */
@Service
@Slf4j
public class DrillService {
    @Autowired
    private IParamConfig paramConfig;

    @Autowired
    private Dwg2Dxf dwg2Dxf;

    @Autowired
    private DrillResultStorage drillResultStorage;

    private String randomKey() {
        return (new Date() + UUID.randomUUID().toString()).replaceAll(" ", "");
    }

    /**
     * 异步方法，spring boot有默认线程池
     * @param zipFile 压缩文件
     * @throws IOException io异常
     */
    @Async
    public void asyncParse(MultipartFile zipFile, String token, ParseDto info) throws IOException, InterruptedException {
        File unzipDir = unzip(zipFile);
        if(FileUtils.isEmptyFolder(unzipDir)) {
            return;
        }
        File dxfDir = toDxfDir(unzipDir);
        FileUtils.delete(unzipDir);
        String key = randomKey();
        log.info("当前key为->" + key);
        for (File file : Objects.requireNonNull(dxfDir.listFiles())) {
            log.info("当前解析文件：" + file.getPath());
            File out = parse(file);
            drillResultStorage.storage(token, key, out, file.getName(), info);
            out.delete();
        }
        drillResultStorage.success(key);
        FileUtils.delete(dxfDir);
        zipFile.getInputStream().close();
    }

    public File parse(File file) throws IOException {
        String fileName = file.getName();
        File outFile = File.createTempFile(fileName.replace(".dxf", UUID.randomUUID().toString()), ".xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        Drill.build(fileInputStream, fileOutputStream, "UTF-8", paramConfig);
        fileInputStream.close();
        fileOutputStream.close();
        return outFile;
    }

    /**
     * 解压文件夹
     * @param zipFile 原文件
     * @return 解压后
     */
    private File unzip(MultipartFile zipFile) throws IOException {
        InputStream inputStream = zipFile.getInputStream();
        File file = FileUtils.transferToFile(inputStream, Objects.requireNonNull(zipFile.getOriginalFilename()));
        File unZipFiles = ZipUtils.unZipFiles(file);
        FileUtils.delete(file);
        return unZipFiles;
    }

    private File toDxfDir(File folder) throws IOException, InterruptedException {
        Path dxfDir = Files.createTempDirectory(UUID.randomUUID().toString());
        Path dwgDir = Files.createTempDirectory(UUID.randomUUID().toString());
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (FileUtils.getSuffix(file.getName()).equals(".dwg")) {
                Files.copy(file.toPath(), dwgDir.resolve(file.getName()));
            }else if (FileUtils.getSuffix(file.getName()).equals(".dxf")) {
                Files.copy(file.toPath(), dxfDir.resolve(file.getName()));
            }
        }
        if (!FileUtils.isEmptyFolder(dwgDir.toFile())) {
            dwg2Dxf.transfer(dwgDir.toString(), dxfDir.toString());
        }
        FileUtils.delete(dwgDir.toFile());
        return dxfDir.toFile();
    }

    /**
     * 获取解析结果list
     * @param token 用户令牌
     */
    public List<ParseVO> getParseList(String token) throws IOException, ClassNotFoundException {
        return drillResultStorage.getParseList(token);
    }
}
