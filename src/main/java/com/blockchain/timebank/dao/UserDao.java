package com.blockchain.timebank.dao;

import com.blockchain.timebank.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<UserEntity, Long> {

    UserEntity findUserEntityByPhone(String phone);

    UserEntity findUserEntityByPhoneAndPassword(String phone, String password);

    UserEntity findUserEntityByQrCode(String qrcode);
}
