package com.adnan;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

//@SuppressWarnings("deprecation")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FileUrl {
  String url;

  public FileUrl(String url) {
    this.url = url;
  }

  public FileUrl() {
  }
}
