package net.nashlegend.legendutils.Tools;

import java.lang.reflect.Method;

import android.text.InputType;
import android.widget.EditText;

public class TextUtil {

	public static boolean isChineseCharactor(char ch) {
		return ch >= 0x4e00 && ch <= 0x9fa5 || ch == 0x3007;
	}

	public static boolean isEnglishCharactor(char ch) {
		return ch >= 65 && ch <= 90 || ch >= 97 && ch <= 122;
	}

	public static boolean isNumberCharactor(char ch) {
		return ch >= 48 && ch <= 57;
	}

	public static String[] splitIgnoringEmpty(String sourceString,
			String splitter) {
		String[] orig = sourceString.split(splitter);
		int len = sourceString.length();
		int olen = len;
		for (int i = 0; i < orig.length; i++) {
			if ("".equals(orig[i])) {
				len--;
			}
		}
		if (len == olen) {
			return orig;
		} else {
			String[] res = new String[len];
			for (int i = 0, k = 0; i < orig.length && k < len; i++) {
				if (!"".equals(orig[i])) {
					res[k] = orig[i];
					k++;
				}
			}
			return res;
		}
	}

	/**
	 * 禁止editText弹出输入法框，但是仍然可编辑，有光标等，与普通模式唯一的区别是没有软键盘
	 * 
	 * @param editText
	 * @param show
	 */
	public static void setShowSoftInputOnFocus(EditText editText, boolean show) {
		try {
			// 反射setShowSoftInputOnFocus(false);
			editText.setInputType(editText.getInputType()
					| InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
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
