package com.example.myapplication;

/**
 * Created by ljh on 2017/7/23.
 */

public class ItemBean {
    public int ItemImageResid;
    public  String ItemTitle;
    public  String ItemContent;

    ItemBean(int ItemImageResid,String ItemTitle,String ItemContent){
        this.ItemContent = ItemContent;
        this.ItemTitle = ItemTitle;
        this.ItemImageResid = ItemImageResid;
    }

}
