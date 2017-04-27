package com.baiheplayer.desk.global.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.chest.Chest;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.global.Device;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class DeviceAdapter extends RecyclerView.Adapter {
    private final static int TYPE_DRAWER = 1;
    private final static int TYPE_CHEST = 2;
    private Context context;
    private List<Device> list;

    public DeviceAdapter(){}


    public DeviceAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof Chest) {
            return TYPE_CHEST;
        } else {                    //(list.get(position).getType() == Device.TYPE_DRAWER)
            return TYPE_DRAWER;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CHEST) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_d_chest, null);
            return new ChestVH(view);
        }
        if (viewType == TYPE_DRAWER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_d_drawer, null);
            return new DrawerVH(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DrawerVH) {
            Drawer drawer = (Drawer) list.get(position);
            ((DrawerVH) holder).onBindViewHolder(context, drawer);
        }
        if (holder instanceof ChestVH) {
            Chest chest = (Chest) list.get(position);
            ((ChestVH) holder).onBindViewHolder(context, chest);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
