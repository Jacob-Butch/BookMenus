package com.jake.bookmenus.commands.executors;

import com.jake.bookmenus.data.BookData;
import com.jake.bookmenus.util.Serializer;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import java.nio.file.Files;
import java.util.List;

public class PrintBook implements CommandExecutor {

    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(Text.of("You must be a player to run this command!"));
        }
        Player player = (Player) src;
        String book = args.<String>getOne("bookName").orElse("");
        if(book.equals("")) {
            if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
                ItemStack item = player.getItemInHand(HandTypes.MAIN_HAND).get();
                if (item.get(Keys.BOOK_PAGES).isPresent()) {
                    List<Text> pages = Serializer.formatPages(item.get(Keys.BOOK_PAGES).get(), src, player);
                    for (Text page : pages){
                        player.sendMessage(page);
                    }
                } else {
                    throw new CommandException(Text.of("You do not have a book in your hand!"));
                }
            } else {
                throw new CommandException(Text.of("You do not have an item in your hand!"));
            }
        }
        else {
            if(!Files.exists(BookData.getBookFile(book)))
                throw new CommandException(Text.of("That book file does not exist!"));
            List<Text> pages;
            try {
                pages = Serializer.formatSPages(BookData.getBookPages(book), src, player);
            } catch (ObjectMappingException e) {
                e.printStackTrace();
                return CommandResult.empty();
            }
            for(Text page : pages) {
                src.sendMessage(page);
            }
        }

        return CommandResult.success();
    }
}
