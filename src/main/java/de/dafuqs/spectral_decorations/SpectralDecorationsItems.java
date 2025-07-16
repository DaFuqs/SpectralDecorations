package de.dafuqs.spectral_decorations;

import de.dafuqs.fractal.api.*;
import de.dafuqs.spectral_decorations.items.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.*;

import java.util.*;

public class SpectralDecorationsItems {
	
	public static final List<PropertyHolder> holder = new ArrayList<>();
	
	public static void register() {
		for (DyeColor color : SpectrumColorHelper.VANILLA_DYE_COLORS) {
			String colorString = color.getSerializedName();
			registerItem(SpectrumItemGroups.RESOURCES, colorString + "_resplendent_feather", new ColoredResplendentFeatherItem(new Item.Properties().rarity(Rarity.UNCOMMON), color), color);
		}
	}

	public static void registerItem(ItemSubGroup subGroup, String name, Item item, DyeColor dyeColor) {
		Registry.register(BuiltInRegistries.ITEM, SpectralDecorations.locate(name), item);
		holder.add(new PropertyHolder(item, subGroup, dyeColor));
	}
	
	public record PropertyHolder(Item item, ItemSubGroup subGroup, DyeColor color) {
	}
	
}