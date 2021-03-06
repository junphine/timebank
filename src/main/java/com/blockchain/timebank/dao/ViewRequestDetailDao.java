package com.blockchain.timebank.dao;

import com.blockchain.timebank.entity.ViewRequestDetailEntity;
import com.blockchain.timebank.entity.ViewRequestDetailEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface ViewRequestDetailDao extends CrudRepository<ViewRequestDetailEntity, Long> {
    List<ViewRequestDetailEntity> findViewRequestDetailEntitiesByServiceTypeAndIsDeletedOrderByCreateTimeDesc(String type, int deleted);
    ViewRequestDetailEntity findViewRequestDetailEntityById(long id);
    List<ViewRequestDetailEntity> findViewRequestDetailEntitiesByUserIdAndIsDeleted(Long id, int isDeleted);
    @Query("select e from ViewRequestDetailEntity e where e.serviceType=?1 and e.price <= ?2 and e.price >=?3 and ((e.endTime <= ?4 and e.endTime >= ?5) or (e.beginTime <= ?4 and e.beginTime >= ?5) or (?4 <= e.endTime and ?5 >= e.beginTime) or (?5 <= e.endTime and ?5 >= e.beginTime)) and e.serviceName in ?6")
    List<ViewRequestDetailEntity> findViewRequestDetailEntityByConditionOrderByCreateTimeDesc(String type, BigDecimal upperPrice, BigDecimal lowerPrice, Timestamp upperTime, Timestamp lowerTime, String[] serviceNameArr);

}
