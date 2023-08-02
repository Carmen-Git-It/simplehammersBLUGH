package org.spysat.simplehammers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HammerItem extends MiningToolItem {
    public static HashMap<Block, Block> HammeringMap = new HashMap<>(
            Map.of(
                    Blocks.COBBLED_DEEPSLATE, Blocks.COBBLESTONE,
                    Blocks.COBBLESTONE, Blocks.GRAVEL,
                    Blocks.GRAVEL, Blocks.DIRT,
                    Blocks.DIRT, Blocks.SAND
            )
    );

    public HammerItem(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Item.Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Override // Hammers can mine anything that shovels and pickaxes can
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return state.isIn(BlockTags.PICKAXE_MINEABLE) || state.isIn(BlockTags.SHOVEL_MINEABLE) ? this.miningSpeed : 1.0f;
    }

    @Override
    public boolean isSuitableFor(BlockState state) { //Check the tool material to compare whether it can be mined by the hammer
        if (state.isIn(SimpleHammers.HAMMER_TAG)) {
            return this.getMaterial().getMiningLevel() >= getMiningLevel(state);
        }
        return false;
    }

    public static int getMiningLevel(BlockState state) {
        if (state.isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return ToolMaterials.DIAMOND.getMiningLevel();
        } else if (state.isIn(BlockTags.NEEDS_IRON_TOOL)) {
            return ToolMaterials.IRON.getMiningLevel();
        } else if (state.isIn(BlockTags.NEEDS_STONE_TOOL)) {
            return ToolMaterials.STONE.getMiningLevel();
        }
        return 0;
    }
}