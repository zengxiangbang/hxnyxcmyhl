package cn.richinfo.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.io.Serializable;

/**
 * 猜谜记录(GuessRecord)实体类
 *
 * @author Grant
 * @since 2019-02-01 15:41:23
 */
public class GuessRecord implements Serializable {
    private static final long serialVersionUID = -49716550665267373L;
    @JsonIgnore
    private Long recordId;
    
    private Byte isSuccess;
    
    private Byte helpTime;
    
    private Byte isLottery;

    @JsonIgnore
    private Long userId;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;


    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Byte getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Byte isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Byte getHelpTime() {
        return helpTime;
    }

    public void setHelpTime(Byte helpTime) {
        this.helpTime = helpTime;
    }

    public Byte getIsLottery() {
        return isLottery;
    }

    public void setIsLottery(Byte isLottery) {
        this.isLottery = isLottery;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

}