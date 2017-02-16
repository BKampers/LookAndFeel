/*
** Copyright © Bart Kampers
*/

package bka.swing;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import org.json.*;


class LoggingUtil {


    static void setup(JSONObject loggerConfiguration) throws JSONException, IOException {
        loggers = new HashSet<>();
        Iterator keys = loggerConfiguration.keys();
        while (keys.hasNext()) {
            String loggerName = (String) keys.next();
            Logger logger = Logger.getLogger(loggerName);
            JSONObject configuration = loggerConfiguration.getJSONObject(loggerName);
            String level = configuration.optString(LEVEL_KEY, null);
            String fileName = configuration.optString(FILE_KEY, null);
            if (fileName == null) {
                fileName = loggerName + LOG_EXTENSION;
            }
            logger.setLevel((level != null) ? Level.parse(level) : Level.INFO);
            FileHandler fileHandler = new FileHandler(fileName);
            fileHandler.setFormatter(new DefaultFormatter());
            logger.addHandler(fileHandler);
            loggers.add(logger);
        }
    }


    static Collection<Logger> getLoggers() {
        return (loggers != null) ? new HashSet<>(loggers) : null;
    }

    
    static class DefaultFormatter extends java.util.logging.Formatter {

        @Override
        public synchronized String format(LogRecord record) {
            StringBuilder builder = new StringBuilder();
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            builder.append(dateFormat.format(new java.util.Date(record.getMillis())));
            builder.append(" [");
            builder.append(record.getLevel());
            builder.append("] ");
            builder.append(formatMessage(record));
            if (record.getThrown() != null) {
                builder.append(" thrown ");
                builder.append(record.getThrown().getMessage());
            }
            builder.append('\n');
            return builder.toString();
        }

    }

    
    private static Collection<Logger> loggers;

    private static final String LEVEL_KEY = "Level";
    private static final String FILE_KEY = "File";

    private static final String LOG_EXTENSION = ".log";

}
