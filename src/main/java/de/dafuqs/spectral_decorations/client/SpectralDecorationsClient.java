package de.dafuqs.spectral_decorations.client;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.*;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.*;
import net.fabricmc.fabric.api.client.item.v1.*;
import net.fabricmc.fabric.api.resource.*;
import net.fabricmc.loader.api.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.item.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;

import java.util.*;

public class SpectralDecorationsClient implements ClientModInitializer {
	
	private static void registerColorPredicate(Item item) {
		ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(SpectralDecorations.MOD_ID, "color"), (itemStack, clientWorld, livingEntity, i) -> {
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
					BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutout());
				}
				case LIGHT -> {
					BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.translucent());
				}
			}
		}
		
		ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipFlag, list) -> {
			if (stack.is(SpectralDecorationsItemTags.BEDROCK_ARMOR) || stack.is(SpectrumBlocks.BOTTOMLESS_BUNDLE.asItem())) {
				Optional<DyeColor> optionalColor = BedrockArmorColorizer.getColor(stack);
				if (optionalColor.isPresent()) {
					DyeColor c = optionalColor.get();
					Component t = Component.translatable("tooltip.spectral-decorations.colored")
							.append(Component.translatable("color.minecraft." + c.getName()).withStyle((style -> style.withColor(c.getTextColor()))));
					list.add(1, t);
				}
			}
		});
		
		ClientLifecycleEvents.CLIENT_STARTED.register(minecraft -> {
			registerColorPredicate(SpectrumItems.BEDROCK_HELMET);
			registerColorPredicate(SpectrumItems.BEDROCK_CHESTPLATE);
			registerColorPredicate(SpectrumItems.BEDROCK_LEGGINGS);
			registerColorPredicate(SpectrumItems.BEDROCK_BOOTS);
			
			registerColorPredicate(SpectrumBlocks.BOTTOMLESS_BUNDLE.asItem());
		});
		
		// Builtin Resource Packs
		Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(SpectralDecorations.MOD_ID);
		modContainer.ifPresent(container -> ResourceManagerHelper.registerBuiltinResourcePack(SpectralDecorations.locate("spectral_decorations"), container, Component.nullToEmpty("Spectral Decorations Overrides"), ResourcePackActivationType.DEFAULT_ENABLED));
	}
	
}
