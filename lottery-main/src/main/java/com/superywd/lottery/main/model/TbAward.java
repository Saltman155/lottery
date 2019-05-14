package com.superywd.lottery.main.model;

/**
 * @author 迷宫的中心
 * @date 2019/5/14 23:14
 */
public class TbAward {

    private Integer id;

    private String awardName;

    private Integer awardCount;

    private Double baseRate;

    public TbAward() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public Integer getAwardCount() {
        return awardCount;
    }

    public void setAwardCount(Integer awardCount) {
        this.awardCount = awardCount;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

    @Override
    public String toString() {
        return "TbAward{" +
                "id=" + id +
                ", awardName='" + awardName + '\'' +
                ", awardCount=" + awardCount +
                ", baseRate=" + baseRate +
                '}';
    }
}
