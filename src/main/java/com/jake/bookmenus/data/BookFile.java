package com.jake.bookmenus.data;

import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.google.common.reflect.TypeToken.of;

@ConfigSerializable
public class BookFile {
    private static ConfigurationLoader<CommentedConfigurationNode> loader;
    private static CommentedConfigurationNode main;

    public static String book;
    public static List<String> bookPages;

    public BookFile(String bookname, List<String> pages) throws ObjectMappingException {
        this.loadBook(bookname, pages);
    }

    // Creates a file for a book with the given name and pages
    private void loadBook(String bookName, List<String> pages) throws ObjectMappingException {
        book = bookName;
        Path path = BookData.getBookFile(book);
        loader = BookData.getLoader(book);
        try {
            if(!Files.exists(path)) {
                Files.createFile(path);
            }

            main = loader.load(ConfigurationOptions.defaults().setShouldCopyDefaults(true));

            CommentedConfigurationNode data = main.getNode("book");
            data.setComment("Book data for " + book);
            data.getNode("bookName").getString(book);
            data.getNode("bookPages").getList(of(String.class), pages);
            loader.save(main);
        } catch (IOException | ObjectMappingException e) {
            e.printStackTrace();
        }

        this.loadData();
    }

    private void loadData() throws ObjectMappingException {
        book = main.getNode("book", "bookName").getString();
        bookPages = main.getNode("bookPages").getList(of(String.class));
    }
}

