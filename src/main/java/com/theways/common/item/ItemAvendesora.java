package com.theways.common.item;

import com.theways.common.block.BlockWaygateAvendesoraReceptacle;
import com.theways.common.block.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemAvendesora extends ItemBase {
    public ItemAvendesora() {
        super("avendesora");
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        ItemStack itemstack = player.getHeldItem(hand);

        if(player.canPlayerEdit(pos.offset(facing), facing, itemstack) && iblockstate.getBlock() ==
                ModBlocks.WAYGATE_AVENDESORA_RECEPTACLE &&
                !((Boolean)iblockstate.getValue(BlockWaygateAvendesoraReceptacle.AVENDESORA)).booleanValue()) {
            if(worldIn.isRemote) {
                return EnumActionResult.SUCCESS;
            }
            else {
                worldIn.setBlockState(pos, iblockstate.withProperty(BlockWaygateAvendesoraReceptacle.AVENDESORA,
                        Boolean.valueOf(true)));
                itemstack.shrink(1);

                BlockPattern.PatternHelper patternHelper = null;
                BlockPos offset = null;
                EnumFacing receptacleFacing = null;

                BlockPattern[] patterns = BlockWaygateAvendesoraReceptacle.getOrCreatePortalShapesNS();
                if((patternHelper = patterns[0].match(worldIn, pos)) != null) {
                    offset = new BlockPos(0, -3, -3);
                    receptacleFacing = EnumFacing.NORTH;
                } else if((patternHelper = patterns[1].match(worldIn, pos)) != null){
                    offset = new BlockPos(0, -3, -3);
                    receptacleFacing = EnumFacing.SOUTH;
                }

                if(offset == null) {
                    patterns = BlockWaygateAvendesoraReceptacle.getOrCreatePortalShapesEW();
                    if((patternHelper = patterns[0].match(worldIn, pos)) != null) {
                        offset = new BlockPos(-3, -3, 0);
                        receptacleFacing = EnumFacing.EAST;
                    } else if((patternHelper = patterns[1].match(worldIn, pos)) != null) {
                        offset = new BlockPos(-3, -3, 0);
                        receptacleFacing = EnumFacing.WEST;
                    }
                }

                if(patternHelper != null && offset != null) {
                    BlockPos blockPos = patternHelper.getFrontTopLeft().add(offset);
                    for (int j = 0; j < 3; ++j) {
                        for (int k = 0; k < 3; ++k) {
                            if(receptacleFacing == EnumFacing.NORTH || receptacleFacing == EnumFacing.SOUTH) {
                                worldIn.setBlockState(blockPos.add(0, k, j), ModBlocks.WAYGATE.getDefaultState(), 2);
                            } else {
                                worldIn.setBlockState(blockPos.add(j, k, 0), ModBlocks.WAYGATE.getDefaultState(), 2);
                            }
                        }
                    }
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        else {
            return EnumActionResult.FAIL;
        }
        return EnumActionResult.SUCCESS;
    }
}
