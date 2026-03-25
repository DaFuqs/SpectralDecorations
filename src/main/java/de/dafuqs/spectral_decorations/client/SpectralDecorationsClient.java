package de.dafuqs.spectral_decorations.client;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.item.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.*;
import net.neoforged.fml.common.*;
import net.neoforged.fml.event.lifecycle.*;

import java.util.*;

@Mod(value = SpectralDecorations.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SpectralDecorations.MOD_ID, value = Dist.CLIENT)
public class SpectralDecorationsClient {
	
	public SpectralDecorationsClient(IEventBus modBus, ModContainer modContainer) {
	
	}
	
	@SubscribeEvent
	public static void registerClientStuffs(FMLClientSetupEvent event) {
		for (SpectralDecorationsBlocks.PropertyHolder entry : SpectralDecorationsBlocks.HOLDER) {
			switch (entry.type()) {
				case LANTERN -> ItemBlockRenderTypes.setRenderLayer(entry.block().get(), RenderType.cutout());
				case LIGHT -> ItemBlockRenderTypes.setRenderLayer(entry.block().get(), RenderType.translucent());
			}
		}
		
		registerColorPredicate(SpectrumItems.BEDROCK_HELMET.get());
		registerColorPredicate(SpectrumItems.BEDROCK_CHESTPLATE.get());
		registerColorPredicate(SpectrumItems.BEDROCK_LEGGINGS.get());
		registerColorPredicate(SpectrumItems.BEDROCK_BOOTS.get());
		registerColorPredicate(SpectrumBlocks.BOTTOMLESS_BUNDLE.asItem());
	}
	
	private static void registerColorPredicate(Item item) {
		ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(SpectralDecorations.MOD_ID, "color"), (itemStack, clientWorld, livingEntity, i) -> {
			Optional<DyeColor> color = BedrockArmorColorizer.getColor(itemStack);
			return color.map(dyeColor -> (1F + dyeColor.getId()) / 100F).orElse(0F);
		});
	}
	
}
