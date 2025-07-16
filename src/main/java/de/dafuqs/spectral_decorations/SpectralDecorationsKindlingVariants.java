package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SpectralDecorationsKindlingVariants {
	
	public static @Nullable Holder<KindlingVariant> getColoredVariant(Level level, DyeColor color) {
		Registry<KindlingVariant> kindlingVariantRegistry = level.registryAccess().registry(SpectrumRegistryKeys.KINDLING_VARIANT).get();
		Optional<Holder.Reference<KindlingVariant>> variant = kindlingVariantRegistry.getHolder(SpectralDecorations.locate(color.getName()));
		return variant.orElse(null);
	}
	
}
