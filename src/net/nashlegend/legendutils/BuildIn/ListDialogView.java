
package net.nashlegend.legendutils.BuildIn;

import java.util.ArrayList;

import net.nashlegend.legendutils.Dialogs.ListDialog.OnItemResultListener;

import net.nashlegend.legendutils.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ListDialogView extends FrameLayout implements OnClickListener {

    ListView listView;
    ListDialogAdapter adapter;
    boolean multiSelect = false;
    OnItemResultListener onItemResultListener;
    Button positiveButton;
    Button negativeButton;
    LinearLayout footerLayout;

    public ListDialogView(Context context, boolean multi, String[] contents) {
        super(context);
        ArrayList<ListDialogItem> list = getDialogLists(multi, contents);
        multiSelect = multi;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.dialog_list, this);
        footerLayout = (LinearLayout) findViewById(R.id.footer_layout_listdialog);
        positiveButton = (Button) findViewById(R.id.button_listdialog_ok);
        negativeButton = (Button) findViewById(R.id.button_listdialog_cancel);

        listView = (ListView) findViewById(R.id.list_dialog_list);
        adapter = new ListDialogAdapter(getContext());
        adapter.setList(list);
        listView.setAdapter(adapter);

        if (!multiSelect) {
            footerLayout.setVisibility(View.GONE);
            listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    onSingleItemSelected(position);
                }

            });
        } else {
            positiveButton.setOnClickListener(this);
            negativeButton.setOnClickListener(this);
        }
    }

    private ArrayList<ListDialogItem> getDialogLists(boolean multi, String[] contents) {
        ArrayList<ListDialogItem> list = new ArrayList<ListDialogItem>();
        for (int i = 0; i < contents.length; i++) {
            ListDialogItem item = new ListDialogItem();
            String content = contents[i];
            item.setId(i);
            item.setContent(content);
            item.setSelected(false);
            item.setMultiSelect(multi);
            list.add(item);
        }
        return list;
    }

    public ListDialogView(Context context) {
        super(context);
    }

    public ListDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListDialogView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void onSingleItemSelected(int position) {
        int[] items = {
                position
        };
        if (onItemResultListener != null) {
            onItemResultListener.onItemsSelected(items);
        }
    }

    public void onItemsSelected() {
        // TODO
        int[] items = adapter.getSelectedItems();
        if (onItemResultListener != null) {
            onItemResultListener.onItemsSelected(items);
        }
    }

    public void onCancelSelect() {
        if (onItemResultListener != null) {
            onItemResultListener.onCancelSelect();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_listdialog_ok) {
            onItemsSelected();
        } else if (id == R.id.button_listdialog_cancel) {
            onCancelSelect();
        } else {
            
        }
    }

    public OnItemResultListener getOnItemResultListener() {
        return onItemResultListener;
    }

    public void setOnItemResultListener(OnItemResultListener onItemResultListener) {
        this.onItemResultListener = onItemResultListener;
    }

}
