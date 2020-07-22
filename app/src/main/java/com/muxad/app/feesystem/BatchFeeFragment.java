package com.muxad.app.feesystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class BatchFeeFragment extends Fragment {

    View v;
    ListView listView;
    String[] fruitNames = {"Apple","Orange","Kiwi","Passion","Banana"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_batch_fee, container, false);
        listView = (ListView) v.findViewById(R.id.batch_list);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ///////////////do some this duh/////////////////////////////////
                ////////////////////////////////////////////////////////////////
            }
        });
        return v;
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return fruitNames.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.batch_row,null);
            //getting view in row_data
            TextView name = view1.findViewById(R.id.fruits);

            name.setText(fruitNames[i]);
            return view1;
        }
    }
}
