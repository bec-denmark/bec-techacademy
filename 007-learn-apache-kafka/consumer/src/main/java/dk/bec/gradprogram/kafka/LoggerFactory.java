package dk.bec.gradprogram.kafka;

import org.slf4j.Logger;

public class LoggerFactory {
    public static Logger logRunning(Class aClass) {
        Logger logger = org.slf4j.LoggerFactory.getLogger(aClass);
        logger.info("Running");

        return logger;
    }
}
