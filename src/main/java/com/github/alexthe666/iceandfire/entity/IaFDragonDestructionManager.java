package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.BlockCharedPath;
import com.github.alexthe666.iceandfire.block.BlockFallingReturningState;
import com.github.alexthe666.iceandfire.block.BlockReturningState;
import com.github.alexthe666.iceandfire.block.IDragonProof;
import com.github.alexthe666.iceandfire.core.ModBlocks;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDragonforgeInput;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IaFDragonDestructionManager {

    public static void destroyAreaFire(World world, BlockPos center, EntityDragonBase destroyer){
        int stage = destroyer.getDragonStage();
        if(stage <= 3){
            for(BlockPos pos : BlockPos.getAllInBox(center.add(-1, -1, -1), center.add(1, 1, 1))){
                if(IceAndFire.CONFIG.dragonGriefing != 2 && world.rand.nextBoolean()){
                    IBlockState transformState = transformBlockFire(world.getBlockState(pos));
                    world.setBlockState(pos, transformState);
                    if(world.rand.nextBoolean() && transformState.isFullBlock() && world.isAirBlock(pos.up())){
                        world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
                    }
                }
                if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityDragonforgeInput){
                    ((TileEntityDragonforgeInput)world.getTileEntity(pos)).onHitWithFlame();
                }
            }
            for (EntityLiving entityliving : world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB((double) center.getX() - 1, (double) center.getY() - 1, (double) center.getZ() - 1, (double) center.getX() + 1, (double) center.getY() + 1, (double) center.getZ() + 1))) {
                if(!destroyer.isOnSameTeam(entityliving) && !destroyer.isEntityEqual(entityliving)){
                    entityliving.attackEntityFrom(IceAndFire.dragonFire, Math.max(1, stage - 1));
                    entityliving.setFire(5);
                }
            }
        } else {
            int radius = stage == 4 ? 2 : 3;
            int j = radius + world.rand.nextInt(1);
            int k = (radius + world.rand.nextInt(1));
            int l = radius + world.rand.nextInt(1);
            float f = (float) (j + k + l) * 0.333F + 0.5F;
            for (BlockPos blockpos : BlockPos.getAllInBox(center.add(-j, -k, -l), center.add(j, k, l))) {
                if (blockpos.distanceSq(center) <= (double) (f * f)) {
                    if(IceAndFire.CONFIG.dragonGriefing != 2 && world.rand.nextFloat() > (float)blockpos.distanceSq(center) / (f * f)){
                        IBlockState transformState = transformBlockFire(world.getBlockState(blockpos));
                        world.setBlockState(blockpos, transformState);
                        if(world.rand.nextBoolean() && transformState.isFullBlock() && world.isAirBlock(blockpos.up())){
                            world.setBlockState(blockpos.up(), Blocks.FIRE.getDefaultState());
                        }
                    }
                }
                if(world.getTileEntity(blockpos) != null && world.getTileEntity(blockpos) instanceof TileEntityDragonforgeInput){
                    ((TileEntityDragonforgeInput)world.getTileEntity(blockpos)).onHitWithFlame();
                }
            }
            for (EntityLiving entityliving : world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB((double) center.getX() - j, (double) center.getY() - k, (double) center.getZ() - l, (double) center.getX() + j, (double) center.getY() + k, (double) center.getZ() + l))) {
                if(!destroyer.isOnSameTeam(entityliving) && !destroyer.isEntityEqual(entityliving)){
                    entityliving.attackEntityFrom(IceAndFire.dragonFire, Math.max(1, stage - 1));
                    entityliving.setFire(5);
                }
            }
        }
    }

    public static void destroyAreaIce(World world, BlockPos center, EntityDragonBase destroyer){
        int stage = destroyer.getDragonStage();
        if(stage <= 3){
            for(BlockPos pos : BlockPos.getAllInBox(center.add(-1, -1, -1), center.add(1, 1, 1))){
                if(IceAndFire.CONFIG.dragonGriefing != 2 && world.rand.nextBoolean()){
                    IBlockState transformState = transformBlockIce(world.getBlockState(pos));
                    world.setBlockState(pos, transformState);
                    if(world.rand.nextInt(9) == 0 && transformState.isFullBlock() && world.isAirBlock(pos.up())){
                        world.setBlockState(pos.up(), ModBlocks.dragon_ice_spikes.getDefaultState());
                    }
                }
                if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityDragonforgeInput){
                    ((TileEntityDragonforgeInput)world.getTileEntity(pos)).onHitWithFlame();
                }
            }
            for (EntityLiving entityliving : world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB((double) center.getX() - 1, (double) center.getY() - 1, (double) center.getZ() - 1, (double) center.getX() + 1, (double) center.getY() + 1, (double) center.getZ() + 1))) {
                if(!destroyer.isOnSameTeam(entityliving) && !destroyer.isEntityEqual(entityliving)){
                    entityliving.attackEntityFrom(IceAndFire.dragonIce, Math.max(1, stage - 1));
                    FrozenEntityProperties frozenProps = EntityPropertiesHandler.INSTANCE.getProperties(entityliving, FrozenEntityProperties.class);
                    if(frozenProps != null) {
                        frozenProps.setFrozenFor(200);
                    }
                }
            }
        } else {
            int radius = stage == 4 ? 2 : 3;
            int j = radius + world.rand.nextInt(1);
            int k = (radius + world.rand.nextInt(1));
            int l = radius + world.rand.nextInt(1);
            float f = (float) (j + k + l) * 0.333F + 0.5F;
            for (BlockPos blockpos : BlockPos.getAllInBox(center.add(-j, -k, -l), center.add(j, k, l))) {
                if (blockpos.distanceSq(center) <= (double) (f * f)) {
                    if(IceAndFire.CONFIG.dragonGriefing != 2 && world.rand.nextFloat() > (float)blockpos.distanceSq(center) / (f * f)){
                        IBlockState transformState = transformBlockIce(world.getBlockState(blockpos));
                        world.setBlockState(blockpos, transformState);
                        if(world.rand.nextInt(9) == 0 && transformState.isFullBlock() && world.isAirBlock(blockpos.up())){
                            world.setBlockState(blockpos.up(), ModBlocks.dragon_ice_spikes.getDefaultState());
                        }
                    }
                }
                if(world.getTileEntity(blockpos) != null && world.getTileEntity(blockpos) instanceof TileEntityDragonforgeInput){
                    ((TileEntityDragonforgeInput)world.getTileEntity(blockpos)).onHitWithFlame();
                }
            }
            for (EntityLiving entityliving : world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB((double) center.getX() - j, (double) center.getY() - k, (double) center.getZ() - l, (double) center.getX() + j, (double) center.getY() + k, (double) center.getZ() + l))) {
                if(!destroyer.isOnSameTeam(entityliving) && !destroyer.isEntityEqual(entityliving)){
                    entityliving.attackEntityFrom(IceAndFire.dragonIce, Math.max(1, stage - 1));
                    FrozenEntityProperties frozenProps = EntityPropertiesHandler.INSTANCE.getProperties(entityliving, FrozenEntityProperties.class);
                    if(frozenProps != null) {
                        frozenProps.setFrozenFor(200);
                    }
                }
            }
        }
    }

    public static void destroyAreaFireCharge(World world, BlockPos center, EntityDragonBase destroyer){
        if(destroyer != null) {
            int stage = destroyer.getDragonStage();
            if (stage <= 3) {
                for (BlockPos pos : BlockPos.getAllInBox(center.add(-2, -2, -2), center.add(2, 2, 2))) {
                    if (world.rand.nextFloat() > pos.distanceSq(center) && !(world.getBlockState(pos).getBlock() instanceof IDragonProof)) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                }
                for (BlockPos pos : BlockPos.getAllInBox(center.add(-2, -2, -2), center.add(2, 2, 2))) {
                    if (world.rand.nextBoolean()) {
                        IBlockState transformState = transformBlockFire(world.getBlockState(pos));
                        world.setBlockState(pos, transformState);
                        if (world.rand.nextBoolean() && transformState.isFullBlock() && world.isAirBlock(pos.up())) {
                            world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
                        }
                    }
                }
                for (EntityLiving entityliving : world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB((double) center.getX() - 2, (double) center.getY() - 2, (double) center.getZ() - 2, (double) center.getX() + 2, (double) center.getY() + 2, (double) center.getZ() + 2))) {
                    if (!destroyer.isOnSameTeam(entityliving) && !destroyer.isEntityEqual(entityliving)) {
                        entityliving.attackEntityFrom(IceAndFire.dragonFire, Math.max(1, stage - 1) * 2F);
                        entityliving.setFire(15);
                    }
                }
            } else {
                int radius = stage == 4 ? 2 : 3;
                int j = radius + world.rand.nextInt(2);
                int k = (radius + world.rand.nextInt(2));
                int l = radius + world.rand.nextInt(2);
                float f = (float) (j + k + l) * 0.333F + 0.5F;
                for (BlockPos blockpos : BlockPos.getAllInBox(center.add(-j, -k, -l), center.add(j, k, l))) {
                    if (blockpos.distanceSq(center) <= (double) (f * f)) {
                        if (world.rand.nextFloat() > (float) blockpos.distanceSq(center) / (f * f) && !(world.getBlockState(blockpos).getBlock() instanceof IDragonProof)) {
                            world.setBlockState(blockpos, Blocks.AIR.getDefaultState());
                        }
                    }
                }
                j++;
                k++;
                l++;
                for (BlockPos blockpos : BlockPos.getAllInBox(center.add(-j, -k, -l), center.add(j, k, l))) {
                    if (blockpos.distanceSq(center) <= (double) (f * f)) {
                        IBlockState transformState = transformBlockFire(world.getBlockState(blockpos));
                        world.setBlockState(blockpos, transformState);
                        if (world.rand.nextBoolean() && transformState.isFullBlock() && world.isAirBlock(blockpos.up())) {
                            world.setBlockState(blockpos.up(), Blocks.FIRE.getDefaultState());
                        }
                    }
                }
                for (EntityLiving entityliving : world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB((double) center.getX() - j, (double) center.getY() - k, (double) center.getZ() - l, (double) center.getX() + j, (double) center.getY() + k, (double) center.getZ() + l))) {
                    if (!destroyer.isOnSameTeam(entityliving) && !destroyer.isEntityEqual(entityliving)) {
                        entityliving.attackEntityFrom(IceAndFire.dragonFire, Math.max(1, stage - 1) * 2F);
                        entityliving.setFire(15);
                    }
                }
            }
        }
    }

    public static void destroyAreaIceCharge(World world, BlockPos center, EntityDragonBase destroyer){
        if(destroyer != null) {
            int stage = destroyer.getDragonStage();
            if (stage <= 3) {
                for (BlockPos pos : BlockPos.getAllInBox(center.add(-2, -2, -2), center.add(2, 2, 2))) {
                    if (world.rand.nextFloat() > pos.distanceSq(center) && !(world.getBlockState(pos).getBlock() instanceof IDragonProof)) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                }
                for (BlockPos pos : BlockPos.getAllInBox(center.add(-2, -2, -2), center.add(2, 2, 2))) {
                    if (world.rand.nextBoolean()) {
                        IBlockState transformState = transformBlockIce(world.getBlockState(pos));
                        world.setBlockState(pos, transformState);
                        if (world.rand.nextBoolean() && transformState.isFullBlock() && world.isAirBlock(pos.up())) {
                            world.setBlockState(pos.up(), ModBlocks.dragon_ice_spikes.getDefaultState());
                        }
                    }
                }
                for (EntityLiving entityliving : world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB((double) center.getX() - 2, (double) center.getY() - 2, (double) center.getZ() - 2, (double) center.getX() + 2, (double) center.getY() + 2, (double) center.getZ() + 2))) {
                    if (!destroyer.isOnSameTeam(entityliving) && !destroyer.isEntityEqual(entityliving)) {
                        entityliving.attackEntityFrom(IceAndFire.dragonIce, Math.max(1, stage - 1) * 2F);
                        FrozenEntityProperties frozenProps = EntityPropertiesHandler.INSTANCE.getProperties(entityliving, FrozenEntityProperties.class);
                        if (frozenProps != null) {
                            frozenProps.setFrozenFor(400);
                        }
                    }
                }
            } else {
                int radius = stage == 4 ? 2 : 3;
                int j = radius + world.rand.nextInt(2);
                int k = (radius + world.rand.nextInt(2));
                int l = radius + world.rand.nextInt(2);
                float f = (float) (j + k + l) * 0.333F + 0.5F;
                for (BlockPos blockpos : BlockPos.getAllInBox(center.add(-j, -k, -l), center.add(j, k, l))) {
                    if (blockpos.distanceSq(center) <= (double) (f * f)) {
                        if (world.rand.nextFloat() > (float) blockpos.distanceSq(center) / (f * f) && !(world.getBlockState(blockpos).getBlock() instanceof IDragonProof)) {
                            world.setBlockState(blockpos, Blocks.AIR.getDefaultState());
                        }
                    }
                }
                j++;
                k++;
                l++;
                for (BlockPos blockpos : BlockPos.getAllInBox(center.add(-j, -k, -l), center.add(j, k, l))) {
                    if (blockpos.distanceSq(center) <= (double) (f * f)) {
                        IBlockState transformState = transformBlockIce(world.getBlockState(blockpos));
                        world.setBlockState(blockpos, transformState);
                        if (world.rand.nextBoolean() && transformState.isFullBlock() && world.isAirBlock(blockpos.up())) {
                            world.setBlockState(blockpos.up(), ModBlocks.dragon_ice_spikes.getDefaultState());
                        }
                    }
                }
                for (EntityLiving entityliving : world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB((double) center.getX() - j, (double) center.getY() - k, (double) center.getZ() - l, (double) center.getX() + j, (double) center.getY() + k, (double) center.getZ() + l))) {
                    if (!destroyer.isOnSameTeam(entityliving) && !destroyer.isEntityEqual(entityliving)) {
                        entityliving.attackEntityFrom(IceAndFire.dragonIce, Math.max(1, stage - 1) * 2F);
                        FrozenEntityProperties frozenProps = EntityPropertiesHandler.INSTANCE.getProperties(entityliving, FrozenEntityProperties.class);
                        if (frozenProps != null) {
                            frozenProps.setFrozenFor(400);
                        }
                    }
                }
            }
        }
    }

    public static IBlockState transformBlockFire(IBlockState in){
        if(in.getBlock() instanceof IDragonProof){
            return in;
        }
        if (in.getMaterial() == Material.GRASS || in.getMaterial() == Material.CRAFTED_SNOW) {
            return ModBlocks.charedGrass.getDefaultState().withProperty(BlockReturningState.REVERTS, true);
        } else if (in.getMaterial() == Material.GROUND && in.getBlock() == Blocks.DIRT) {
            return ModBlocks.charedDirt.getDefaultState().withProperty(BlockReturningState.REVERTS, true);
        } else if (in.getMaterial() == Material.GROUND && in.getBlock() == Blocks.GRAVEL) {
            return ModBlocks.charedGravel.getDefaultState().withProperty(BlockFallingReturningState.REVERTS, true);
        } else if (in.getMaterial() == Material.ROCK && (in.getBlock() == Blocks.COBBLESTONE || in.getBlock().getTranslationKey().contains("cobblestone"))) {
            return ModBlocks.charedCobblestone.getDefaultState().withProperty(BlockReturningState.REVERTS, true);
        } else if (in.getMaterial() == Material.ROCK && in.getBlock() != ModBlocks.charedCobblestone) {
            return ModBlocks.charedStone.getDefaultState().withProperty(BlockReturningState.REVERTS, true);
        } else if (in.getBlock() == Blocks.GRASS_PATH) {
            return ModBlocks.charedGrassPath.getDefaultState().withProperty(BlockCharedPath.REVERTS, true);
        } else if (in.getMaterial() == Material.WOOD) {
            return ModBlocks.ash.getDefaultState();
        } else if (in.getMaterial() == Material.LEAVES || in.getMaterial() == Material.PLANTS || in.getBlock() == Blocks.SNOW_LAYER) {
            return Blocks.AIR.getDefaultState();
        }
        return in;
    }

    public static IBlockState transformBlockIce(IBlockState in){
        if(in.getBlock() instanceof IDragonProof || !canDragonDestroyBlock(in)){
            return in;
        }
        if (in.getMaterial() == Material.GRASS || in.getMaterial() == Material.CRAFTED_SNOW) {
            return ModBlocks.frozenGrass.getDefaultState().withProperty(BlockReturningState.REVERTS, true);
        } else if (in.getMaterial() == Material.GROUND && in.getBlock() == Blocks.DIRT || in.getMaterial() == Material.CRAFTED_SNOW) {
            return ModBlocks.frozenDirt.getDefaultState().withProperty(BlockReturningState.REVERTS, true);
        } else if (in.getMaterial() == Material.GROUND && in.getBlock() == Blocks.GRAVEL) {
            return ModBlocks.frozenGravel.getDefaultState().withProperty(BlockFallingReturningState.REVERTS, true);
        } else if (in.getMaterial() == Material.ROCK && (in.getBlock() == Blocks.COBBLESTONE || in.getBlock().getTranslationKey().contains("cobblestone"))) {
            return ModBlocks.frozenCobblestone.getDefaultState().withProperty(BlockReturningState.REVERTS, true);
        } else if (in.getMaterial() == Material.ROCK && in.getBlock() != ModBlocks.frozenCobblestone) {
            return ModBlocks.frozenStone.getDefaultState().withProperty(BlockReturningState.REVERTS, true);
        } else if (in.getBlock() == Blocks.GRASS_PATH) {
            return ModBlocks.frozenGrassPath.getDefaultState().withProperty(BlockCharedPath.REVERTS, true);
        } else if (in.getMaterial() == Material.WOOD) {
            return ModBlocks.frozenSplinters.getDefaultState();
        } else if (in.getMaterial() == Material.LEAVES || in.getMaterial() == Material.PLANTS || in.getBlock() == Blocks.SNOW_LAYER) {
            return Blocks.AIR.getDefaultState();
        }
        return in;
    }

    private static boolean canDragonDestroyBlock(IBlockState in) {
        return EntityWither.canDestroyBlock(in.getBlock());
    }
}
