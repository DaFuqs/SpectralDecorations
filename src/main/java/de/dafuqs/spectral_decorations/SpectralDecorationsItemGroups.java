package de.dafuqs.spectral_decorations;

import de.dafuqs.fractal.api.*;
import de.dafuqs.spectrum.api.item_group.*;
import net.minecraft.item.*;

public class SpectralDecorationsItemGroups {

	public static void register() {
		ItemSubGroupEvents.modifyEntriesEvent(ItemGroupIDs.SUBTAB_COLORED_WOOD).register(entries -> {
			for (SpectralDecorationsBlocks.PropertyHolder item : SpectralDecorationsBlocks.items) {
				if (item.subGroup().getIdentifier().equals(ItemGroupIDs.SUBTAB_COLORED_WOOD)) {
					entries.add(new ItemStack(item.item()));
				}
			}
		});
		ItemSubGroupEvents.modifyEntriesEvent(ItemGroupIDs.SUBTAB_DECORATION).register(entries -> {
			for (SpectralDecorationsBlocks.PropertyHolder item : SpectralDecorationsBlocks.items) {
				if (item.subGroup().getIdentifier().equals(ItemGroupIDs.SUBTAB_DECORATION)) {
					entries.add(new ItemStack(item.item()));
				}
			}
		});
		ItemSubGroupEvents.modifyEntriesEvent(ItemGroupIDs.SUBTAB_RESOURCES).register(entries -> {
			for (SpectralDecorationsItems.PropertyHolder item : SpectralDecorationsItems.items) {
				if (item.subGroup().getIdentifier().equals(ItemGroupIDs.SUBTAB_RESOURCES)) {
					entries.add(item.item().getDefaultStack());
				}
			}
		});
	}
	
}
