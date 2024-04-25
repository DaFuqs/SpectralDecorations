package de.dafuqs.spectral_decorations.client;

import de.dafuqs.spectral_decorations.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.*;
import net.minecraft.block.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import oshi.util.tuples.*;

public class SpectralDecorationsClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		for (SpectralDecorationsBlocks.PropertyHolder entry : SpectralDecorationsBlocks.items) {
			Block block = ((BlockItem) entry.item()).getBlock();
			switch (entry.type()) {
				case LANTERN -> {
					BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
				}
				case LIGHT -> {
					BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
				}
			}
		}
		
	}
	
}
