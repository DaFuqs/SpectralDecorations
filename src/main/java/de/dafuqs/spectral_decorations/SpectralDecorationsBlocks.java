package de.dafuqs.spectral_decorations;

import de.dafuqs.fractal.api.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.blocks.amphora.*;
import de.dafuqs.spectrum.blocks.decoration.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.material.*;

import java.util.*;

public class SpectralDecorationsBlocks {
	
	public static final List<PropertyHolder> items = new ArrayList<>();
	
	public static void register() {
		for (VanillaWood wood : VanillaWood.values()) {
			String name = wood.getName();
			MapColor mapColor = wood.getMapColor();
			boolean isFireResistant = wood.isFireResistant();
			SoundType blockSoundGroup = wood.getBlockSoundGroup();
			registerBlockWithItem(SpectrumItemGroups.DECORATION, name + "_beam", new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).mapColor(mapColor).sound(blockSoundGroup)), new Item.Properties(), Type.BEAM, InkColors.LIME, isFireResistant ? 0 : 5, isFireResistant ? 0 : 20);
			registerBlockWithItem(SpectrumItemGroups.DECORATION, name + "_amphora", new AmphoraBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).mapColor(mapColor).sound(blockSoundGroup)), new Item.Properties(), Type.AMPHORA, InkColors.LIME, 0, 0);
		}
		
		for (DyeColor color : SpectrumColorHelper.VANILLA_DYE_COLORS) {
			String colorString = color.getSerializedName();
			InkColor inkColor = InkColor.ofDyeColor(color);
			
			registerBlockWithItem(SpectrumItemGroups.COLORED_WOOD, colorString + "_beam", new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(4.0F).mapColor(color).sound(SoundType.WOOD)), new Item.Properties(), Type.BEAM, inkColor, 5, 20);
			registerBlockWithItem(SpectrumItemGroups.COLORED_WOOD, colorString + "_amphora", new AmphoraBlock(BlockBehaviour.Properties.of().strength(4.0F).mapColor(color).sound(SoundType.WOOD)), new Item.Properties(), Type.AMPHORA, inkColor, 0, 0);
			registerBlockWithItem(SpectrumItemGroups.COLORED_WOOD, colorString + "_lantern", new FlexLanternBlock(BlockBehaviour.Properties.of().strength(4.0F).mapColor(color).sound(SoundType.WOOD).lightLevel(state -> 13)), new Item.Properties(), Type.LANTERN, inkColor, 0, 0);
			registerBlockWithItem(SpectrumItemGroups.COLORED_WOOD, colorString + "_light", new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(4.0F).mapColor(color).sound(SoundType.WOOD).lightLevel(state -> 15)), new Item.Properties(), Type.LIGHT, inkColor, 5, 20);
			
			registerBlockWithItem(SpectrumItemGroups.DECORATION, colorString + "_effulgent_block", new CushionedFacingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_WOOL).mapColor(color)), new Item.Properties().rarity(Rarity.UNCOMMON), Type.EFFULGENT_BLOCK, inkColor, 0, 0);
			registerBlockWithItem(SpectrumItemGroups.DECORATION, colorString + "_effulgent_cushion", new CushionBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_WOOL).mapColor(color).noOcclusion().isValidSpawn((state, world, pos, type) -> false)), new Item.Properties().rarity(Rarity.UNCOMMON), Type.EFFULGENT_CUSHION, inkColor, 0, 0);
			registerBlockWithItem(SpectrumItemGroups.DECORATION, colorString + "_effulgent_carpet", new CushionedCarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).mapColor(color)), new Item.Properties().rarity(Rarity.UNCOMMON), Type.EFFULGENT_CARPET, inkColor, 0, 0);
			registerBlockWithItem(SpectrumItemGroups.DECORATION, colorString + "_effulgent_bed", new SpectrumBedBlock(color, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_BED).mapColor(color)), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), Type.EFFULGENT_BED, inkColor, 0, 0);
		}
	}
	
	public static void registerBlockWithItem(ItemSubGroup subGroup, String name, Block block, Item.Properties itemSettings, Type type, InkColor color, int fireBurn, int fireSpread) {
		Registry.register(BuiltInRegistries.BLOCK, SpectralDecorations.locate(name), block);
		BlockItem blockItem = new BlockItem(block, itemSettings);
		Registry.register(BuiltInRegistries.ITEM, SpectralDecorations.locate(name), blockItem);
		
		items.add(new PropertyHolder(blockItem, subGroup, type, color));
		
		if(fireBurn > 0 && fireSpread > 0) {
			FlammableBlockRegistry.getDefaultInstance().add(block, fireBurn, fireSpread);
		}
	}
	
	public enum VanillaWood {
		OAK("oak", MapColor.WOOD, SoundType.WOOD, false),
		SPRUCE("spruce", MapColor.PODZOL, SoundType.WOOD, false),
		BIRCH("birch", MapColor.SAND, SoundType.WOOD, false),
		DARK_OAK("dark_oak", MapColor.COLOR_BROWN, SoundType.WOOD, false),
		JUNGLE("jungle", MapColor.DIRT, SoundType.WOOD, false),
		ACACIA("acacia", MapColor.COLOR_ORANGE, SoundType.WOOD, false),
		BAMBOO("bamboo", MapColor.COLOR_YELLOW, SoundType.BAMBOO_WOOD, false),
		MANGROVE("mangrove", MapColor.COLOR_RED, SoundType.WOOD, false),
		CHERRY("cherry", MapColor.TERRACOTTA_WHITE, SoundType.CHERRY_WOOD, false),
		CRIMSON("crimson", MapColor.CRIMSON_STEM, SoundType.NETHER_WOOD, true),
		WARPED("warped", MapColor.WARPED_STEM, SoundType.NETHER_WOOD, true);
		
		private final String name;
		private final MapColor mapColor;
		private final SoundType blockSoundGroup;
		private final boolean isFireResistant;
		
		VanillaWood(String name, MapColor mapColor, SoundType blockSoundGroup, boolean isFireResistant) {
			this.name = name;
			this.mapColor = mapColor;
			this.blockSoundGroup = blockSoundGroup;
			this.isFireResistant = isFireResistant;
		}
		
		public String getName() {
			return name;
		}
		
		public SoundType getBlockSoundGroup() {
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
		LIGHT,
		EFFULGENT_BLOCK,
		EFFULGENT_CUSHION,
		EFFULGENT_CARPET,
		EFFULGENT_BED
	}
	
	public record PropertyHolder(Item item, ItemSubGroup subGroup, Type type, InkColor color) {
	}
	
}