![](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/images/jpom_logo.png)

##  `简而轻的低侵入式在线构建、自动部署、日常运维、项目监控软件`

### 前言

> 本文主要介绍：
> 如何从零开始使用 Jpom 中的构建功能实现将 node(vue) 项目从仓库中构建并通过 ssh 方式发布到服务器中,再配置 nginx。
> 
>  文中使用到的依赖环境版本仅供参考，实际使用中请根据业务情况来安装对应的版本


## 需要准备的环境

1. Jpom 服务端（安装 jpom 需要 java 环境）
2. 服务端所在服务器需要 node 环境
3. ssh 所在服务器需要 nginx 环境

# 操作步骤

## 第一步：安装 Jpom 服务端

> 目前安装 Jpom 服务端的方式有：一键安装、下载安装、编译打包安装、docker 安装，建议按照自己熟悉的方式来安装
> 
> 教程中使用一键安装的命令安装服务端

```
mkdir -p /home/jpom/server && cd /home/jpom/server
# 这里我们选择快速安装 jdk 实际中请根据自己情况选择
yum install -y wget && wget -O install.sh https://dromara.gitee.io/jpom/docs/install.sh && bash install.sh Server jdk
```

### 执行命令后控制台输出如下

![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/install1.png)
![install2](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/install2.png)
![install3](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/install3.png)
![install4](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/install4.png)

## 第二步：安装 node 环境

> 安装 node 环境、这里演示中我们使用 node 16.13.1 版本（项目实际依赖版本请根据业务情况调整）
>
> https://oss.npmmirror.com/dist/node/v16.13.1/node-v16.13.1-linux-x64.tar.gz
>

下载安装

```
wget -O node-v16.13.1-linux-x64.tar.gz https://oss.npmmirror.com/dist/node/v16.13.1/node-v16.13.1-linux-x64.tar.gz

mkdir -p /usr/node/ && tar -zxf node-v16.13.1-linux-x64.tar.gz  -C /usr/node/
```

![node1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/node1.png)

配置环境变量

```
echo '# node '>>/etc/profile
echo 'export NODE_HOME=/usr/node/node-v16.13.1-linux-x64'>>/etc/profile
echo 'export PATH=$NODE_HOME/bin:$PATH'>>/etc/profile
source /etc/profile
```

## 第三步：重启服务器端

> 重启服务器端，由于在启动服务端后安装端 node 环境，所以需要重启服务端让 node 环境在服务端中生效

```
sh /home/jpom/server/Server.sh restart
```

## 第四步：在 ssh 所在到服务器安装 nginx 环境

教程中使用 centos7 rpm 方式安装，实际中可以根据环境情况安装(如果是编译安装则需要)

```
rpm -ivh http://nginx.org/packages/centos/7/noarch/RPMS/nginx-release-centos-7-0.el7.ngx.noarch.rpm
yum install -y nginx
```

配置 nginx 

```
vim /etc/nginx/nginx.conf
```
建议使用 `include /etc/nginx/conf.d/*.conf` 方式来授权给 Jpom 来管理 nginx 配置文件

启动 nginx

```
systemctl start nginx
```

## 第五步：初始化 Jpom 服务端和配置 ssh 信息

访问：http://IP:2122 这里 ip 请更换为您服务器中第实际 ip

如果无法访问请优先检查 Jpom 访问是否正常运行、服务端防火墙、云服务器的安全组规则等网络原因

### 初始化系统管理员

第一次使用系统需要设置一个系统管理员账号（系统管理员账号密码有强度要求，请安装提示设置。同时也请您牢记系统管理员账号）

![install-user1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/install-user1.png)
![install-user2](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/install-user2.png)

### 在 Jpom 中添加 ssh 信息

1. ![ssh-list](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/ssh-list.png)
2. ![ssh-add](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/ssh-add.png)
3. ![ssh-add2](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/ssh-add2.png)

注意这里一定要配置：文件目录，文件目录为授权允许在 Jpom 管理的文件夹，这里为后面构建发布会使用到

## 第六步：创建构建仓库、创建构建信息

1. 添加仓库
   1. ![repository-list](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/repository-list.png)
   2. ![repository-add1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/repository-add1.png)
   3. ![repository-add2](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/repository-add2.png)
2. 添加构建信息
   1. ![build-list](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/build-list.png)
   2. ![build-add1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/build-add1.png)
   3. ![build-ssh](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/build-ssh.png)
   4. 构建命令解释：`cd antdv && npm i && npm run build` 由于仓库是多模块项目的仓库，首先需要切换到对应到目录（如果项目存在仓库根路径则不需要切换到对应的目录），如果执行对应到构建命令，由于 node 项目构建都需要装包这里先执行 `npm i`装包再执行 `npm run build` 多条命令用 && 拼接是为了保证上一条命令执行成功才执行下一条
   5. 产物目录解释：`antdv/dist` 由于当前项目存储到 antdv 目录中，构建完成将生成 dist 目录，那么这里需要填写：`antdv/dist`，这里注意需要添加仓库路径下面到相对路径
   6. ssh/目录，是选择发布到哪个 ssh 中的哪个目录里面
   7. 发布命令为文件上传成功后执行的命令，示例中随意执行的一个命令，实际请根据业务情况修改
3. 执行构建
   1. 第一次构建可能需要较长时间，是因为需要安装依赖包。加快构建速度也可以考虑修改镜像源地址
   2. 构建中请注意执行构建命令过程中是否发生错误信息影响到没有达到预期到构建结果（没有对应到构建产物）
   3. ![build-release-ssh](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/build-release-ssh.png)

## 第七步：配置 nginx 访问

1. 查看文件是否上传成功 `/home/web/testvue`
   2. ![ssh-view](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/ssh-view.png)
2. 配置 nginx 
   1. `vim /etc/nginx/conf.d/default.conf` 实际中请根据业务配置来变更配置路径和方式
   2. ![ssh-edit-nginx](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/ssh-edit-nginx.png)

## 第八步：愉快的使用前端项目

![use](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/build_node_release/use.png)


## Jpom 链接

官网：`https://jpom.io`

Gitee: `https://gitee.com/dromara/Jpom`

Github: `https://github.com/dromara/Jpom`

常见问题：`https://jpom-site.keepbx.cn/docs/#/FQA/FQA`

>> ![微信群：jpom66 (请备注 Jpom)](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/images/wx_qrcode.jpg)


