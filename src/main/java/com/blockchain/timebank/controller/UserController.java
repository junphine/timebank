package com.blockchain.timebank.controller;

import com.blockchain.timebank.entity.PublishEntity;
import com.blockchain.timebank.entity.ServiceEntity;
import com.blockchain.timebank.entity.RecordEntity;
import com.blockchain.timebank.entity.UserEntity;
import com.blockchain.timebank.service.RecordService;
import com.blockchain.timebank.service.PublishService;
import com.blockchain.timebank.service.ServiceService;
import com.blockchain.timebank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    PublishService publishService;

    @Autowired
    RecordService recordService;

    @Autowired
    ServiceService serviceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String userPage(ModelMap map) {
        UserEntity userEntity = getCurrentUser();
        map.addAttribute("user", userEntity);
        return "userinfo";
    }


    // 登陆请求提交接口
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String userLogin(ModelMap map, @RequestParam String phone, @RequestParam String password) {
        if (phone.equals("") || password.equals("")) {
            map.addAttribute("error", "输入信息不能为空");
            return "login";
        }
        if (userService.findUserEntityByPhoneAndPassword(phone, password) == null) {
            map.addAttribute("error", "错误的用户名或者密码");
            return "login";
        } else {
            Authentication token = new UsernamePasswordAuthenticationToken(phone, password);
            SecurityContextHolder.getContext().setAuthentication(token);
            return "redirect:/index";
        }
    }

    // 登出请求
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(ModelMap map) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        map.addAttribute("logout", "已经为您安全退出！");
        return "/login";
    }


    // 注册请求接口
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String userRegister(ModelMap map, @RequestParam String name, @RequestParam String phone, @RequestParam String password) {
        if (phone.equals("") || name.equals("") || password.equals("")) {
            map.addAttribute("error", "输入信息不能为空");
            return "/register";
        }
        if (userService.findUserEntityByPhone(phone) != null) {
            map.addAttribute("error", "注册失败，手机号已经被注册！");
            return "/register";
        }
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(name);
            userEntity.setPhone(phone);
            userEntity.setPassword(password);
            userService.saveUserEntity(userEntity);
            Authentication token = new UsernamePasswordAuthenticationToken(phone, password);
            SecurityContextHolder.getContext().setAuthentication(token);
            return "/index";
        } catch (Exception e) {
            map.addAttribute("error", "注册失败，重复的用户！");
            return "/register";
        }
    }

    //查询所有的发布服务的接口
    @RequestMapping(value="/queryPublish",method = RequestMethod.GET)
    public String queryPublish(ModelMap map){
        //第一个list
        List<PublishEntity> firstList1 = publishService.findByUserID(getCurrentUser().getId());
        List<ServiceEntity> firstList2 = new ArrayList<ServiceEntity>();

        for(int i=0;i<firstList1.size();i++){
            ServiceEntity serviceEntity = serviceService.findById(firstList1.get(i).getServiceId());
            firstList2.add(serviceEntity);
        }

        //第二个list
        List<RecordEntity> secondList1 = recordService.findRecordEntitiesByServiceUserIdAndStatus(getCurrentUser().getId(),"已申请");
        List<PublishEntity> secondList2 = new ArrayList<PublishEntity>();
        List<UserEntity> secondList3 = new ArrayList<UserEntity>();

        for(int i=0;i<secondList1.size();i++){
            PublishEntity publishEntity = publishService.findPublishEntityById(secondList1.get(i).getPublishId());
            secondList2.add(publishEntity);

            UserEntity userEntity = userService.findUserEntityById(secondList1.get(i).getApplyUserId());
            secondList3.add(userEntity);
        }

        //第三个list
        List<RecordEntity> thirdList1 = recordService.findRecordEntitiesByServiceUserIdAndStatus(getCurrentUser().getId(),"待服务");
        List<UserEntity> thirdList2 = new ArrayList<UserEntity>();

        for(int i=0;i<thirdList1.size();i++){
            UserEntity userEntity = userService.findUserEntityById(thirdList1.get(i).getApplyUserId());
            thirdList2.add(userEntity);
        }

        //第四个list
        List<RecordEntity> fourthList1 = recordService.findRecordEntitiesByServiceUserIdAndStatus(getCurrentUser().getId(),"待支付");
        List<UserEntity> fourthList2 = new ArrayList<UserEntity>();

        for(int i=0;i<fourthList1.size();i++){
            UserEntity userEntity = userService.findUserEntityById(fourthList1.get(i).getApplyUserId());
            fourthList2.add(userEntity);
        }

        //第五个list
        List<RecordEntity> fifthList1 = recordService.findRecordEntitiesByServiceUserIdAndStatus(getCurrentUser().getId(),"已完成");
        List<RecordEntity> fifthList2 = recordService.findRecordEntitiesByServiceUserIdAndStatus(getCurrentUser().getId(),"已拒绝");
        fifthList1.addAll(fifthList2);
        List<UserEntity> fifthList3 = new ArrayList<UserEntity>();

        for(int i=0;i<fifthList1.size();i++){
            UserEntity userEntity = userService.findUserEntityById(fifthList1.get(i).getApplyUserId());
            fifthList3.add(userEntity);
        }

        map.addAttribute("firstList1", firstList1);
        map.addAttribute("firstList2", firstList2);

        map.addAttribute("secondList1", secondList1);
        map.addAttribute("secondList2", secondList2);
        map.addAttribute("secondList3", secondList3);

        map.addAttribute("thirdList1", thirdList1);
        map.addAttribute("thirdList2", thirdList2);

        map.addAttribute("fourthList1", fourthList1);
        map.addAttribute("fourthList2", fourthList2);

        map.addAttribute("fifthList1", fifthList1);
        map.addAttribute("fifthList3", fifthList3);
        return "service_posted_all";
    }

    //查询申请者申请订单的接口
    @RequestMapping(value = "/queryOrder",method = RequestMethod.GET)
    public String queryOrder(ModelMap map){
        List<RecordEntity> list = recordService.findByApplyUserId(0);
        map.addAttribute("list", list);
        return "service_requested_all";
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
