package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.blocks.amphora.*;
import de.dafuqs.spectrum.blocks.furniture.*;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.*;
import de.dafuqs.spectrum.registries.*;
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