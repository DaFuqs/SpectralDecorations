package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.blocks.amphora.*;
import de.dafuqs.spectrum.blocks.furniture.*;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.item.v1.*;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.registry.*;
import oshi.util.tuples.*;

import java.util.*;

public class SpectralDecorationsBlocks {
	
	public static void register() {
		for(DyeColor color : DyeColor.values()) {
			if (color.getId() > 15) {
				break;
			}
			
			String colorString = color.asString();
			registerBlockWithItem(colorString + "_beam", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, color).sounds(BlockSoundGroup.WOOD)), new FabricItemSettings().group(SpectralDecorationsItemGroups.ITEM_GROUP), Type.BEAM, color, 5, 20);
			Item i = registerBlockWithItem(colorString + "_amphora", new AmphoraBlock(AbstractBlock.Settings.of(Material.WOOD, color).sounds(BlockSoundGroup.WOOD)), new FabricItemSettings().group(SpectralDecorationsItemGroups.ITEM_GROUP), Type.AMPHORA, color, 0, 0);
			registerBlockWithItem(colorString + "_lantern", new FlexLanternBlock(AbstractBlock.Settings.of(Material.WOOD, color).sounds(BlockSoundGroup.WOOD).luminance(state -> 13)), new FabricItemSettings().group(SpectralDecorationsItemGroups.ITEM_GROUP), Type.LANTERN, color, 0, 0);
			registerBlockWithItem(colorString + "_light", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, color).sounds(BlockSoundGroup.WOOD).luminance(state -> 15)), new FabricItemSettings().group(SpectralDecorationsItemGroups.ITEM_GROUP), Type.LIGHT, color, 5, 20);
		}
	}
	
	public enum Type {
		AMPHORA,
		LANTERN,
		LIGHT,
		BEAM
	}
	
	public static final List<Triplet<Item, SpectralDecorationsBlocks.Type, DyeColor>> items = new ArrayList<>();
	
	public static Item registerBlockWithItem(String name, Block block, FabricItemSettings itemSettings, Type type, DyeColor dyeColor, int fireBurn, int fireSpread) {
		Registry.register(Registry.BLOCK, SpectralDecorations.locate(name), block);
		BlockItem blockItem = new BlockItem(block, itemSettings);
		Registry.register(Registry.ITEM, SpectralDecorations.locate(name), blockItem);
		
		items.add(new Triplet<>(blockItem, type, dyeColor));
		
		if(fireBurn > 0 && fireSpread > 0) {
			FlammableBlockRegistry.getDefaultInstance().add(ColoredLogBlock.byColor(dyeColor), fireBurn, fireSpread);
		}
		
		return blockItem;
	}
	
}