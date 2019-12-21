package com.jake.bookmenus.util;

import me.rojo8399.placeholderapi.PlaceholderService;
import me.rojo8399.placeholderapi.impl.PlaceholderAPIPlugin;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.List;

public class Papi {

    public static List<Text> parseList(List<Text> pages, CommandSource source, CommandSource observer){
        List<Text> newPages = new ArrayList<>();
        for(Text page : pages){
            newPages.add(parse(page, source, observer));
        }
        return newPages;
    }

    public static Text parse(String message, CommandSource source, CommandSource observer){
        if(papiEnabled() && message.contains("%")) {
            return getPapi().replacePlaceholders(message, source, observer);
        }
        return TextSerializers.FORMATTING_CODE.deserialize(message);
    }

    public static Text parse(Text message, CommandSource source, CommandSource observer){
        if(papiEnabled()) {
            return getPapi().replacePlaceholders(message, source, observer);
        }
        return TextSerializers.FORMATTING_CODE.deserialize(TextSerializers.FORMATTING_CODE.serialize(message));
    }

    public static boolean papiEnabled(){
        return Sponge.getPluginManager().isLoaded("placeholderapi");
    }

    private static PlaceholderService getPapi(){
        return PlaceholderAPIPlugin.getInstance().getGame().getServiceManager().provide(PlaceholderService.class).orElse(null);
    }
}
