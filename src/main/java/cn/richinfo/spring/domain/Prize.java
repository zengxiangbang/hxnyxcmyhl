package cn.richinfo.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.io.Serializable;

/**
 * 活动奖品(Prize)实体类
 *
 * @author Grant
 * @since 2019-02-01 15:41:23
 */
public class Prize implements Serializable {
    private static final long serialVersionUID = 247355459133483339L;


    private Integer prizeId;
    
    private String prizeName;
    @JsonIgnore
    private Integer prizeNumber;
    @JsonIgnore
    private Integer surplusQuantity;
    @JsonIgnore
    private Integer probabilityInterval;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;


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

    public Integer getPrizeNumber() {
        return prizeNumber;
    }

    public void setPrizeNumber(Integer prizeNumber) {
        this.prizeNumber = prizeNumber;
    }

    public Integer getSurplusQuantity() {
        return surplusQuantity;
    }

    public void setSurplusQuantity(Integer surplusQuantity) {
        this.surplusQuantity = surplusQuantity;
    }

    public Integer getProbabilityInterval() {
        return probabilityInterval;
    }

    public void setProbabilityInterval(Integer probabilityInterval) {
        this.probabilityInterval = probabilityInterval;
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

    @Override
    public String toString() {
        return "Prize{" +
                "prizeId=" + prizeId +
                ", prizeName='" + prizeName + '\'' +
                ", prizeNumber=" + prizeNumber +
                ", surplusQuantity=" + surplusQuantity +
                ", probabilityInterval=" + probabilityInterval +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}