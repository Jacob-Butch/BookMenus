package com.jake.bookmenus.commands.manager;

import com.jake.bookmenus.BookMenus;
import com.jake.bookmenus.commands.*;
import com.jake.bookmenus.commands.elements.BookCommandElement;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import static com.jake.bookmenus.commands.permissions.EnumPerms.*;

public class CommandManager {
    private BookMenus bm;

    public CommandManager(BookMenus bm) {
        this.bm = bm;
        this.registerCommands();
    }

    private void registerCommands() {
        CommandSpec openBookCmd = CommandSpec.builder()
                .description(OpenBook.getDescription())
                .arguments(GenericArguments.onlyOne(new BookCommandElement(Text.of("bookName"))))
                .permission(OPENBOOK)
                .executor(new OpenBook())
                .build();
        CommandSpec openHeldBookCmd = CommandSpec.builder()
                .description(OpenHeldBook.getDescription())
                .permission(OPENHELDBOOK)
                .executor(new OpenHeldBook())
                .build();
        CommandSpec printBookCmd = CommandSpec.builder()
                .description(PrintBook.getDescription())
                .permission(PRINTBOOK)
                .executor(new PrintBook())
                .build();
        CommandSpec saveBookCmd = CommandSpec.builder()
                .description(SaveBook.getDescription())
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("bookName"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("overwrite"))))
                .permission(SAVEBOOK)
                .executor(new SaveBook())
                .build();
        CommandSpec bookCopyCmd = CommandSpec.builder()
                .description(BookCopy.getDescription())
                .arguments(GenericArguments.onlyOne(new BookCommandElement(Text.of("bookName"))))
                .permission(BOOKCOPY)
                .executor(new BookCopy())
                .build();
        CommandSpec colorBookCmd = CommandSpec.builder()
                .description(ColorBook.getDescription())
                .permission(COLORBOOK)
                .executor(new ColorBook())
                .build();
        CommandSpec sendBookCmd = CommandSpec.builder()
                .description(SendBook.getDescription())
                .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.onlyOne(new BookCommandElement(Text.of("bookName"))))
                .permission(SENDBOOK)
                .executor(new SendBook())
                .build();
        Sponge.getCommandManager().register(bm, openBookCmd, OpenBook.getAlias());
        Sponge.getCommandManager().register(bm, openHeldBookCmd, OpenHeldBook.getAlias());
        Sponge.getCommandManager().register(bm, printBookCmd, PrintBook.getAlias());
        Sponge.getCommandManager().register(bm, saveBookCmd, SaveBook.getAlias());
        Sponge.getCommandManager().register(bm, bookCopyCmd, BookCopy.getAlias());
        Sponge.getCommandManager().register(bm, colorBookCmd, ColorBook.getAlias());
        Sponge.getCommandManager().register(bm, sendBookCmd, SendBook.getAlias());
    }
}
