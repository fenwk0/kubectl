package com.macymoo.com.cmd;

import java.io.IOException;
import java.util.concurrent.Executors;

public class processor {

    public static void main(String[] args) {

        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");
        int exitCode =1;
        String homeDirectory = System.getProperty("user.home");
        Process process;
        try {
            if (isWindows) {
                process = Runtime.getRuntime()
                        .exec(String.format("cmd.exe /c dir %s", homeDirectory));
            } else {
                process = Runtime.getRuntime()
                        .exec(String.format("sh -c ls %s", homeDirectory));
            }
            StreamGobbler streamGobbler =
                    new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            exitCode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            assert exitCode == 0;
        }
        assert exitCode == 0;
        System.out.println("exitCode = " + exitCode);
    }
}
