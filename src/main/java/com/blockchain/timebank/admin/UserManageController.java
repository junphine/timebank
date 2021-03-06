package com.blockchain.timebank.admin;

import com.blockchain.timebank.entity.TechnicAuthEntity;
import com.blockchain.timebank.entity.UserAuthEntity;
import com.blockchain.timebank.entity.UserEntity;
import com.blockchain.timebank.service.TeamService;
import com.blockchain.timebank.service.TechnicAuthService;
import com.blockchain.timebank.service.UserAuthService;
import com.blockchain.timebank.service.UserService;
import com.blockchain.timebank.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class UserManageController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    UserService userService;

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    TechnicAuthService technicAuthService;


    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String userListPage(ModelMap map) {
        List<UserEntity> list_user = userService.findAll();
        map.addAttribute("list_user", list_user);
        map.addAttribute("link_userView", "1");
        map.addAttribute("link_userEdit", "1");
        return "../admin/user_list";
    }

    @RequestMapping(value = "/userAdd", method = RequestMethod.GET)
    public String userAddPage(ModelMap map) {
        return "../admin/user_add";
    }

    @RequestMapping(value = "/userEdit", method = RequestMethod.GET)
    public String userEditPage(ModelMap map, @RequestParam long userId) {
        map.addAttribute("user", userService.findUserEntityById(userId));
        return "../admin/user_edit";
    }

    @RequestMapping(value = "/userView", method = RequestMethod.GET)
    public String userViewPage(ModelMap map, @RequestParam long userId) {
        map.addAttribute("user", userService.findUserEntityById(userId));
        return "../admin/user_view";
    }

    @RequestMapping(value = "/userPhotoAdd", method = RequestMethod.GET)
    public String userAddPhotoPage(ModelMap map, @RequestParam long userId) {
        UserEntity user = userService.findUserEntityById(userId);
        map.addAttribute("user", user);
        if (user.getIdCard() == null || user.getIdCard().equals("")) {
            map.addAttribute("error", "身份证号未填写，不能上传照片！");
        }
        return "../admin/user_photo_add";
    }

    @RequestMapping(value = "/userVerifyList", method = RequestMethod.GET)
    public String userVerifyPage(ModelMap map) {
        List<UserEntity> list_user = userService.findAll();
        map.addAttribute("list_user", list_user);
        return "../admin/user_verify_list";
    }

    @RequestMapping(value = "/userVerify", method = RequestMethod.GET)
    public String userVerifyPage(ModelMap map, @RequestParam long userId) {
        map.addAttribute("user", userService.findUserEntityById(userId));
        return "../admin/user_verify";
    }

    @RequestMapping(value = "/userTechVerifyList", method = RequestMethod.GET)
    public String userTechVerifyPage(ModelMap map) {
        List<TechnicAuthEntity> list_tech = technicAuthService.findAll();
        map.addAttribute("list_tech", list_tech);
        return "../admin/user_tech_verify_list";
    }

    @RequestMapping(value = "/userTechVerify", method = RequestMethod.GET)
    public String userTechVerifyPage(ModelMap map, @RequestParam long techId) {
        TechnicAuthEntity technicAuthEntity = technicAuthService.findTechnicAuthEntityById(techId);
        map.addAttribute("tech", technicAuthEntity);
        map.addAttribute("user", userService.findUserEntityById(technicAuthEntity.getUserId()));
        return "../admin/user_tech_verify";
    }

    @RequestMapping(value = "/userTechVerifySubmit", method = RequestMethod.POST)
    public String userTechVerifySubmit(ModelMap map, @RequestParam long techId, @RequestParam int isVerify){
        TechnicAuthEntity technicAuthEntity = technicAuthService.findTechnicAuthEntityById(techId);
        technicAuthEntity.setVerified(isVerify==1);
        technicAuthEntity.setAuthId(getCurrentUser().getId());
        technicAuthEntity.setAuthDate(new Timestamp(new java.util.Date().getTime()));
        technicAuthService.saveTechnicAuthEntity(technicAuthEntity);
        map.addAttribute("tech", technicAuthEntity);
        map.addAttribute("user", userService.findUserEntityById(technicAuthEntity.getUserId()));
        map.addAttribute("ok", "提交成功！");
        return "../admin/user_tech_verify";
    }

    @RequestMapping(value = "/userVerifySubmit", method = RequestMethod.POST)
    public String userVerifySubmit(ModelMap map, @RequestParam long userId, @RequestParam int isVerify) {
        UserEntity userEntity = userService.findUserEntityById(userId);
        userEntity.setIsVerify(isVerify);
        if (isVerify == 1){
            SimpleDateFormat cardBirth = new SimpleDateFormat("yyyyMMdd");
            try {
                Date birth = new Date(cardBirth.parse(userEntity.getIdCard().substring(6, 14)).getTime());
                userEntity.setBirth(birth);

                String sex = Integer.parseInt(userEntity.getIdCard().substring(16, 17)) % 2 == 0 ? "女" : "男";
                userEntity.setSex(sex);
            }
            catch (ParseException e){
                e.printStackTrace();
            }


        }
        userService.saveUserEntity(userEntity);
        map.addAttribute("user", userEntity);
        map.addAttribute("ok", "提交成功！");
        return "../admin/user_verify";
    }

    @RequestMapping(value = "/userAddMany", method = RequestMethod.GET)
    public String userAddManyPage(ModelMap map) {
        return "../admin/user_add_many";
    }


    @RequestMapping(value = "/userAddSubmit", method = RequestMethod.POST)
    public String userAddSubmit(ModelMap map, @RequestParam String name, @RequestParam String phone, @RequestParam String idCard, @RequestParam String sex, @RequestParam String QRCode) {
        if (phone.equals("") || name.equals("")) {
            map.addAttribute("error", "姓名和手机号不能为空");
            return "../admin/user_add";
        }
        if (userService.findUserEntityByPhone(phone) != null) {
            map.addAttribute("error", "添加失败，手机号已经被注册！");
            return "../admin/user_add";
        }
        if (idCard != null && userService.findUserEntityByIdCard(idCard) != null) {
            map.addAttribute("error", "添加失败，身份证号已经被注册！");
            return "../admin/user_add";
        }
        UserEntity userEntity = new UserEntity();
        String defaultStr = "";
        userEntity.setName(name);
        userEntity.setPhone(phone);
        if(!(idCard.equals(defaultStr))){
            userEntity.setIdCard(idCard);
            String MD5Password = MD5Util.getMD5(idCard.substring(12, idCard.length()));
            userEntity.setPassword(MD5Password);

            SimpleDateFormat cardBirth = new SimpleDateFormat("yyyyMMdd");
            try {
                Date birthDate = new Date(cardBirth.parse(idCard.substring(6, 14)).getTime());
                userEntity.setBirth(birthDate);
            }catch (ParseException e){
                e.printStackTrace();
            }

        }

        //userEntity.setPassword(idCard.substring(12, idCard.length()));
        userEntity.setSex(sex);

        /*if(birth!=null){
            userEntity.setBirth(birth);
        }*/

        if(!(QRCode.equals(defaultStr))){
            userEntity.setQrCode(QRCode);
        }

        userEntity.setRegisterDate(new Date(System.currentTimeMillis()));
        userEntity.setTimeCoin(10);
        userService.updateUserEntity(userEntity);
        map.addAttribute("ok", "添加成功，用户初始密码为注册身份证的后6位！");
        return "../admin/user_add";
    }

    @RequestMapping(value = "/userEditSubmit", method = RequestMethod.POST)
    public String userEditSubmit(ModelMap map, @RequestParam long userId, @RequestParam String name, @RequestParam String phone, @RequestParam String idCard, @RequestParam String sex, @RequestParam Date birth, @RequestParam String QRCode) {
        UserEntity userEntity = userService.findUserEntityById(userId);
        userEntity.setName(name);
        userEntity.setPhone(phone);
        userEntity.setIdCard(idCard);
        userEntity.setSex(sex);
        userEntity.setBirth(birth);
        userEntity.setQrCode(QRCode);
        map.addAttribute("user", userEntity);
        if (phone.equals("") || name.equals("")) {
            map.addAttribute("error", "姓名和手机号不能为空");
            return "../admin/user_edit";
        }
        UserEntity user = userService.findUserEntityByPhone(phone);
        if (user != null && user.getId() != userId) {
            map.addAttribute("error", "手机号已被其他账号绑定，不能重复绑定！");
            return "../admin/user_edit";
        } else {
            userService.updateUserEntity(userEntity);
            map.addAttribute("ok", "信息更新成功！");
            return "../admin/user_edit";
        }
    }


    @RequestMapping(value = "/userUploadPhoto")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(value = "file", required = false) MultipartFile file, String idCard, int img) {
        File uploadDir = new File(getUploadPath());
        if (!uploadDir.exists()) uploadDir.mkdir();
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = idCard + "_" + img + suffix;
        Map<String, Object> map = new HashMap<String, Object>();
        UserEntity user = userService.findUserEntityByIdCard(idCard);
        if (img == 1) user.setImg1(fileName);
        else user.setImg2(fileName);
        try {
            file.transferTo(new File(getUploadPath() + fileName));// 转存文件
            map.put("url", "/img/profile/" + fileName);
            userService.saveUserEntity(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    private UserAuthEntity getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails != null) {
            UserAuthEntity userEntity = userAuthService.findUserAuthEntityByPhone(userDetails.getUsername());
            return userEntity;
        } else {
            return null;
        }
    }
    // 文件上传路径
    private String getUploadPath() {
        return request.getSession().getServletContext().getRealPath("/") + "WEB-INF/img/profile/";
    }
}
