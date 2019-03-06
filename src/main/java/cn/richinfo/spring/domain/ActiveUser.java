package cn.richinfo.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.io.Serializable;

/**
 * 活动用户(ActiveUser)实体类
 *
 * @author Grant
 * @since 2019-02-01 15:41:19
 */
public class ActiveUser implements Serializable {
    private static final long serialVersionUID = -10880977395922227L;
    @JsonIgnore
    private Long userId;
    @JsonIgnore
    private String account;

    private Byte questionType;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Byte questionType) {
        this.questionType = questionType;
    }
}