package com.blockchain.timebank.controller;

import com.blockchain.timebank.dao.ViewPublishDetailDao;
import com.blockchain.timebank.entity.PublishEntity;
import com.blockchain.timebank.entity.ServiceEntity;
import com.blockchain.timebank.entity.UserEntity;
import com.blockchain.timebank.entity.ViewPublishDetailEntity;
import com.blockchain.timebank.service.PublishService;
import com.blockchain.timebank.service.ServiceService;
import com.blockchain.timebank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/publish")
public class PublishController {

    @Autowired
    PublishService publishService;

    @Autowired
    ServiceService serviceService;

    @Autowired
    UserService userService;

    @Autowired
    ViewPublishDetailDao viewPublishDetailDao;

    //服务种类页面
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String categoryPage(ModelMap map) {
        return "publish_category";
    }

    //发布服务页面
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPage(ModelMap map) {
        List<ServiceEntity> list = serviceService.findAllServiceEntity();
        map.addAttribute("service_list", list);
        return "publish_add";
    }

    //服务显示列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPage(ModelMap map, @RequestParam String type) {
        List<ViewPublishDetailEntity> list = viewPublishDetailDao.findAllByServiceType(type);
        map.addAttribute("list", list);
        map.addAttribute("type", type);
        return "publish_list";
    }

    //服务详细列表
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detailPage(ModelMap map, @RequestParam long id) {
        //System.out.println(id);
        ViewPublishDetailEntity viewPublishDetailEntity = viewPublishDetailDao.findOne(id);
        map.addAttribute("detail", viewPublishDetailEntity);
        return "publish_detail";
    }

    //发布服务提交接口
    @RequestMapping(value = "/add/submit", method = RequestMethod.POST)
    public String addSubmitPage(ModelMap map, @RequestParam String serviceType, @RequestParam String serviceName, @RequestParam String description, @RequestParam String beginDate, @RequestParam String endDate, @RequestParam double price, @RequestParam String address) {
        try {
            PublishEntity publishEntity = new PublishEntity();
            publishEntity.setAddress(address);
            publishEntity.setDescription(description);
            publishEntity.setUserId(getCurrentUser().getId());
            publishEntity.setPrice(price);
            publishEntity.setServiceId(serviceService.findFirstByTypeAndName(serviceType, serviceName).getId());//serviceId
            Date beginTime = new SimpleDateFormat("yyyy-MM-dd").parse(beginDate);//SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            Date endTime = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            publishEntity.setBeginDate(new Timestamp(beginTime.getTime()));
            publishEntity.setEndDate(new Timestamp(endTime.getTime()));
            publishService.savePublishEntity(publishEntity);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        map.addAttribute("type", serviceType);
        return "redirect:/publish/list";
    }

    public UserEntity getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails != null) {
            UserEntity userEntity = userService.findUserEntityByPhone(userDetails.getUsername());
            System.out.println(userEntity.toString());
            return userEntity;
        } else {
            return null;
        }
    }
}
