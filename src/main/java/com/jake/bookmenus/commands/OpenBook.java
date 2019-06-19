package com.jake.bookmenus.commands;

import com.jake.bookmenus.data.BookDataUtil;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.annotation.Nonnull;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class OpenBook implements CommandExecutor {

    public OpenBook() {
    }

    public static Text getDescription() {
        return Text.of("/openbook");
    }

    public static String[] getAlias() { return new String[]{"openbook"}; }

    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(Text.of("You must be a player to run this command!"));
        }
        Player player = (Player) src;
        String book = args.<String>getOne("bookName").orElse("");
        if(!book.equals("")) {
            if(!Files.exists(BookDataUtil.getBookFile(book)))
                throw new CommandException(Text.of("That book file does not exist!"));
            List<String> spages;
            try {
               spages = BookDataUtil.getBookPages(book);
            } catch (ObjectMappingException e) {
                e.printStackTrace();
                return CommandResult.empty();
            }
            List<Text> pages = new ArrayList<>();
            for(String page : spages) {
                pages.add(TextSerializers.FORMATTING_CODE.deserialize(page));
            }
            BookView bookView = BookView.builder()
                    .title(Text.of(book))
                    .addPages(pages)
                    .build();
            player.sendBookView(bookView);
        }

        return CommandResult.success();
    }
}
