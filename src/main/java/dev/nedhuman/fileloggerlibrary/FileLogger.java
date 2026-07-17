package dev.nedhuman.fileloggerlibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.zip.GZIPOutputStream;

/**
 * The main FileLogger class
 *
 * @author NedHuman
 */
public class FileLogger {

    private final File logsDirectory;
    private final List<Loggable> logs;
    private boolean newLogsAdded;
    private final String currentInstanceName;

    /**
     * Initialise a new FileLogger instance.
     * @param logsDirectory the logs directory
     */
    public FileLogger(File logsDirectory) {
        this.logsDirectory = logsDirectory;
        this.logs = new LinkedList<>();
        currentInstanceName = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace(":", "-");
    }

    /**
     * Add a log to the FileLogger
     * @param log the log
     */
    public void addLog(Loggable log) {
        logs.add(log);
        newLogsAdded = true;
    }

    public File getLogsDirectory() {
        return logsDirectory;
    }
    public String getCurrentInstanceName() {
        return currentInstanceName;
    }

    /**
     * Save the logs to the log file, "{@link #getLogsDirectory()}/{@link #getCurrentInstanceName()}.log.gz"
     * @return
     * @throws IOException
     */
    public boolean save() throws IOException{
        File logFile = new File(logsDirectory.getAbsolutePath()+"/"+currentInstanceName+".log.gz");
        if(!newLogsAdded) {
            return false;
        }

        /// make the fils
        logFile.getParentFile().mkdirs();
        logFile.createNewFile();

        GZIPOutputStream gzipOS = new GZIPOutputStream(new FileOutputStream(logFile));
        for(Loggable i : logs) {
            String msg = i+"\n";
            gzipOS.write(msg.getBytes(StandardCharsets.UTF_8));
        }
        newLogsAdded = false;
        gzipOS.close();
        return true;
    }
}
