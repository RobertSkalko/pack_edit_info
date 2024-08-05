Use command /pack_edit_info save_current_pack_info   (requires OP perms)

The command must be used when you load your modpack right before you're about to publish an update for it.

paste the result into data/pack_edit_info/info.json

Zip the folder containing pack.mcmeta and data folders.

By having the mod in your modpack, every time your players update, remove or add new mods to the modpack, these changes will be logged by comparing them to this datapack you saved.
These changes will show in the log file and should be helpful in debugging any issues.