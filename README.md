<p align="center">
	<a href="https://jpom.top/"  target="_blank">
	    <img src="https://jpom.top/images/logo/jpom_logo.svg" width="400" alt="logo">
	</a>
</p>
<p align="center">
	<strong>简而轻的低侵入式在线构建、自动部署、日常运维、项目监控软件</strong>
</p>

<p align="center">
	<a target="_blank" href="https://gitee.com/dromara/Jpom">
        <img src='https://gitee.com/dromara/Jpom/badge/star.svg?theme=gvp' alt='gitee star'/>
    </a>
 	<a target="_blank" href="https://github.com/dromara/Jpom">
		<img src="https://img.shields.io/github/stars/dromara/Jpom.svg?style=social" alt="github star"/>
    </a>
    <a target="_blank" href="https://gitee.com/dromara/Jpom">
        <img src='https://img.shields.io/github/license/dromara/Jpom?style=flat' alt='license'/>
    </a>
    <a target="_blank" href="https://gitee.com/dromara/Jpom">
        <img src='https://img.shields.io/badge/JDK-1.8.0_40+-green.svg' alt='jdk'/>
    </a>
</p>

<p align="center">
    <a target="_blank" href="https://travis-ci.org/dromara/Jpom">
        <img src='https://travis-ci.org/dromara/Jpom.svg?branch=master' alt='travis'/>
    </a>
    <a target="_blank" href="https://www.codacy.com/gh/dromara/Jpom/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=dromara/Jpom&amp;utm_campaign=Badge_Grade">
      <img src="https://app.codacy.com/project/badge/Grade/843b953f1446449c9a075e44ea778336" alt="codacy"/>
    </a>
</p>

<p align="center">
	👉 <a target="_blank" href="https://jpom.top/">https://jpom.top/</a> 👈
</p>
<p align="center">
	备用地址：<a target="_blank" href="https://jpom.top/">https://jpom.top/</a> 
</p>

# 使用 git submodule 合并管理代码和文档仓库


```shell
[submodule "jpom-parent"]
	path = jpom-parent
	url = git@gitee.com:dromara/Jpom.git
	branch = master
[submodule "docs"]
	path = docs
	url = git@gitee.com:dromara/Jpom.git
	branch = docs
[submodule "download-link"]
	path = download-link
	url = git@gitee.com:dromara/Jpom.git
	branch = download_link
```

```shell
git submodule add -b master git@gitee.com:dromara/Jpom.git jpom-parent

git submodule add -b docs git@gitee.com:dromara/Jpom.git docs
git submodule add -b download_link git@gitee.com:dromara/Jpom.git download-link
git submodule add -b aliyun-computenest git@gitee.com:dromara/Jpom.git aliyun-computenest
```

### 执行后需要重启 idea 才能生效

```shell
git submodule init
git submodule sync 
git submodule update --init
git submodule update --remote
```

```shell
# 创建空白分支 (docs-pages)
git checkout --orphan docs-pages
git rm -rf .
git push --set-upstream origin docs-pages
```

## 换行符不生效

```shell
git config --global core.autocrlf false
```

## git config

```shell
git config --global user.name "bwcx_jzy"
git config --global user.email bwcx_jzy@163.com 
git config --global core.autocrlf false
```

```shell
chmod 600 ~/.ssh/id_rsa ~/.ssh/id_rsa.pub
```

## 同步 tag

git tag -l | xargs git tag -d #删除所有本地分支
git fetch origin --prune #从远程拉取所有信息

## 版权

```shell
mvn license:format
```