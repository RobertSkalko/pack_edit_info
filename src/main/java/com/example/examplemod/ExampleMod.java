package com.example.examplemod;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod.EventBusSubscriber(modid = ExampleMod.MODID)
@Mod(ExampleMod.MODID)
public class ExampleMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "pack_edit_info";

    public static PackInfoData ORIGINAL_DATA = new PackInfoData();


    @SubscribeEvent
    public static void addReloadListenerEvent(AddReloadListenerEvent event) {

        Gson gson = new Gson();
        // need both modid and a folder for this datapack to work
        event.addListener(new SimpleJsonResourceReloadListener(gson, "pack_info") {
            @Override
            protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager man, ProfilerFiller profile) {

                try {
                    for (Map.Entry<ResourceLocation, JsonElement> en : map.entrySet()) {
                        PackInfoData data = gson.fromJson(en.getValue().getAsJsonObject(), PackInfoData.class);

                        if (data != null && data.list != null && !data.list.isEmpty()) {
                            ORIGINAL_DATA = data;
                        }

                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public ExampleMod() {

        ForgeEvents.registerForgeEvent(RegisterCommandsEvent.class, event -> {
            CommandRegister.Register(event.getDispatcher());
        });

        ForgeEvents.registerForgeEvent(ServerStartedEvent.class, event -> {
            PackChanges changes = new PackChanges();
            changes.print();
        });

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        //   ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        //  if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        //    event.accept(EXAMPLE_BLOCK_ITEM);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        //LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            //LOGGER.info("HELLO FROM CLIENT SETUP");
            //LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
