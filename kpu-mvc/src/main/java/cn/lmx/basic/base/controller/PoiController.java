package cn.lmx.basic.base.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.ExcelXorHtmlUtil;
import cn.afterturn.easypoi.excel.entity.ExcelToHtmlParams;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.lmx.basic.annotation.log.SysLog;
import cn.lmx.basic.annotation.security.PreAuth;
import cn.lmx.basic.base.R;
import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.base.request.PageParams;
import cn.lmx.basic.utils.BeanPlusUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <Entity>    实体
 * @param <PageQuery> 分页查询参数
 * @author lmx
 * @version 1.0
 * @description: 导入导出
 * @date 2023/7/4 14:27
 */
public interface PoiController<Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO> extends PageController<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> {
    /**
     * 获取实体的类型
     *
     * @return 实体的类型
     */
    default Class<?> getExcelClass() {
        return getEntityClass();
    }


    /**
     * 导出Excel
     *
     * @param params   参数
     * @param request  请求
     * @param response 响应
     */
    @ApiOperation(value = "导出Excel")
    @RequestMapping(value = "/export", method = RequestMethod.POST, produces = "application/octet-stream")
    @SysLog("'导出Excel:'.concat(#params.extra[" + NormalExcelConstants.FILE_NAME + "]?:'')")
    @PreAuth("hasAnyPermission('{}export')")
    default void exportExcel(@RequestBody @Validated PageParams<PageQuery> params, HttpServletRequest request, HttpServletResponse response) {
        ExportParams exportParams = getExportParams(params);

        List<?> list = findExportList(params);

        Map<String, Object> map = new HashMap<>(7);
        map.put(NormalExcelConstants.DATA_LIST, list);
        map.put(NormalExcelConstants.CLASS, getExcelClass());
        map.put(NormalExcelConstants.PARAMS, exportParams);
        Object fileName = params.getExtra().getOrDefault(NormalExcelConstants.FILE_NAME, "临时文件");
        map.put(NormalExcelConstants.FILE_NAME, fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
    }


    /**
     * 查询待导出的数据， 子类可以重写
     *
     * @param params params
     * @return java.util.List<?>
     * @author lmx
     * @date 2023/7/4 14:27
     */
    default List<?> findExportList(PageParams<PageQuery> params) {
        params.setSize(params.getSize() == -1 ? Convert.toLong(Integer.MAX_VALUE) : params.getSize());
        IPage<Entity> page = query(params);
        return BeanPlusUtil.toBeanList(page.getRecords(), getExcelClass());
    }

    /**
     * 预览Excel
     *
     * @param params 预览参数
     * @return 预览html
     */
    @ApiOperation(value = "预览Excel")
    @SysLog("'预览Excel:' + (#params.extra[" + NormalExcelConstants.FILE_NAME + "]?:'')")
    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    @PreAuth("hasAnyPermission('{}export')")
    default R<String> preview(@RequestBody @Validated PageParams<PageQuery> params) {
        ExportParams exportParams = getExportParams(params);
        List<?> list = findExportList(params);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, getExcelClass(), list);
        return success(ExcelXorHtmlUtil.excelToHtml(new ExcelToHtmlParams(workbook)));
    }

    /**
     * 使用自动生成的实体+注解方式导入
     * 建议自建实体使用
     *
     * @param simpleFile 上传文件
     * @param request    请求
     * @param response   响应
     * @return 是否导入成功
     * @throws Exception 异常
     */
    @ApiOperation(value = "导入Excel")
    @PostMapping(value = "/import")
    @SysLog(value = "'导入Excel:' + #simpleFile?.originalFilename", request = false)
    @PreAuth("hasAnyPermission('{}import')")
    default R<Boolean> importExcel(@RequestParam(value = "file") MultipartFile simpleFile, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        ImportParams params = new ImportParams();

        params.setTitleRows(StrUtil.isEmpty(request.getParameter("titleRows")) ? 0 : Convert.toInt(request.getParameter("titleRows")));
        params.setHeadRows(StrUtil.isEmpty(request.getParameter("headRows")) ? 1 : Convert.toInt(request.getParameter("headRows")));
        List<Map<String, String>> list = ExcelImportUtil.importExcel(simpleFile.getInputStream(), Map.class, params);

        if (list != null && !list.isEmpty()) {
            return handlerImport(list);
        }
        return validFail("导入Excel无有效数据！");
    }

    /**
     * 转换后保存
     *
     * @param list 集合
     * @return 是否成功
     */
    default R<Boolean> handlerImport(List<Map<String, String>> list) {
        return R.successDef(null, "请在子类Controller重写导入方法，实现导入逻辑");
    }

    /**
     * 构建导出参数
     * 子类可以重写
     *
     * @param params 分页参数
     * @return 导出参数
     */
    default ExportParams getExportParams(PageParams<PageQuery> params) {
        Object title = params.getExtra().get("title");
        Object type = params.getExtra().getOrDefault("type", ExcelType.XSSF.name());
        Object sheetName = params.getExtra().getOrDefault("sheetName", "SheetName");

        ExcelType excelType = ExcelType.XSSF.name().equals(type) ? ExcelType.XSSF : ExcelType.HSSF;
        ExportParams ep = new ExportParams(title == null ? null : String.valueOf(title), sheetName.toString(), excelType);
        enhanceExportParams(ep);
        return ep;
    }

    /**
     * 子类增强ExportParams
     *
     * @param ep ep
     * @author lmx
     * @date 2023/7/4 14:27
     */
    default void enhanceExportParams(ExportParams ep) {
    }
}
