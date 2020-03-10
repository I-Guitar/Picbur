# OSS 图床

[TOC]

## 简介

> 其实都是废话。。大佬们请直接看[使用](#使用)

* 工作上经常用到图床，现有的免费图床并不好用，而且你并`不知道什么时候就关闭了`。so这个demo项目诞生了！
* **OSS**，阿里云提供的对象存储，用来当图床、网盘都是非常合适的，详情见 -> [传送门](https://www.aliyun.com/product/oss)



## 使用

### 开箱即用

1. 下载[releases](https://github.com/I-Guitar/oss-pic-bed/releases)中编译好的jar包

2. 在jar包同目录下创建`conf.properties`文件，内容如下：

   ```properties
   # 替换 xxx 为你自己的
   endpoint=xxx
   bucketName=xxx
   accessKeyId=xxx
   secretAccessKey=xxx
   ```

3. `java -jar xxx.jar`直接运行

4. `http://<ip>:8868` 访问



### 自行编译

1. 将`resources`中的`conf.properties.example`文件拷贝一份命名为`conf.properties`。
2. 按照其中内容填写好自己的oss endpoint、accessKeyId、secretAccessKey。
3. 直接maven打包
4. java -jar运行jar包
5. `http://<ip>:8868` 访问



## 最近更新
* 2020年03月10日  1.0.2正式版发布：
  * 修改oss bucketName硬编码错误
  * 端口号修改，由`8080`改为`8868`
* 2020年02月11日  1.0.1正式版发布：
  * 取消oss配置文件硬编码到代码
  * 上传编译好可执行的jar文件
* 2020年02月09日  1.0.0正式版发布：
  * 添加图片上传日期显示
  * 历史记录图片修改为缩略图，省流
  * 添加一键copy功能
  * 前端界面优化
* 2020年02月08日  0.1.0测试版发布：
  * 新增上传历史功能
  * 前端大幅修改、优化
* 2019年xx月xx日 项目创建，首次上传



## 待实现

* 后期考虑用Go去实现，开发客户端程序
