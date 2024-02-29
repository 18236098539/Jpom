/*
 * Copyright (c) 2019 Code Technology Studio
 * Jpom is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * 			http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package org.dromara.jpom.controller.manage;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import cn.keepbx.jpom.IJsonMessage;
import cn.keepbx.jpom.model.JsonMessage;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.dromara.jpom.common.BaseAgentController;
import org.dromara.jpom.common.commander.CommandOpResult;
import org.dromara.jpom.common.commander.ProjectCommander;
import org.dromara.jpom.common.validator.ValidatorItem;
import org.dromara.jpom.common.validator.ValidatorRule;
import org.dromara.jpom.model.data.NodeProjectInfoModel;
import org.dromara.jpom.socket.ConsoleCommandOp;
import org.dromara.jpom.util.CommandUtil;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目文件管理
 *
 * @author bwcx_jzy
 * @since 2019/4/17
 */
@RestController
@RequestMapping(value = "/manage/")
@Slf4j
public class ProjectStatusController extends BaseAgentController {
    private final ProjectCommander projectCommander;

    public ProjectStatusController(ProjectCommander projectCommander) {
        this.projectCommander = projectCommander;
    }

    /**
     * 获取项目的进程id
     *
     * @param id 项目id
     * @return json
     */
    @RequestMapping(value = "getProjectStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IJsonMessage<JSONObject> getProjectStatus(@ValidatorItem(value = ValidatorRule.NOT_BLANK, msg = "项目id 不正确") String id, String getCopy) {
        NodeProjectInfoModel nodeProjectInfoModel = tryGetProjectInfoModel();
        Assert.notNull(nodeProjectInfoModel, "项目id不存在");
        JSONObject jsonObject = new JSONObject();
        try {
            CommandUtil.openCache();
            try {
                CommandOpResult status = projectCommander.execCommand(ConsoleCommandOp.status, nodeProjectInfoModel);
                jsonObject.put("pId", status.getPid());
                jsonObject.put("pIds", status.getPids());
                jsonObject.put("statusMsg", status.getStatusMsg());
            } catch (Exception e) {
                log.error("获取项目pid 失败", e);
            }
        } finally {
            CommandUtil.closeCache();
        }
        return JsonMessage.success("", jsonObject);
    }

    /**
     * 获取项目的运行端口
     *
     * @param ids ids
     * @return obj
     */
    @RequestMapping(value = "getProjectPort", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IJsonMessage<JSONObject> getProjectPort(String ids) {
        Assert.hasText(ids, "没有要获取的信息");
        JSONArray jsonArray = JSONArray.parseArray(ids);
        JSONObject jsonObject = new JSONObject();
        try {
            CommandUtil.openCache();
            for (Object object : jsonArray) {
                String item = object.toString();
                JSONObject itemObj = new JSONObject();
                try {
                    NodeProjectInfoModel projectInfoServiceItem = projectInfoService.getItem(item);
                    itemObj.put("name", projectInfoServiceItem.getName());
                    CommandOpResult commandOpResult = projectCommander.execCommand(ConsoleCommandOp.status, projectInfoServiceItem);
                    Integer pid = commandOpResult.getPid();
                    //
                    itemObj.put("pid", pid);
                    itemObj.put("pids", commandOpResult.getPids());
                    itemObj.put("statusMsg", commandOpResult.getStatusMsg());
                    if (StrUtil.isNotEmpty(commandOpResult.getPorts())) {
                        itemObj.put("port", commandOpResult.getPorts());
                    } else {
                        String port = projectCommander.getMainPort(pid);
                        itemObj.put("port", port);
                    }
                } catch (Exception e) {
                    log.error("获取端口错误", e);
                    itemObj.put("error", e.getMessage());
                }
                jsonObject.put(item, itemObj);
            }
        } finally {
            CommandUtil.closeCache();
        }
        return JsonMessage.success("", jsonObject);
    }


    @RequestMapping(value = "operate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IJsonMessage<CommandOpResult> operate(@ValidatorItem(value = ValidatorRule.NOT_BLANK, msg = "项目id 不正确") String id,
                                                 @ValidatorItem String opt) throws Exception {
        NodeProjectInfoModel item = projectInfoService.getItem(id);
        Assert.notNull(item, "没有找到对应的项目");
        ConsoleCommandOp consoleCommandOp = EnumUtil.fromStringQuietly(ConsoleCommandOp.class, opt);
        Assert.notNull(consoleCommandOp, "请选择操作类型");
        Assert.state(consoleCommandOp.isCanOpt(), "不支持当前操作：" + opt);
        CommandOpResult result = projectCommander.execCommand(consoleCommandOp, item);
        return new JsonMessage<>(result.isSuccess() ? 200 : 201, result.isSuccess() ? "操作成功" : "操作失败:" + result.msgStr(), result);
    }
}
