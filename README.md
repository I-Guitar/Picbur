# Joe's picture bed

[TOC]


**体验接口：[picbur.hujinwen.com](http://picbur.hujinwen.com)**

## 简介
> 其实都是无用的话。。大佬们请直接看[使用](#使用)


* 工作上经常用到图床，现有的免费图床并不好用，而且你并`不知道什么时候就关闭了`。so这个demo项目诞生了！



## 使用

### 开箱即用

1. 下载[releases](https://github.com/hu-jinwen/Picbur/releases)中编译好的jar包

2. 在jar包同目录下创建`config.yml`配置文件，配置说明请查看[配置详解（config.yml）](#配置详解（config.yml）)

3. `java -jar xxx.jar`直接运行

4. `http://<ip>:8868` 访问



### 自行编译

1. 该项目依赖我的另一个项目[Jokit]()（一个java工具包）。由于没有放到中央仓库所以需要先clone该项目，再安装到maven本地仓库，注意pom.xml中版本号是否一致。

   ```bash
   # clone代码
   git clone https://github.com/hu-jinwen/Jokit.git
   # 进入目录
   cd Jokit
   # 安装到maven本地仓库
   mvn install
   ```

   

2. 将`resources`中的`config.yml.example`文件拷贝一份命名为`config.yml`。

3. 按照[配置详解（config.yml）](#配置详解（config.yml）)提示填上你自己的内容

4. mvn package打包

5. java -jar运行jar包

6. `http://<ip>:8868` 访问



## 详解

### 配置详解（config.yml）

#### 图片存储配置

<u>MinIO和OSS存储至少需要配置一个，配置了多个优先存储在MinIO中</u>

* MinIO

  ```yaml
  minIOStore:
    # 多endpoints以分号分隔;
    endpoints: http://xxxx.xxxx.com;http://xxx.xxx.com
    bucketName: <my-bucket>
    accessKeyId: <accessKeyId>
    secretAccessKey: <secretAccessKey>
  ```

* OSS

  开通方式见[阿里OSS开通说明](#阿里OSS开通说明)

  ```yaml
  ossStore:
    # oss不支持多endpoints
    endpoints: oss-cn-hangzhou.aliyuncs.com
    bucketName: <bucketName>
    accessKeyId: <accessKeyId>
    secretAccessKey: <secretAccessKey>
  ```



### 阿里OSS开通说明

1. 访问阿里云oss[首页](https://www.aliyun.com/product/oss)根据提示开通

2. 登陆阿里云管理控制台 -> [控制台](https://oss.console.aliyun.com/overview)

3. 创建bucket

   ![](http://minio1.hujinwen.com/joe-data/pic-bed/2020-08-23/59ed6de6e4c073ab1e934c6132f8ae6c.png)

   填写必要信息

   ![](http://minio1.hujinwen.com/joe-data/pic-bed/2020-08-23/a7ceecb4dc40142d9b4f66dc6085656c.png)

   读写权限选公共读

   ![](http://minio.hujinwen.com/joe-data/pic-bed/2020-08-23/8cb0cbf9906ec60cf60511b43ad6fa80.png)

   点击bucket -> 概览，得到该bucket的endpoint

   ![](http://minio.hujinwen.com/joe-data/pic-bed/2020-08-23/e2784790889ac1c0ab932b3a13c28a45.png)


4. 获取`accessKeyId`和`secretAccessKey`
   获取accessKeyId和secretAccessKey
   ![](http://minio.hujinwen.com/joe-data/pic-bed/2020-08-29/c2facd2097901902d07ef5e4ae1c6152.png)


## 最近更新

* 2020年8月23日 2.2.0发布：
  * 可选图片存储位置，minio或者oss
  * 配置文件修改，由conf.properties改为config.yml
* 2020年03月26日  2.1.0发布：
  * MinIO客户端，由单机版改为集群
  * 配置可添加多集群（多endpoint以分号分隔）
* 2020年03月20日  2.0.0正式版发布（重大更新，前期版本不可用）：
  * 将内置存储由 阿里OSS 改为 MinIO。自己搭建 MinIO 集群，0费用
  * 新增测试接口 [picbur.hujinwen.com](http://picbur.hujinwen.com)



## TODO

* 提供GUI版本
* 将端口号外置，配置文件外置
* 批量上传（一次选多张图，选择文件夹）
* 通过图片URL上传



