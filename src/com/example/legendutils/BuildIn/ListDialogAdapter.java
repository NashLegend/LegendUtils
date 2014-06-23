
package com.example.legendutils.BuildIn;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListDialogAdapter extends BaseAdapter {
    ArrayList<ListDialogItem> list = new ArrayList<ListDialogItem>();
    Context mContext;

    public ListDialogAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int[] getSelectedItems() {
        ArrayList<ListDialogItem> items = new ArrayList<ListDialogItem>();
        for (Iterator<ListDialogItem> iterator = list.iterator(); iterator.hasNext();) {
            ListDialogItem itemDialogList = (ListDialogItem) iterator.next();
            if (itemDialogList.isSelected()) {
                items.add(itemDialogList);
            }
        }
        int[] selectsDialogLists = new int[items.size()];
        for (int i = 0; i < selectsDialogLists.length; i++) {
            selectsDialogLists[i] = items.get(i).getId();
        }
        return selectsDialogLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = new ListDialogItemView(mContext);
            holder.itemView = (ListDialogItemView) convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemView.setItem(list.get(position));
        return holder.itemView;
    }

    class ViewHolder {
        ListDialogItemView itemView;
    }

    public void setList(ArrayList<ListDialogItem> list) {
        this.list = list;
    }

}
