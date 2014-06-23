
package com.example.legendutils.BuildIn;

import com.example.legendutils.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListDialogItemView extends FrameLayout implements OnClickListener {
    ListDialogItem item;
    CheckBox checkBox;
    TextView textView;
    RelativeLayout layout;

    public ListDialogItemView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_list, this);
        textView = (TextView) findViewById(R.id.item_content);
        checkBox = (CheckBox) findViewById(R.id.item_check);
        layout = (RelativeLayout) findViewById(R.id.item_layout);
        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setSelected(isChecked);
            }
        });
    }

    public void setItem(ListDialogItem item) {
        this.item = item;
        if (item.isMultiSelect()) {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setChecked(item.isSelected());
        } else {
            checkBox.setVisibility(View.GONE);
        }
        textView.setText(item.getContent());
    }

    public ListDialogItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListDialogItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void toggleSelect() {
        item.setSelected(!item.isSelected());
        if (item.isMultiSelect()) {
        } else {
            
            layout.setBackgroundColor(Color.CYAN);
        }

    }

    @Override
    public void onClick(View v) {
        toggleSelect();
    }

}
