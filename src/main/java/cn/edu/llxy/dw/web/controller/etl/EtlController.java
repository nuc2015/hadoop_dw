package cn.edu.llxy.dw.web.controller.etl;

import cn.edu.llxy.dw.service.etl.EtlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/etl")
public class EtlController {

    @Autowired
    private EtlService etlService;

    /**
     * 查询表数据
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/queryTableData")
    @ResponseBody
    public Map<String, Object> queryTableData(HttpServletRequest request) throws Exception{
        Map<String, Object> result = new HashMap<>();
        String nodeId = request.getParameter("nodeId");
        List<Map<String, Object>> dataList = etlService.queryTableData(nodeId);
        if(dataList.size() > 0){
            Map<String, Object> row = dataList.get(0);
            Set<String> cols = row.keySet();
            result.put("cols", cols.toArray());
        }else{
            result.put("cols", new ArrayList<>());
        }
        result.put("clms", dataList);
        return result;
    }
}
