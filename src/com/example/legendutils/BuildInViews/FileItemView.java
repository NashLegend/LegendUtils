package com.example.legendutils.BuildInViews;

import java.io.File;

import com.example.legendutils.R;
import com.example.legendutils.BuildInVO.FileItem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * 文件列表单个item的view
 * 
 * @author NashLegend
 */
public class FileItemView extends FrameLayout implements OnClickListener {

	private ImageView icon;
	private TextView title;
	private Button button;
	private ViewGroup rootFileItemView;

	private FileItem fileItem;

	public FileItemView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_file_item, this);
		icon = (ImageView) findViewById(R.id.image_file_icon);
		title = (TextView) findViewById(R.id.text_file_title);
		button = (Button) findViewById(R.id.button_file_item_select);
		rootFileItemView = (ViewGroup) findViewById(R.id.rootFileItemView);
		setOnClickListener(this);
		button.setOnClickListener(this);
	}

	public FileItem getFileItem() {
		return fileItem;
	}

	public void setFileItem(FileItem fileItem) {
		this.fileItem = fileItem;
		icon.setImageResource(fileItem.getIcon());
		title.setText(fileItem.getName());
		toggleSelectState();
	}

	public void setFileItem(File file) {
		setFileItem(new FileItem(file));
	}

	public void setFileItem(String path) {
		setFileItem(new FileItem(path));
	}

	/**
	 * 切换选中、未选中状态,fileItem.setSelected(boolean)先发生;
	 */
	public void toggleSelectState() {
		if (fileItem.isSelected()) {
			rootFileItemView.setBackgroundColor(Color.CYAN);
		} else {
			rootFileItemView.setBackgroundColor(Color.WHITE);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.button_file_item_select) {
			fileItem.setSelected(!fileItem.isSelected());
			toggleSelectState();
		}else {
			if (fileItem.isDirectory()) {
				openFolder();
			}else {
				fileItem.setSelected(!fileItem.isSelected());
				toggleSelectState();
			}
		}
	}
	
	/**
	 * 打开文件夹
	 */
	public void openFolder() {
		
	}
}
