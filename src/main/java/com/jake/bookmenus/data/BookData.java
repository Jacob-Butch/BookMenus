package com.jake.bookmenus.data;

import com.jake.bookmenus.BookMenus;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static com.google.common.reflect.TypeToken.of;

public class BookData {
    // Gets a book's loaded config node
    private static CommentedConfigurationNode getLoadedBookNode(String book) {
        CommentedConfigurationNode rootNode;
        try {
            rootNode = getLoader(book).load();
        } catch(IOException e) {
            BookMenus.logger.error("Error loading data file for " + book);
            return null;
        }
        return rootNode;
    }

    // Gets a book file's loader
    static ConfigurationLoader<CommentedConfigurationNode> getLoader(String book) {
        return (HoconConfigurationLoader.builder().setPath(getBookFile(book)).build());
    }

    // Gets a book's file
    public static Path getBookFile(String book) {
        return (Paths.get(BookMenus.getBookPath() + File.separator + book + ".json"));
    }

    // Gets a list of pages from a book file
    public static List<String> getBookPages(String book) throws ObjectMappingException {
        return Objects.requireNonNull(getLoadedBookNode(book)).getNode("book", "bookPages").getList(of(String.class));
    }

    // Writes a list of pages to a book file
    public static void setBookPages(String book, List<String> pages) {
        CommentedConfigurationNode rootNode = getLoadedBookNode(book);
        if(rootNode != null) {
            rootNode.getNode("book", "bookPages").setValue(pages);
            try {
                getLoader(book).save(rootNode);
            } catch (IOException e) {
                BookMenus.logger.error("Error saving data file for " + book);
            }
        } else {
            BookMenus.logger.error("Error setting pages for " + book + "'s data file.");
        }
    }
}

