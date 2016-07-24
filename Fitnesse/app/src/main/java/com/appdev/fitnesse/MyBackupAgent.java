package com.appdev.fitnesse;

import android.app.backup.BackupAgentHelper;
import android.app.backup.FileBackupHelper;

import java.io.File;

/**
 * Created by SasiDKM on 21-07-2016.
 */
public class MyBackupAgent extends BackupAgentHelper {
    private static final String DB_NAME="routineDB";

    @Override
    public void onCreate() {
        FileBackupHelper hosts=new FileBackupHelper(this,DB_NAME);
        addHelper(DB_NAME,hosts);

    }

    @Override
    public File getFilesDir() {
        File path=getDatabasePath(DB_NAME);
        return path.getParentFile();
    }


}
