package com.example.appuser.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private Integer id;

    private String name;

    private String address;

    private String mobile;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private String createBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    private String updateBy;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public String getCreateBy() {
//        return createBy;
//    }
//
//    public void setCreateBy(String createBy) {
//        this.createBy = createBy;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    public String getUpdateBy() {
//        return updateBy;
//    }
//
//    public void setUpdateBy(String updateBy) {
//        this.updateBy = updateBy;
//    }
}