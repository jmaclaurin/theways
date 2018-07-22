package com.theways.common.block;

import com.theways.TheWays;
import com.theways.common.tileentity.TileEntityWaygate;
import com.theways.init.ModWorldGen;
import com.theways.worldgen.TeleporterTheWays;
import jline.internal.Nullable;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockWaygate extends BlockContainer {
    protected BlockWaygate(Material materialIn) {
        super(materialIn);
        this.setLightLevel(1.0F);
        this.setUnlocalizedName("waygate");
        this.setRegistryName(new ResourceLocation(TheWays.MODID, this.getUnlocalizedName()));
    }

    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityWaygate();
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return side != EnumFacing.DOWN && side != EnumFacing.UP ?
                super.shouldSideBeRendered(blockState, blockAccess, pos, side) : false;
    }

    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
                                      List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if(!entityIn.isRiding() &&
                !entityIn.isBeingRidden() &&
                entityIn instanceof EntityPlayerMP) {
            EntityPlayerMP thePlayer = (EntityPlayerMP)entityIn;
            if(entityIn.dimension != ModWorldGen.THE_WAYS_DIMENSION_ID) {
                thePlayer.mcServer.getPlayerList().transferPlayerToDimension(thePlayer, ModWorldGen.THE_WAYS_DIMENSION_ID,
                        new TeleporterTheWays(thePlayer.getServer().getWorld(ModWorldGen.THE_WAYS_DIMENSION_ID)));
            }
            else {
                thePlayer.mcServer.getPlayerList().transferPlayerToDimension(thePlayer, 0 ,
                        new TeleporterTheWays(thePlayer.getServer().getWorld(0)));
            }
        }
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return ItemStack.EMPTY;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
