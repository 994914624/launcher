package com.yzk.launcher.interfaces;

/**
 * Created by yzk on 2020/8/13
 */

public interface Phone {
     void call(String phoneNumber);
     void topClick(String name);
     void onContactLongClick(String contactId,int position);

}
