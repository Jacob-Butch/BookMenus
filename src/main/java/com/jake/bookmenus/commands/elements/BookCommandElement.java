package com.jake.bookmenus.commands.elements;

import com.jake.bookmenus.BookMenus;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BookCommandElement extends CommandElement {

    public BookCommandElement(Text key) {
        super(key);
    }

    @Nonnull
    @Override
    protected Object parseValue(@Nonnull CommandSource source, @Nonnull CommandArgs args) throws ArgumentParseException {
        return args.next();
    }
    @Nonnull
    @Override
    public List<String> complete(@Nonnull CommandSource src, @Nonnull CommandArgs args, @Nonnull CommandContext context) {
        List<String> books = new ArrayList<>();
        File[] files = BookMenus.getBookPath().toFile().listFiles();
        assert files != null;
        for(File file : files){
            if(file.isFile()){
                books.add(file.getName().replace(".json", ""));
            }
        }
        return books;
    }

    @Nonnull
    @Override
    public Text getUsage(CommandSource src) {
        return Text.of("<bookName>");
    }
}
