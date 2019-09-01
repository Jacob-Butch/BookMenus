package com.jake.bookmenus.commands;

import com.jake.bookmenus.data.BookData;
import com.jake.bookmenus.data.BookFile;
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
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.annotation.Nonnull;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SaveBook implements CommandExecutor {

    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(Text.of("You must be a player to run this command!"));
        }
        Player player = (Player) src;
        List<String> pages = new ArrayList<>();
        String bookName = args.<String>getOne("bookName").orElse("");
        String overwrite = args.<String>getOne("overwrite").orElse("");
        if(bookName.equals("")){
            throw new CommandException(Text.of("Invalid book name!"));
        }
        if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
            ItemStack item = player.getItemInHand(HandTypes.MAIN_HAND).get();
            if(item.get(Keys.BOOK_PAGES).isPresent()) {
                for(Text page : item.get(Keys.BOOK_PAGES).get())
                    pages.add(TextSerializers.FORMATTING_CODE.serialize(page));
            } else {
                throw new CommandException(Text.of("You do not have a book in your hand!"));
            }
        } else {
            throw new CommandException(Text.of("You do not have an item in your hand!"));
        }
        if(!pages.isEmpty()) {
            if(!Files.exists(BookData.getBookFile(bookName))) {
                try {
                    new BookFile(bookName, pages);
                    player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&3A book with the name &b" + bookName + " &3has been created!"));
                } catch (ObjectMappingException e) {
                    e.printStackTrace();
                }
            } else {
                if(overwrite.equals("-o")){
                    BookData.setBookPages(bookName, pages);
                    player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&b" + bookName + " &3has been successfully overwritten!"));
                } else {
                    throw new CommandException(Text.of("There is already a book with that name! Run the command again with '-o' to overwrite it."));
                }
            }
        } else {
            throw new CommandException(Text.of("That book is empty!"));
        }
        return CommandResult.success();
    }
}