package com.blockchain.timebank.entity;

import javax.persistence.*;

/**
 * Created by yang on 17-12-22.
 */
@Entity
@Table(name = "recharge", schema = "mydb", catalog = "")
public class RechargeEntity {
    private long id;
    private long userId;
    private double totalAmount;
    private String rechargeDate;
    private String rechargeStatus;
    private String uuid;
    private String extra;

    @Id
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "UserID")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "TotalAmount")
    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Basic
    @Column(name = "RechargeDate")
    public String getRechargeDate() {
        return rechargeDate;
    }

    public void setRechargeDate(String rechargeDate) {
        this.rechargeDate = rechargeDate;
    }

    @Basic
    @Column(name = "RechargeStatus")
    public String getRechargeStatus() {
        return rechargeStatus;
    }

    public void setRechargeStatus(String rechargeStatus) {
        this.rechargeStatus = rechargeStatus;
    }

    @Basic
    @Column(name = "UUID")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "Extra")
    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RechargeEntity that = (RechargeEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (Double.compare(that.totalAmount, totalAmount) != 0) return false;
        if (rechargeDate != null ? !rechargeDate.equals(that.rechargeDate) : that.rechargeDate != null) return false;
        if (rechargeStatus != null ? !rechargeStatus.equals(that.rechargeStatus) : that.rechargeStatus != null)
            return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (extra != null ? !extra.equals(that.extra) : that.extra != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        temp = Double.doubleToLongBits(totalAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (rechargeDate != null ? rechargeDate.hashCode() : 0);
        result = 31 * result + (rechargeStatus != null ? rechargeStatus.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        return result;
    }
}
