package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.blocks.amphora.*;
import de.dafuqs.spectrum.blocks.furniture.*;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.*;
import net.fabricmc.fabric.api.item.v1.*;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import oshi.util.tuples.*;

import java.util.*;

public class SpectralDecorationsBlocks {
	
	public static void register() {
		for (VanillaWood wood : VanillaWood.values()) {
			String name = wood.getName();
			MapColor mapColor = wood.getMapColor();
			boolean isFireResistant = wood.isFireResistant();
			registerBlockWithItem(name + "_beam", new PillarBlock(AbstractBlock.Settings.create().mapColor(mapColor).sounds(BlockSoundGroup.WOOD)), new FabricItemSettings(), Type.BEAM, DyeColor.LIME, isFireResistant ? 0 : 5, isFireResistant ? 0 : 20);
			registerBlockWithItem(name + "_amphora", new AmphoraBlock(AbstractBlock.Settings.create().mapColor(mapColor).sounds(BlockSoundGroup.WOOD)), new FabricItemSettings(), Type.AMPHORA, DyeColor.LIME, 0, 0);
		}
		
		
		for(DyeColor color : DyeColor.values()) {
			if (color.getId() > 15) {
				break;
			}
			
			String colorString = color.asString();
			registerBlockWithItem(colorString + "_beam", new PillarBlock(AbstractBlock.Settings.create().mapColor(color).sounds(BlockSoundGroup.WOOD)), new FabricItemSettings(), Type.BEAM, color, 5, 20);
			registerBlockWithItem(colorString + "_amphora", new AmphoraBlock(AbstractBlock.Settings.create().mapColor(color).sounds(BlockSoundGroup.WOOD)), new FabricItemSettings(), Type.AMPHORA, color, 0, 0);
			registerBlockWithItem(colorString + "_lantern", new FlexLanternBlock(AbstractBlock.Settings.create().mapColor(color).sounds(BlockSoundGroup.WOOD).luminance(state -> 13)), new FabricItemSettings(), Type.LANTERN, color, 0, 0);
			registerBlockWithItem(colorString + "_light", new PillarBlock(AbstractBlock.Settings.create().mapColor(color).sounds(BlockSoundGroup.WOOD).luminance(state -> 15)), new FabricItemSettings(), Type.LIGHT, color, 5, 20);
		}
	}
	
	public enum VanillaWood {
		ACACIA,
		BAMBOO,
		BIRCH,
		CHERRY,
		CRIMSON,
		DARK_OAK,
		JUNGLE,
		MANGROVE,
		OAK,
		SPRUCE,
		WARPED;
		
		private String name;
		private MapColor mapColor;
		private boolean isFireResistant;
		
		VanillaWood(String name, MapColor mapColor, boolean isFireResistant) {
			this.name = name;
			this.mapColor = mapColor;
			this.isFireResistant = isFireResistant;
		}
		
		public String getName() {
			return name;
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
	
	public static final List<Triplet<Item, SpectralDecorationsBlocks.Type, DyeColor>> items = new ArrayList<>();
	
	public static Item registerBlockWithItem(String name, Block block, FabricItemSettings itemSettings, Type type, DyeColor dyeColor, int fireBurn, int fireSpread) {
		Registry.register(Registries.BLOCK, SpectralDecorations.locate(name), block);
		BlockItem blockItem = new BlockItem(block, itemSettings);
		Registry.register(Registries.ITEM, SpectralDecorations.locate(name), blockItem);
		
		items.add(new Triplet<>(blockItem, type, dyeColor));
		
		if(fireBurn > 0 && fireSpread > 0) {
			FlammableBlockRegistry.getDefaultInstance().add(ColoredLogBlock.byColor(dyeColor), fireBurn, fireSpread);
		}
		
		return blockItem;
	}
	
}