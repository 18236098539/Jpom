/*
 * Copyright (c) 2019 Code Technology Studio
 * Jpom is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * 			http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package org.dromara.jpom.controller.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.keepbx.jpom.IJsonMessage;
import cn.keepbx.jpom.model.JsonMessage;
import com.alibaba.fastjson2.JSONObject;
import org.dromara.jpom.common.BaseServerController;
import org.dromara.jpom.common.ServerConst;
import org.dromara.jpom.common.ServerOpenApi;
import org.dromara.jpom.common.UrlRedirectUtil;
import org.dromara.jpom.common.forward.NodeForward;
import org.dromara.jpom.common.forward.NodeUrl;
import org.dromara.jpom.common.validator.ValidatorItem;
import org.dromara.jpom.common.validator.ValidatorRule;
import org.dromara.jpom.model.PageResultDto;
import org.dromara.jpom.model.data.NodeModel;
import org.dromara.jpom.model.data.WorkspaceEnvVarModel;
import org.dromara.jpom.model.user.UserModel;
import org.dromara.jpom.permission.ClassFeature;
import org.dromara.jpom.permission.Feature;
import org.dromara.jpom.permission.MethodFeature;
import org.dromara.jpom.service.system.WorkspaceEnvVarService;
import org.dromara.jpom.service.user.TriggerTokenLogServer;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bwcx_jzy
 * @since 2021/12/10
 */

@RestController
@Feature(cls = ClassFeature.SYSTEM_WORKSPACE_ENV)
@RequestMapping(value = "/system/workspace_env/")
public class WorkspaceEnvVarController extends BaseServerController {

    private final WorkspaceEnvVarService workspaceEnvVarService;
    private final TriggerTokenLogServer triggerTokenLogServer;

    public WorkspaceEnvVarController(WorkspaceEnvVarService workspaceEnvVarService,
                                     TriggerTokenLogServer triggerTokenLogServer) {
        this.workspaceEnvVarService = workspaceEnvVarService;
        this.triggerTokenLogServer = triggerTokenLogServer;
    }

    /**
     * 分页列表
     *
     * @return json
     */
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Feature(method = MethodFeature.LIST)
    public IJsonMessage<PageResultDto<WorkspaceEnvVarModel>> list(HttpServletRequest request) {
        PageResultDto<WorkspaceEnvVarModel> listPage = workspaceEnvVarService.listPage(request);
        listPage.each(workspaceEnvVarModel -> {
            Integer privacy = workspaceEnvVarModel.getPrivacy();
            if (privacy != null && privacy == 1) {
                workspaceEnvVarModel.setValue(StrUtil.EMPTY);
            }
        });
        return JsonMessage.success("", listPage);
    }

    /**
     * 全部环境变量
     *
     * @return json
     */
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Feature(method = MethodFeature.LIST)
    public IJsonMessage<List<WorkspaceEnvVarModel>> allList(HttpServletRequest request) {
        List<WorkspaceEnvVarModel> list = workspaceEnvVarService.listByWorkspace(request);
        list.forEach(workspaceEnvVarModel -> {
            Integer privacy = workspaceEnvVarModel.getPrivacy();
            if (privacy != null && privacy == 1) {
                workspaceEnvVarModel.setValue(StrUtil.EMPTY);
            }
        });
        return JsonMessage.success("", list);
    }

