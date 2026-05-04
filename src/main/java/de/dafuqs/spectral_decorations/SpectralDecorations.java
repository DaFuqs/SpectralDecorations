package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.api.color.ItemColors;
import de.dafuqs.spectrum.api.interaction.EntityColorProcessorRegistry;
import de.dafuqs.spectrum.entity.SpectrumEntityTypes;
import de.dafuqs.spectrum.entity.variants.KindlingVariant;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class SpectralDecorations implements ModInitializer {
	
	public static final String MOD_ID = "spectral-decorations";
	
	@Override
	public void onInitialize() {
		SpectralDecorationsBlocks.register();
		SpectralDecorationsItems.register();
		SpectralDecorationsItemGroups.register();
		SpectralDecorationsRecipeTypes.registerRecipeSerializers();

		ServerLifecycleEvents.SERVER_STARTING.register(server -> delayedLoad());
	}

	private static boolean delayedLoadTriggered = false;

	public static void delayedLoad() {
		if (delayedLoadTriggered) {
			return;
		}
		delayedLoadTriggered = true;

		for (SpectralDecorationsBlocks.PropertyHolder entry : SpectralDecorationsBlocks.holder) {
			ItemColors.ITEM_COLORS.registerColorMapping(entry.item(), entry.color());
		}
		for (SpectralDecorationsBlocks.PropertyHolder entry : SpectralDecorationsBlocks.holder) {
			if (entry.type() == SpectralDecorationsBlocks.Type.AMPHORA) {
				SpectrumBlockEntities.AMPHORA.addSupportedBlock(entry.block());
			}
		}

		// Register only after server is started to not cause weird load order behavior
		// Server side is enough, so we are doing it here
		EntityColorProcessorRegistry.register(SpectrumEntityTypes.KINDLING, (entity, dyeColor, player) -> {
			if (dyeColor.isEmpty()) {
				return false;
			}

			@Nullable Holder<KindlingVariant> coloredVariant = SpectralDecorationsKindlingVariants.getColoredVariant(entity.level(), dyeColor.get());
			if (coloredVariant == null) {
				return false;
			}
			if (entity.getKindlingVariant() == coloredVariant) {
				return false;
			}
			entity.setKindlingVariant(coloredVariant);
			return true;
		});
	}

	public static ResourceLocation locate(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}
	
}
