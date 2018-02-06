package com.sopt.saver.saver.PointPage;

/**
 * Created by lynn on 2017-07-01.
 */

public class PointPageListViewItem {

    private int type;

    private String account;
    private String account_num;

    private String title;

    private String section;

    private String money;

    public void setMoney(String pmoney){
        this.money = pmoney;
    }

    public void setSection(String psection){
        this.section = psection;
    }

    public void setType(int type){
        this.type = type;
    }

    public  void setTitle(String ptitle){
        this.title = ptitle;
    }

    public void setAccount(String paccount){
        this.account = paccount;
    }

    public void setAccountNum(String paccountNum){
        this.account_num = paccountNum;
    }

    public int getType(){
        return this.type;
    }

    public String getAccount(){
        return this.account;
    }

    public String getAccount_num(){
        return this.account_num;
    }

    public String getTitle(){
        return this.title;
    }

    public String getSection(){
        return this.section;
    }

    public String getMoney(){
        return this.money;
    }

}
