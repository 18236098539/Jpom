/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Code Technology Studio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.dromara.jpom.util;

import cn.hutool.core.map.SafeConcurrentHashMap;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.*;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.dromara.jpom.system.ExtConfigBean;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author bwcx_jzy
 * @since 24/1/17 017
 */
@Slf4j
public class ApacheExecUtil {

    private static final ShutdownHookProcessDestroyer shutdownHookProcessDestroyer = new ShutdownHookProcessDestroyer();
    private static final Map<String, Process> processMap = new SafeConcurrentHashMap<>();

    /**
     * 关闭 Process
     *
     * @param execId 执行Id
     */
    public static void kill(String execId) {
        Process process = processMap.remove(execId);
        if (process == null) {
            return;
        }
        CommandUtil.kill(process);
    }

    /**
     * 执行脚本
     *
     * @param scriptFile  脚本文件
     * @param baseDir     基础目录
     * @param env         环境变量
     * @param args        参数
     * @param logRecorder 日志记录
     * @return 退出码
     * @throws IOException io
     */
    public static int exec(String execId, File scriptFile, File baseDir, Map<String, String> env, String args, LogRecorder logRecorder) throws IOException {
        List<String> build = CommandUtil.build(scriptFile, args);
        String join = String.join(StrUtil.SPACE, build);
        CommandLine commandLine = CommandLine.parse(join);
        log.debug(join);
        Charset charset;
        try {
            charset = ExtConfigBean.getConsoleLogCharset();
        } catch (Exception e) {
            // 直接执行，使用默认编码格式
            charset = CharsetUtil.systemCharset();
        }
        Map<String, String> procEnvironment = EnvironmentUtils.getProcEnvironment();
        procEnvironment.putAll(env);
        final LogOutputStream logOutputStream = new LogOutputStream(1, charset) {
            @Override
            protected void processLine(String line, int logLevel) {
                logRecorder.info(line);
            }
        };
        // 重定向stdout和stderr到文件
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(logOutputStream, logOutputStream);

        // 创建执行器
        DefaultExecutor executor = DefaultExecutor.builder()
            .setExecuteStreamHandler(pumpStreamHandler)
            .setWorkingDirectory(baseDir)
            .get();
        //
        executor.setProcessDestroyer(new ProcessDestroyer() {
            @Override
            public boolean add(Process process) {
                processMap.put(execId, process);
                return shutdownHookProcessDestroyer.add(process);
            }

            @Override
            public boolean remove(Process process) {
                processMap.remove(execId);
                return shutdownHookProcessDestroyer.remove(process);
            }

            @Override
            public int size() {
                return shutdownHookProcessDestroyer.size();
            }
        });
        pumpStreamHandler.stop();
        // 执行，打印退出码
        try {
            return executor.execute(commandLine, procEnvironment);
        } catch (ExecuteException executeException) {
            logRecorder.systemWarning("执行异常：{}", executeException.getMessage());
            return executeException.getExitValue();
        }
    }
}
