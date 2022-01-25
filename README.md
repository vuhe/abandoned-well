# AdminTemplate
管理框架模版，使用 spring boot + layui 实现

## 项目结构

项目分三个大结构：`common` `function` `starter`

`common`：项目基础代码，包含基本工具和 spring 的通用配置
- `common-api`: 项目的基本工具模块
- `common-spring`: 项目的 spring 配置模块

`function`：项目功能代码，包含具体功能模块的实现
- `function-generator`: 项目代码生成模块（未完成）
- `function-schedule`: 系统任务定时执行模块
- `function-system`: 系统基本管理系统模块

`starter`：项目启动代码，包含不同项目细分逻辑代码
