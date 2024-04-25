package de.dafuqs.spectral_decorations.client;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.energy.color.*;
import de.dafuqs.spectrum.items.magic_items.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.*;
import net.minecraft.block.*;
import net.minecraft.client.item.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import oshi.util.tuples.*;

import java.util.*;

public class SpectralDecorationsClient implements ClientModInitializer {
	
	private static void registerColorPredicate(Item item) {
		ModelPredicateProviderRegistry.register(item, new Identifier("color"), (itemStack, clientWorld, livingEntity, i) -> {
			Optional<DyeColor> color = BedrockArmorColorizer.getColor(itemStack);
			return color.map(dyeColor -> (1F + dyeColor.getId()) / 100F).orElse(0F);
		});
	}
	
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
		
		registerColorPredicate(SpectrumItems.BEDROCK_HELMET);
		registerColorPredicate(SpectrumItems.BEDROCK_CHESTPLATE);
		registerColorPredicate(SpectrumItems.BEDROCK_LEGGINGS);
		registerColorPredicate(SpectrumItems.BEDROCK_BOOTS);
	}
	
}
