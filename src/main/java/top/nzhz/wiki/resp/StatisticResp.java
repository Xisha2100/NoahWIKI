package top.nzhz.wiki.resp;

import java.util.Date;

public class StatisticResp {
    private Date date;

    private Integer listenCount;

    private Integer voteCount;

    private Integer listenIncrease;

    private Integer voteIncrease;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getListenCount() {
        return listenCount;
    }

    public void setListenCount(Integer listenCount) {
        this.listenCount = listenCount;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getListenIncrease() {
        return listenIncrease;
    }

    public void setListenIncrease(Integer listenIncrease) {
        this.listenIncrease = listenIncrease;
    }

    public Integer getVoteIncrease() {
        return voteIncrease;
    }

    public void setVoteIncrease(Integer voteIncrease) {
        this.voteIncrease = voteIncrease;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", date=").append(date);
        sb.append(", listenCount=").append(listenCount);
        sb.append(", voteCount=").append(voteCount);
        sb.append(", listenIncrease=").append(listenIncrease);
        sb.append(", voteIncrease=").append(voteIncrease);
        sb.append("]");
        return sb.toString();
    }
}