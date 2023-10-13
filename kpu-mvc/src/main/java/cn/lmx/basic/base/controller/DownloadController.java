package cn.lmx.basic.base.controller;

import cn.hutool.core.util.URLUtil;
import cn.lmx.basic.base.entity.SuperEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * @param <Entity>    实体
 * @param <PageQuery> 分页查询参数
 * @author lmx
 * @version 1.0
 * @description: 导入导出
 * @date 2023/7/4 14:27
 */
public interface DownloadController<Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO> extends PageController<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> {
    /**
     * 获取实体的类型
     *
     * @return 实体的类型
     */
    default Class<?> getExcelClass() {
        return getEntityClass();
    }

    /**
     * 下载文件
     *
     * @param data     数据
     * @param fileName 文件吗
     * @param response
     * @date 2023/10/9 23:35
     */
    default void write(byte[] data, String fileName, HttpServletResponse response) {
        try {
            response.reset();

            //处理中文文件名 转换成base64
            fileName = URLUtil.encode(fileName, StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getOutputStream().write(data);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
