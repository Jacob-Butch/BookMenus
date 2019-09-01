package com.jake.bookmenus.data;

import com.jake.bookmenus.BookMenus;

import java.io.IOException;
import java.nio.file.Files;

public class BookDirectory {

    public BookDirectory() {
        makeBookDirectory();
    }

    // Creates the book directory if it doesnt exist already
    private void makeBookDirectory(){
        try {
            if (!Files.exists(BookMenus.getBookPath())) {
                Files.createDirectories(BookMenus.getBookPath());
            }
        } catch (final IOException exx) {
            BookMenus.logger.error("Error on creating Books directory!");
        }
    }
}