    /**
     * 编辑变量
     *
     * @param workspaceId 空间id
     * @param name        变量名称
     * @param value       值
     * @param description 描述
     * @return json
     */
    @PostMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    @Feature(method = MethodFeature.EDIT)
    public IJsonMessage<Object> edit(String id,
                                     @ValidatorItem String workspaceId,
                                     @ValidatorItem String name,
                                     String value,
                                     @ValidatorItem String description,
                                     String privacy,
                                     String nodeIds) {
        if (!getUser().isSystemUser()) {
            Assert.state(!StrUtil.equals(workspaceId, ServerConst.WORKSPACE_GLOBAL), "全局工作空间变量请到系统管理修改");
        }

        workspaceEnvVarService.checkUserWorkspace(workspaceId);

        this.checkInfo(id, name, workspaceId);
        boolean privacyBool = BooleanUtil.toBoolean(privacy);
        //
        WorkspaceEnvVarModel workspaceModel = new WorkspaceEnvVarModel();
        workspaceModel.setName(name);
        if (privacyBool) {
            if (StrUtil.isNotEmpty(value)) {
                workspaceModel.setValue(value);
            } else {
                // 隐私字段 创建必填
                Assert.state(StrUtil.isNotEmpty(id), "请填写参数值");
            }
        } else {
            // 非隐私必填
            Assert.hasText(value, "请填写参数值");
            workspaceModel.setValue(value);
        }
        workspaceModel.setWorkspaceId(workspaceId);
        workspaceModel.setNodeIds(nodeIds);
        workspaceModel.setDescription(description);
        //
        String oldNodeIds = null;
        if (StrUtil.isEmpty(id)) {
            // 创建
            workspaceModel.setPrivacy(privacyBool ? 1 : 0);
            workspaceEnvVarService.insert(workspaceModel);
        } else {
            WorkspaceEnvVarModel byKey = workspaceEnvVarService.getByKey(id);
            Assert.notNull(byKey, "没有对应的数据");
            Assert.state(StrUtil.equals(workspaceId, byKey.getWorkspaceId()), "工作空间错误,或者没有权限编辑此数据");
            oldNodeIds = byKey.getNodeIds();
            workspaceModel.setId(id);
            // 不能修改
            workspaceModel.setPrivacy(null);
            workspaceEnvVarService.updateById(workspaceModel);
        }
        this.syncNodeEnvVar(workspaceModel, oldNodeIds);
        return JsonMessage.success("操作成功");
    }

    private void syncDelNodeEnvVar(String name, Collection<String> delNode, String workspaceId) {
        for (String s : delNode) {
            NodeModel byKey = nodeService.getByKey(s);
            Assert.state(StrUtil.equals(workspaceId, byKey.getWorkspaceId()), "选择节点错误");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            JsonMessage<String> jsonMessage = NodeForward.request(byKey, NodeUrl.Workspace_EnvVar_Delete, jsonObject);
            Assert.state(jsonMessage.success(), "处理 " + byKey.getName() + " 节点删除脚本失败" + jsonMessage.getMsg());
        }
    }

    private void syncNodeEnvVar(WorkspaceEnvVarModel workspaceEnvVarModel, String oldNode) {
        String workspaceId = workspaceEnvVarModel.getWorkspaceId();
        List<String> newNodeIds = StrUtil.splitTrim(workspaceEnvVarModel.getNodeIds(), StrUtil.COMMA);
        List<String> oldNodeIds = StrUtil.splitTrim(oldNode, StrUtil.COMMA);
        Collection<String> delNode = CollUtil.subtract(oldNodeIds, newNodeIds);
        // 删除
        this.syncDelNodeEnvVar(workspaceEnvVarModel.getName(), delNode, workspaceId);
        // 更新
        for (String newNodeId : newNodeIds) {
            NodeModel byKey = nodeService.getByKey(newNodeId);
            Assert.state(StrUtil.equals(workspaceId, byKey.getWorkspaceId()), "选择节点错误");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("description", workspaceEnvVarModel.getDescription());
            jsonObject.put("name", workspaceEnvVarModel.getName());
            jsonObject.put("privacy", workspaceEnvVarModel.getPrivacy());
            if (StrUtil.isNotEmpty(workspaceEnvVarModel.getValue())) {
                jsonObject.put("value", workspaceEnvVarModel.getValue());
            } else {
                // 查询
                WorkspaceEnvVarModel byKeyExits = workspaceEnvVarService.getByKey(workspaceEnvVarModel.getId());
                jsonObject.put("value", byKeyExits.getValue());
            }
            JsonMessage<String> jsonMessage = NodeForward.request(byKey, NodeUrl.Workspace_EnvVar_Update, jsonObject);
            Assert.state(jsonMessage.getCode() == 200, "处理 " + byKey.getName() + " 节点同步脚本失败" + jsonMessage.getMsg());
        }
    }

