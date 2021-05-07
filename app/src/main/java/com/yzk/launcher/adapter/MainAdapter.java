package com.yzk.launcher.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yzk.launcher.R;
import com.yzk.launcher.interfaces.Phone;
import com.yzk.launcher.entity.ContactEntity;

import java.util.List;

/**
 * Created by yzk on 2020/8/13
 */

public class MainAdapter extends BaseMultiItemQuickAdapter<ContactEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private Phone phone;
    private ItemTouchHelper mItemTouchHelper;

    public MainAdapter(List<ContactEntity> data, Phone phone) {
        super(data);
        // 添加布局种类及对应关系
        addItemType(ContactEntity.FEED_ITEM_TOP, R.layout.main_item_top);
        addItemType(ContactEntity.FEED_ITEM_CONTACT, R.layout.main_item_contact);
        this.phone =phone;
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactEntity item) {
//        Log.e("--------",""+item.getItemType());

        if(item.getItemType() ==ContactEntity.FEED_ITEM_TOP){
            // top 布局
            bindTop(helper, item);
        } else if(item.getItemType() ==ContactEntity.FEED_ITEM_CONTACT){
            // 联系人 布局
            bindContact(helper, item);
        }

    }

    private void bindTop(BaseViewHolder helper, final ContactEntity item) {
        CardView cardView = ((CardView)helper.getView(R.id.item_top));
        cardView.setCardBackgroundColor(item.getBgColor());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"top",Toast.LENGTH_SHORT).show();
                phone.topClick(item.getName());
            }
        });
        ((ImageView)(helper.getView(R.id.item_top_img))).setImageResource(item.getImgId());
        ((TextView)(helper.getView(R.id.item_top_name))).setText(item.getName());
    }

    private void bindContact(final BaseViewHolder helper, final ContactEntity item) {
        ((TextView) helper.getView(R.id.item_contact_name)).setText(item.getName());
        ((ImageView) helper.getView(R.id.item_contact_icon)).setImageBitmap(item.getIcon());
        helper.getView(R.id.item_contact_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO call
                Toast.makeText(v.getContext(),"call",Toast.LENGTH_SHORT).show();
                phone.call(item.getPhoneNumber());
            }
        });
        helper.getView(R.id.item_contact_icon).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 长按允许拖动
                if(mItemTouchHelper !=null) {
                    mItemTouchHelper.startDrag(helper);
                }
                return true;
            }
        });
        // 长按移除联系人
        helper.getView(R.id.item_contact_name).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                phone.onContactLongClick(item.getContactId(),helper.getAdapterPosition());
                return true;
            }
        });

    }

    public void setItemTouchHelper(ItemTouchHelper mItemTouchHelper){
        this.mItemTouchHelper = mItemTouchHelper;
    }
}
