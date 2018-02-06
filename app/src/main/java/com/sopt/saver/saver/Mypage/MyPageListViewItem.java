package com.sopt.saver.saver.Mypage;

/**
 * Created by lynn on 2017-06-30.
 */

public class MyPageListViewItem {

    private int type;

    private String title;

    private String section;

    private String title_toggle;

    private String item;

    public void setItem(String m_item){
        this.item = m_item;
    }

    public void setType(int m_type){
        this.type = m_type;
    }

    public void setTitle(String m_title){
        this.title = m_title;
    }

    public void setSection(String m_section){
        this.section = m_section;
    }

    public void setTitle_toggle(String m_titlet){
        this.title_toggle = m_titlet;
    }

    public int getType(){
        return this.type;
    }

    public String getTitle(){
        return this.title;
    }


    public String getSection(){
        return this.section;
    }

    public String getTitle_t(){
        return this.title_toggle;
    }

    public String getItem(){
        return this.item;
    }

}
