package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.registries.color.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import oshi.util.tuples.*;

public class SpectralDecorations implements ModInitializer {
	
	public static final String MOD_ID = "spectral-decorations";

	@Override
	public void onInitialize() {
		SpectralDecorationsBlocks.register();
		SpectralDecorationsItemGroups.register();
		//SpectralDecorationsKindlingVariants.register();
		
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			for (SpectralDecorationsBlocks.PropertyHolder entry : SpectralDecorationsBlocks.items) {
				ItemColors.ITEM_COLORS.registerColorMapping(entry.item(), entry.color());
			}
		});
	}
	
	public static Identifier locate(String name) {
		return new Identifier(MOD_ID, name);
	}
	
}
