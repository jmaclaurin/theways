package com.theways.common.block;

import com.google.common.base.Predicates;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWaygateAvendesoraReceptacle extends BlockBase {
    private static BlockPattern[] portalShapesNS = new BlockPattern[2];
    private static BlockPattern[] portalShapesEW = new BlockPattern[2];
    public static final PropertyBool AVENDESORA = PropertyBool.create("avendesora");
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockWaygateAvendesoraReceptacle() {
        super("waygate_avendesora_receptacle");
        this.setDefaultState(this.blockState.getBaseState().withProperty(AVENDESORA, Boolean.valueOf(false)
            ).withProperty(FACING, EnumFacing.NORTH));
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX,
                                            float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()
            ).withProperty(AVENDESORA, Boolean.valueOf(false));
    }

    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {AVENDESORA, FACING});
    }

    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
        if (((Boolean)state.getValue(AVENDESORA)).booleanValue()) {
            i |= 4;
        }
        return i;
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AVENDESORA, Boolean.valueOf((meta & 4) != 0)
        ).withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
    }

    private static BlockPattern generatePortalShapeFromDirection(EnumFacing direction) {
        FactoryBlockPattern blockPattern;
        if(direction == EnumFacing.NORTH || direction == EnumFacing.WEST) {
            blockPattern = FactoryBlockPattern.start().aisle(
                    "?vvv?",
                    "v???v",
                    "r???v",
                    "v???v",
                    "?vvv?");
        } else {
            blockPattern = FactoryBlockPattern.start().aisle(
                    "?vvv?",
                    "v???v",
                    "v???r",
                    "v???v",
                    "?vvv?"
            );
        }
        return blockPattern.where('?', BlockWorldState.hasState(BlockStateMatcher.ANY)
        ).where('r', BlockWorldState.hasState(BlockStateMatcher.forBlock(
                ModBlocks.WAYGATE_AVENDESORA_RECEPTACLE).where(AVENDESORA, Predicates.equalTo(true)
                ).where(FACING, Predicates.equalTo(direction)))
        ).where('v', BlockWorldState.hasState(BlockStateMatcher.forBlock(
                ModBlocks.WAYGATE_PORTAL_STONE))
        ).build();
    }

    public static BlockPattern[] getOrCreatePortalShapesNS() {
        portalShapesNS[0] = generatePortalShapeFromDirection(EnumFacing.NORTH);
        portalShapesNS[1] = generatePortalShapeFromDirection(EnumFacing.SOUTH);
        return portalShapesNS;
    }

    public static BlockPattern[] getOrCreatePortalShapesEW() {
        portalShapesEW[0] = generatePortalShapeFromDirection(EnumFacing.EAST);
        portalShapesEW[1] = generatePortalShapeFromDirection(EnumFacing.WEST);
        return portalShapesEW;
    }
}
