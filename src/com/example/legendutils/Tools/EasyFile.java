package com.example.legendutils.Tools;

import java.io.File;
import java.net.URI;

import org.apache.http.client.methods.HttpRequestBase;

public class EasyFile extends File {

	public EasyFile(String path) {
		super(path);
	}

	public EasyFile(URI uri) {
		super(uri);
	}

	public EasyFile(File dir, String name) {
		super(dir, name);
	}

	public EasyFile(String dirPath, String name) {
		super(dirPath, name);
	}

	public boolean copy2File(File destFile) {
		return false;
	}

	public boolean copy2Directory(File destFile) {
		return false;
	}

	public boolean move2File(File destFile) {
		return false;
	}

	public boolean move2Directory(File destFile) {
		return false;
	}

	@Override
	public boolean delete() {
		return super.delete();
	}

	public void upload(HttpRequestBase request, String fileName) {

	}

}
