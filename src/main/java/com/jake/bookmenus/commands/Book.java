package com.jake.bookmenus.commands;

import com.jake.bookmenus.message.Messages;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Book implements CommandExecutor {

    public static Text getDescription() {
        return Text.of("Main command for the book menus plugin");
    }

    public static String[] getAlias() { return new String[]{"book"}; }

    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(Text.of("You must be a player to run this command!"));
        }
        List<Text> commands = new ArrayList<>();

        commands.add(TextSerializers.FORMATTING_CODE.deserialize("&f/&6book open <book> &7- &a" + Messages.open)
                .toBuilder()
                .onClick(TextActions.suggestCommand("/book open"))
                .onHover(TextActions.showText(TextSerializers.FORMATTING_CODE.deserialize("&7&o" + Messages.click)))
                .build());
        commands.add(TextSerializers.FORMATTING_CODE.deserialize("&f/&6book save <book> [-o] &7- &a" + Messages.save)
                .toBuilder()
                .onClick(TextActions.suggestCommand("/book save"))
                .onHover(TextActions.showText(TextSerializers.FORMATTING_CODE.deserialize("&7&o" + Messages.click)))
                .build());
        commands.add(TextSerializers.FORMATTING_CODE.deserialize("&f/&6book copy <book> &7- &a" + Messages.copy)
                .toBuilder()
                .onClick(TextActions.suggestCommand("/book copy"))
                .onHover(TextActions.showText(TextSerializers.FORMATTING_CODE.deserialize("&7&o" + Messages.click)))
                .build());
        commands.add(TextSerializers.FORMATTING_CODE.deserialize("&f/&6book send <player> <book> &7- &a" + Messages.send)
                .toBuilder()
                .onClick(TextActions.suggestCommand("/book send"))
                .onHover(TextActions.showText(TextSerializers.FORMATTING_CODE.deserialize("&7&o" + Messages.click)))
                .build());
        commands.add(TextSerializers.FORMATTING_CODE.deserialize("&f/&6book print [book] &7- &a" + Messages.print)
                .toBuilder()
                .onClick(TextActions.suggestCommand("/book print"))
                .onHover(TextActions.showText(TextSerializers.FORMATTING_CODE.deserialize("&7&o" + Messages.click)))
                .build());
        commands.add(TextSerializers.FORMATTING_CODE.deserialize("&f/&6book openheld &7- &a" + Messages.openheld)
                .toBuilder()
                .onClick(TextActions.suggestCommand("/book openheld"))
                .onHover(TextActions.showText(TextSerializers.FORMATTING_CODE.deserialize("&7&o" + Messages.click)))
                .build());
        commands.add(TextSerializers.FORMATTING_CODE.deserialize("&f/&6book serialize &7- &a" + Messages.serialize)
                .toBuilder()
                .onClick(TextActions.suggestCommand("/book serialize"))
                .onHover(TextActions.showText(TextSerializers.FORMATTING_CODE.deserialize("&7&o" + Messages.click)))
                .build());

        PaginationList.builder()
                .title(Text.of("Book Menus"))
                .contents(commands)
                .padding(Text.of(TextColors.BLUE, "-"))
                .build()
                .sendTo(src);

        return CommandResult.success();
    }
}
