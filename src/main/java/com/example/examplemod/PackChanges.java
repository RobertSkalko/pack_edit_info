package com.example.examplemod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PackChanges {

    public PackChanges() {
        var original = ExampleMod.ORIGINAL_DATA;
        var current = UTIL.getCurrentPackInfo();

        if (original.list.isEmpty()) {
            System.out.println("The Pack Edit Info datapack is empty, stopping check");
            return;
        }
        for (Map.Entry<String, ModInfoData> en : original.list.entrySet()) {
            if (!current.list.containsKey(en.getKey())) {
                changes.add(new ModChange(ChangeType.MOD_REMOVED, en.getValue(), null));
            }
        }
        for (Map.Entry<String, ModInfoData> en : current.list.entrySet()) {
            if (!original.list.containsKey(en.getKey())) {
                changes.add(new ModChange(ChangeType.MOD_ADDED, null, en.getValue()));
            }
        }
        for (Map.Entry<String, ModInfoData> en : original.list.entrySet()) {
            if (current.list.containsKey(en.getKey())) {
                var cur = current.list.get(en.getKey());
                if (!cur.isSame(en.getValue())) {
                    changes.add(new ModChange(ChangeType.MOD_UPDATED, en.getValue(), cur));
                }
            }
        }
    }


    public void print() {

        if (ExampleMod.ORIGINAL_DATA.list.isEmpty()) {
            return;
        }

        if (changes.isEmpty()) {
            System.out.println("No mods were added, removed or updated from the Modpack.\n");
        } else {

            System.out.println("Modpack was changed, displaying mod changes:\n");
            for (ChangeType type : ChangeType.values()) {
                for (ModChange change : changes) {
                    if (change.change == type) {
                        System.out.println(change.getInfoMessage() + "\n");
                    }
                }
            }
        }


    }

    public List<ModChange> changes = new ArrayList<>();

    public class ModChange {

        public ChangeType change;
        public ModInfoData original;
        public ModInfoData current;

        public String getInfoMessage() {

            return change.getInfoMessage(this);
        }

        public ModChange(ChangeType change, ModInfoData original, ModInfoData current) {
            this.change = change;
            this.original = original;
            this.current = current;
        }
    }

    public enum ChangeType {
        MOD_UPDATED() {
            @Override
            public String getInfoMessage(ModChange data) {
                return "MOD UPDATED: " + data.original.modid + " - Original: " + data.original.version + " - Current: " + data.current.version;
            }
        }, MOD_REMOVED() {
            @Override
            public String getInfoMessage(ModChange data) {
                return "MOD REMOVED: " + data.original.modid;
            }
        }, MOD_ADDED {
            @Override
            public String getInfoMessage(ModChange data) {
                return "MOD ADDED: " + data.original.modid + " - Version: " + data.current.version;
            }
        };

        public abstract String getInfoMessage(ModChange data);
    }
}
