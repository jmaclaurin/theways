package com.theways.common;

import com.theways.TheWays;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TheWaysCreativeTab extends CreativeTabs {
    public static final TheWaysCreativeTab INSTANCE = new TheWaysCreativeTab();

    public TheWaysCreativeTab() {
        super(TheWays.MODID);
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(Items.ENDER_EYE);
    }

    @Override
    public ItemStack getTabIconItem() {
        return getIconItemStack();
    }
}
