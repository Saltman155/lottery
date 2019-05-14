package com.superywd.lottery.main.model;

import java.util.Date;
import java.util.List;

/**
 * @author 迷宫的中心
 * @date 2019/5/14 23:06
 */
public class TbActivity {

    private Integer id;

    private String title;

    private String description;

    private TbTactic tactic;

    private List<TbAward> awardList;

    private TbAdmin creator;

    private Integer drawNumber;

    private Date startTime;

    private Date endTime;

    public TbActivity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TbTactic getTactic() {
        return tactic;
    }

    public void setTactic(TbTactic tactic) {
        this.tactic = tactic;
    }

    public List<TbAward> getAwardList() {
        return awardList;
    }

    public void setAwardList(List<TbAward> awardList) {
        this.awardList = awardList;
    }

    public TbAdmin getCreator() {
        return creator;
    }

    public void setCreator(TbAdmin creator) {
        this.creator = creator;
    }

    public Integer getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(Integer drawNumber) {
        this.drawNumber = drawNumber;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "TbActivity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tactic=" + tactic +
                ", awardList=" + awardList +
                ", creator=" + creator +
                ", drawNumber='" + drawNumber + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
