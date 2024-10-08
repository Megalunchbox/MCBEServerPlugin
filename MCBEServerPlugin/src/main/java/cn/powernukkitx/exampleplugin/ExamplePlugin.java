package cn.powernukkitx.exampleplugin;

import cn.nukkit.Server;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.lang.PluginI18n;
import cn.nukkit.lang.PluginI18nManager;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.registry.EntityRegistry;
import cn.nukkit.registry.RegisterException;
import cn.nukkit.registry.Registries;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import cn.powernukkitx.exampleplugin.customblock.MyBlock;
import cn.powernukkitx.exampleplugin.customblock.MySlab;
import cn.powernukkitx.exampleplugin.customench.MyEnchantment1;
import cn.powernukkitx.exampleplugin.customench.MyEnchantment2;
import cn.powernukkitx.exampleplugin.customentity.MyHuman;
import cn.powernukkitx.exampleplugin.customentity.MyPig;
import cn.powernukkitx.exampleplugin.customitem.MyArmor;
import cn.powernukkitx.exampleplugin.customitem.MyPickaxe;
import cn.powernukkitx.exampleplugin.customitem.MySword;

import java.io.File;
import java.util.LinkedHashMap;

public class ExamplePlugin extends PluginBase {
    public static ExamplePlugin INSTANCE;
    public static PluginI18n I18N;
    public static PDataManager pData;

    @Override
    public void onLoad() {
        //save Plugin Instance
        INSTANCE = this;
        //register the plugin i18n
        I18N = PluginI18nManager.register(this);
        //register the command of plugin
        this.getServer().getCommandMap().register("exampleplugin", new ExampleCommand());

        this.getLogger().info(TextFormat.WHITE + "The example plugin has been loaded!");

        INSTANCE = this;
        try {
            Registries.ITEM.registerCustomItem(this, MyArmor.class, MySword.class, MyPickaxe.class);
            Registries.BLOCK.registerCustomBlock(this, MyBlock.class, MySlab.class);
            Registries.ENTITY.registerCustomEntity(this, new EntityRegistry.CustomEntityDefinition("powernukkitx:boar", "", false, true), MyPig.class);
            Registries.ENTITY.registerCustomEntity(this, new EntityRegistry.CustomEntityDefinition("powernukkitx:human", "", false, true), MyHuman.class);
            Enchantment.register(new MyEnchantment1(), new MyEnchantment2());
        } catch (RegisterException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable() {
        this.getLogger().info(TextFormat.DARK_GREEN + "The example plugin has been successfully launched!");
        this.getLogger().info(String.valueOf(this.getDataFolder().mkdirs()));

        //Use the plugin's i18n output
        this.getLogger().info(I18N.tr(Server.getInstance().getLanguageCode(), "exampleplugin.helloworld", "世界"));

        //Register the EventListener
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);

        //PluginTask
        this.getServer().getScheduler().scheduleDelayedRepeatingTask(new BroadcastPluginTask(this), 500, 200);

        //Save resources
        this.saveResource("string.txt");

        //Config reading and writing
        Config config = new Config(
                new File(this.getDataFolder(), "config.yml"),
                Config.YAML,
                //Default values (not necessary)
                new ConfigSection(new LinkedHashMap<>() {
                    {
                        put("this-is-a-key", "Hello! Config!");
                        put("another-key", true); //you can also put other standard objects!
                    }
                }));

        //Now try to get the value, the default value will be given if the key isn't exist!
        this.getLogger().info(String.valueOf(config.get("this-is-a-key", "this-is-default-value")));

        //Don't forget to save it!
        config.save();

        pData = new PDataManager(this);
    }

    @Override
    public void onDisable() {
        this.getLogger().info(TextFormat.DARK_RED + "The example plugin has been disabled!");
    }
}