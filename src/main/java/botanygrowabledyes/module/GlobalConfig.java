package botanygrowabledyes.module;


import botanygrowabledyes.common.blocks.BlockFlower;
import net.minecraftforge.common.config.Configuration;

public final class GlobalConfig {
    public static boolean debug;
    public static int maxPlatformBlocks;

    public static void initGlobalConfig() {
        String category = "_global";

        ConfigHelper.needsRestart = ConfigHelper.allNeedRestart = true;

        debug = ConfigHelper.loadPropBool("Debug", category, "Enables debug features", false);
        maxPlatformBlocks = ConfigHelper.loadPropInt("Max Platform Blocks", category, "Max blocks a platform can have", 128);

        ConfigHelper.needsRestart = ConfigHelper.allNeedRestart = false;

        BlockFlower.growthChance = ConfigHelper.loadPropDouble("Growth Chance","Flower","Flower has a 1/X chance of growing where X is this value, the following modifiers divide this value", 15D);
        BlockFlower.fertileModifier = ConfigHelper.loadPropDouble("Fertile Modifier","Flower","Modifies Flower Growth Chance when planted on Fertile Farmland", 1.33);
        BlockFlower.lampModifier = ConfigHelper.loadPropDouble("Light Block Modifier","Flower","Modifies Flower Growth Chance when a Light Block is two blocks above the Flower",  1.5D);
        BlockFlower.neighborModifier = ConfigHelper.loadPropDouble("Neighbor Modifier","Flower","Modifies Flower Growth Chance for each other crop next to it ",  1.1D);


    }
    public static void changeConfig(String moduleName, String category, String key, String value, boolean saveToFile) {
        Configuration config = ModuleLoader.config;
        String fullCategory = moduleName;
        if(!category.equals("-"))
            fullCategory += "." + category;

        char type = key.charAt(0);
        key = key.substring(2);

        if(config.hasKey(fullCategory, key)) {
            boolean changed = false;

            try {
                switch(type) {
                    case 'B':
                        boolean b = Boolean.parseBoolean(value);
                        config.get(fullCategory, key, false).setValue(b);
                    case 'I':
                        int i = Integer.parseInt(value);
                        config.get(fullCategory, key, 0).setValue(i);
                    case 'D':
                        double d = Double.parseDouble(value);
                        config.get(fullCategory, key, 0.0).setValue(d);
                    case 'S':
                        config.get(fullCategory, key, "").setValue(value);
                }
            } catch(IllegalArgumentException ignored) {}

            if(config.hasChanged()) {
                ModuleLoader.forEachModule(Module::setupConfig);

                if(saveToFile)
                    config.save();
            }
        }
    }
}