package com.jake.bookmenus.commands;

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

public class PrintBook implements CommandExecutor {

    public PrintBook() {
    }

    public static Text getDescription() {
        return Text.of("/printbook");
    }

    public static String[] getAlias() { return new String[]{"printbook"}; }

    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(Text.of("You must be a player to run this command!"));
        }
        Player player = (Player) src;
        if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
            ItemStack item = player.getItemInHand(HandTypes.MAIN_HAND).get();
            if(item.get(Keys.BOOK_PAGES).isPresent()) {
                for(Text page : item.get(Keys.BOOK_PAGES).get())
                    player.sendMessage(page);
            } else {
                throw new CommandException(Text.of("You do not have a book in your hand!"));
            }
        } else {
            throw new CommandException(Text.of("You do not have an item in your hand!"));
        }

        return CommandResult.success();
    }
}
