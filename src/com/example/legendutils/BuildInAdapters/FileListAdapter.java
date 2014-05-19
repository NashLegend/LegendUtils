package com.example.legendutils.BuildInAdapters;

import java.io.File;
import java.util.ArrayList;

import com.example.legendutils.BuildInViews.FileItemView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileListAdapter extends BaseAdapter {
	private ArrayList<File> list = new ArrayList<File>();
	private Context mContext;

	public FileListAdapter(Context Context) {
		mContext = Context;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = new FileItemView(mContext);
			holder.fileItemView = (FileItemView) convertView;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.fileItemView.setFileItem(list.get(position));
		return holder.fileItemView;
	}

	class ViewHolder {
		FileItemView fileItemView;
	}

	public ArrayList<File> getList() {
		return list;
	}

	public void setList(ArrayList<File> list) {
		this.list = list;
	}

}
