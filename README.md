# OSS 图床

[TOC]

## 简介

> 其实都是废话。。大佬们请直接看[使用](#使用)

* 工作上经常用到图床，现有的免费图床并不好用，而且你并`不知道什么时候就关闭了`。so这个demo项目诞生了！
* **OSS**，阿里云提供的对象存储，用来当图床、网盘都是非常合适的，详情见 -> [传送门](https://www.aliyun.com/product/oss)



## 使用

1. 将`resources`中的`conf.properties.example`文件拷贝一份命名为`conf.properties`。
2. 按照其中内容填写好自己的oss endpoint、accessKeyId、secretAccessKey。
3. 直接maven打包
4. java -jar运行jar包

## 最近更新

* 2020年02月08日 图床界面修改，新增最近上传功能
* 2019年xx月xx日 项目创建，首次上传



## 待实现

* 后期考虑用Go去实现，开发客户端程序
