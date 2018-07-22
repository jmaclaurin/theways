package com.theways.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityWaygate extends TileEntity {
    public TileEntityWaygate() {

    }

    @SideOnly(Side.CLIENT)
    public boolean shouldRenderFace(EnumFacing facing) {
        return facing != EnumFacing.UP && facing != EnumFacing.DOWN;
    }
}
