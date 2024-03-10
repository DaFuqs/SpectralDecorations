package de.dafuqs.spectral_decorations;

import de.dafuqs.fractal.api.*;
import de.dafuqs.spectrum.api.item_group.*;
import net.fabricmc.fabric.api.itemgroup.v1.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import oshi.util.tuples.*;

public class SpectralDecorationsItemGroups {
	
	public static void register() {
		ItemSubGroupEvents.modifyEntriesEvent(ItemGroupIDs.SUBTAB_DECORATION).register(new ItemSubGroupEvents.ModifyEntries() {
			@Override
			public void modifyEntries(FabricItemGroupEntries entries) {
				for (Triplet<Item, SpectralDecorationsBlocks.Type, DyeColor> item : SpectralDecorationsBlocks.items) {
					entries.add(new ItemStack(item.getA()));
				}
			}
		});
		
	}
	
}
