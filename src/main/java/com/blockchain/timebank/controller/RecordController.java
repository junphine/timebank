package com.blockchain.timebank.controller;

import com.blockchain.timebank.dao.UserDao;
import com.blockchain.timebank.dao.ViewActivityPublishDetailDao;
import com.blockchain.timebank.dao.ViewPublishDetailDao;
import com.blockchain.timebank.dao.ViewRecordDetailDao;
import com.blockchain.timebank.entity.*;
import com.blockchain.timebank.service.PublishService;
import com.blockchain.timebank.service.RecordService;
import com.blockchain.timebank.service.UserActivityService;
import com.blockchain.timebank.service.UserService;
import com.blockchain.timebank.weixin.util.MessageUtil;
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

@Controller
@RequestMapping("/record")
public class RecordController {

    @Autowired
    UserService userService;

    @Autowired
    RecordService recordService;

    @Autowired
    ViewPublishDetailDao viewPublishDetailDao;

    @Autowired
    ViewRecordDetailDao viewPublishOrderDetailDao;

    @Autowired
    UserDao userDao;

    @Autowired
    PublishService publishService;

    @Autowired
    UserActivityService userActivityService;

    @Autowired
    ViewActivityPublishDetailDao viewActivityPublishDetailDao;

    //申请服务页面
    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public String applyPage(ModelMap map, long id) {
        UserEntity userEntity = getCurrentUser();
        if(userEntity.getIsVerify()==null){
            map.addAttribute("msg", "notVerify");
            return "apply_service_result";
        }else if(userEntity.getIsVerify()==0){
            map.addAttribute("msg", "failVerify");
            return "apply_service_result";
        }
        map.addAttribute("detail", viewPublishDetailDao.findOne(id));
        map.addAttribute("name", userEntity.getName());
        map.addAttribute("phone", userEntity.getPhone());
        return "record_apply";
    }

    //已申请活动页面查看活动详情
    @RequestMapping(value = "/yishenqingxiangqing_activities", method = RequestMethod.GET)
    public String yishenqingxiangqing(ModelMap map,long id) {
        UserEntity userEntity = getCurrentUser();
        return "";
    }

    //申请评价活动页面（参与活动的成员）num代表评价次数
    @RequestMapping(value = "/pingjia_activities", method = RequestMethod.GET)
    public String pingjiaActivities(ModelMap map,@RequestParam String num,@RequestParam long activityId) {
        ViewActivityPublishDetailEntity activityy=viewActivityPublishDetailDao.findOne(activityId);
        UserActivityEntity userActivity=userActivityService.findUserFromActivity(getCurrentUser().getId(),activityId);
        map.addAttribute("activityDetail",activityy);
        map.addAttribute("userActivity",userActivity);
        map.addAttribute("rateCount",num);
        return "activities_rate";
    }

