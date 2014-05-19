package com.example.legendutils.Views;

import android.app.Dialog;
import android.content.Context;

public class FileDialog extends Dialog {

	public static final int FILE_MODE_OPEN = 0;
	public static final int FILE_MODE_SAVE = 1;

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

}
