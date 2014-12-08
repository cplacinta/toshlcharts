package com.placinta.toshlcharts.fileupload;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileWrapper {

  MultipartFile file;

  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }

}
