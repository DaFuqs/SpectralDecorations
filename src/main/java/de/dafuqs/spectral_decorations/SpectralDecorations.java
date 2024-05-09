package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.api.interaction.*;
import de.dafuqs.spectrum.entity.*;
import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.registries.color.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.item.v1.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.*;
import net.fabricmc.fabric.api.resource.*;
import net.fabricmc.loader.api.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class SpectralDecorations implements ModInitializer {
	
	public static final String MOD_ID = "spectral-decorations";

	@Override
	public void onInitialize() {
		SpectralDecorationsBlocks.register();
		SpectralDecorationsItems.register();
		SpectralDecorationsItemGroups.register();
		SpectralDecorationsRecipeTypes.registerRecipeSerializers();
		SpectralDecorationsKindlingVariants.register();
		
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			for (SpectralDecorationsBlocks.PropertyHolder entry : SpectralDecorationsBlocks.items) {
				ItemColors.ITEM_COLORS.registerColorMapping(entry.item(), entry.color());
			}

			// Register only after server is started to not cause weird load order behavior
			// Server side is enough, so we are doing it here
			EntityColorProcessorRegistry.register(SpectrumEntityTypes.KINDLING, (entity, dyeColor) -> {
				KindlingVariant coloredVariant = SpectralDecorationsKindlingVariants.getColoredVariant(dyeColor);
				if (entity.getKindlingVariant() == coloredVariant) {
					return false;
				}
				entity.setKindlingVariant(coloredVariant);
				return true;
			});
		});
		
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
		
		// Builtin Resource Packs
		Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(MOD_ID);
		modContainer.ifPresent(container -> ResourceManagerHelper.registerBuiltinResourcePack(locate("spectral_decorations"), container, Text.of("Spectral Decorations Overrides"), ResourcePackActivationType.DEFAULT_ENABLED));
	}
	
	public static Identifier locate(String name) {
		return new Identifier(MOD_ID, name);
	}
	
}
