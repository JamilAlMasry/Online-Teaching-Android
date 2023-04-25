package com.example.teachingonline;

public class courses {
    private  String name;
    private  String orgprice;

    public courses (String name, String orgprice){
        this.name = name;
        this.orgprice = orgprice;
    }
    public courses(String name)
    {
        this.name = name;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getOrgprice() {
        return orgprice;
    }

    public void setOrgprice(String orgprice) {
        this.orgprice = orgprice;
    }
}
