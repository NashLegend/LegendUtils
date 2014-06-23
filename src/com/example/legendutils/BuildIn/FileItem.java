
package com.example.legendutils.BuildIn;

import java.io.File;
import java.net.URI;

import com.example.legendutils.R;
import com.example.legendutils.Tools.FileUtil;

/**
 * 文件对象，继承自File
 * 
 * @author NashLegend
 */
public class FileItem extends File {

    private static final long serialVersionUID = 2675728441786325207L;

    /**
     * 文件在文件列表中显示的icon
     */
    private int icon = R.drawable.ic_launcher;

    /**
     * 文件是否在列表中被选中
     */
    private boolean selected = false;

    /**
     * 文件类型，默认为FILE_TYPE_NORMAL，即普通文件。
     */
    private int fileType = FileUtil.FILE_TYPE_NORMAL;

    /**
     * 文件后缀
     */
    private String suffix = "";

    public FileItem(File file) {
        this(file.getAbsolutePath());
    }

    public FileItem(String path) {
        super(path);
        setFileTypeBySuffix();
    }

    public FileItem(URI uri) {
        super(uri);
        setFileTypeBySuffix();
    }

    public FileItem(File dir, String name) {
        super(dir, name);
        setFileTypeBySuffix();
    }

    public FileItem(String dirPath, String name) {
        super(dirPath, name);
        setFileTypeBySuffix();
    }

    /**
     * 根据后缀取得文件类型
     */
    private void setFileTypeBySuffix() {

        int type = FileUtil.getFileType(this);
        setFileType(type);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getFileType() {
        return fileType;
    }

    /**
     * 设置fileTyle,同时修改icon
     * 
     * @param fileType
     */
    public void setFileType(int fileType) {
        this.fileType = fileType;
        switch (fileType) {
            case FileUtil.FILE_TYPE_APK:
                setIcon(R.drawable.format_apk);
                break;
            case FileUtil.FILE_TYPE_FOLDER:
                setIcon(R.drawable.format_folder);
                break;
            case FileUtil.FILE_TYPE_IMAGE:
                setIcon(R.drawable.format_picture);
                break;
            case FileUtil.FILE_TYPE_NORMAL:
                setIcon(R.drawable.format_unkown);
                break;
            case FileUtil.FILE_TYPE_AUDIO:
                setIcon(R.drawable.format_music);
                break;
            case FileUtil.FILE_TYPE_TXT:
                setIcon(R.drawable.format_text);
                break;
            case FileUtil.FILE_TYPE_VIDEO:
                setIcon(R.drawable.format_media);
                break;
            case FileUtil.FILE_TYPE_ZIP:
                setIcon(R.drawable.format_zip);
                break;
            case FileUtil.FILE_TYPE_HTML:
                setIcon(R.drawable.format_html);
                break;
            case FileUtil.FILE_TYPE_PDF:
                setIcon(R.drawable.format_pdf);
                break;
            case FileUtil.FILE_TYPE_WORD:
                setIcon(R.drawable.format_word);
                break;
            case FileUtil.FILE_TYPE_EXCEL:
                setIcon(R.drawable.format_excel);
                break;
            case FileUtil.FILE_TYPE_PPT:
                setIcon(R.drawable.format_ppt);
                break;
            case FileUtil.FILE_TYPE_TORRENT:
                setIcon(R.drawable.format_torrent);
                break;
            case FileUtil.FILE_TYPE_EBOOK:
                setIcon(R.drawable.format_ebook);
                break;
            case FileUtil.FILE_TYPE_CHM:
                setIcon(R.drawable.format_chm);
                break;
            default:
                setIcon(R.drawable.format_unkown);
                break;
        }
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
