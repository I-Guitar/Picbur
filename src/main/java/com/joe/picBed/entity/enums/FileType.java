package com.joe.picBed.entity.enums;

/**
 * Created by joe on 2020/3/20
 * <p>
 * 文件 type 对应的 stream 头编码
 * 未全部列举，参考 https://app.yinxiang.com/shard/s20/nl/18962043/3dde50cd-e239-465f-8d14-b4878e3f9ac6
 */
public enum FileType {
    // images
    JPG("jpg", "ffd8ffe000", "ffd8ffe117"),
    PNG("png", "89504e470d"),
    GIF("gif", "4749463839"),
    TIF("tif", "49492a0022"),
    BMP("bmp", "424d228c01", "424d824009", "424d8e1b03"),
    // office
    DWG("dwg", "4143313031"),
    PSD("psd", "3842505300"),
    VSD("vsd", "d0cf11e0a1"),
    DOC("doc", "d0cf11e0a1"),
    PDF("pdf", "255044462d"),
    WPS("wps", "d0cf11e0a1"),
    // os,
    EXE("exe", "4d5a900003"),
    // zip,
    ZIP("zip", "504b030414"),
    RAR("rar", "526172211a"),
    GZ("gz", "1f8b080000"),
    //code
    JAVA("java", "7061636b61"),
    HTML("html", "3c21444f43"),
    HTM("htm", "3c21646f63"),
    CSS("css", "48544d4c20"),
    JS("js", "696b2e7162"),
    XML("xml", "3c3f786d6c"),
    JAR("jar", "504b03040a"),
    CLASS("class", "cafebabe00"),
    JSP("jsp", "3c25402070"),
    PROPERTIES("properties", "6c6f67346a"),
    // media,
    MP4("mp4", "0000002066"),
    MP3("mp3", "4944330300"),
    FLV("flv", "464c560105"),
    WMV("wmv", "3026b2758e"),
    WAV("wav", "52494646e2"),
    AVI("avi", "52494646d0"),
    TORRENT("torrent", "6431303a63");

    public final String typeName;

    public final String[] streamCodes;


    FileType(String typeName, String... codes) {
        this.typeName = typeName;
        this.streamCodes = codes;
    }

}
