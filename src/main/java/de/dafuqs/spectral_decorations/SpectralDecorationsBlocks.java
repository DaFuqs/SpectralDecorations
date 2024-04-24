package de.dafuqs.spectral_decorations;

import de.dafuqs.fractal.api.*;
import de.dafuqs.spectrum.blocks.amphora.*;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.*;
import de.dafuqs.spectrum.blocks.furniture.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.item.v1.*;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;

import java.util.*;

public class SpectralDecorationsBlocks {
	
	public static final List<PropertyHolder> items = new ArrayList<>();
	
	;
	
	public static void register() {
		for (VanillaWood wood : VanillaWood.values()) {
			String name = wood.getName();
			MapColor mapColor = wood.getMapColor();
			boolean isFireResistant = wood.isFireResistant();
			BlockSoundGroup blockSoundGroup = wood.getBlockSoundGroup();
			registerBlockWithItem(SpectrumItemGroups.DECORATION, name + "_beam", new PillarBlock(AbstractBlock.Settings.create().mapColor(mapColor).sounds(blockSoundGroup)), new FabricItemSettings(), Type.BEAM, DyeColor.LIME, isFireResistant ? 0 : 5, isFireResistant ? 0 : 20);
			registerBlockWithItem(SpectrumItemGroups.DECORATION, name + "_amphora", new AmphoraBlock(AbstractBlock.Settings.create().mapColor(mapColor).sounds(blockSoundGroup)), new FabricItemSettings(), Type.AMPHORA, DyeColor.LIME, 0, 0);
		}
		
		for(DyeColor color : DyeColor.values()) {
			if (color.getId() > 15) {
				break;
			}
			
			String colorString = color.asString();
			registerBlockWithItem(SpectrumItemGroups.COLORED_WOOD, colorString + "_beam", new PillarBlock(AbstractBlock.Settings.create().mapColor(color).sounds(BlockSoundGroup.WOOD)), new FabricItemSettings(), Type.BEAM, color, 5, 20);
			registerBlockWithItem(SpectrumItemGroups.COLORED_WOOD, colorString + "_amphora", new AmphoraBlock(AbstractBlock.Settings.create().mapColor(color).sounds(BlockSoundGroup.WOOD)), new FabricItemSettings(), Type.AMPHORA, color, 0, 0);
			registerBlockWithItem(SpectrumItemGroups.COLORED_WOOD, colorString + "_lantern", new FlexLanternBlock(AbstractBlock.Settings.create().mapColor(color).sounds(BlockSoundGroup.WOOD).luminance(state -> 13)), new FabricItemSettings(), Type.LANTERN, color, 0, 0);
			registerBlockWithItem(SpectrumItemGroups.COLORED_WOOD, colorString + "_light", new PillarBlock(AbstractBlock.Settings.create().mapColor(color).sounds(BlockSoundGroup.WOOD).luminance(state -> 15)), new FabricItemSettings(), Type.LIGHT, color, 5, 20);
		}
	}
	
	public static Item registerBlockWithItem(ItemSubGroup subGroup, String name, Block block, FabricItemSettings itemSettings, Type type, DyeColor dyeColor, int fireBurn, int fireSpread) {
		Registry.register(Registries.BLOCK, SpectralDecorations.locate(name), block);
		BlockItem blockItem = new BlockItem(block, itemSettings);
		Registry.register(Registries.ITEM, SpectralDecorations.locate(name), blockItem);
		
		items.add(new PropertyHolder(blockItem, subGroup, type, dyeColor));
		
		if(fireBurn > 0 && fireSpread > 0) {
			FlammableBlockRegistry.getDefaultInstance().add(ColoredLogBlock.byColor(dyeColor), fireBurn, fireSpread);
		}
		
		return blockItem;
	}
	
	public enum VanillaWood {
		OAK("oak", MapColor.OAK_TAN, BlockSoundGroup.WOOD, false),
		SPRUCE("spruce", MapColor.SPRUCE_BROWN, BlockSoundGroup.WOOD, false),
		BIRCH("birch", MapColor.PALE_YELLOW, BlockSoundGroup.WOOD, false),
		DARK_OAK("dark_oak", MapColor.BROWN, BlockSoundGroup.WOOD, false),
		JUNGLE("jungle", MapColor.DIRT_BROWN, BlockSoundGroup.WOOD, false),
		ACACIA("acacia", MapColor.ORANGE, BlockSoundGroup.WOOD, false),
		BAMBOO("bamboo", MapColor.YELLOW, BlockSoundGroup.BAMBOO_WOOD, false),
		MANGROVE("mangrove", MapColor.RED, BlockSoundGroup.WOOD, false),
		CHERRY("cherry", MapColor.TERRACOTTA_WHITE, BlockSoundGroup.CHERRY_WOOD, false),
		CRIMSON("crimson", MapColor.DULL_PINK, BlockSoundGroup.NETHER_WOOD, false),
		WARPED("warped", MapColor.DARK_AQUA, BlockSoundGroup.NETHER_WOOD, false);
		
		private final String name;
		private final MapColor mapColor;
		private final BlockSoundGroup blockSoundGroup;
		private final boolean isFireResistant;
		
		VanillaWood(String name, MapColor mapColor, BlockSoundGroup blockSoundGroup, boolean isFireResistant) {
			this.name = name;
			this.mapColor = mapColor;
			this.blockSoundGroup = blockSoundGroup;
			this.isFireResistant = isFireResistant;
		}
		
		public String getName() {
			return name;
		}
		
		public BlockSoundGroup getBlockSoundGroup() {
			return blockSoundGroup;
		}
		
		public MapColor getMapColor() {
			return mapColor;
		}
		
		public boolean isFireResistant() {
			return isFireResistant;
		}
	}

	public enum Type {
		BEAM,
		AMPHORA,
		LANTERN,
		LIGHT
	}
	
	public record PropertyHolder(Item item, ItemSubGroup subGroup, Type type, DyeColor color) {
	}
	
}