package com.yzk.launcher.entity;

import android.graphics.Bitmap;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by yzk on 2019/7/18
 */

public class ContactEntity implements MultiItemEntity {


    /**
     * item 布局
     */
    public static final int FEED_ITEM_TOP = 1;
    public static final int FEED_ITEM_CONTACT = 2;

    private int type = 2; // 默认 2， contact 的布局

    @Override
    public int getItemType() {
        return type;
    }

    /**
     * for top 布局
     */
    public ContactEntity(String name, int bgColor, int imgId, int type) {
        this.type = type;
        this.name = name;
        this.bgColor = bgColor;
        this.imgId = imgId;
    }

    /**
     *
     */
    public ContactEntity() {
    }

    private int bgColor;
    private String imgPtah;
    private int imgId;

    private String name;
    private String phoneNumber;
    private String contactId;
    private Bitmap icon;

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public int getImgId() {
        return imgId;
    }

    public String getImgPath() {
        return imgPtah;
    }

    public void setImgPath(String imgPtah) {
        this.imgPtah = imgPtah;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }


    public int getBgColor() {
        return bgColor;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "{" +
                "name = '" + name + '\'' +
                ", phoneNumber = '" + phoneNumber + '\'' +
                ", contactId = '" + contactId + '\'' +
                ", icon = '" + icon + '\'' +
                '}';
    }
}
