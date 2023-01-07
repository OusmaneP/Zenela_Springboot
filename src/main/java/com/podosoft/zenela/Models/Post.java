package com.podosoft.zenela.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "posts", uniqueConstraints = @UniqueConstraint(columnNames = "file_name"))
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "poster_id")
    private Long posterId;

    private String comment;

    private String type;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "created_at")
    private Date createdAt;

    private boolean notified;

    private String thumbnail;

    @Transient
    private String posterName;

    @Transient
    private String posterProfile;

    @Transient
    private LikingPossibility likingPossibility;

    @Transient
    private int savingPossibility = 1;

    @Transient
    private Collection<Comment> commentsList = new ArrayList<>();

    @Transient
    private Long totalComments;

    public Post() {
    }


    public Post(Long posterId, String comment, String type, String fileName, Date createdAt) {
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

    public Date getCreatedAt() {

        //return getDay(createdAt)+ " " + getMonth(createdAt) + " " + getYear(createdAt);
        return createdAt;
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

    public int getSavingPossibility() {
        return savingPossibility;
    }

    public void setSavingPossibility(int savingPossibility) {
        this.savingPossibility = savingPossibility;
    }

    private String getMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);

        switch (month){
            case 0:
                return "Jan";
            case 1:
                return "Fev";
            case 2:
                return "Mars";
            case 3:
                return "Avr";
            case 4:
                return "Mai";
            case 5:
                return "Juin";
            case 6:
                return "Juil";
            case 7:
                return "Aout";
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

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
