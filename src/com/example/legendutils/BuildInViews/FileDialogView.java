package com.example.legendutils.BuildInViews;

import java.io.File;
import java.util.ArrayList;

import com.example.legendutils.R;
import com.example.legendutils.BuildInAdapters.FileListAdapter;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View.OnClickListener;

/**
 * FileDialog的view
 * 
 * @author NashLegend
 */
public class FileDialogView extends FrameLayout implements OnClickListener {

	private FileListAdapter adapter;
	private ListView listView;
	private EditText pathText;
	private ImageButton backButton;
	private ImageButton selectAllButton;

	// Call by outer method
	private Button cancelButton;
	private Button okButton;

	public FileDialogView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.dialog_file, this);

		listView = (ListView) findViewById(R.id.listview_dialog_file);
		pathText = (EditText) findViewById(R.id.edittext_dialog_file_path);
		backButton = (ImageButton) findViewById(R.id.imagebutton_dialog_file_back);
		selectAllButton = (ImageButton) findViewById(R.id.imagebutton_dialog_file_all);
		cancelButton = (Button) findViewById(R.id.button_dialog_file_cancel);
		okButton = (Button) findViewById(R.id.button_dialog_file_ok);

		backButton.setOnClickListener(this);
		selectAllButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		okButton.setOnClickListener(this);

		adapter = new FileListAdapter(context);
		listView.setAdapter(adapter);
	}

	public FileDialogView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FileDialogView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 打开目录
	 * 
	 * @param file
	 *            要打开的文件夹
	 */
	public void openFolder(File file) {
		if (!file.exists() || !file.isDirectory()) {
			// 若不存在此目录，则打开根文件夹
			file = Environment.getExternalStorageDirectory();
		}
		adapter.openFolder(file);
	}

	/**
	 * 打开目录
	 * 
	 * @param path
	 *            要打开的文件夹路径
	 * 
	 */
	public void openFolder(String path) {
		openFolder(new File(path));
	}

	/**
	 * 返回上级目录
	 */
	private void back2ParentLevel() {
		File file = adapter.getCurrentDirectory();
		if (file != null && file.getParentFile() != null) {
			openFolder(file.getParentFile());
			pathText.setText(file.getParentFile().getAbsolutePath());
		}
	}

	/**
	 * 选中当前目录所有文件
	 */
	private void selectAll() {
		adapter.selectAll();
	}

	/**
	 * @return 返回选中的文件列表
	 */
	public ArrayList<File> getSelectedFiles() {
		return adapter.getSelectedFiles();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.imagebutton_dialog_file_back) {
			back2ParentLevel();
		} else if (id == R.id.imagebutton_dialog_file_all) {
			selectAll();
		} else if (id == R.id.button_dialog_file_cancel) {
			// do nothing , called by outer method
		} else if (id == R.id.button_dialog_file_ok) {
			// do nothing , called by outer method
		} else {

		}
	}

}
