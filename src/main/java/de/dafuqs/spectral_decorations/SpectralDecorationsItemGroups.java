package de.dafuqs.spectral_decorations;

import net.fabricmc.fabric.api.itemgroup.v1.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import oshi.util.tuples.*;

public class SpectralDecorationsItemGroups {
	
	public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder().displayName(Text.translatable("itemGroup.spectral-decorations.deco"))
			.icon(() -> new ItemStack(SpectralDecorationsBlocks.item))
			.entries((displayContext, entries) -> {
				for (Triplet<Item, SpectralDecorationsBlocks.Type, DyeColor> item : SpectralDecorationsBlocks.items) {
					entries.add(new ItemStack(item.getA()));
				}
			})
			.build();
	
	public static void register() {
		Registry.register(Registries.ITEM_GROUP, SpectralDecorations.locate("group"), ITEM_GROUP);
	}
	
}
