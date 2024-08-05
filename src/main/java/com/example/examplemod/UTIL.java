package com.example.examplemod;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

public class UTIL {

    public static PackInfoData getCurrentPackInfo() {
        PackInfoData data = new PackInfoData();

        for (IModInfo mod : ModList.get().getMods()) {
            String version = mod.getVersion().getMajorVersion() + "." + mod.getVersion().getMinorVersion() + "." + mod.getVersion().getIncrementalVersion();
            ModInfoData modData = new ModInfoData(mod.getModId(), version);
            data.list.put(mod.getModId(), modData);
        }

        return data;
    }
    
}
