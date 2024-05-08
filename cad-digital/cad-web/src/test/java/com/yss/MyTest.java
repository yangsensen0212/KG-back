package com.yss;
import com.yss.common.core.utils.ZipUtils;
import org.apache.commons.compress.archivers.zip.ZipUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author 杨森森
 * @Data 2024/5/6  11:39
 */
public class MyTest {
    @Test
    public void unZipTest() throws IOException {
        File fileInput = new File("C:\\Users\\pcforsen\\Downloads\\dxf.zip");
        File fileDesc = new File("C:\\Users\\pcforsen\\Downloads\\dxf-unzip");
        ZipUtils.unZipFiles(fileInput, fileDesc);
    }
}
