package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.entity.*;
import de.dafuqs.spectrum.registries.color.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.item.v1.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.*;
import net.fabricmc.fabric.api.resource.*;
import net.fabricmc.loader.api.*;
import net.minecraft.client.item.*;
import net.minecraft.item.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import oshi.util.tuples.*;

import java.util.*;

public class SpectralDecorations implements ModInitializer {
	
	public static final String MOD_ID = "spectral-decorations";

	@Override
	public void onInitialize() {
		SpectralDecorationsBlocks.register();
		SpectralDecorationsItemGroups.register();
		SpectralDecorationsRecipeTypes.registerRecipeSerializers();
		//SpectralDecorationsKindlingVariants.register();
		
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			for (SpectralDecorationsBlocks.PropertyHolder entry : SpectralDecorationsBlocks.items) {
				ItemColors.ITEM_COLORS.registerColorMapping(entry.item(), entry.color());
			}
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
		if (modContainer.isPresent()) {
			ResourceManagerHelper.registerBuiltinResourcePack(locate("spectral_decorations"), modContainer.get(), Text.of("Spectral Decorations Overrides"), ResourcePackActivationType.DEFAULT_ENABLED);
		}
		
		/*EntityColorProcessorRegistry.register(SpectrumEntityTypes.KINDLING, (entity, dyeColor) -> {
			if (entity.getVariant() == dyeColor) {
				return false;
			}
			entity.setVariant(dyeColor);
			return true;
		});*/
	}
	
	public static Identifier locate(String name) {
		return new Identifier(MOD_ID, name);
	}
	
}
