package com.example.legendutils.Dialogs;

import com.example.legendutils.R;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

/**
 * 数字选择对话框
 * 
 * @author NashLegend
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NumberPickerDialog extends Dialog {

	public NumberPickerDialog(Context context) {
		super(context);
	}

	public NumberPickerDialog(Context context, int theme) {
		super(context, theme);
	}

	public NumberPickerDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public interface onNumberSetListener {
		public void onNumberSet(DialogInterface diaalog, int num);
	}

	public static class Builder {
		private Context context;
		private NumberPicker picker;
		private Button button;
		private int max = 0;
		private int min = 0;
		private onNumberSetListener numberSetListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setNumberSetListener(onNumberSetListener listener) {
			numberSetListener = listener;
			return this;
		}

		public Builder setMaxValue(int max) {
			this.max = max;
			return this;
		}

		public Builder setMinValue(int min) {
			this.min = min;
			return this;
		}

		public NumberPickerDialog create() {
			final NumberPickerDialog dialog = new NumberPickerDialog(context,
					R.style.Dialog);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.dialog_number_picker, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			picker = (NumberPicker) layout.findViewById(R.id.picker);
			picker.setMaxValue(max);
			picker.setMinValue(min);
			button = (Button) layout.findViewById(R.id.button_OK);
			button.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					if (numberSetListener != null) {
						// 如果不clearFocus将得不到EditText的值
						picker.clearFocus();
						numberSetListener.onNumberSet(dialog, picker.getValue());
						dialog.dismiss();
					}
				}
			});
			dialog.setContentView(layout);
			return dialog;
		}
	}

}
