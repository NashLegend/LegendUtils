package net.nashlegend.legendutils.Tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;

import android.util.Log;

public class SystemUtil {
	public static final String TAG = "SystemUtil";

	public static boolean isRooted() {
		return findBinary("su");
	}

	public static boolean findBinary(String binaryName) {
		boolean found = false;
		String[] places = { "/sbin/", "/system/bin/", "/system/xbin/",
				"/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/",
				"/system/bin/failsafe/", "/data/local/" };
		for (String where : places) {
			if (new File(where + binaryName).exists()) {
				found = true;
				break;
			}
		}
		return found;
	}

	public static BufferedReader shellExecute(String command,
			boolean requireRoot) {
		return shellExecute(new String[] { command }, requireRoot);
	}

	public static BufferedReader shellExecute(String[] commands,
			boolean requireRoot) {
		Process shell = null;
		DataOutputStream out = null;
		BufferedReader reader = null;
		String startCommand = requireRoot ? "su" : "sh";
		try {
			shell = Runtime.getRuntime().exec(startCommand);
			out = new DataOutputStream(shell.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(
					shell.getInputStream()));
			for (String command : commands) {
				out.writeBytes(command + "\n");
				out.flush();
			}
			out.writeBytes("exit\n");
			out.flush();
			shell.waitFor();
		} catch (Exception e) {
			Log.e(TAG, "ShellRoot#shExecute() finished with error", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {

			}
		}
		return reader;
	}
}
