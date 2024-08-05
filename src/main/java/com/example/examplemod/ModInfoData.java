package com.example.examplemod;

public class ModInfoData {

    public String modid = "removed";
    public String version = "removed";

    public ModInfoData(String modid, String version) {
        this.modid = modid;
        this.version = version;
    }

    public boolean isSame(ModInfoData other) {
        return modid.equals(other.modid) && version.equals(other.version);
    }
}
