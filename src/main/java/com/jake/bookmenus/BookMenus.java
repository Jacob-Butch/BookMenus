package com.jake.bookmenus;

import com.jake.bookmenus.commands.CommandManager;
import com.jake.bookmenus.data.BookDirectory;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Plugin(id = BookMenus.ID, name = BookMenus.NAME, version = BookMenus.VERSION,
        authors = "jakearoo", url = "https://github.com/Jacob-Butch/BookMenus")
public class BookMenus {
    static final String ID = "bookmenus";
    static final String NAME = "BookMenus";
    static final String VERSION = "1.1.1";

    private static BookMenus instance;
    public static Logger logger;

    private static Path configdirpath;
    private static Path bookPath;

    @Inject
    public BookMenus(@ConfigDir(sharedRoot = true) Path path) {
        configdirpath = path.resolve(ID);
        bookPath = Paths.get(getConfigPath().toString() + File.separator + "books");
        logger = LoggerFactory.getLogger("BookMenus");
    }

    @Listener
    public void onGamePreInit(@Nullable final GamePreInitializationEvent event) {
        logger.info("BookMenus Initializing...");

        if(!Files.exists(getConfigPath())) {
            try {
                Files.createDirectory(getConfigPath());
            } catch (IOException e) {
                logger.error("Error on creating root directory", e);
            }
        }

        if(!Files.exists(bookPath)) {
            new BookDirectory();
        }

        instance = this;
    }

    @Listener
    public void onGameInit(@Nullable final GameInitializationEvent event) {
        logger.info("BookMenus Initialized!");
    }

    @Listener
    public void onServerStarting(GameStartingServerEvent event) {
        logger.info("Registering BookMenus commands...");
        new CommandManager(instance);
        logger.info("BookMenus commands registered!");
    }

    @Nonnull
    private static Path getConfigPath() { return configdirpath; }

    @Nonnull
    public static Path getBookPath() { return bookPath; }
}
