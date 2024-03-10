package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.registries.color.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.*;
import net.minecraft.item.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import oshi.util.tuples.*;

public class SpectralDecorations implements ModInitializer {
	
	public static final String MOD_ID = "spectral-decorations";

	@Override
	public void onInitialize() {
		SpectralDecorationsBlocks.register();
		SpectralDecorationsItemGroups.register();
		
		ServerLifecycleEvents.SERVER_STARTED.register(new ServerLifecycleEvents.ServerStarted() {
			@Override
			public void onServerStarted(MinecraftServer server) {
				for(Triplet<Item, SpectralDecorationsBlocks.Type, DyeColor> entry : SpectralDecorationsBlocks.items) {
					ItemColors.ITEM_COLORS.registerColorMapping(entry.getA(), entry.getC());
				}
			}
		});
	}
	
	public static Identifier locate(String name) {
		return new Identifier(MOD_ID, name);
	}
	
}
