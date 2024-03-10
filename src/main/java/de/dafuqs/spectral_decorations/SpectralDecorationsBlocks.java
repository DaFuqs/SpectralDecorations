package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.blocks.amphora.*;
import de.dafuqs.spectrum.blocks.decoration.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.item.v1.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import oshi.util.tuples.*;

import java.util.*;

public class SpectralDecorationsBlocks {
	
	public enum Type {
		AMPHORA,
		LAMP,
		LIGHT
	}
	
	public static Item item;
	
	public static void register() {
		for(DyeColor color : DyeColor.values()) {
			String colorString = color.asString();
			Item i = registerBlockWithItem(colorString + "_amphora", new AmphoraBlock(AbstractBlock.Settings.copy(SpectrumBlocks.SLATE_NOXWOOD_AMPHORA).mapColor(color).sounds(BlockSoundGroup.WOOD)), SpectrumItems.IS.of(), Type.AMPHORA, color);
			if(item == null) {
				item = i;
			}
			registerBlockWithItem(colorString + "_lamp", new FlexLanternBlock(AbstractBlock.Settings.copy(SpectrumBlocks.SLATE_NOXWOOD_LAMP).mapColor(color).sounds(BlockSoundGroup.WOOD)), SpectrumItems.IS.of(), Type.LAMP, color);
			registerBlockWithItem(colorString + "_light", new PillarBlock(AbstractBlock.Settings.copy(SpectrumBlocks.SLATE_NOXWOOD_LIGHT).mapColor(color)), SpectrumItems.IS.of(), Type.LIGHT, color);
		}
	}
	
	public static final List<Triplet<Item, SpectralDecorationsBlocks.Type, DyeColor>> items = new ArrayList<>();
	
	public static Item registerBlockWithItem(String name, Block block, FabricItemSettings itemSettings, Type type, DyeColor dyeColor) {
		Registry.register(Registries.BLOCK, SpectralDecorations.locate(name), block);
		BlockItem blockItem = new BlockItem(block, itemSettings);
		Registry.register(Registries.ITEM, SpectralDecorations.locate(name), blockItem);
		
		items.add(new Triplet<>(blockItem, type, dyeColor));
		
		return blockItem;
	}
	
}