    private void checkInfo(String id, String name, String workspaceId) {
        Validator.validateGeneral(name, 1, 50, "变量名称 1-50 英文字母 、数字和下划线");
        //
        Entity entity = Entity.create();
        entity.set("name", name);
        if (!StrUtil.equals(workspaceId, ServerConst.WORKSPACE_GLOBAL)) {
            entity.set("workspaceId", CollUtil.newArrayList(workspaceId, ServerConst.WORKSPACE_GLOBAL));
        }
        if (StrUtil.isNotEmpty(id)) {
            entity.set("id", StrUtil.format(" <> {}", id));
        }
        boolean exists = workspaceEnvVarService.exists(entity);
        Assert.state(!exists, "对应的变量名称已经存在啦");
    }


    /**
     * 删除变量
     *
     * @param id 变量 ID
     * @return json
     */
    @GetMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @Feature(method = MethodFeature.DEL)
    public IJsonMessage<Object> delete(@ValidatorItem(value = ValidatorRule.NOT_BLANK, msg = "数据 id 不能为空") String id,
                                       @ValidatorItem String workspaceId) {
        if (!getUser().isSystemUser()) {
            Assert.state(!StrUtil.equals(workspaceId, ServerConst.WORKSPACE_GLOBAL), "全局工作空间变量请到系统管理修改");
        }
        workspaceEnvVarService.checkUserWorkspace(workspaceId);
        WorkspaceEnvVarModel byKey = workspaceEnvVarService.getByKey(id);
        Assert.notNull(byKey, "没有对应的数据");
        Assert.state(StrUtil.equals(workspaceId, byKey.getWorkspaceId()), "选择工作空间错误");
        String oldNodeIds = byKey.getNodeIds();
        List<String> delNode = StrUtil.splitTrim(oldNodeIds, StrUtil.COMMA);
        this.syncDelNodeEnvVar(byKey.getName(), delNode, workspaceId);
        // 删除信息
        workspaceEnvVarService.delByKey(id);
        return JsonMessage.success("删除成功");
    }

    /**
     * get a trigger url
     *
     * @param id id
     * @return json
     */
    @RequestMapping(value = "trigger-url", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Feature(method = MethodFeature.EDIT)
    public IJsonMessage<Map<String, String>> getTriggerUrl(@ValidatorItem String id, @ValidatorItem String workspaceId, String rest, HttpServletRequest request) {
        workspaceEnvVarService.checkUserWorkspace(workspaceId);
        WorkspaceEnvVarModel item = workspaceEnvVarService.getByKey(id);
        Assert.notNull(item, "没有对应的环境变量");
        Assert.state(StrUtil.equals(workspaceId, item.getWorkspaceId()), "选择工作空间错误");
        //
        Assert.state(ObjectUtil.defaultIfNull(item.getPrivacy(), -1) == 0, "隐私变量不能生成触发器");
        UserModel user = getUser();
        WorkspaceEnvVarModel updateInfo;
        if (StrUtil.isEmpty(item.getTriggerToken()) || StrUtil.isNotEmpty(rest)) {
            updateInfo = new WorkspaceEnvVarModel();
            updateInfo.setId(id);
            updateInfo.setTriggerToken(triggerTokenLogServer.restToken(item.getTriggerToken(), workspaceEnvVarService.typeName(),
                item.getId(), user.getId()));
            workspaceEnvVarService.updateById(updateInfo);
        } else {
            updateInfo = item;
        }
        Map<String, String> map = this.getBuildToken(updateInfo, request);
        return JsonMessage.success(StrUtil.isEmpty(rest) ? "ok" : "重置成功", map);
    }

    private Map<String, String> getBuildToken(WorkspaceEnvVarModel item, HttpServletRequest request) {
        String contextPath = UrlRedirectUtil.getHeaderProxyPath(request, ServerConst.PROXY_PATH);
        String url = ServerOpenApi.SERVER_ENV_VAR_TRIGGER_URL.
            replace("{id}", item.getId()).
            replace("{token}", item.getTriggerToken());
        String triggerBuildUrl = String.format("/%s/%s", contextPath, url);
        Map<String, String> map = new HashMap<>(10);
        map.put("triggerUrl", FileUtil.normalize(triggerBuildUrl));

        map.put("id", item.getId());
        map.put("token", item.getTriggerToken());
        return map;
    }
}
