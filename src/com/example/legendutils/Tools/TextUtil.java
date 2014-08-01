package com.example.legendutils.Tools;

import java.lang.reflect.Method;

import android.text.InputType;
import android.widget.EditText;

public class TextUtil {

	/**
	 * 禁止editText弹出输入法框，但是仍然可编辑，有光标等，与普通模式唯一的区别是没有软键盘
	 * @param editText
	 * @param show
	 */
	public static void setShowSoftInputOnFocus(EditText editText, boolean show) {
		try {
			// 反射setShowSoftInputOnFocus(false);
			editText.setInputType(editText.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
			Method setShowSoftInputOnFocus;
			setShowSoftInputOnFocus = EditText.class.getMethod(
					"setShowSoftInputOnFocus", boolean.class);
			setShowSoftInputOnFocus.setAccessible(true);
			setShowSoftInputOnFocus.invoke(editText, show);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
