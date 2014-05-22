package com.example.legendutils.BuildInAdapters;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import com.example.legendutils.BuildInVO.FileItem;
import com.example.legendutils.BuildInViews.FileDialogView;
import com.example.legendutils.BuildInViews.FileItemView;
import com.example.legendutils.Dialogs.FileDialog;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FileListAdapter extends BaseAdapter {
	private ArrayList<FileItem> list = new ArrayList<FileItem>();
	private Context mContext;
	private File currentDirectory;
	private FileDialogView dialogView;

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
		holder.fileItemView.setFileItem(list.get(position), this,
				dialogView.getFileMode());
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
				currentDirectory = file;
				list.clear();
				File[] files = file.listFiles();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						list.add(new FileItem(files[i]));
					}
				}
				files = null;
				sortList();
				notifyDataSetChanged();
			}
		}
		dialogView.getPathText().setText(file.getAbsolutePath());
	}

	/**
	 * 选择当前目录下所有文件
	 */
	public void selectAll() {
		int mode = dialogView.getFileMode();
		if (mode > FileDialog.FILE_MODE_OPEN_FILE_MULTI) {
			// 单选模式应该看不到全选按钮才对
			return;
		}
		for (Iterator<FileItem> iterator = list.iterator(); iterator.hasNext();) {
			FileItem fileItem = (FileItem) iterator.next();

			if (mode == FileDialog.FILE_MODE_OPEN_FILE_MULTI
					&& fileItem.isDirectory()) {
				// fileItem是目录，但是只能选择文件，则返回
				continue;
			}
			if (mode == FileDialog.FILE_MODE_OPEN_FOLDER_MULTI
					&& !fileItem.isDirectory()) {
				// fileItem是文件，但是只能选择目录，则返回
				continue;
			}

			fileItem.setSelected(true);
		}
		notifyDataSetChanged();
	}

	/**
	 * 取消所有文件的选中状态
	 */
	public void unselectAll() {
		for (Iterator<FileItem> iterator = list.iterator(); iterator.hasNext();) {
			FileItem fileItem = (FileItem) iterator.next();
			fileItem.setSelected(false);
		}
		notifyDataSetChanged();
	}

	/**
	 * 只在选中时调用，取消选中不调用，且只由FileItemView调用
	 * 
	 * @param fileItem
	 */
	public void selectOne(FileItem fileItem) {
		int mode = dialogView.getFileMode();
		if (mode > FileDialog.FILE_MODE_OPEN_FILE_MULTI) {
			// 如果是单选
			if (mode == FileDialog.FILE_MODE_OPEN_FILE_SINGLE
					&& fileItem.isDirectory()) {
				// fileItem是目录，但是只能选择文件，则返回
				return;
			}
			if (mode == FileDialog.FILE_MODE_OPEN_FOLDER_SINGLE
					&& !fileItem.isDirectory()) {
				// fileItem是文件，但是只能选择目录，则返回
				return;
			}
			for (Iterator<FileItem> iterator = list.iterator(); iterator
					.hasNext();) {
				FileItem tmpItem = (FileItem) iterator.next();
				if (tmpItem.equals(fileItem)) {
					tmpItem.setSelected(true);
				} else {
					tmpItem.setSelected(false);
				}
			}
		} else {
			// 如果是多选
			if (mode == FileDialog.FILE_MODE_OPEN_FILE_MULTI
					&& fileItem.isDirectory()) {
				// fileItem是目录，但是只能选择文件，则返回
				return;
			}
			if (mode == FileDialog.FILE_MODE_OPEN_FOLDER_MULTI
					&& !fileItem.isDirectory()) {
				// fileItem是文件，但是只能选择目录，则返回
				return;
			}
			fileItem.setSelected(true);
		}

		notifyDataSetChanged();
	}

	public void sortList() {
		FileItemComparator comparator=new FileItemComparator();
		Collections.sort(list, comparator);
	}

	/**
	 * 取消一个的选择，其他逻辑都在FileItemView里面
	 */
	public void unselectOne() {
		dialogView.unselectCheckBox();
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

	public class FileItemComparator implements Comparator<FileItem> {

		@Override
		public int compare(FileItem lhs, FileItem rhs) {
			if (lhs.isDirectory() != rhs.isDirectory()) {
				// 如果一个是文件，一个是文件夹，优先按照类型排序
				if (lhs.isDirectory()) {
					return -1;
				} else {
					return 1;
				}
			} else {
				// 如果同是文件夹或者文件，则按名称排序
				return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
			}
		}
	}

	public File getCurrentDirectory() {
		return currentDirectory;
	}

	public FileDialogView getDialogView() {
		return dialogView;
	}

	public void setDialogView(FileDialogView dialogView) {
		this.dialogView = dialogView;
	}

}
