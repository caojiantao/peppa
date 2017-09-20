package com.cjt.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtil {

  /**
   * 使用文件通道的方式高效复制文件
   */
  public static boolean copyFileByChannel(FileInputStream fis, File tarFile) {
    // 若目标文件存在，则删除替换
    if (!tarFile.exists()) {
      File tarDir = tarFile.getParentFile();
      if (!tarDir.exists() && (!tarDir.mkdirs())) {
        return false;
      }
      try {
        if (!tarFile.createNewFile()) {
          return false;
        }
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
    }
    FileOutputStream fos = null;
    FileChannel in = null;
    FileChannel out = null;
    try {
      fos = new FileOutputStream(tarFile);
      // 得到对应的文件通道
      in = fis.getChannel();
      out = fos.getChannel();
      // 连接两个通道，并且从in通道读取，然后写入out通道
      in.transferTo(0, in.size(), out);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }
}
