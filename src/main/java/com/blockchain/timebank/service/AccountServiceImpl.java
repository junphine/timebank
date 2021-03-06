package com.blockchain.timebank.service;

import com.blockchain.timebank.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    UserService userService;

    @Autowired
    RecordService recordService;

    @Autowired
    RequestService requestService;

    @Autowired
    RequestOrderService requestOrderService;

    @Autowired
    RechargeService rechargeService;

    @Transactional
    public void payTimeVol(long recordID) {
        //1.查询订单价格
        PublishOrderEntity record = recordService.findRecordEntityById(recordID);
        double price = record.getPayMoney();

        //2.扣除申请者志愿者币账户
        UserEntity applyUser = userService.findUserEntityById(record.getApplyUserId());
        if(applyUser.getTimeVol()<price){
            throw new AccountServiceException("您的金额不足！");
        }
        double timeVol = applyUser.getTimeVol() - price;
        BigDecimal bigTimeVol = new BigDecimal(timeVol);
        timeVol  =   bigTimeVol.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        applyUser.setTimeVol(timeVol);
        userService.updateUserEntity(applyUser);

        //3.增加服务者志愿者币账户
        UserEntity serviceUser = userService.findUserEntityById(record.getServiceUserId());
        double timeVol2 = serviceUser.getTimeVol() + price;
        BigDecimal bigTimeVol2 = new BigDecimal(timeVol2);
        timeVol2  =   bigTimeVol2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        serviceUser.setTimeVol(timeVol2);
        userService.updateUserEntity(serviceUser);

        //4.更改订单状态
        record.setStatus(OrderStatus.alreadyComplete);
        recordService.updateRecordEntity(record);
    }

    //支付互助时间
    @Transactional
    public void payTimeCoin(long recordID) {
        //1.查询订单价格
        PublishOrderEntity record = recordService.findRecordEntityById(recordID);
        double price = record.getPayMoney();

        //2.扣除申请者互助时间账户
        UserEntity applyUser = userService.findUserEntityById(record.getApplyUserId());
        if(applyUser.getTimeCoin()<price){
            throw new AccountServiceException("您的金额不足！");
        }
        double timeCoin = applyUser.getTimeCoin() - price;
        BigDecimal bigTimeCoin = new BigDecimal(timeCoin);
        timeCoin  =   bigTimeCoin.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        applyUser.setTimeCoin(timeCoin);
        userService.updateUserEntity(applyUser);

        //3.增加服务者互助时间账户
        UserEntity serviceUser = userService.findUserEntityById(record.getServiceUserId());
        double timeCoin2 = serviceUser.getTimeCoin() + price;
        BigDecimal bigTimeCoin2 = new BigDecimal(timeCoin2);
        timeCoin2  =   bigTimeCoin2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        serviceUser.setTimeCoin(timeCoin2);
        userService.updateUserEntity(serviceUser);

        //4.更改订单状态
        record.setStatus(OrderStatus.alreadyComplete);
        recordService.updateRecordEntity(record);
    }

    @Transactional
    public void updateOrderToComplete(long recordID) {
        //1.查询订单价格
        PublishOrderEntity record = recordService.findRecordEntityById(recordID);
        //4.更改订单状态
        record.setStatus(OrderStatus.alreadyComplete);
        recordService.updateRecordEntity(record);
    }

    @Transactional
    public void payRequestTimeVol(long matchID, BigDecimal price) {
        //1.查询订单价格
        RequestOrderEntity matchEntity = requestOrderService.findRequestOrderEntityById(matchID);
        BigDecimal originPrice = matchEntity.getPayMoney();
        if(!originPrice.equals(price)){
            matchEntity.setPayMoney(price);
            matchEntity = requestOrderService.saveRequestOrderEntity(matchEntity);
        }

        //2.扣除申请者志愿者币账户
        UserEntity requestUser = userService.findUserEntityById(matchEntity.getRequestUserId());
        if(requestUser.getTimeVol()<originPrice.doubleValue()){
            throw new AccountServiceException("您的金额不足！");
        }
        double timeVol = requestUser.getTimeVol() - originPrice.doubleValue();
        BigDecimal bigTimeVol = new BigDecimal(timeVol);
        timeVol  =   bigTimeVol.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        requestUser.setTimeVol(timeVol);
        userService.updateUserEntity(requestUser);

        //3.增加服务者互助时间账户
        UserEntity applyUser = userService.findUserEntityById(matchEntity.getApplyUserId());
        double timeVol2 = applyUser.getTimeVol() + originPrice.doubleValue();
        BigDecimal bigTimeVol2 = new BigDecimal(timeVol2);
        timeVol2  =   bigTimeVol2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        applyUser.setTimeVol(timeVol2);
        userService.updateUserEntity(applyUser);

        //4.更改订单状态
        matchEntity.setStatus(OrderStatus.alreadyComplete);
        requestOrderService.updateRequestOrderEntity(matchEntity);

        //5.更改需求状态
        RequestEntity requestEntity = requestService.findRequestById(matchEntity.getRequestId());
        requestEntity.setIsComplete(1);
        try {
            requestService.saveRequestEntity(requestEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void payRequestTimeCoin(long matchID, BigDecimal price) {
        //1.查询订单价格
        RequestOrderEntity matchEntity = requestOrderService.findRequestOrderEntityById(matchID);
        BigDecimal originPrice = matchEntity.getPayMoney();
        if(!originPrice.equals(price)){
            matchEntity.setPayMoney(price);
            matchEntity = requestOrderService.saveRequestOrderEntity(matchEntity);
        }

        //2.扣除申请者志愿者币账户
        UserEntity requestUser = userService.findUserEntityById(matchEntity.getRequestUserId());
        if(requestUser.getTimeCoin()<originPrice.doubleValue()){
            throw new AccountServiceException("您的金额不足！");
        }
        double timeVol = requestUser.getTimeVol() - originPrice.doubleValue();
        requestUser.setTimeVol(timeVol);
        userService.updateUserEntity(requestUser);

        //3.增加服务者互助时间账户
        UserEntity applyUser = userService.findUserEntityById(matchEntity.getApplyUserId());
        double timeVol2 = applyUser.getTimeCoin() + originPrice.doubleValue();
        applyUser.setTimeVol(timeVol2);
        userService.updateUserEntity(applyUser);

        //4.更改订单状态
        matchEntity.setStatus(OrderStatus.alreadyComplete);
        requestOrderService.updateRequestOrderEntity(matchEntity);

        //5.更改需求状态
        RequestEntity requestEntity = requestService.findRequestById(matchEntity.getRequestId());
        requestEntity.setIsComplete(1);
        try {
            requestService.saveRequestEntity(requestEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public void updateRechargeTimeCoin(String rechargeUuid ,String sign){
        RechargeEntity rechargeEntity = rechargeService.findByUuid(rechargeUuid);
        System.out.println("rechargeentity"+rechargeEntity.getRechargeStatus());
        if("ok".equals(sign)) {
            long userid = rechargeEntity.getUserId();
            //修改用户账户中的
            UserEntity userEntity = userService.findUserEntityById(userid);
            double amount = rechargeEntity.getTotalAmount() + userEntity.getTimeCoin();
            userEntity.setTimeCoin(amount);
            userService.updateUserEntity(userEntity);
            //修改recharge表的支付状态
            rechargeEntity.setRechargeStatus("success");
            rechargeService.saveRechargeEntity(rechargeEntity);
        }
        else{
            rechargeEntity.setRechargeStatus("fail");
            rechargeService.saveRechargeEntity(rechargeEntity);
        }
    }

    @Transactional
    public void updateRequestOrderToComplete(long orderID) {
        //1.查询订单价格
        RequestOrderEntity order = requestOrderService.findRequestOrderEntityById(orderID);
        //4.更改订单状态
        order.setStatus(OrderStatus.alreadyComplete);
        requestOrderService.saveRequestOrderEntity(order);
    }
}
