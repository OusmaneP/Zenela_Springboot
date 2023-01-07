package com.podosoft.zenela.ApiMobile.Dto;

import com.podosoft.zenela.Models.Comment;
import com.podosoft.zenela.Models.LikingPossibility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class PostMobileDto {

    private Long id;

    private Long posterId;

    private String comment;

    private String type;

    private String fileName;

    private Date createdAt;

    private String posterName;

    private String posterProfile;

    private LikingPossibility likingPossibility;

    private Collection<Comment> commentsList = new ArrayList<>();

    private Long totalComments;

    public PostMobileDto() {
    }


    public PostMobileDto(Long posterId, String comment, String type, String fileName, Date createdAt) {
        this.posterId = posterId;
        this.comment = comment;
        this.type = type;
        this.fileName = fileName;
        this.createdAt = createdAt;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPosterId() {
        return posterId;
    }

    public void setPosterId(Long posterId) {
        this.posterId = posterId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        if (type.contains("image"))
            return "image";
        else if (type.contains("video"))
            return "video";
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreatedAt() {

        return getDay(createdAt)+ " " + getMonth(createdAt) + " " + getYear(createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public String getPosterProfile() {
        return posterProfile;
    }

    public void setPosterProfile(String posterProfile) {
        this.posterProfile = posterProfile;
    }

    public LikingPossibility getLikingPossibility() {
        return likingPossibility;
    }

    public void setLikingPossibility(LikingPossibility likingPossibility) {
        this.likingPossibility = likingPossibility;
    }

    public Collection<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(Collection<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    public Long getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Long totalComments) {
        this.totalComments = totalComments;
    }


    private String getMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);

        switch (month){
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mach";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Aug";
            case 8:
                return "Sept";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Dec";
            default:
                return " ";
        }
    }

    private int getYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    private int getDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }


}
