package de.dafuqs.spectral_decorations.client;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.*;
import net.fabricmc.fabric.api.client.item.v1.*;
import net.fabricmc.fabric.api.resource.*;
import net.fabricmc.loader.api.*;
import net.minecraft.block.*;
import net.minecraft.client.item.*;
import net.minecraft.client.render.*;
import net.minecraft.item.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

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
		
		ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
			if (stack.isIn(SpectralDecorationsItemTags.BEDROCK_ARMOR)) {
				Optional<DyeColor> optionalColor = BedrockArmorColorizer.getColor(stack);
				if (optionalColor.isPresent()) {
					DyeColor c = optionalColor.get();
					Text t = Text.translatable("tooltip.spectral-decorations.colored").append(Text.translatable("color.minecraft." + c.asString()).styled(style -> style.withColor(c.getSignColor())));
					lines.add(1, t);
				}
			}
		});
		
		registerColorPredicate(SpectrumItems.BEDROCK_HELMET);
		registerColorPredicate(SpectrumItems.BEDROCK_CHESTPLATE);
		registerColorPredicate(SpectrumItems.BEDROCK_LEGGINGS);
		registerColorPredicate(SpectrumItems.BEDROCK_BOOTS);
		
		// Builtin Resource Packs
		Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(SpectralDecorations.MOD_ID);
		modContainer.ifPresent(container -> ResourceManagerHelper.registerBuiltinResourcePack(SpectralDecorations.locate("spectral_decorations"), container, Text.of("Spectral Decorations Overrides"), ResourcePackActivationType.DEFAULT_ENABLED));
	}
	
}
