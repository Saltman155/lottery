package com.superywd.lottery.main.model.dto;

import com.superywd.library.utils.ArrayUtil;
import com.superywd.lottery.main.model.TbActivity;
import com.superywd.lottery.main.model.TbAward;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据传输对象
 * @author 迷宫的中心
 * @date 2019/5/15 1:19
 */
public class ActivityInfoDTO {

    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Integer id;

    private String title;

    private String description;

    private String startTime;

    private String endTime;

    private Integer drawNumber;

    private List<AwardInfoDTO> awardInfoList;

    public ActivityInfoDTO() {
    }

    public ActivityInfoDTO(TbActivity activity) {
        this.id = activity.getId();
        this.title = activity.getTitle();
        this.description = activity.getDescription();
        this.startTime = format.format(activity.getStartTime());
        this.endTime = format.format(activity.getEndTime());
        this.drawNumber = activity.getDrawNumber();
        if(!ArrayUtil.isEmpty(activity.getAwardList())){
            this.awardInfoList = new ArrayList<>(activity.getAwardList().size());
            for(TbAward award : activity.getAwardList()){
                this.awardInfoList.add(new AwardInfoDTO(award.getId(),award.getAwardName(),award.getAwardCount()));
            }
        }
    }

    public static DateFormat getFormat() {
        return format;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(Integer drawNumber) {
        this.drawNumber = drawNumber;
    }

    public List<AwardInfoDTO> getAwardInfoList() {
        return awardInfoList;
    }

    public void setAwardInfoList(List<AwardInfoDTO> awardInfoList) {
        this.awardInfoList = awardInfoList;
    }

    public static class AwardInfoDTO{

        private Integer id;

        private String name;

        private Integer count;

        public AwardInfoDTO() {
        }

        public AwardInfoDTO(Integer id, String name, Integer count) {
            this.id = id;
            this.name = name;
            this.count = count;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

    }


}
