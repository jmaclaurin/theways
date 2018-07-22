package com.theways.common.item;

import com.theways.common.TheWaysCreativeTab;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ItemBase extends Item {
    public ItemBase(String unlocalizedName, int maxStackSize, int maxDamage) {
        this.setUnlocalizedName(unlocalizedName);
        this.setMaxStackSize(maxStackSize);
        this.setMaxDamage(maxDamage);
        this.setRegistryName(new ResourceLocation(com.theways.TheWays.MODID, unlocalizedName));
        this.setCreativeTab(TheWaysCreativeTab.INSTANCE);
    }

    public ItemBase(String unlocalizedName, int maxStackSize) {
        this(unlocalizedName, maxStackSize, 0);
    }

    public ItemBase(String unlocalizedName) {
        this(unlocalizedName, 64);
    }

    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}
