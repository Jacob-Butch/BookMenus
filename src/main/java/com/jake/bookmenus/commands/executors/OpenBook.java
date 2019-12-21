package com.jake.bookmenus.commands.executors;

import com.jake.bookmenus.data.BookData;
import com.jake.bookmenus.util.Papi;
import com.jake.bookmenus.util.Serializer;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import java.nio.file.Files;
import java.util.List;

public class OpenBook implements CommandExecutor {

    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(Text.of("You must be a player to run this command!"));
        }
        Player player = (Player) src;
        String book = args.<String>getOne("bookName").orElse("");
        if(!book.equals("")) {
            if(!Files.exists(BookData.getBookFile(book)))
                throw new CommandException(Text.of("That book file does not exist!"));
            List<Text> pages;
            try {
               pages = Serializer.formatSPages(BookData.getBookPages(book), src, player);
            } catch (ObjectMappingException e) {
                e.printStackTrace();
                return CommandResult.empty();
            }

            BookView bookView = BookView.builder()
                    .title(Serializer.format(book, src, player))
                    .addPages(pages)
                    .build();
            player.sendBookView(bookView);
        }

        return CommandResult.success();
    }
}
