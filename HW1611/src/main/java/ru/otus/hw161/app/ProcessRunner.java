package ru.otus.hw161.app;

import java.io.IOException;

/**
 * Created by tully.
 */
public interface ProcessRunner {
    void start(String command) throws IOException;

    void stop();

    String getOutput();
}
