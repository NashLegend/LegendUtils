package com.example.legendutils.BuildInViews;

import java.io.File;

import com.example.legendutils.R;
import com.example.legendutils.BuildInVO.FileItem;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 文件列表单个item的view
 * 
 * @author NashLegend
 */
public class FileItemView extends FrameLayout {

	private ImageView icon;
	private TextView title;
	private ViewGroup rootFileItemView;

	private FileItem fileItem;

	public FileItemView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_file_item, this);
		icon = (ImageView) findViewById(R.id.image_file_icon);
		title = (TextView) findViewById(R.id.text_file_title);
		rootFileItemView = (ViewGroup) findViewById(R.id.rootFileItemView);
	}

	public FileItem getFileItem() {
		return fileItem;
	}

	public void setFileItem(FileItem fileItem) {
		this.fileItem = fileItem;
		icon.setImageResource(fileItem.getIcon());
		title.setText(fileItem.getName());
	}

	public void setFileItem(File file) {
		setFileItem(new FileItem(file));
	}

	public void setFileItem(String path) {
		setFileItem(new FileItem(path));
	}

	/**
	 * 切换选中、未选中状态
	 */
	public void toggleSelectState() {
		if (fileItem.isSelected()) {
			rootFileItemView.setBackgroundColor(Color.CYAN);
			fileItem.setSelected(false);
		} else {
			rootFileItemView.setBackgroundColor(Color.WHITE);
			fileItem.setSelected(true);
		}
	}

}
