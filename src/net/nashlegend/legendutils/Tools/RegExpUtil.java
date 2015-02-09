package net.nashlegend.legendutils.Tools;

public class RegExpUtil {

	/**
	 * 匹配IPV4地址
	 */
	public static final String IPV4RegExp = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
	/**
	 * 匹配座机号
	 */
	public static final String PhoneRegExp = "\\(0\\d{2}\\)[- ]?\\d{8}|0\\d{2}[- ]?\\d{8}|\\(0\\d{3}\\)[- ]?\\d{7}|0\\d{3}[- ]?\\d{7}";
	/**
	 * 匹配网络图片地址，未考虑url中的\w-以外的字符
	 */
	public static final String NetPictureRegExp = "http://((([A-Za-z0-9][A-Za-z0-9-]*\\.)+[A-Za-z]{2,})|(((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)))(:\\d{1,5})?(/[\\w-]+)+\\.(?i)(jpg|bmp|png|gif)";
	/**
	 * 电子邮件匹配正则表达式
	 */
	public static final String EmailRegExp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	/**
	 * 中国手机号匹配正则表达式
	 */
	public static final String MobilePhoneNumberRegExp = "(\\+86|86)?(134|135|136|137|138|139|147|150|151|152|157|158|159|182|183|184|187|188|130|131|132|145|155|156|185|186|133|153|180|181|189|170|176|177|178|140|141|142|143|144|146|148|149|154)\\d{8}";

	/**
	 * 生成比较数字的正则表达式,该正则表达式匹配包含有比num大的正整数的字符串。
	 * 匹配时将匹配完全数字字符串，不会将一个长的数字字符串分开匹配，而是当作一个数字，前面的0会被忽略
	 * 
	 * @param num
	 * @return
	 */
	public static String regLessThanInt(int num) {
		return regLargerThanInt(num, false);
	}

	/**
	 * 生成比较数字的正则表达式,该正则表达式匹配包含有比num小的正整数的字符串.
	 * 如kk512it匹配regLessThanInt(513),kk513it不会.
	 * 如果include为true。则只要包含num就可以，5123匹配regLessThanInt(513)，因为5123包含512
	 * 如果为true，前面的0不会被忽略，0000777匹配regLessThanInt(1000, true)，结果将是0,0,0,0,777。
	 * 如果为false，前面的0会被忽略，0000777匹配regLessThanInt(1000, false)，结果将是777。00007结果将是7
	 * 
	 * @param num
	 *            要比较的数字
	 * @param include
	 *            一般不需要
	 * @return
	 */
	public static String regLessThanInt(int num, boolean include) {
		String front = "";
		String back = "";
		if (include) {
			front = "(";
			back = ")";
		} else {
			front = "(?<=\\D|\\b|0)(";
			back = ")(?=\\D|\\b)";
		}
		int cou = String.valueOf(num).length();
		String reg = "";

		if (cou > 1) {
			int k1 = (int) ((num % Math.pow(10, cou)) / Math.pow(10, cou - 1));
			String lessThanK = null;
			if (k1 > 2) {
				lessThanK = "[1-" + (k1 - 1) + "]\\d{" + (cou - 1) + "}";
			} else {
				if (k1 == 2) {
					lessThanK = "1\\d{" + (cou - 1) + "}";
				}
			}
			if (lessThanK != null) {
				reg += lessThanK + "|";
			}

			for (int i = cou - 1; i > 0; i--) {
				int head = (int) (num / Math.pow(10, i));
				int tail = (int) ((num % Math.pow(10, i)) / Math.pow(10, i - 1));
				String top = null;
				if (tail > 1) {
					top = head + "[0-" + (tail - 1) + "]"
							+ (i == 1 ? "" : "\\d{" + (i - 1) + "}");
				} else if (tail == 1) {
					top = head + "0" + (i == 1 ? "" : "\\d{" + (i - 1) + "}");
				}
				if (top != null) {
					reg += top + "|";
				}
			}
			String low = null;
			if (cou > 2) {
				low = "[1-9]\\d{0," + (cou - 2) + "}|0";// [1-9]xx或者0
			} else {
				// 一位允许0开头
				low = "\\d";
			}
			reg += low;
		} else {
			reg = "[0-" + (num - 1) + "]";
		}
		reg = front + reg + back;
		return reg;
	}

