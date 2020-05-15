package com.example.korpustokenandroidappnongit.scripts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.korpustokenandroidappnongit.R;

import java.util.HashMap;
import java.util.List;

public class MainActivityExpandableListAdapter extends BaseExpandableListAdapter {
    private HashMap<String, List<String>> stringListHashMap;
    private String[] listHeaderGroup;

    public MainActivityExpandableListAdapter(HashMap<String, List<String>> stringListHashMap){
        this.stringListHashMap = stringListHashMap;
        this.listHeaderGroup = stringListHashMap.keySet().toArray(new String[0]);
    }

    @Override
    public int getGroupCount() {
        return this.listHeaderGroup.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.stringListHashMap.get(this.listHeaderGroup[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listHeaderGroup[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.stringListHashMap.get(this.listHeaderGroup[groupPosition]).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition*childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_expandable_list_group, parent, false);

        TextView textView = convertView.findViewById(R.id.expandable_lst_group);
        textView.setText(String.valueOf(getGroup(groupPosition)));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_expandable_item, parent, false);

        TextView textView = convertView.findViewById(R.id.item_expandable);
        textView.setText(String.valueOf(getChild(groupPosition, childPosition)));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
