package com.example.legendutils.Dialogs;

import android.app.Dialog;
import android.content.Context;

/**
 * Win8 Style loading
 * 
 * @author NashLegend
 * 
 */
public class Win8ProgressDialog extends Dialog {

	public Win8ProgressDialog(Context context) {
		super(context);
	}

	public Win8ProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public Win8ProgressDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

}