	/**
	 * 生成比较数字的正则表达式,该正则表达式匹配包含有比num大的正整数的字符串。
	 * 匹配时将匹配完全数字字符串，不会将一个长的数字字符串分开匹配，而是当作一个数字
	 * 
	 * @param num
	 * @return
	 */
	public static String regLargerThanInt(int num) {
		return regLargerThanInt(num, false);
	}

	/**
	 * 生成比较数字的正则表达式,该正则表达式匹配包含有比num大的正整数的字符串.
	 * 如kk512it匹配regLessThanInt(511),kk511it不会. 不会匹配数字前面的0，007只会匹配7
	 * 如果include为true。则只要包含num就可以。貌似不必加……
	 * 
	 * @param num
	 *            要比较的数字
	 * @param include
	 *            一般为false
	 * @return
	 */
	public static String regLargerThanInt(int num, boolean include) {
		String front = "";
		String back = "";
		if (include) {
			front = "(";
			back = ")";
		} else {
			front = "(?<=\\D|\\b|0)(";
			back = ")(?=\\D|\\b)";
		}
		int cou = String.valueOf(num).length();
		String reg = "";
		String longer = "[1-9]\\d{" + cou + ",}";
		reg += longer + "|";

		if (cou > 1) {
			int k1 = (int) ((num % Math.pow(10, cou)) / Math.pow(10, cou - 1));
			String largerThanK = null;
			if (k1 == 8) {
				largerThanK = "9" + "\\d{" + (cou - 1) + "}";
			} else if (k1 < 8) {
				largerThanK = "[" + (k1 + 1) + "-9]" + "\\d{" + (cou - 1) + "}";
			}

			if (largerThanK != null) {
				reg += largerThanK + "|";
			}
			for (int i = cou - 1; i > 0; i--) {
				int head = (int) (num / Math.pow(10, i));
				int tail = (int) ((num % Math.pow(10, i)) / Math.pow(10, i - 1));
				String top = null;
				if (tail == 8) {
					top = head + "9" + (i == 1 ? "" : "\\d{" + (i - 1) + "}");
				} else if (tail < 8) {
					top = head + "[" + (tail + 1) + "-9]"
							+ (i == 1 ? "" : "\\d{" + (i - 1) + "}");
				}
				if (top != null) {
					reg += top + "|";
				}
			}
		} else {
			String lar = "";
			if (num == 8) {
				lar = "9";
			} else if (num < 8) {
				lar = "[" + (num + 1) + "-9]";
			}
			reg += lar;
		}

		if (reg.lastIndexOf("|") == reg.length() - 1) {
			reg = reg.substring(0, reg.length() - 1);
		}
		reg = front + reg + back;
		return reg;
	}

	/**
	 * 生成密码匹配正则
	 * 
	 * @param min
	 *            密码最低长度
	 * @param max
	 *            密码最高长度
	 * @param requireLowerCase
	 *            是否必须有小写
	 * @param requireHigherCase
	 *            是否必须有大写
	 * @param requireNumber
	 *            是否必须有数字
	 * @param requireSpecialChara
	 *            是否必须有特殊字符（@#$%._-）
	 * @return 密码正则表达式
	 */
	public static String regPassword(int min, int max,
			boolean requireLowerCase, boolean requireHigherCase,
			boolean requireNumber, boolean requireSpecialChara) {
		String low = "(?=.*[a-z])";
		String high = "(?=.*[A-Z])";
		String num = "(?=.*\\d)";
		String spe = "(?=.*[@#$%._-])";
		String reg = "^(";
		if (requireLowerCase) {
			reg += low;
		}
		if (requireHigherCase) {
			reg += high;
		}
		if (requireNumber) {
			reg += num;
		}
		if (requireSpecialChara) {
			reg += spe;
		}
		reg += ".{" + min + "," + max + "})$";
		return reg;
	}
}
