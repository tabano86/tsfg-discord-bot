package com.tsfg.commands;

import org.springframework.stereotype.Component;

import static picocli.CommandLine.Command;

@Component
@Command(subcommands = {MessageCmd.class, JoinCmd.class, LeaveCmd.class, MusicCmd.class, RepeatCmd.class, StockCmd.class}, mixinStandardHelpOptions = true, version = "discord tsfg bot v0.1")
public class ApplicationCmd {
}
