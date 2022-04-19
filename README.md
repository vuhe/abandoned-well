# 废弃井名录管理系统

系统用作毕设使用，此系统内部所有数据均为个人测试使用的假数据，请勿当真～； 

系统使用 spring boot + layui 实现

## 项目结构

- common-api: 项目的基本工具模块
- common-spring: 项目的 spring 配置模块
- function-system: 系统基本管理系统模块
- function-well: 井信息管理系统基的实现模块

项目使用 spring boot 的分层结构：controller、service、repository

由于项目已经确定数据库，并且大部分和数据库强相关的由 repository 负责实现，service 层不再定义接口

## 项目依赖

### 语言及环境

- Java: target:11.x, dependency: 17.x
- Kotlin: 1.6.20
- spring boot: 2.6.6
- ktorm(database dependency): 3.4.x
- springdoc-openapi(system doc): 1.6.x

### 工具依赖

- hutool tool(api module only): 5.7.x
- caffeine cache(api module only): 3.0.x
- jsoup(api module only): 1.14.x
- oshi-java11(api module only): 6.1.x
- easy-poi(api module only): 4.4.x
- easy-captcha(spring module only): 1.6.x

## Road Map

- [x] Update spring boot to 2.6.x
- [x] Update kotlin to 1.6.x
