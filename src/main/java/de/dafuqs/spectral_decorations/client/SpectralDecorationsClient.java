package de.dafuqs.spectral_decorations.client;

import de.dafuqs.spectral_decorations.BedrockArmorColorizer;
import de.dafuqs.spectral_decorations.SpectralDecorations;
import de.dafuqs.spectral_decorations.SpectralDecorationsBlocks;
import de.dafuqs.spectral_decorations.SpectralDecorationsItemTags;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class SpectralDecorationsClient implements ClientModInitializer {
	
	private static void registerColorPredicate(Item item) {
		ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(SpectralDecorations.MOD_ID, "color"), (itemStack, clientWorld, livingEntity, i) -> {
			Optional<DyeColor> color = BedrockArmorColorizer.getColor(itemStack);
			return color.map(dyeColor -> (1F + dyeColor.getId()) / 100F).orElse(0F);
		});
	}
	
	@Override
	public void onInitializeClient() {
		for (SpectralDecorationsBlocks.PropertyHolder entry : SpectralDecorationsBlocks.holder) {
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
					Component t = Component.translatable("tooltip.spectral-decorations.colored").append(Component.translatable("color.minecraft." + c.getName()).withStyle((style -> style.withColor(c.getTextColor()))));
					list.add(1, t);
				}
			}
		});
		
		ClientLifecycleEvents.CLIENT_STARTED.register(minecraft -> {
			SpectralDecorations.delayedLoad();

			registerColorPredicate(SpectrumItems.BEDROCK_HELMET);
			registerColorPredicate(SpectrumItems.BEDROCK_CHESTPLATE);
			registerColorPredicate(SpectrumItems.BEDROCK_LEGGINGS);
			registerColorPredicate(SpectrumItems.BEDROCK_BOOTS);
			
			registerColorPredicate(SpectrumBlocks.BOTTOMLESS_BUNDLE.asItem());
		});

		// Builtin Resource Packs
		Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(SpectralDecorations.MOD_ID);
		modContainer.ifPresent(container -> ResourceManagerHelper.registerBuiltinResourcePack(SpectralDecorations.locate("spectral_decorations"), container, Component.nullToEmpty("Spectral Decorations Overrides"), ResourcePackActivationType.ALWAYS_ENABLED));
	}
	
}
