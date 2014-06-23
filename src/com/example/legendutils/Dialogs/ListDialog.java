
package com.example.legendutils.Dialogs;

import com.example.legendutils.BuildIn.ListDialogView;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.NumberPicker;

public class ListDialog extends Dialog {

    public ListDialog(Context context) {
        super(context);
    }

    public static interface OnItemSelectedListener {
        void OnItemSelected(int[] items);

        void OnCalcelSelect();
    }

    public static interface OnItemResultListener {
        void onItemsSelected(int[] items);

        void onCancelSelect();
    }

    public static class Builder {
        private Context mContext;
        NumberPicker picker;
        String[] values;
        String title = "";
        boolean multi = false;
        OnItemSelectedListener onItemSelectedListener;
        ListDialog dialog;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setTitle(String t) {
            title = t;
            return this;
        }

        public Builder setMultiSelect(boolean mul) {
            multi = mul;
            return this;
        }

        public Builder setDisplayedValues(String[] values) {
            this.values = values;
            return this;
        }

        public Builder setOnItemSelectedListener(OnItemSelectedListener listener) {
            onItemSelectedListener = listener;
            return this;
        }

        public ListDialog create() {
            dialog = new ListDialog(mContext);

            ListDialogView dialogView = new ListDialogView(mContext, multi, values);
            dialogView.setOnItemResultListener(new OnItemResultListener() {

                @Override
                public void onItemsSelected(int[] items) {
                    if (onItemSelectedListener != null) {
                        dialog.dismiss();
                        onItemSelectedListener.OnItemSelected(items);
                    }
                }

                @Override
                public void onCancelSelect() {
                    if (onItemSelectedListener != null) {
                        dialog.dismiss();
                        onItemSelectedListener.OnCalcelSelect();
                    }
                }
            });
            LayoutParams params = new LayoutParams(-1, -2);
            dialog.setTitle(title);
            dialog.addContentView(dialogView, params);

            return dialog;
        }
    }

}
