/*
 * Copyright (c) 2019 Code Technology Studio
 * Jpom is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * 			http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
import cn.hutool.core.io.CharsetDetector;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import org.dromara.jpom.util.CommandUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by bwcx_jzy on 2019/3/15.
 */
public class TestFile {
    public static void main(String[] args) throws IOException {
//        File file = new File("C:/WINDOWS/system32/s/s");
//        System.out.println(file.toPath().startsWith(new File("C:/Windows/System32/s/S").toPath()));
////        System.out.println(file());
//
//
//        File file1 = new File("D:/keystore.p12");
//        System.out.println(file1.exists() && file1.isFile());
        File file = FileUtil.file("D:\\jpom\\server\\data\\build\\39a61a05c63b4f56baf0d90bad498ac2\\history\\#7");
        System.out.println(FileUtil.mainName(file));
    }


    @Test
    public void testDel() {
        // xxx.pdf
        // xxx (1).pdf
        CommandUtil.systemFastDel(new File("/Users/user/Downloads/xxx (1).pdf"));
    }


    @Test
    public void testFile() {
        File file = FileUtil.file("D:\\Idea\\hutool\\.git");
        System.out.println(file.isHidden());

        System.out.println(new File("../").getAbsoluteFile());
    }

    @Test
    public void testSystemFile() {
        Path path = FileSystems.getDefault().getPath("~", "runs", "../Dockerfile");
        System.out.println(path);

        Path path1 = FileSystems.getDefault().getPath("~/runs/../Dockerfile");
        System.out.println(path1);

        System.out.println(FileUtil.file("~", "runs/../", "/Dockerfile"));

    }

    @Test
    public void testFileExt() {
        String extName = FileUtil.extName("canal.deployer-1.1.7-SNAPSHOT.tar.gz");
        System.out.println(extName);
    }

    @Test
    public void testMd5() {
        File file = FileUtil.file("D:\\迅雷下载\\zh-cn_windows_11_business_editions_version_22h2_updated_sep_2022_x64_dvd_515a832b.iso");
        File file1 = FileUtil.file("D:\\迅雷下载\\zh-cn_windows_11_business_editions_version_22h2_updated_sep_2022_x64_dvd_515a832b (1).iso");
        File file2 = FileUtil.file("D:\\迅雷下载\\zh-cn_windows_11_business_editions_version_22h2_updated_sep_2022_x64_dvd_515a832b (2).iso");
        System.out.println(SecureUtil.md5(file));
        System.out.println(SecureUtil.md5(file1));
        System.out.println(SecureUtil.md5(file2));
    }

    @Test
    public void testFilecd() {
        File file = FileUtil.file("D:\\System-Data\\Downloads\\导出的 ssh 数据 2023-12-25.csv");
        Charset detect = CharsetDetector.detect(file);
        System.out.println(detect);
    }

}
