package com.theways.common.block;

import com.theways.TheWays;
import com.theways.common.TheWaysCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockBase extends Block {
    public BlockBase(String unlocalizedName, Material material, float hardness, float resistance) {
        super(material);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(TheWays.MODID, unlocalizedName));
        this.setCreativeTab(TheWaysCreativeTab.INSTANCE);
        this.setHardness(hardness);
        this.setResistance(resistance);
    }

    public BlockBase(String unlocalizedName, float hardness, float resistance) {
        this(unlocalizedName, Material.ROCK, hardness, resistance);
    }

    public BlockBase(String unlocalizedName) {
        this(unlocalizedName, 2.0F, 10.0F);
    }
}
