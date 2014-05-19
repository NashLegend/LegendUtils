package com.example.legendutils.Dialogs;

import android.app.Dialog;
import android.content.Context;

public class FileDialog extends Dialog {

	/**
	 * 以打开文件模式打开文件对话框，有可能是文件夹也有可能是文件,最终返回值为一个File对象。
	 */
	public static final int FILE_MODE_OPEN = 0;

	/**
	 * 以打开文件模式打开文件对话框，只能选择文件夹而不是文件，最终返回值为一个File对象。
	 */
	public static final int FILE_MODE_OPEN_FOLDER = 1;

	/**
	 * 以打开文件模式打开文件对话框，只能选择文件而不是文件夹，最终返回值为一个File对象。
	 */
	public static final int FILE_MODE_OPEN_FILE = 2;

	/**
	 * 以保存文件模式打开文件对话框，最终返回保存文件的File对象。
	 * 
	 * 需要传入一个文件默认名，可修改。
	 * 
	 * 另外还要传入一个可保存的文件形式。如File、文件路径、byte[]、String、网址。
	 * 
	 * 如果是网址的话那就是下载器(要不要搞) TODO
	 */
	public static final int FILE_MODE_SAVE = 3;

	public FileDialog(Context context) {
		super(context);
	}

	public FileDialog(Context context, int theme) {
		super(context, theme);
	}

	public FileDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public class Builder {
		private Context context;

		public Builder(Context context) {
			this.context = context;
		}
	}

}
