package com.example.legendutils.BuildInViews;

import java.io.File;

import com.example.legendutils.R;
import com.example.legendutils.BuildInAdapters.FileListAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * FileDialog的view
 * 
 * @author NashLegend
 */
public class FileDialogView extends FrameLayout {

	private FileListAdapter adapter;
	private ListView listView;

	public FileDialogView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.dialog_file, this);
	}

	public FileDialogView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FileDialogView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void openFolder(File file) {

	}

	public void openFolder(String path) {
		openFolder(new File(path));
	}

}
