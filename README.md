<p align="center">
	<a href="https://jpom.io/"  target="_blank">
	    <img src="https://jpom-docs.keepbx.cn/images/jpom_logo.png" width="400" alt="logo">
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
	<a target="_blank" href="https://jpom-docs.keepbx.cn/images/wx-qrcode-praise.png">
		<img src='https://img.shields.io/badge/%E5%BE%AE%E4%BF%A1%E7%BE%A4(%E8%AF%B7%E5%A4%87%E6%B3%A8%3AJpom)-jpom66-yellowgreen.svg' alt='jpom66 请备注jpom'/>
	</a>
</p>

<p align="center">
	👉 <a target="_blank" href="https://jpom.io/">https://jpom.io/</a> | <a target="_blank" href="https://demo.jpom.io/">https://demo.jpom.io/</a>👈
</p>
<p align="center">
	备用地址：<a target="_blank" href="https://jpom-docs.keepbx.cn/">https://jpom-docs.keepbx.cn/</a> | <a target="_blank" href="https://jpom.keepbx.cn/">https://jpom.keepbx.cn/</a>
</p>

# 使用 git submodule 合并管理代码和文档仓库


```shell
git submodule add -b master https://gitee.com/dromara/Jpom.git jpom-parent
```

```shell
git submodule add -b docs https://gitee.com/dromara/Jpom.git docs
```


## idea modules 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="ProjectModuleManager">
    <modules>
      <module fileurl="file://$PROJECT_DIR$/.idea/Jpom.iml" filepath="$PROJECT_DIR$/.idea/Jpom.iml" />
      <module fileurl="file://$PROJECT_DIR$/jpom-parent/modules/agent/agent.iml" filepath="$PROJECT_DIR$/jpom-parent/modules/agent/agent.iml" />
      <module fileurl="file://$PROJECT_DIR$/jpom-parent/modules/sub-plugin/auto-charset-jchardet/auto-charset-jchardet.iml" filepath="$PROJECT_DIR$/jpom-parent/modules/sub-plugin/auto-charset-jchardet/auto-charset-jchardet.iml" />
      <module fileurl="file://$PROJECT_DIR$/jpom-parent/modules/common/common.iml" filepath="$PROJECT_DIR$/jpom-parent/modules/common/common.iml" />
      <module fileurl="file://$PROJECT_DIR$/jpom-parent/modules/sub-plugin/db-h2/db-h2.iml" filepath="$PROJECT_DIR$/jpom-parent/modules/sub-plugin/db-h2/db-h2.iml" />
      <module fileurl="file://$PROJECT_DIR$/jpom-parent/modules/sub-plugin/docker-cli/docker-cli.iml" filepath="$PROJECT_DIR$/jpom-parent/modules/sub-plugin/docker-cli/docker-cli.iml" />
      <module fileurl="file://$PROJECT_DIR$/docs/docs.iml" filepath="$PROJECT_DIR$/docs/docs.iml" />
      <module fileurl="file://$PROJECT_DIR$/jpom-parent/modules/sub-plugin/email/email.iml" filepath="$PROJECT_DIR$/jpom-parent/modules/sub-plugin/email/email.iml" />
      <module fileurl="file://$PROJECT_DIR$/jpom-parent/modules/sub-plugin/git-clone/git-clone.iml" filepath="$PROJECT_DIR$/jpom-parent/modules/sub-plugin/git-clone/git-clone.iml" />
      <module fileurl="file://$PROJECT_DIR$/jpom-parent/jpom-parent.iml" filepath="$PROJECT_DIR$/jpom-parent/jpom-parent.iml" />
      <module fileurl="file://$PROJECT_DIR$/jpom-parent/modules/sub-plugin/jpom-plugins-parent.iml" filepath="$PROJECT_DIR$/jpom-parent/modules/sub-plugin/jpom-plugins-parent.iml" />
      <module fileurl="file://$PROJECT_DIR$/jpom-parent/modules/sub-plugin/maven-plugin/maven-plugin.iml" filepath="$PROJECT_DIR$/jpom-parent/modules/sub-plugin/maven-plugin/maven-plugin.iml" />
      <module fileurl="file://$PROJECT_DIR$/jpom-parent/modules/server/server.iml" filepath="$PROJECT_DIR$/jpom-parent/modules/server/server.iml" />
      <module fileurl="file://$PROJECT_DIR$/jpom-parent/modules/sub-plugin/svn-clone/svn-clone.iml" filepath="$PROJECT_DIR$/jpom-parent/modules/sub-plugin/svn-clone/svn-clone.iml" />
      <module fileurl="file://$PROJECT_DIR$/jpom-parent/modules/sub-plugin/webhook/webhook.iml" filepath="$PROJECT_DIR$/jpom-parent/modules/sub-plugin/webhook/webhook.iml" />
    </modules>
  </component>
</project>
```