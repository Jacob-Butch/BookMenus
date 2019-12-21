package com.jake.bookmenus.commands.executors;

import com.jake.bookmenus.util.Papi;
import com.jake.bookmenus.util.Serializer;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class OpenHeldBook implements CommandExecutor {

    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(Text.of("You must be a player to run this command!"));
        }
        Player player = (Player) src;
        if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
            ItemStack item = player.getItemInHand(HandTypes.MAIN_HAND).get();
            if(item.get(Keys.BOOK_PAGES).isPresent()) {
                List<Text> pages = Serializer.formatPages(item.get(Keys.BOOK_PAGES).get(), src, player);

                BookView bookView = BookView.builder()
                        .title(item.get(Keys.DISPLAY_NAME).orElse(Text.of(item.getType().getName())))
                        .author(Text.of("Jake"))
                        .addPages(pages)
                        .build();

                player.sendBookView(bookView);
            } else {
                throw new CommandException(Text.of("You do not have a book in your hand!"));
            }
        } else {
            throw new CommandException(Text.of("You do not have an item in your hand!"));
        }

        return CommandResult.success();
    }
}
