package com.example.fetchrewardtask;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomizedExpandableListAdapter extends BaseExpandableListAdapter {


    private final Context context;
    private final Map<Integer, List<Item>> expandableDetailList;
    private final ArrayList<Integer> groupSet;

    public CustomizedExpandableListAdapter(Context context, Map<Integer, List<Item>> itemsHashMap) {
        this.context = context;
        this.expandableDetailList = itemsHashMap;
        groupSet = new ArrayList<>(expandableDetailList.keySet());
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        List <Item> childList = expandableDetailList.get(groupSet.get(groupPosition));
        Item item = childList != null ? childList.get(childPosition) : new Item();
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item, parent, false);
            }
            TextView expandedListTextView = convertView.findViewById(R.id.expandedListItem);
            expandedListTextView.setText(item.getName());
            return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return Objects.requireNonNull(this.expandableDetailList.get(groupPosition+1)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableDetailList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableDetailList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String listTitle = "Group  " + groupSet.get(groupPosition).toString();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, parent, false);
        }
        TextView listTitleTextView = convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return false;
    }
}