    //申请服务页面
    @RequestMapping(value = "/applySubmit", method = RequestMethod.POST)
    public String applySubmit(ModelMap map, @RequestParam long serviceUserId, @RequestParam long publishId, @RequestParam String applyUserName, @RequestParam String applyUserPhone, @RequestParam String address, @RequestParam String beginTime, @RequestParam int serveTime) {

        //判断余额是否充足
        PublishEntity publishEntity = publishService.findPublishEntityById(publishId);
        double price = publishEntity.getPrice();
        double sum = price * serveTime;
        boolean hasMoney = true;
        long serviceId = publishEntity.getServiceId();
        int payWay = 1;
        if(serviceId / 100 ==1){
            payWay = 1;
            if(getCurrentUser().getTimeVol()<sum){
                hasMoney = false;
            }
        }else if(serviceId / 100 ==3){
            payWay = 2;
            if(getCurrentUser().getTimeCoin()<sum){
                hasMoney = false;
            }
        } else{
            payWay = 3;
        }

        //判断是否申请自己的服务
        boolean isOneself = false;
        if(serviceUserId == getCurrentUser().getId()){
            isOneself = true;
        }


        if(hasMoney){
            try {
                PublishOrderEntity publishOrderEntity = new PublishOrderEntity();
                publishOrderEntity.setServiceUserId(serviceUserId);
                publishOrderEntity.setPublishId(publishId);
                publishOrderEntity.setApplyUserId(getCurrentUser().getId());
                publishOrderEntity.setApplyUserName(applyUserName);
                publishOrderEntity.setApplyUserPhone(applyUserPhone);
                publishOrderEntity.setApplyAddress(address);
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(beginTime.replace("T", " "));//SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                publishOrderEntity.setBeginTime(new Timestamp(date.getTime()));
                publishOrderEntity.setEndTime(new Timestamp(date.getTime() + serveTime * 60 * 60 * 1000));
                publishOrderEntity.setPayWay(payWay);
                publishOrderEntity.setStatus(OrderStatus.alreadyApply);
                PublishOrderEntity insertPublishOrder = recordService.saveRecordEntity(publishOrderEntity);
                map.addAttribute("msg","ok");
                //发消息给发布服务的人
                UserEntity userEntity = userService.findUserEntityById(serviceUserId);
                ViewPublishOrderDetailEntity viewPublishOrderDetailEntity = recordService.findViewRecordDetailEntityById(insertPublishOrder.getId());
                if(userEntity != null && viewPublishOrderDetailEntity != null) {
                    System.out.println("===========================进入判断===============================");
                    MessageUtil.customer_appoint(userEntity, viewPublishOrderDetailEntity);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                map.addAttribute("msg","error");
                map.addAttribute("detail","parseException");
            }
        }else{
            map.addAttribute("msg","error");
            map.addAttribute("detail","noMoney");
        }

        if(isOneself){
            map.addAttribute("msg","error");
            map.addAttribute("detail","isOneself");
        }

        return "record_apply_result";
    }

    //服务者处理订单
    @RequestMapping(value = "/handleApplicantRecord", method = RequestMethod.GET)
    public String handleApplicantRecord(ModelMap map,@RequestParam long recordID,@RequestParam String handle){
        PublishOrderEntity publishOrderEntity = recordService.findRecordEntityById(recordID);
        if(handle.equals("refuse")){
            publishOrderEntity.setStatus(OrderStatus.alreadyRefuse);
            recordService.updateRecordEntity(publishOrderEntity);
        }

        if(handle.equals("confirm")){
            publishOrderEntity.setStatus(OrderStatus.waitingService);
            recordService.updateRecordEntity(publishOrderEntity);
        }

        if(handle.equals("cancel")){
            publishOrderEntity.setStatus(OrderStatus.alreadyCancel);
            recordService.updateRecordEntity(publishOrderEntity);
        }

        UserEntity userEntity = userService.findUserEntityById(publishOrderEntity.getApplyUserId());
        PublishEntity publishEntity = publishService.findPublishEntityById(publishOrderEntity.getPublishId());
        ViewPublishOrderDetailEntity viewPublishOrderDetailEntity = recordService.findViewRecordDetailEntityById(publishOrderEntity.getId());
        if(handle.equals("cancel")) {
            String text = "您好，您预约的" + viewPublishOrderDetailEntity.getServiceName()+ "服务已被" + viewPublishOrderDetailEntity.getServiceUserName() + "用户取消！";
            MessageUtil.TextMessage(userEntity.getOpenId(), text);
        } else {
            MessageUtil.apply_result(userEntity, viewPublishOrderDetailEntity);
        }
        map.addAttribute("userEntity",userEntity);
        map.addAttribute("publishEntity",publishEntity);
        map.addAttribute("publishOrderEntity", publishOrderEntity);
        return "takendetails";
    }

    //预约服务者取消订单
    @RequestMapping(value = "/cancelApplicantService", method = RequestMethod.GET)
    public String cancelApplicantService(ModelMap map,@RequestParam long recordID,@RequestParam String handle){
        PublishOrderEntity publishOrderEntity = recordService.findRecordEntityById(recordID);
        if(handle.equals("cancel")){
            publishOrderEntity.setStatus(OrderStatus.alreadyCancel);
            recordService.updateRecordEntity(publishOrderEntity);
        }

        UserEntity userEntity = userService.findUserEntityById(publishOrderEntity.getServiceUserId());
        PublishEntity publishEntity = publishService.findPublishEntityById(publishOrderEntity.getPublishId());
        ViewPublishOrderDetailEntity viewPublishOrderDetailEntity = recordService.findViewRecordDetailEntityById(publishOrderEntity.getId());
        String text = "您好，预约您" + viewPublishOrderDetailEntity.getServiceName()+ "服务的" + viewPublishOrderDetailEntity.getApplyUserName() + "用户已取消预约！";
        MessageUtil.TextMessage(userEntity.getOpenId(), text);
        map.addAttribute("userEntity",userEntity);
        map.addAttribute("publishEntity",publishEntity);
        map.addAttribute("publishOrderEntity", publishOrderEntity);

        return "takendetails";
    }

    public UserEntity getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails != null) {
            UserEntity userEntity = userService.findUserEntityByPhone(userDetails.getUsername());
            return userEntity;
        } else {
            return null;
        }
    }

}
