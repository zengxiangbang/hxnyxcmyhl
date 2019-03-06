package cn.richinfo.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户中奖记录(UserPrize)实体类
 *
 * @author Grant
 * @since 2019-02-01 15:41:23
 */
public class UserPrize implements Serializable {
    private static final long serialVersionUID = 212929548580447586L;
    @JsonIgnore
    private Long userPrizeId;
    @JsonIgnore
    private Long userId;

    private Integer prizeId;
    
    private String prizeName;

    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;


    public Long getUserPrizeId() {
        return userPrizeId;
    }

    public void setUserPrizeId(Long userPrizeId) {
        this.userPrizeId = userPrizeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Integer prizeId) {
        this.prizeId = prizeId;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
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