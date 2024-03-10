package de.dafuqs.spectral_decorations.client;

import de.dafuqs.spectral_decorations.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.*;
import net.minecraft.block.*;
import net.minecraft.client.render.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import oshi.util.tuples.*;

public class SpectralDecorationsClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		for (Triplet<Item, SpectralDecorationsBlocks.Type, DyeColor> entry : SpectralDecorationsBlocks.items) {
			Block block = ((BlockItem) entry.getA()).getBlock();
			switch (entry.getB()) {
				case LAMP -> {
					BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
				}
				case LIGHT -> {
					BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
				}
			}
		}
	}
	
}
