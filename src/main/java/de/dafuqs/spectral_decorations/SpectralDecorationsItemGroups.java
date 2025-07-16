package de.dafuqs.spectral_decorations;

import de.dafuqs.fractal.api.*;
import de.dafuqs.spectrum.api.item_group.*;
import net.minecraft.world.item.*;

public class SpectralDecorationsItemGroups {

	public static void register() {
		ItemSubGroupEvents.modifyEntriesEvent(ItemGroupIDs.SUBTAB_COLORED_WOOD).register(entries -> {
			for (SpectralDecorationsBlocks.PropertyHolder item : SpectralDecorationsBlocks.holder) {
				if (item.subGroup().getIdentifier().equals(ItemGroupIDs.SUBTAB_COLORED_WOOD)) {
					entries.accept(new ItemStack(item.item()));
				}
			}
		});
		ItemSubGroupEvents.modifyEntriesEvent(ItemGroupIDs.SUBTAB_DECORATION).register(entries -> {
			for (SpectralDecorationsBlocks.PropertyHolder item : SpectralDecorationsBlocks.holder) {
				if (item.subGroup().getIdentifier().equals(ItemGroupIDs.SUBTAB_DECORATION)) {
					entries.accept(new ItemStack(item.item()));
				}
			}
		});
		ItemSubGroupEvents.modifyEntriesEvent(ItemGroupIDs.SUBTAB_RESOURCES).register(entries -> {
			for (SpectralDecorationsItems.PropertyHolder item : SpectralDecorationsItems.holder) {
				if (item.subGroup().getIdentifier().equals(ItemGroupIDs.SUBTAB_RESOURCES)) {
					entries.accept(item.item().getDefaultInstance());
				}
			}
		});
	}
	
}
