package com.example.teachingonline;

public class Course {
    private String courseName;
    private String courseId;
    private String courseDescription;
    private String courseRequirement;
    private int coursePicture;
    private String catId;
    private  String orgPrice;
    private String salePrice;

    public String getOrgPrice() {
        return orgPrice;
    }

    public Course(String courseName, int coursePicture, String orgPrice) {
        this.courseName = courseName;
        this.coursePicture = coursePicture;
        this.orgPrice = orgPrice;
    }

    public void setOrgPrice(String orgPrice) {
        this.orgPrice = orgPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseRequirement() {
        return courseRequirement;
    }

    public void setCourseRequirement(String courseRequirement) {
        this.courseRequirement = courseRequirement;
    }

    public int getCoursePicture() {
        return coursePicture;
    }

    public void setCoursePicture(int coursePicture) {
        this.coursePicture = coursePicture;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }
}
