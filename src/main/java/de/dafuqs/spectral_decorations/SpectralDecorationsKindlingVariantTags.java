package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.tags.*;

public class SpectralDecorationsKindlingVariantTags {
	
	public static final TagKey<KindlingVariant> WASHES_TO_DEFAULT = of("washes_to_default");
	
	private static TagKey<KindlingVariant> of(String name) {
		return TagKey.create(SpectrumRegistryKeys.KINDLING_VARIANT, SpectralDecorations.locate(name));
	}
	
}
