package com.yzk.launcher.adapter;

import android.content.Context;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.yzk.launcher.MainActivity;
import com.yzk.launcher.entity.ContactEntity;

import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * Created by yzk on 2021/5/2
 */

public class RecycleViewItemTouchHelper extends ItemTouchHelper.Callback {


    private  MainAdapter myAdapter;
    private Context context;

    public  RecycleViewItemTouchHelper(MainAdapter myAdapter,Context context){
        this.myAdapter = myAdapter;
        this.context = context;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        if (myAdapter == null || context == null) return false;
        //得到当拖拽的viewHolder的Position
        int fromPosition = viewHolder.getAdapterPosition();
        //拿到当前拖拽到的item的viewHolder
        int toPosition = target.getAdapterPosition();
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(myAdapter.getData(), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(myAdapter.getData(), i, i - 1);
            }
        }
        // 更新
        if(myAdapter !=null) myAdapter.notifyItemMoved(fromPosition, toPosition);

        LinkedHashSet<String> displayContactIds = new LinkedHashSet<>();
        for (ContactEntity contactEntity: myAdapter.getData()){
            if(contactEntity.getContactId()!= null){
                displayContactIds.add(contactEntity.getContactId());
            }
        }
        // 拖动后更新 SP
        MainActivity.saveDataToSp(displayContactIds,context);
        Log.i("ItemTouchHelper","------onMove-----");
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        Log.i("ItemTouchHelper","------onSwiped-----");
    }

    /**
     * 重写拖拽不可用,
     * 设置 mItemTouchHelper.startDrag(helper); 可拖动
     *
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }
}
