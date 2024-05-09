package de.dafuqs.spectral_decorations;

import de.dafuqs.fractal.api.*;
import de.dafuqs.spectrum.blocks.amphora.*;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.*;
import de.dafuqs.spectrum.blocks.decoration.*;
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

public class SpectralDecorationsItems {
	
	public static final List<PropertyHolder> items = new ArrayList<>();
	
	public static void register() {
		for(DyeColor color : DyeColor.values()) {
			if (color.getId() > 15) {
				break;
			}

			String colorString = color.asString();
			registerItem(SpectrumItemGroups.RESOURCES, colorString + "_effulgent_feather", new Item(new FabricItemSettings()), color);
		}
	}
	
	public static Item registerItem(ItemSubGroup subGroup, String name, Item item, DyeColor dyeColor) {
		Registry.register(Registries.ITEM, SpectralDecorations.locate(name), item);
		
		items.add(new PropertyHolder(item, subGroup, dyeColor));
		
		return item;
	}
	
	public record PropertyHolder(Item item, ItemSubGroup subGroup, DyeColor color) {
	}
	
}