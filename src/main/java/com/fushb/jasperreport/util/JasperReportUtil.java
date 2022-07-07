package com.fushb.jasperreport.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JasperReportUtil
 * @Description jasper打印工具类
 * @Author fushb
 * @Date 2022/7/7 10:57
 **/
public class JasperReportUtil {
    private final static String baseDir="templates";

    /**
     * @Author FuShb
     * @Description 根据模板名获取jasper模板路径  TODO 后续可修改为数据库存储路径
     * @Date 11:00 2022/7/7
     * @Param [templateName]
     * @return java.lang.String
     **/
    public static String getJasperTemplateDir(String templateName){
        return baseDir+ File.separator+templateName+".jasper";
    }

    /**
     * @Author FuShb
     * @Description 获取响应得contentType
     * @Date 11:03 2022/7/7
     * @Param [type]
     * @return java.lang.String
     **/
    private static String getContentType(ReportType type) {
        String contentType;
        switch (type) {
            case HTML:
                contentType = "text/html;charset=utf-8";
                break;
            case PDF:
                contentType = "application/pdf";
                break;
            case XLS:
                contentType = "application/vnd.ms-excel";
                break;
            case XLSX:
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                break;
            case XML:
                contentType = "text/xml";
                break;
            case RTF:
                contentType = "application/rtf";
                break;
            case CSV:
                contentType = "text/plain";
                break;
            case DOC:
                contentType = "application/msword";
                break;
            default:
                contentType = "text/html;charset=utf-8";
        }
        return contentType;
    }

    /**
     * @Author FuShb
     * @Description 获取jasperPrint对象
     * @Date 11:11 2022/7/7
     * @Param [jasperStream 模板得输入流, parameters 模板输入参数, list 打印内容]
     * @return net.sf.jasperreports.engine.JasperPrint
     **/
    public static JasperPrint getJasperPrint(InputStream jasperStream, Map parameters, List<?> list) throws JRException {
        JRDataSource dataSource=null;
        if(ObjectUtils.isEmpty(list)){
            dataSource=new JREmptyDataSource();
        }else {
            dataSource=new JRBeanCollectionDataSource(list);
        }
        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperStream,parameters,dataSource);
        return jasperPrint;
    }

    /**
     * @Author FuShb
     * @Description pdf输出
     * @Date 11:19 2022/7/7
     * @Param [jasperPath, parameters, list, response]
     * @return void
     **/
    public static void exportToPdf(String jasperPath, Map parameters, List<?>list, HttpServletResponse response) throws IOException, JRException {
        OutputStream out=response.getOutputStream();
        ClassPathResource resource=new ClassPathResource(jasperPath);
        response.setContentType(getContentType(ReportType.PDF));
        InputStream jasperStream=resource.getInputStream();
        JasperPrint jasperPrint=getJasperPrint(jasperStream,parameters,list);
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
        out.flush();
        out.close();
    }

    enum ReportType {
        HTML,
        PDF,
        XLS,
        XLSX,
        XML,
        RTF,
        CSV,
        DOC
    }
}
