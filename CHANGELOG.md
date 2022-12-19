# 🚀 版本日志

## 2.10.0

### 🐣 新增功能

1. 【all】外置 `logback` 配置文件
2. 【server】服务端管理相关功能独立页面菜单
3. 【server】新增项目触发器用于管理项目状态
4. 【all】新增 构建项目发布支持配置发布到二级目录
5. 【server】新增 节点分发发布支持配置发布到二级目录

### 🐞 解决BUG、优化功能

1. 【all】启动相关信息由控制台输出改为 `logback`
2. 【all】节点管理中 `其他功能` 菜单更名为 `脚本管理`
3. 【all】优化版本升级修改管理脚本里变量,采用文件记录方式
4. 【server】优化容器启动脚本，支持监听进程已经终端重启操作
5. 【server】修复 自动刷新页面已经关闭的标签页，后台仍然在发送请求
   （感谢[@苏生不语](https://gitee.com/sushengbuyu) [Gitee issues I664OP](https://gitee.com/dromara/Jpom/issues/I664OP) ）
6. 【server】修正触发器说明错别字

### ❌ 不兼容功能

1. 【server】取消支持 2.8.0 以下 json 文件转存数据库
2. 【all】下架 JDK 管理模块（请使用 DSL 项目模式代替）
3. 【all】下架 TOMCAT 管理模块（请使用 DSL 项目模式代替）
4. 【all】删除 项目内存监控页面
5. 【all】配置文件名称由 `extConfig.yml` 变更为 `application.yml`
6. 【all】调整项目打包目录结构
7. 【all】取消兼容低版本数据目录文件迁移（调试运行）
8. 【all】取消自动识别文件编码格式模块 `auto-charset-jchardet`
9. 【all】更新管理脚本，进程标识更新（已经存在的需要手动停止）
10. 【all】取消插件端配置化向服务端注册功能（采用快速导入方式替代）
11. 【server】取消服务端授权 token 配置
12. 【all】下架 节点脚本导入功能
13. 【server】取消限制创建用户最大数配置：`user.maxCount`
14. 【server】删除 node_info 表 cycle 字段
15. 【agent】删除项目回收记录功能

### ❌ 不兼容的属性配置变更

> 属性配置支持驼峰和下划线

1. 【agent】`whitelistDirectory.checkStartsWith` -> `jpom.whitelist-directory.check-starts-with`
2. 【agent】`project.stopWaitTime` -> `jpom.project.statusWaitTime`
3. 【agent】`project.*` -> `jpom.project.*`
4. 【agent】修正拼写错误 `log.*back` -> `jpom.project.log.*backup`
5. 【agent】`log.*` -> `jpom.project.log.*`
6. 【agent】`log.intiReadLine` -> `jpom.init-read-line`
7. 【agent】 `log.autoBackConsoleCron` 不支持配置 none (none 使用 `jpom.project.log.autoBackupToFile` 代替)
8. 【all】删除 `consoleLog.reqXss` 、`consoleLog.reqResponse`
9. 【all】`consoleLog.charset` -> `jpom.system.console-charset`
10. 【server】`node.uploadFileTimeOut` -> `jpom.node.uploadFileTimeout`
11. 【server】`system.nodeHeartSecond` -> `jpom.node.heartSecond`
12. 【server】`user.*` -> `jpom.user.*`
13. 【server】`jpom.authorize.expired` -> `jpom.user.tokenExpired`
14. 【server】`jpom.authorize.renewal` -> `jpom.user.tokenRenewal`
15. 【server】`jpom.authorize.key` -> `jpom.user.tokenJwtKey`
16. 【server】`jpom.webApiTimeout` -> `jpom.web.api-timeout`
17. 【server】删除 `ssh.initEnv`
18. 【server】批量修正前端相关配置属性均修改到 `jpom.web.*`
19. 【server】`db.*` -> `jpom.db.*`
20. 【server】`build.*` -> `jpom.build.*`

### ⚠️ 注意

> 此版本为不兼容升级，需要手动升级修改相关配置才能正常使用


#### 简洁的升级流程

1. 停止正在运行的程序插件端或者服务端
2. 备份已经存在的插件端或者服务端的数据目录
3. 手动安装新版本 `2.10.0+` 
4. 还原数据：将备份的数据目录迁移到新安装的数据目录（需要再未运行的状态下操作）
5. 重启程序

------

## 2.9.21 (2022-12-08)

### 🐞 解决BUG、优化功能

1. 【server】修复 不能在线保存服务端配置（@迷了鹿）
2. 【all】修复 服务端脚本分发节点工作空间错乱问题，需要再次保存自动修正
   （感谢[@mt-mored](https://gitee.com/mt-mored) [Gitee issues I65B19](https://gitee.com/dromara/Jpom/issues/I65B19) ）

### ❌ 不兼容功能

1. 【agent】取消 对 2.4.3 以下的项目白名单目录修复支持

------

## 2.9.20 (2022-12-06)

### 🐞 解决BUG、优化功能

1. 【server】优化 服务端脚本触发器传入参数添加为执行环境变量（变量名自动转下划线并且添加 `trigger_` 前缀）（感谢@李中原）
2. 【agent】优化 插件端脚本触发器传入参数添加为执行环境变量（变量名自动转下划线并且添加 `trigger_` 前缀）（感谢@李中原）
3. 【server】优化 构建确认弹窗支持选择差异构建
4. 【server】优化 修剪 docker 镜像支持选择悬空
5. 【server】优化 git 仓库标签拉取支持差异构建判断（感谢@M⃰í⃰n⃰g⃰ z⃰á⃰i⃰、@阿超）
6. 【server】优化 提醒 MFA 绑定取消 esc 关闭弹窗
7. 【server】优化 添加构建时未选择分支表单一致验证失败问题
8. 【agent】修复 上传文件并重启不能正常重启（节点分发完整顺序重启、顺序重启）
9. 【server】优化 节点分发列表显示更多字段
10. 【server】优化 日志阅读不可以阅读文件不能选择
11. 【server】优化 节点分发支持调整到实时阅读页面

------

## 2.9.19 (2022-12-05)

### 🐣 新增功能

1. 【server】新增 在线构建新增附加环境变量配置 来实现定义构建运行环境变量
2. 【server】新增 构建发布 docker 镜像新增构建参数,使构建镜像更灵活 （感谢@loyal）
3. 【server】新增 构建发布 docker 镜像新增镜像标签、no-cache、更新镜像配置
4. 【server】新增 修剪 docker 相关资源功能
5. 【server】新增 单 IP 登录失败指定次数将锁定对应 IP，默认 10 次 `user.alwaysIpLoginError`
6. 【server】新增 镜像创建容器支持配置 label 属性

### 🐞 解决BUG、优化功能

1. 【server】修复 在线构建仓库文件存在`.env`文件时带来构建结果异常问题（感谢@蹉跎岁月）
2. 【agent】优化 项目`清空上传文件`
   等待文件上传完成后再清空、上传文件优先到临时目录完成后再移动至项目目录
   （感谢[@tiv-cloud_0](https://gitee.com/tiv-cloud_0) [Gitee issues I649NU](https://gitee.com/dromara/Jpom/issues/I649NU) ）
3. 【server】修复 docker 创建容器部分场景端口无法正常暴露问题（感谢@李中原）
4. 【server】优化 docker 控制台刷新页面自动打开控制台
5. 【server】优化 docker java 依赖包
6. 【all】优化 取消 hutool-all 包，采用 bom 方式引入

### ❌ 不兼容功能

1. 【server】在线构建本地构建、本地发布、docker 镜像取消默认读取 `.env`文件,需要手动配置
2. 【server】取消 `user.ipErrorLockTime` 属性表达式支持，改为 `Duration 风格`，转换异常默认值为 5h

------

## 2.9.18 (2022-12-01)

### 🐣 新增功能

1. 【agent】新增 DSL 项目运行脚本环境变量配置（`run.start.scriptEnv`）
2. 【agent】新增 DSL 项目自定义 `restart` 流程 （`run.restart`）
3. 【agent】新增项目状态检测间隔时间配置属性：`project.statusDetectionInterval` （感谢@项勇祥）

### 🐞 解决BUG、优化功能

1. 【agent】修复 DSL 项目重启操作被偶发异常（自动）关闭问题
2. 【agent】优化 DSL 项目控制台日志输出格式
3. 【all】优化日志监听器：控制台支持自动重定向、第一次启动项目自动重新 showlog
4. 【server】节点超时时间配置为 0 失效问题
5. 【agent】修复进程列表在部分场景下进程号数字转换异常（感谢@易自玉）
6. 【agent】优化启动、停止、重启响应结果输出
7. 【agent】修复 windows DSL 模式：`另一个程序正在使用此文件，进程无法访问。` 问题
8. 【agent】修复节点脚本模板在一定情况下出现：`另一个程序正在使用此文件，进程无法访问。` 问题（感谢@易自玉）
9. 【server】修复服务端脚本模板在一定情况下出现：`另一个程序正在使用此文件，进程无法访问。` 问题
10. 【server】修复节点分发无法正常使用控制台、项目文件管理功能（感谢@ccx2480）

------

## 2.9.17 (2022-11-28)

### 🐣 新增功能

1. 【server】新增强制提示用户开启 MFA 认证,新增配置属性：`user.forceMfa`
   （感谢[@长风](https://gitee.com/angh5) [Gitee issues I5ZBFU](https://gitee.com/dromara/Jpom/issues/I5ZBFU) ）
2. 【server】新增镜像创建容器支持配置网络模式属性、--privileged、重启策略属性
3. 【server】新增自动探测本地 docker 按钮

### 🐞 解决BUG、优化功能

1. 【server】优化系统已经初始化过进入初始化页面提示信息
   （感谢[@说一](https://gitee.com/aacsgaa) [Gitee issues I61LQH](https://gitee.com/dromara/Jpom/issues/I61LQH) ）
2. 【server】优化构建流程状态，不存在产物构建状态标记为构建失败
3. 【agent】修复未配置 nginx 路径时 reload 报错
4. 【server】优化在线构建根据标签拉取代码（感谢@Tʀᴜsᴛ¹⁹⁹¹）
5. 【server】优化克隆 git 仓库仅显示少量日志
6. 【server】优化升级页面 loading 效果、全局 loading 关闭事件

### ❌ 不兼容功能

1. 【server】取消启动自动加载本地 docker
2. 【server】在线构建选择标签构建后，不能切换分支

------

## 2.9.16 (2022-11-25)

### 🐣 新增功能

1. 【server】节点分发列表自动刷新数据
2. 【server】容器安装的服务端不能使用本地构建

### 🐞 解决BUG、优化功能

1. 【server】优化节点转发超时时间计算方法
2. 【server】优化构建项目上传文件、节点分发上传文件超时时间
3. 【server】修复 Docker 检查 URL 是否正确引起的 NPE （感谢@～）
4. 【server】修复节点分发列表在部分场景出现错误数据行
5. 【server】优化节点分发结果添加执行耗时、上传文件大小信息
6. 优化安装脚本支持 Ubuntu 系统快速安装 （感谢@阿超）
7. 【server】修复节点分发日志未存储到对应的工作空间问题
8. 【server】H2 缓存大小配置调整为 50MB，已经安装的版本需要手动修改配置：`db.cacheSize`
9. 【server】修复编辑关联分发项目删除项目数据错乱问题
   （感谢[@DreamAgo](https://gitee.com/DreamAgo) [Gitee issues I631K2](https://gitee.com/dromara/Jpom/issues/I631K2) ）
10. 【agent】修复 DSL 模式未运行的项目在部分场景下控制台按钮状态不正确（感谢@项勇祥）
11. 【server】优化编辑在线构建弹窗默认不加载仓库分支 （感谢@阿超）
12. 【server】优化构建切换分支时自动重新拉取仓库
13. 【server】优化构建自动放弃本地修改避免构建引起文件变动冲突（感谢@阿超）

### ❌ 不兼容功能

1. 【server】已经创建的构建不支持切换构建方式

------

## 2.9.15 (2022-11-17)

### 🐞 解决BUG、优化功能

1. 【server】修复监控报警无法触发 webhook 类型的通知
   （感谢[@项勇祥](https://gitee.com/xiang-yong-xiang) [Gitee issues I61KRV](https://gitee.com/dromara/Jpom/issues/I61KRV) ）
2. 优化获取程序实参的方法（main 方法参数、使用 SimpleCommandLinePropertySource）
3. 【server】修复构建切换分支冲突后，手动清除代码(构建缓存)
   时文件被占用问题（感谢[@xuejun84](https://gitee.com/xuejun84) [Gitee issues I61ANL](https://gitee.com/dromara/Jpom/issues/I61ANL) ）

------

## 2.9.14 (2022-11-08)

### 🐞 解决BUG、优化功能

1. 【server】更新 Server.sh JVM 参数 -XX:+UnlockExperimentalVMOptions：该参数为解锁 JVM 实验性参数
2. 【agent】更新 Server.sh JVM 参数 -XX:+UnlockExperimentalVMOptions：该参数为解锁 JVM 实验性参数
3. 【server】修复脚本执行日志名称搜索异常
   （感谢[@伤感的风铃草](https://gitee.com/bwy-flc) [Gitee issues I600OE](https://gitee.com/dromara/Jpom/issues/I600OE) ）

------

## 2.9.13 (2022-10-20)

### 🐞 解决BUG、优化功能

1. 【server】修复 SSH管理 中的编辑命令出现内容错乱问题
2. 【server】修复构建历史回滚无法正常使用问题（感谢@rs、@Aholic）

------

## 2.9.12 (2022-10-10)

### 🐞 解决BUG、优化功能

1. 【server】修正错别字`传入` （感谢@fangdan）
2. 【server】修复下拉框组件在弹窗中不能完整显示问题（感谢@冷月）

------

## 2.9.11 (2022-09-09)

### 🐞 解决BUG、优化功能

1. 【server】修复全局变量未自动加载列表
2. 【server】修复全局变量编辑中不能选择分发节点
3. 【server】修复跨工作空间同步数据不能正常使用问题（感谢@ccx2480）
4. 【server】修复 SVN 仓库无法构建问题（感谢@Tʀᴜsᴛ¹⁹⁹¹）
5. 【server】修复全局变量和工作空间变量可能冲突的问题

------

## 2.9.10 (2022-09-07)

### 🐣 新增功能

1. 【server】新增全局工作空间变量
2. 【server】构建仓库中密码支持引用工作空间变量值（统一配置账号密码） （感谢
   @G、 @[hjk2008](https://gitee.com/hjk2008) [Gitee issues I4SH8V](https://gitee.com/dromara/Jpom/issues/I4SH8V) ）
3. 【server】ssh 账号密码支持引用工作空间变量值
4. 【server】构建列表新增下载最新产物方式（感谢@奇奇）

### 🐞 解决BUG、优化功能

1. 【server】修复下拉框组件在弹窗中不能完整显示问题（感谢@飞儿、@冷月、@ccx2480）
2. 【server】取消下拉菜单、下拉框跟随页面滚动（from 2.9.2）

------

## 2.9.9 (2022-08-22)

### 🐣 新增功能

1.【agent】DSL 项目新增支持单独配置是否开启日志文件备份属性（感谢@ccx2480）

### 🐞 解决BUG、优化功能

1. 【server】容器构建 cache 插件支持使用环境变量替换值
2. 【agent】优化检查 jps 异常,调整为实时检查
   （感谢[@傲梅科技](https://gitee.com/aomeitech) [Gitee issues I5MTA4](https://gitee.com/dromara/Jpom/issues/I5MTA4) ）
3. 【server】修复登录后跳转到上次使用到工作空间中问题 （感谢@木迷榖）
4. 【server】缓存日志弹窗相关操作配置
   （感谢[@我已经没有头发了](https://gitee.com/christina204) [Gitee issues I5MANC](https://gitee.com/dromara/Jpom/issues/I5MANC) ）
5. 【server】优化节点首页：支持自定义刷新频率、图标自动缩放 （感谢@ccx2480）
6. 【server】优化构建列表：支持自动刷新、构建状态添加标签颜色
7. 【server】优化 SSH 发布上传异常没有抛出导致构建状态不一致问题（感谢@print(1)）

------

## 2.9.8 (2022-08-10)

### 🐞 解决BUG、优化功能

1. 【server】修复创建账号默认密码无法正常登录问题（感谢@ʟᴊx💎💎）
2. 【server】错别字：权限组周末改为周日、周七改为周日
3. 【server】容器构建 JDK
   依赖下载地址更新（感谢[@Yousinnmu](https://gitee.com/yang-xinwu) [Gitee issues I5LC3K](https://gitee.com/dromara/Jpom/issues/I5LC3K) ）
4. 【server】构建相关环境变量 GIT 仓库时新增：`BUILD_BRANCH_NAME`,`BUILD_TAG_NAME`
   （感谢[@Yousinnmu](https://gitee.com/yang-xinwu)
5. 【server】修复插件端管理页面中无法正常远程更新问题

### 特别感谢

感谢 [@💎ℳ๓₯㎕斌💎💘](https://weihongbin.com/)  贡献 `Adoptium JDK` 自动更新的解决方案

------

## 2.9.7 (2022-08-08)

### 🐣 新增功能

1. 【server】新增权限组，用于批量管理用户权限（感谢@木迷榖）
2. 【server】账号新增状态字段,用于控制禁用账号
3. 【server】重置密码使用系统生成
4. 【server】构建 docker 镜像支持使用构建序号 ID 为 tag 版本号递增（感谢@Y.）
5. 【server】部分列表新增排序功能(置顶、上移、下移)
   （感谢[@爱琳琳真是太好了](https://gitee.com/qiqi513_admin) [Gitee issues I5KBN9](https://gitee.com/dromara/Jpom/issues/I5KBN9) ）

### 🐞 解决BUG、优化功能

1. 【server】用户资料支持修改昵称
2. 【server】修复节点管理中【系统设置】菜单重复显示菜单项问题（感谢@ʟᴊx💎💎）
3. 【server】修复节点首页历史监控图表按钮未显示问题（感谢@ʟᴊx💎💎）
4. 【agent】优化加载 nginx 配置相关代码（感谢@伤感的风铃草🌿）
5. 【server】修改 node welcome vue 中单词拼写错误
   （感谢[@dongge](https://gitee.com/gao-weidong) [Gitee issues I5JT9R](https://gitee.com/dromara/Jpom/issues/I5JT9R) ）
6. 【server】修复 IP 字段长度不足,不能支持 IPV6 问题
   （感谢[@宋晶磊](https://gitee.com/songjingl) [Gitee issues I5JSEW](https://gitee.com/dromara/Jpom/issues/I5JSEW) ）
7. 【server】修复在 docker 开启 TLS 时候配置私有仓库不生效问题（感谢@🐠）
8. 【server】docker 心跳时间调整为超时时间
9. 【server】修改在未配置 docker 私有仓库信息,部分功能出现 NPE（感谢@🐠、Y.）
10. 优化 windows 环境管理脚本,保存配置并重启不能正确启动问题（感谢@大灰灰）
11. 【server】用户管理中使用系统生成密码代替输入密码

### ⚠️ 注意事项

1. 高版本会兼容低版本已经配置低用户权限，但是在编辑用户时候需要重新配置权限组
2. 升级后需要重新配置用户工作空间权限相关

------

## 2.9.6 (2022-07-27)

### 🐞 解决BUG、优化功能

1. 【server】修复日志弹窗在部分屏幕下按钮重叠问题
   （感谢[@all-around-badass](https://gitee.com/all-around-badass) [Gitee issues I5IZ9H](https://gitee.com/dromara/Jpom/issues/I5IZ9H) ）
2. 【server】优化工作空间和用户昵称显示区域,支持显示更多文字（感谢@木迷榖）

------

## 2.9.5 (2022-07-26)

### 🐣 新增功能

1. 【server】新增配置容器构建基础镜像相关功能（ 感谢[@💎ℳ๓₯㎕斌💎💘](https://gitee.com/weihongbin) ）
2. 【server】SSH 脚本、服务端脚本新增执行触发器
3. 节点脚本新增执行触发器
   （感谢[@田良](https://gitee.com/tian8liang) [Gitee issues I5HB6F](https://gitee.com/dromara/Jpom/issues/I5HB6F) ）
4. 【server】查看日志组件新增是否换行（感谢@hjk2008 [Gitee issues I5CYQK](https://gitee.com/dromara/Jpom/issues/I5CYQK) ）

### 🐞 解决BUG、优化功能

1. 优化 HTTP GET 请求打开重定向
2. 【server】触发器 token 机制调整,取消 SHA256 生成。采用数据表单独存储
3. 【server】修复启动加载全局代理时机,避免（The database is not initialized）
4. 管理脚本执行支持环境变量配置
   jvm、端口等，`JPOM_AGENT_JVM`,`JPOM_AGENT_PORT`,`JPOM_AGENT_ARGS`、`JPOM_SERVER_JVM`,`JPOM_SERVER_PORT`,`JPOM_SERVER_ARGS`
   （ 感谢@飞儿 ）
5. 【agent】兼容 `log.autoBackConsoleCron: none` 配置属性

------

## 2.9.4 (2022-07-12)

### 🐣 新增功能

1. 【server】docker 支持配置私有仓库地址（感谢@🐠）
2. 【server】构建 Docker 镜像支持推送到仓库中

### 🐞 解决BUG、优化功能

1. 【agent】插件端上传文件大小配置恢复默认配置（之前版本遗漏）
2. 【server】docker 连接使用连接池实现

------

## 2.9.3 (2022-07-08)

### 🐣 新增功能

1. 【server】新增容器 stats ,方便实时查看容器内存、网络信息
2. 【server】SSH 文件管理新增重命名文件功能
3. 【server】新增全局配置,解决 git httpProxy
   （感谢[@五六](https://gitee.com/tutu-father) [Gitee issues I5EY03](https://gitee.com/dromara/Jpom/issues/I5EY03) ）
4. 【server】新增更新容器配置（CPU、内存等）
5. 【server】新增页面 ICON 配置属性：`jpom.iconFile`
   （感谢[@flyhigh318](https://gitee.com/flyhigh318) [Gitee issues I5FKMW](https://gitee.com/dromara/Jpom/issues/I5FKMW) ）
6. 【server】SSH 脚本新增跨工作空间同步功能
   （感谢[@flyhigh318](https://gitee.com/flyhigh318) [Gitee issues I5FC9R](https://gitee.com/dromara/Jpom/issues/I5FC9R) ）
7. 【server】服务端脚本模版新增跨工作空间同步功能
8. 【server】构建新增事件脚本属性,在构建环节可以执行指定脚本来实现部分功能
   （感谢[@沈世举](https://gitee.com/shen-shiju) [Gitee issues I5FKFM](https://gitee.com/dromara/Jpom/issues/I5FKFM) ）
9. 【server】优化构建任务独立线程池,并且新增配置属性 `build.poolSize`、`build.poolWaitQueue` （感谢@小翼哥）
10. 【agent】配置项目是否备份控制台日志属性独立：`log.autoBackToFile` （感谢@Vergil。）

### 🐞 解决BUG、优化功能

1. 升级 SpringBoot、Hutool、jgit、svnkit
2. 【server】docker 加入集群无法正常使用问题
3. 【server】项目文件备份列表不能取消弹窗（点击关闭依然执行）问题（感谢@ʟᴊx💎💎）
4. 【server】修复编辑构建仓库切换事件重复问题
   （感谢[@五六](https://gitee.com/tutu-father) [Gitee issues I5F35E](https://gitee.com/dromara/Jpom/issues/I5F35E) ）
5. 【server】修复 windows 执行脚本出现异常
   （感谢[@all-around-badass](https://gitee.com/all-around-badass) [Gitee issues I5FK0K](https://gitee.com/dromara/Jpom/issues/I5FK0K) ）

### ❌ 不兼容事项

1. 插件端 `log.autoBackConsoleCron` 配置属性替换为：`log.autoBackToFile`

------

## 2.9.2 (2022-06-27)

### 🐣 新增功能

1. 【agent】插件端白名单新增 nginx 安装路径,解决 nginx reload 问题
   （感谢[@all-around-badass](https://gitee.com/all-around-badass) [Gitee issues I5CJR7](https://gitee.com/dromara/Jpom/issues/I5CJR7) ）
2. 【server】通过私人令牌导入仓库支持自建 GitLab
3. 【agent】项目文件管理新增重命名文件功能
4. 快速安装节点支持配置网卡名称（感谢@Elliot）

### 🐞 解决BUG、优化功能

1. 【server】构建历史分页查询不生效
   （感谢[@PQ宝剑](https://gitee.com/pqbaojian) [Gitee issues I5CYOD](https://gitee.com/dromara/Jpom/issues/I5CYOD) ）

2. 【server】优化编辑 ssh 判断重复,支持多账号配置SSH
   （感谢[@xiaofangkang](https://gitee.com/xiaofangkang) [Gitee issues I5D0EY](https://gitee.com/dromara/Jpom/issues/I5D0EY) ）
3. 【agent】文件备份对比流程异步处理,避免大文件对比耗时阻塞（感谢@ʟᴊx💎💎）
4. 【server】修复通过私人令牌导入仓库表格主键指定错误
5. 【server】修复 GitLab 通过私人令牌导入仓库接口分页错误
6. 【agent】优化新增文件、删除文件目录加载两次问题
   （感谢[@all-around-badass](https://gitee.com/all-around-badass) [Gitee issues I5DMKG](https://gitee.com/dromara/Jpom/issues/I5DMKG) ）
7. 【server】优化下拉菜单、下拉框跟随页面滚动
   （感谢[@pl.com](https://gitee.com/pl.com) [Gitee issues I5D6I0](https://gitee.com/dromara/Jpom/issues/I5D6I0) ）
8. 【server】导入仓库支持按仓库名搜索
9. 【server】修复导入仓库 GitLab 私有列显示错误
10. 副本功能优化,新增名称字段,项目列表快速查看
	（感谢[@爱琳琳真是太好了](https://gitee.com/qiqi513_admin) [Gitee issues I5E52A](https://gitee.com/dromara/Jpom/issues/I5E52A) ）
11. 【server】修复非默认工作空间快速安装节点未正常绑定到对应工作空间问题

------

## 2.9.1 (2022-06-16)

### 🐣 新增功能

1. 【server】SSH 终端新增标签页打开方式（感谢@hu丶向...🤡）

### 🐞 解决BUG、优化功能

1. 【server】db 安全检查时机前置(是否开启 web 访问),避免突然关闭数据库（感谢@信徒）
2. 【server】修复部分终端弹窗太小问题（感谢@syso）
3. 【server】修复重新初始化数据库异常问题（感谢@Dream、hu丶向...🤡）
4. 【server】修复系统管理中查看白名单配置报错
   （感谢[@all-around-badass](https://gitee.com/all-around-badass) [Gitee issues I5CGO7](https://gitee.com/dromara/Jpom/issues/I5CGO7) ）
5. 【server】优化监听到路由错误 刷新页面
6. 【server】修复控制台按钮不能正常使用的情况（感谢@😱 会 🎉、ccx2480）

------

## 2.9.0 (2022-06-13)

### 🐣 新增功能

1. 【server】SSH 终端新增全屏方式打开
   （感谢[@jaseeon](https://gitee.com/jaseeon) [Gitee issues I5BS52](https://gitee.com/dromara/Jpom/issues/I5BS52) ）
2. 【server】SSH 新增超时时间配置（感谢@带刺的玫瑰）
3. 【server】SFTP 新增超时时间（感谢@带刺的玫瑰）（特别感谢 [@hutool](https://hutool.cn) 作者紧急发版支持）

### 🐞 解决BUG、优化功能

1. 【server】升级 h2 版本，低版本存在漏洞(CVE-2021-23463)
2. 升级 SpringBoot、Hutool 版本
3. 【server】修复监控日志未存储到对应的工作空间（感谢@带刺的玫瑰）

### ⚠️ 注意

> 此版本为不兼容升级，需要手动升级操作数据相关迁移，操作流程如下：


> 下述流程仅供简单思路参考， 不同版本间存在部分差异，
> 详细流程还请差异完整文档：
> [https://jpom.top/pages/upgrade/2.8.x-to-2.9.x](https://jpom.top/pages/upgrade/2.8.x-to-2.9.x)

1. 导出低版本数据
	1. 启动程序参数里面添加 --backup-h2
	2. linux 环境举例：`sh /xxxx/Server.sh restart --backup-h2`
2. 将导出的低版本数据( sql 文件) 导入到新版本中
	1. 启动程序参数里面添加 `--replace-import-h2-sql=/xxxx.sql --transform-sql` (路径需要替换为第一步控制台输出的 sql
	   文件保存路径)
	2. linux 环境举例：`sh /xxxx/Server.sh restart --replace-import-h2-sql=/xxxx.sql --transform-sql`

✈️ [更详细的升级说明文档](https://jpom.top/pages/upgrade/2.8.x-to-2.9.x/)

------
