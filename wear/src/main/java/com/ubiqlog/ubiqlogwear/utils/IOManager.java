package com.ubiqlog.ubiqlogwear.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.util.Log;

import com.ubiqlog.ubiqlogwear.common.Setting;

public class IOManager{

	String datenow = new String();
	
	public void logData(ArrayList<String> inArr) {
		FileWriter writer;
		datenow = DateFormat.getDateInstance(DateFormat.SHORT).format(System.currentTimeMillis());
		SimpleDateFormat dateformat = new SimpleDateFormat("M-d-yyyy");
        Log.i("IO-Manager", "--- Start writing to log");
        FileOutputStream outputStream;
		try {
			File logFile = new File(Setting.Instance(null).getLogFolder() + "/" + "log_" + dateformat.format(new Date()) + ".txt"); //datenow+ ".txt");
            Log.i("IO-Manager", "--- File = "+Setting.Instance(null).getLogFolder() + "/" + "log_" + dateformat.format(new Date()) + ".txt");
            writer = new FileWriter(logFile, true);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			Iterator<String> it = inArr.iterator();
			while (it.hasNext()) {
				String aaa = it.next();
				writer.append(aaa+ System.getProperty("line.separator"));
			}
			writer.flush();
			writer.close();
			writer = null;

            Log.i("IO-Manager", "--- Finished writing to log");

		} catch (Exception e) {
			
			 Log.e("DataAggregator", "--------Failed to write in a file-----"+ e.getMessage() + "; Stack: " +  Log.getStackTraceString(e));
			
		}
	}

	public void logError(String msg) {
		PrintWriter printWr;
		Date a = new Date (System.currentTimeMillis());
		String errorDate = a.getDate()+"-"+a.getMonth()+"-"+a.getYear();
		File errorFile = new File(Setting.Instance(null).getLogFolder(), "error_"+errorDate+".txt");
		try {
			printWr = new PrintWriter(new FileWriter(errorFile, true));
			printWr.append(msg + System.getProperty("line.separator"));
			printWr.flush();
			printWr.close();
			printWr = null;
		} catch (Exception ex) {
			Log.e("IOManager.logError", ex.getMessage(), ex);
		}
	}

}