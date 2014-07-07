package com.example.legendutils.Tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;

import android.util.Log;

public class SystemUtil {
	public static final String TAG = "SystemUtil";

	public static void executeCommands(String[] commands, boolean requireRoot) {
		Process shell = null;
		DataOutputStream out = null;
		BufferedReader in = null;
		String startCommand = requireRoot ? "su" : "sh";
		try {
			Log.i(TAG, "Starting exec of " + startCommand);
			shell = Runtime.getRuntime().exec(startCommand);
			out = new DataOutputStream(shell.getOutputStream());
			in = new BufferedReader(new InputStreamReader(
					shell.getInputStream()));

			Log.i(TAG, "Executing commands...");
			for (String command : commands) {
				Log.i(TAG, "Executing: " + command);
				out.writeBytes(command + "\n");
				out.flush();
			}

			out.writeBytes("exit\n");
			out.flush();
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line).append("\n");
			}
			Log.i(TAG, sb.toString());
			shell.waitFor();
		} catch (Exception e) {
			Log.e(TAG, "ShellRoot#shExecute() finished with error", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				// shell.destroy();
			} catch (Exception e) {
				// hopeless
			}
		}
	}

	public static boolean isRooted() {
		return findBinary("su");
	}

	public static boolean findBinary(String binaryName) {
		boolean found = false;
		if (!found) {
			String[] places = { "/sbin/", "/system/bin/", "/system/xbin/",
					"/data/local/xbin/", "/data/local/bin/",
					"/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/" };
			for (String where : places) {
				if (new File(where + binaryName).exists()) {
					found = true;
					break;
				}
			}
		}
		return found;
	}
}
