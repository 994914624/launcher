package com.yzk.launcher.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.yzk.launcher.R;
import com.yzk.launcher.entity.ContactEntity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by yzk on 2020/8/18
 */

public class ContactUtil {

    private final static String TAG = "ContactUtil";

    /**
     * 联系人 原图
     */
    public static Bitmap getRawPhoto(Context context, String contactId) {
        try {
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
            Uri displayPhotoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);

            AssetFileDescriptor fd = context.getContentResolver().openAssetFileDescriptor(displayPhotoUri, "r");
            if (fd == null) {
                return BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_contact);
            }
            return BitmapFactory.decodeStream(fd.createInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_contact);
        }
    }

    /**
     * 跳到联系人页面，选择单个联系人
     */
    public static void pickContact(Activity context) {
        try {
            Intent intent = new Intent();
            // 启动的界面,属于哪一种动作.pick,代表的是选择内容,选取某个数据
            intent.setAction("android.intent.action.PICK");
            // 用于界定,选择的是什么类型的数据(这里需要获取电话号码数据)
            // 如:intent.setType("image/*");intent.setType("video/*");
            intent.setType("vnd.android.cursor.dir/phone_v2");
            context.startActivityForResult(intent, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 跳到联系人页面，选择单个联系人的结果
     */
    public static ContactEntity pickContactResult(Intent data, Context context) {
        //没有点击条目直接点返回
        if (data == null) {
            Log.e(TAG, "--------data == null-------" );
            return null;
        }
        try {
            //content://cpom.android.contacts/data/134.具体的联系人的路径
            Uri uri = data.getData();
            //获取内容解析者
            ContentResolver resolver = context.getContentResolver();
            //查询数据
            if (uri != null) {
                Log.e(TAG, "--------uri != null-------" );
                Cursor cursor = resolver.query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.CONTACT_ID}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String name = cursor.getString(0);
                    String phone = cursor.getString(1);
                    String contactId = cursor.getString(2);
                    ContactEntity contactEntity = new ContactEntity();
                    contactEntity.setName(name);
                    contactEntity.setPhoneNumber(phone);
                    contactEntity.setContactId(contactId);
                    contactEntity.setIcon(getRawPhoto(context, contactId));
                    // test
//                getContactInfo(contactId,context);
                    Log.e(TAG, "---------------" + name + phone + contactId);
                    cursor.close();
                    return contactEntity;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG, "--------return null-------" );
        return null;
    }

    /**
     * 根据 contactId 获取单个联系人的 name/phoneNumber/icon
     */
    public static ContactEntity getContactInfo(String contactId, Context context) {
        try {
            Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                ContactEntity contactEntity = new ContactEntity();
                contactEntity.setName(name);
                contactEntity.setPhoneNumber(phone);
                contactEntity.setContactId(contactId);
                contactEntity.setIcon(getRawPhoto(context, contactId));
                Log.d(TAG, "--------getContactInfo-------" + name + phone + contactId);
                cursor.close();
                return contactEntity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有的联系人
     */
    public static ArrayList<ContactEntity> getAllContacts(Context context) {
        ArrayList<ContactEntity> contacts = new ArrayList<ContactEntity>();
        if (context == null) {
            return contacts;
        }
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor == null) {
            return contacts;
        }
        while (cursor.moveToNext()) {
            //新建一个联系人实例
            ContactEntity contactEntity = new ContactEntity();
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contactEntity.setName(name);

            //获取联系人电话号码
            Cursor phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            if (phoneCursor == null) {
                return contacts;
            }
            while (phoneCursor.moveToNext()) {
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phone = phone.replace("-", "");
                phone = phone.replace(" ", "");
                contactEntity.setPhoneNumber(phone);
            }

            // 联系人头像（原图）
            contactEntity.setIcon(getRawPhoto(context, contactId));
            contacts.add(contactEntity);
            phoneCursor.close();
        }
        cursor.close();
        Log.i(TAG, contacts.toString());
        return contacts;
    }

    /**
     * 联系人 缩略图
     */
    private static Bitmap getContactPhoto(Context context, String contactId, int defaultIco) {
        Cursor cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                null, ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + " = " + contactId + " AND " + ContactsContract.Data.MIMETYPE + " ='" + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null, null);
        byte[] data = new byte[0];
        if (cursor == null) {
            return BitmapFactory.decodeResource(context.getResources(), defaultIco);
        }

        if (cursor.moveToFirst()) {
            data = cursor.getBlob(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.DATA15));
        }
        cursor.close();
        if (data == null || data.length == 0) {
            return BitmapFactory.decodeResource(context.getResources(), defaultIco);
        } else
            return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
