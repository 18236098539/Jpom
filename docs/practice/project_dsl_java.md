![](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/images/jpom_logo.png)

##  `简而轻的低侵入式在线构建、自动部署、日常运维、项目监控软件`

## 前言

> 本文主要介绍：
> 如何从零开始使用 Jpom 中的节点项目管理+脚本管理实现自定义管理项目（自定义启动项目，停止，查状态）
>
> 本文中服务端和插件端是安装在同一个服务器中的，实际操作时根据业务情况来安装
>
> 文中使用到的依赖环境版本仅供参考，实际使用中请根据业务情况来安装对应的版本

> 注意：本文采用一键安装同时基于 2.8.8 版本讲解,系统为 ubuntu

## 需要准备的环境

1. Jpom 服务端、Jpom 插件端（安装 jpom 需要 java 环境）

## 安装服务端

```
# 提前创建好文件夹 并且切换到对应到文件夹执行命令
mkdir -p /home/jpom/server/
apt install -y wget && wget -O install.sh https://dromara.gitee.io/jpom/docs/install.sh && bash install.sh Server jdk
```

#### 添加超级管理账号

> 添加一个超级管理员账号，请妥善保管此账号同时请设置安全度较强的密码

![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/install1.png)

#### 开启账号 MFA

> 为了系统安全，强烈建议超级管理员账号开启 MFA 两步验证

![install2](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/install2.png)


### 初始化服务端

![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/inits1.png)
![install2](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/inits2.png)


## 安装插件端

```
# 提前创建好文件夹 并且切换到对应到文件夹执行命令
mkdir -p /home/jpom/agent/
apt install -y wget && wget -O install.sh https://dromara.gitee.io/jpom/docs/install.sh && bash install.sh Agent jdk
```

![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/install-agent1.png)
![install2](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/install-agent2.png)

### 添加节点

注意要填写端口号哟

这里的节点账号密码和超级管理员账号密码是两个都行哟

节点账号密码在安装启动成功后会输出到控制台，请根据输出到内容填写。如果自己修改了账号密码则填写修改后到

![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/inita1.png)


### 配置白名单

项目白名单是为了防止随意配置目录，同时也为了保护系统目录

![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/inita2.png)


## 创建脚本

注意：`Ubuntu/Debian` 执行脚本如果出现错误
`Syntax error: "(" unexpected`
代码对于标准bash而言没有错，因为 `Ubuntu/Debian` 为了加快开机速度，用dash代替了传统的bash，是dash在捣鬼。

解决方法: 就是取消`dash`

执行：`sudo dpkg-reconfigure dash` 在选择项中选No，搞定了！

![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/add-script1.png)
![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/add-script2.png)

脚本内容：

```
#!/bin/bash
Tag="Application_#{PROJECT_ID}"
MainClass="org.springframework.boot.loader.JarLauncher"
Lib="#{PROJECT_PATH}"
Log="#{PROJECT_PATH}/run.log"
JVM="-server -Xms128m -Xmx128m -XX:PermSize=32M -XX:MaxNewSize=64m -XX:MaxPermSize=64m -Djava.awt.headless=true -XX:+CMSClassUnloadingEnabled -XX:+CMSPermGenSweepingEnabled"
echo $Tag
RETVAL="0"

# See how we were called.
function start() {
    echo  $Log 
    if [ ! -f $Log ]; then
        touch $Log
    fi
    nohup java $JVM -Dappliction=$Tag -Djava.ext.dirs=$Lib":${JAVA_HOME}/jre/lib/ext" $MainClass > $Log 2>&1 &
	sleep 3
    head -n 10 $Log
}


function stop() {
    pid=$(ps -ef | grep -v 'grep' | egrep $Tag| awk '{printf $2 " "}')
    if [ "$pid" != "" ]; then      
        echo -n "boot ( pid $pid) is running" 
        echo 
        echo -n $"Shutting down boot: "
        pid=$(ps -ef | grep -v 'grep' | egrep $Tag| awk '{printf $2 " "}')
        if [ "$pid" != "" ]; then
            echo "kill boot process"
            kill -9 "$pid"
        fi
        else 
             echo "boot is stopped" 
        fi

    status
}

function status()
{
    pid=$(ps -ef | grep -v 'grep' | egrep $Tag| awk '{printf $2 " "}')
    #echo "$pid"
    if [ "$pid" != "" ]; then
        echo "running:$pid"
    else
        echo "boot is stopped"
    fi
}

# See how we were called.
RETVAL="0"
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    status)
        status
        ;;
    *)
      usage
      ;;
esac

exit $RETVAL
```

> 1. 脚本里面至少需要实现的三件事：**启动**、**停止**、**查状态**
> 
> 2. 查状态输出最后一行需要是 `running:$pid` $pid 必须为数字且表示当前项目的进程 id ，如果不匹配项目则显示未运行

提供的示例里面将使用三个行数来实现：start、stop、status

里面的细节这里不过多的说明，可以自由发挥（给您足够的空间）

脚本里面支持的变量有：#{PROJECT_ID}、#{PROJECT_NAME}、#{PROJECT_PATH}

更新脚本示例：[✈️进入>>](/FQA/DSL.md)

## 创建项目

![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/add-project1.png)
![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/add-project2.png)

注意：
1. scriptArgs 为对应环节执行脚本传人的参数,这里可以使用空格隔空传人多给参数 
2. scriptId 需要填写当前节点里面存在的脚本的 id,脚本ID 可以在编辑脚本弹窗里面查看，如下图

![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/edit-script3.png)

### 上传项目文件

> 启动项目需要将文件上传到项目目录，这里我们上传一个简单的 SpringBoot 项目
> 
> 注意：项目里面可以在脚本中 使用 `#{PROJECT_PATH}` 获取
> 

> 使用小技巧：上传项目文件非必要操作，这里根据脚本内容里面执行的细节来决定是否需要上传，当然你脚本里面可以使用其他目录只不过这个目录就没有跟随当前项目管理而已

![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/upload-file1.png)
![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/upload-file2.png)

### 启动项目

![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/console1.png)
![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/console2.png)
![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/contsole3.png)

![install1](https://cdn.jsdelivr.net/gh/jiangzeyin/Jpom-site/tutorial/images/project_dsl_java/project-list1.png)