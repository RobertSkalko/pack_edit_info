package com.example.examplemod;

import com.google.gson.Gson;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import static net.minecraft.commands.Commands.literal;

public class CommandRegister {

    public static void Register(CommandDispatcher<CommandSourceStack> dispatcher) {
        save(dispatcher);
        test(dispatcher);
    }

    public static void save(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                literal(ExampleMod.MODID).requires(e -> e.hasPermission(2))
                        .then(literal("save_current_pack_info")
                                .executes(ctx -> {
                                    var player = ctx.getSource().getPlayerOrException();

                                    var data = UTIL.getCurrentPackInfo();

                                    String copy = new Gson().toJson(data);

                                    player.displayClientMessage(Component.literal("Here's the current modpack's modlist info" +
                                                    "Clicking this message will copy it as a json which you need to distribute with the modpack as a datapack." +
                                                    "The datapack folder structure is: data/" + ExampleMod.MODID + "/" + "pack_info/info.json")
                                            .setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, copy))), false);
                                    return 1;
                                    //  return run(ctx.getSource().getPlayerOrException());
                                })));
    }

    public static void test(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                literal(ExampleMod.MODID).requires(e -> e.hasPermission(2))
                        .then(literal("test_print")
                                .executes(ctx -> {
                                    var player = ctx.getSource().getPlayerOrException();

                                    var data = ExampleMod.ORIGINAL_DATA;

                                    String copy = new Gson().toJson(data);

                                    player.displayClientMessage(Component.literal("Click here to copy the json currently loaded. If you created a valid datapack and loaded it correctly" +
                                                    " the result should be a valid json with all the mods and versions")
                                            .setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, copy))), false);
                                    return 1;
                                    //  return run(ctx.getSource().getPlayerOrException());
                                })));
    }
}
