package com.fushb.jasperreport.controller;

import com.fushb.jasperreport.util.JasperReportUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName Demo01Controller
 * @Description TODO
 * @Author fushb
 * @Date 2022/7/7 10:45
 **/
@RestController
@RequestMapping("jasper/demo01")
public class Demo01Controller {

    @GetMapping("/getReport")
    public void getReport(@RequestParam("type") String reportType, HttpServletResponse response){
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", "中文测试");
        List<HashMap> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("Field1",  "姓名-" + i);
            item.put("Field2",  "年龄-" + i);
            list.add(item);
        }
        String path= JasperReportUtil.getJasperTemplateDir("demo05");
        try {
            JasperReportUtil.exportToPdf(path,parameters,list,response);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
