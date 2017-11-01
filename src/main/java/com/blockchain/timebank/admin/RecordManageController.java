package com.blockchain.timebank.admin;

import com.blockchain.timebank.dao.ViewRecordDetailDao;
import com.blockchain.timebank.entity.ViewRecordDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class RecordManageController {

    @Autowired
    ViewRecordDetailDao viewRecordDetailDao;

    @RequestMapping(value = "/recordList", method = RequestMethod.GET)
    public String recordListPage(ModelMap map) {
        List<ViewRecordDetailEntity> list = (List<ViewRecordDetailEntity>) viewRecordDetailDao.findAll();
        map.addAttribute("list", list);
        return "../admin/record_list";
    }

    
}