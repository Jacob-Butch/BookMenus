package com.jake.bookmenus.util;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.List;

public class Serializer {

    public static Text format(Text text, CommandSource source, CommandSource observer){
        return TextSerializers.FORMATTING_CODE.deserialize(
                TextSerializers.FORMATTING_CODE.serialize(
                        Papi.parse(text, source, observer)));
    }

    public static Text format(String text, CommandSource source, CommandSource observer){
        return TextSerializers.FORMATTING_CODE.deserialize(
                TextSerializers.FORMATTING_CODE.serialize(
                        Papi.parse(text, source, observer)));
    }

    public static List<String> deserializeList(List<Text> texts){
        List<String> strings = new ArrayList<>();
        for(Text text : texts){
            strings.add(TextSerializers.FORMATTING_CODE.serialize(text));
        }
        return strings;
    }

    public static List<Text> serializeList(List<String> strings){
        List<Text> texts = new ArrayList<>();
        for(String string : strings){
            texts.add(TextSerializers.FORMATTING_CODE.deserialize(string));
        }
        return texts;
    }

    public static List<Text> formatPages(List<Text> pages, CommandSource source, CommandSource observer){
        return serializeList(deserializeList(Papi.parseList(pages, source, observer)));
    }

    public static List<Text> formatSPages(List<String> spages, CommandSource source, CommandSource observer){
        List<Text> pages = new ArrayList<>();
        for(String spage : spages){
            pages.add(Text.of(spage));
        }
        return serializeList(deserializeList(Papi.parseList(pages, source, observer)));
    }
}
