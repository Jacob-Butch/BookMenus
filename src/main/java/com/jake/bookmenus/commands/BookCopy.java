package com.jake.bookmenus.commands;

import com.jake.bookmenus.data.BookData;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.annotation.Nonnull;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class BookCopy implements CommandExecutor {

    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(Text.of("You must be a player to run this command!"));
        }
        Player player = (Player) src;
        String book = args.<String>getOne("bookName").orElse("");
        if(book.equals("")) {
            throw new CommandException(Text.of("Invalid book!"));
        } else {
            if(!Files.exists(BookData.getBookFile(book)))
                throw new CommandException(Text.of("That book file does not exist!"));
            List<String> spages;
            try {
                spages = BookData.getBookPages(book);
            } catch (ObjectMappingException e) {
                e.printStackTrace();
                return CommandResult.empty();
            }
            List<Text> pages = new ArrayList<>();
            for(String page : spages) {
                pages.add(TextSerializers.FORMATTING_CODE.deserialize(page));
            }
            ItemStack bookItem = ItemStack.builder()
                    .itemType(ItemTypes.WRITTEN_BOOK)
                    .add(Keys.BOOK_AUTHOR, Text.of(player.getName()))
                    .add(Keys.BOOK_PAGES, pages)
                    .add(Keys.DISPLAY_NAME, Text.of(book))
                    .build();
            player.getInventory().offer(bookItem);
        }
        return CommandResult.success();
    }
}
