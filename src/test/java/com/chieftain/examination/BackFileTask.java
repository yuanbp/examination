package com.chieftain.examination;

import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * com.chieftain.jtestcodes [workset]
 * Created by chieftain on 2018/11/25
 *
 * @author chieftain on 2018/11/25
 */
public class BackFileTask implements Runnable {

    private CopyOnWriteArrayList<File> files;

    public BackFileTask(CopyOnWriteArrayList<File> files) {
        this.files = files;
    }

    @Override
    public void run() {
        if(files.size() > 0) {
            try {
                for (File file : files) {
                    String content = FileNIOUtils.readFile(file);
                    System.out.println(content);
                    String filePath = "/Users/chieftain/retainfiles/tmp/send_back" + File.separator + file.getName();
                    File newFile = new File(filePath);
                    FileNIOUtils.nioCopy(file, newFile, true);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public CopyOnWriteArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(CopyOnWriteArrayList<File> files) {
        this.files = files;
    }
}
