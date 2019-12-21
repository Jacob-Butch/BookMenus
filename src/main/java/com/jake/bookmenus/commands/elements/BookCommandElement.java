package com.jake.bookmenus.commands.elements;

import com.google.common.base.Functions;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return getListOfStringsMatchingLastWord(args, books);
    }

    @Nonnull
    @Override
    public Text getUsage(CommandSource src) {
        return Text.of("<bookName>");
    }

    public static List<String> getListOfStringsMatchingLastWord(CommandArgs args, Collection<String> possibleCompletions) {

        Optional<String> optS = args.nextIfPresent();
        if(optS.isPresent()) {
            String s = optS.get();
            List<String> list = new ArrayList<>();

            if (!possibleCompletions.isEmpty()) {
                for (String s1 : possibleCompletions.stream().map(Functions.toStringFunction()).collect(Collectors.toList())) {
                    if (doesStringStartWith(s, s1)) {
                        list.add(s1);
                    }
                }
            }

            return list;
        }

        return new ArrayList<>(possibleCompletions);
    }

    private static boolean doesStringStartWith(String original, String region) {
        return region.regionMatches(true, 0, original, 0, original.length());
    }
}
