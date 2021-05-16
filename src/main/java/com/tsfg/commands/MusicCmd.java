package com.tsfg.commands;

import com.tsfg.commands.music.PlayCmd;
import com.tsfg.commands.music.StopCmd;
import com.tsfg.commands.music.VolumeCmd;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Component
@Command(name = "music",subcommands = {PlayCmd.class, StopCmd.class, VolumeCmd.class}, mixinStandardHelpOptions = true)
public class MusicCmd implements Callable<Integer> {
    @Parameters(hidden = true)
    private List<String> allParameters;

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
