package com.example.legendutils.BuildInAdapters;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.example.legendutils.BuildInVO.FileItem;
import com.example.legendutils.BuildInViews.FileItemView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileListAdapter extends BaseAdapter {
	private ArrayList<FileItem> list = new ArrayList<FileItem>();
	private Context mContext;
	private File currentDirectory;

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

	public ArrayList<FileItem> getList() {
		return list;
	}

	public void setList(ArrayList<FileItem> list) {
		this.list = list;
	}

	/**
	 * 打开文件夹，更新文件列表
	 * 
	 * @param file
	 */
	public void openFolder(File file) {
		if (file != null && file.exists() && file.isDirectory()) {
			if (!file.equals(currentDirectory)) {
				// 与当前目录不同
				File[] files = file.listFiles();
				list.clear();
				for (int i = 0; i < files.length; i++) {
					list.add(new FileItem(files[i]));
				}
				files = null;
				notifyDataSetChanged();
			}
		}
	}

	/**
	 * 选择当前目录下所有文件
	 */
	public void selectAll() {
		for (Iterator<FileItem> iterator = list.iterator(); iterator.hasNext();) {
			FileItem fileItem = (FileItem) iterator.next();
			fileItem.setSelected(true);
		}
		notifyDataSetChanged();
	}

	/**
	 * @return 选中的文件列表
	 */
	public ArrayList<File> getSelectedFiles() {
		ArrayList<File> selectedFiles = new ArrayList<File>();
		for (Iterator<FileItem> iterator = list.iterator(); iterator.hasNext();) {
			File file = (File) iterator.next();// 强制转换为File
			selectedFiles.add(file);
		}
		return selectedFiles;
	}

	public File getCurrentDirectory() {
		return currentDirectory;
	}

}
