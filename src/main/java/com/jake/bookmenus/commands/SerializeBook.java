package com.jake.bookmenus.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SerializeBook implements CommandExecutor {

    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(Text.of("You must be a player to run this command!"));
        }
        Player player = (Player) src;
        if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
            ItemStack item = player.getItemInHand(HandTypes.MAIN_HAND).get();

            if(item.get(Keys.BOOK_PAGES).isPresent()) {
                if(item.getType().equals(ItemTypes.WRITTEN_BOOK)) {
                    List<Text> serialized = new ArrayList<>();
                    for (Text page : item.get(Keys.BOOK_PAGES).get())
                        serialized.add(TextSerializers.FORMATTING_CODE.deserialize(page.toPlain()));
                    player.getItemInHand(HandTypes.MAIN_HAND).get().offer(Keys.BOOK_PAGES, serialized);
                    player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6&oThe book in your hand has been colored and formatted."));
                } else {
                    throw new CommandException(Text.of("The book must be signed to correctly format it!"));
                }
            } else {
                throw new CommandException(Text.of("You do not have a book in your hand!"));
            }
        } else {
            throw new CommandException(Text.of("You do not have an item in your hand!"));
        }

        return CommandResult.success();
    }
}
