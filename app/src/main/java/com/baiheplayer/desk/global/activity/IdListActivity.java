package com.baiheplayer.desk.global.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.connect.Wifi;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/3/30.
 */
@ContentView(R.layout.activity_ssid)
public class IdListActivity extends BaseActivity {

    private ArrayList<Wifi> ssids;

    @ViewInject(R.id.lv_ssid)
    private ListView mListView;

    @Override
    public void onView(Bundle savedInstanceState) {

        ssids = (ArrayList<Wifi>) getIntent().getSerializableExtra("ssids");

        mListView.setAdapter(new WifiAdapter());

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Wifi ssid = ssids.get(position);
                Log.i("chen","onClick-------------->ssid:"+ssid.getName()+" dbm:"+ssid.getDbm());
                Intent data = new Intent();
                data.putExtra("ssid", ssid.getName());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    private class WifiAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return ssids.size();
        }

        @Override
        public Object getItem(int position) {
            return ssids.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(IdListActivity.this).inflate(R.layout.item_wifi,null);
            TextView tvName = (TextView) convertView.findViewById(R.id.name);
            TextView tvDbm = (TextView) convertView.findViewById(R.id.dbm);
            Wifi item = ssids.get(position);
            tvName.setText(item.getName());
            tvDbm.setText(String.valueOf(item.getDbm()));
            return convertView;
        }
    }
}
