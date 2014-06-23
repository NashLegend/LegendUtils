package com.example.legendutils.Dialogs;

import com.example.legendutils.R;
import com.example.legendutils.BuildIn.WPLoading;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.view.ViewGroup.LayoutParams;

/**
 * Win8 Style loading
 * 
 * @author NashLegend
 * 
 */
public class Win8ProgressDialog extends Dialog {
	private WPLoading loading;

	public Win8ProgressDialog(Context context) {
		super(context);
	}

	public Win8ProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public void setLoading(WPLoading loading) {
		this.loading = loading;
	}

	@Override
	public void cancel() {
		loading.cancel();
		super.cancel();
	}

	public Win8ProgressDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public static class Builder {
		private Context mContext;

		public Builder(Context context) {
			mContext = context;
		}

		public Win8ProgressDialog create() {
			final Win8ProgressDialog dialog = new Win8ProgressDialog(mContext,
					R.style.WPDialog);
			WPLoading loading = new WPLoading(mContext);
			dialog.setLoading(loading);
			dialog.setCanceledOnTouchOutside(false);
			dialog.addContentView(loading, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			loading.startAnimate();
			return dialog;
		}
	}

}
