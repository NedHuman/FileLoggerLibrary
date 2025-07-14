package dev.nedhuman.fileloggerlibrary;

/**
 * An object that implements loggable is an object that can be logger
 */
public interface Loggable {

    /**
     * Get the log string, which will be saved to the .log.gz file
     */
    String log();
}
