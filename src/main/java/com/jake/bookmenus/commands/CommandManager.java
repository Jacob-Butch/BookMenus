package com.jake.bookmenus.commands;

import com.jake.bookmenus.BookMenus;
import com.jake.bookmenus.commands.elements.BookCommandElement;
import com.jake.bookmenus.commands.executors.*;
import com.jake.bookmenus.message.Messages;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandManager {
    private BookMenus bm;

    public CommandManager(BookMenus bm) {
        this.bm = bm;
        this.registerCommands();
    }

    private void registerCommands() {
        CommandSpec open = CommandSpec.builder()
                .description(Text.of(Messages.open))
                .arguments(GenericArguments.onlyOne(new BookCommandElement(Text.of("bookName"))))
                .permission("bookmenus.command.book.open")
                .executor(new OpenBook())
                .build();
        CommandSpec openHeld = CommandSpec.builder()
                .description(Text.of(Messages.openheld))
                .permission("bookmenus.command.book.openheld")
                .arguments(GenericArguments.none())
                .executor(new OpenHeldBook())
                .build();
        CommandSpec print = CommandSpec.builder()
                .description(Text.of(Messages.print))
                .permission("bookmenus.command.book.print")
                .arguments(GenericArguments.optional(new BookCommandElement(Text.of("bookName"))))
                .executor(new PrintBook())
                .build();
        CommandSpec save = CommandSpec.builder()
                .description(Text.of(Messages.save))
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("bookName"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("overwrite"))))
                .permission("bookmenus.command.book.save")
                .executor(new SaveBook())
                .build();
        CommandSpec copy = CommandSpec.builder()
                .description(Text.of(Messages.copy))
                .arguments(GenericArguments.onlyOne(new BookCommandElement(Text.of("bookName"))))
                .permission("bookmenus.command.book.copy")
                .executor(new BookCopy())
                .build();
        CommandSpec serialize = CommandSpec.builder()
                .description(Text.of(Messages.serialize))
                .permission("bookmenus.command.book.serialize")
                .arguments(GenericArguments.none())
                .executor(new SerializeBook())
                .build();
        CommandSpec send = CommandSpec.builder()
                .description(Text.of(Messages.open))
                .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.onlyOne(new BookCommandElement(Text.of("bookName"))))
                .permission("bookmenus.command.book.send")
                .executor(new SendBook())
                .build();

        CommandSpec main = CommandSpec.builder()
                .permission("bookmenus.command.book")
                .description(Book.getDescription())
                .child(open, "open")
                .child(openHeld, "openheld")
                .child(print, "print")
                .child(save, "save")
                .child(copy, "copy")
                .child(serialize, "serialize")
                .child(send, "send")
                .executor(new Book())
                .arguments(GenericArguments.none())
                .build();

        Sponge.getCommandManager().register(bm, main, Book.getAlias());
    }
}
