package dev.alnat.plugin_platform.plugin.exception;

/**
 * Created by @author AlNat on 15.01.2022.
 * Licensed by Apache License, Version 2.0
 */
public class PluginProcessingException extends RuntimeException {

    public PluginProcessingException(String message) {
        super(message);
    }

    public PluginProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginProcessingException(Throwable cause) {
        super(cause);
    }

}
