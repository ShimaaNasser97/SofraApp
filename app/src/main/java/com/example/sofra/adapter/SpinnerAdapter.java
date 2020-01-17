package com.example.sofra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.sofra.R;
import com.example.sofra.data.model.generalResponce.GeneralResponceData;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<GeneralResponceData> generalResponceDataList = new ArrayList<>();
    private LayoutInflater inflter;
    public int selectedId = 0;

    public SpinnerAdapter(Context applicationContext) {
        this.context = applicationContext;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<GeneralResponceData> generalResponseDataList, String hint) {
        this.generalResponceDataList = new ArrayList<>();
        this.generalResponceDataList.add(new GeneralResponceData(0, hint));
        this.generalResponceDataList.addAll(generalResponseDataList);
    }

    @Override
    public int getCount() {
        return generalResponceDataList.size();
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
        view = inflter.inflate(R.layout.item_custom_spinner, null);

        TextView names = (TextView) view.findViewById(R.id.item_spinner_tv_text);

        names.setText(generalResponceDataList.get(i).getName());
        selectedId = generalResponceDataList.get(i).getId();

        return view;
    }
}