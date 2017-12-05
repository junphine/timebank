package com.blockchain.timebank.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "activityPublish", schema = "mydb", catalog = "")
public class ActivityPublishEntity {
    private long id;
    private String name;
    private Timestamp beginTime;
    private String address;
    private int count;
    private Timestamp applyEndTime;
    private String description;
    private boolean isPublic;
    private boolean isDeleted;
    private String extra;

    @Id
    @Column(name = "ID", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name", nullable = false, length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "BeginTime", nullable = false)
    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    @Basic
    @Column(name = "Address", nullable = false, length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "Count", nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Basic
    @Column(name = "ApplyEndTime", nullable = false)
    public Timestamp getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(Timestamp applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    @Basic
    @Column(name = "Description", nullable = true, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "IsPublic", nullable = false)
    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Basic
    @Column(name = "IsDeleted", nullable = false)
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Basic
    @Column(name = "Extra", nullable = true, length = 50)
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

        ActivityPublishEntity that = (ActivityPublishEntity) o;

        if (id != that.id) return false;
        if (count != that.count) return false;
        if (isPublic != that.isPublic) return false;
        if (isDeleted != that.isDeleted) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (beginTime != null ? !beginTime.equals(that.beginTime) : that.beginTime != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (applyEndTime != null ? !applyEndTime.equals(that.applyEndTime) : that.applyEndTime != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (extra != null ? !extra.equals(that.extra) : that.extra != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (beginTime != null ? beginTime.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + count;
        result = 31 * result + (applyEndTime != null ? applyEndTime.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (isPublic ? 1 : 0);
        result = 31 * result + (isDeleted ? 1 : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        return result;
    }
}