# 废弃井名录管理系统

> **此系统中的所有数据均为假数据，系统只是用于完成毕设使用，系统没有用于生产环境，请勿索要数据信息**
> 
> 系统中所有信息约束均为个人通过网上公开信息结合自己推断确定，不作为实际使用依据

系统用作毕设使用，此系统内部所有数据均为个人测试使用的假数据，请勿当真～； 

系统使用 spring boot + layui 实现

## 项目结构

|       模块名       |       说明        |
|:---------------:|:---------------:|
|   common-api    |    项目的基本工具模块    |
|  common-spring  | 项目的 spring 配置模块 |
| function-system |   系统基本管理系统模块    |
|  function-well  |  井信息管理系统基的实现模块  |

项目使用 spring boot 的分层结构：controller、service、repository

由于项目已经确定数据库，并且大部分和数据库强相关的由 repository 负责实现，service 层不再定义接口

## 项目依赖

### 语言及环境

|        环境        |   版本   |       说明        |
|:----------------:|:------:|:---------------:|
|       Java       |  11+   | dependency: 17+ |
|      Kotlin      | 1.6.21 |                 |
|   spring boot    | 2.7.0  |                 |
|      ktorm       | 3.4.x  | orm dependency  |
|    springdoc     | 1.6.x  | system api doc  |

### 工具依赖

|        名称        |   版本   |         范围         |
|:----------------:|:------:|:------------------:|
|  caffeine cache  | 3.1.0  |  common-api only   |
|      jsoup       | 1.14.3 |  common-api only   |
|   oshi-java11    | 6.1.6  |  common-api only   |
| user-agent-utils |  1.21  |  common-api only   |
|     easy-poi     | 4.4.0  |  common-api only   |
|   easy-captcha   | 1.6.2  | common-spring only |

## Road Map

- [x] Update spring boot to 2.6.x
- [x] Update spring boot to 2.7.0
- [x] Update kotlin to 1.6.x
