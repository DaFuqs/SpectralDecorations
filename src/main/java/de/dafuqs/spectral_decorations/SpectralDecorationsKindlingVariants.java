package de.dafuqs.spectral_decorations;

import com.google.common.collect.*;
import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.*;

public class SpectralDecorationsKindlingVariants {

	public static BiMap<DyeColor, KindlingVariant> COLORED_VARIANTS = EnumHashBiMap.create(DyeColor.class);
	
	public static void register() {
		for (DyeColor color : ColorHelper.VANILLA_DYE_COLORS) {
			String s = color.asString();
			COLORED_VARIANTS.put(color, register(s,
					"textures/entity/kindling/" + s + ".png",
					"textures/entity/kindling/" + s + "_blinking.png",
					"textures/entity/kindling/" + s + "_angry.png",
					"textures/entity/kindling/" + s + "_clipped.png",
					"textures/entity/kindling/" + s + "_clipped_blinking.png",
							"textures/entity/kindling/" + s + "_angry_clipped.png",
							"gameplay/kindling_clipping/" + s
					)
			);
		}
	}
	
	private static KindlingVariant register(String name, String defaultTexture, String blinkingTexture, String angryTexture, String clippedTexture, String blinkingClippedTexture, String angryClippedTexture, String lootTable) {
		return Registry.register(SpectrumRegistries.KINDLING_VARIANT,
				SpectralDecorations.locate(name),
				new KindlingVariant(
						SpectralDecorations.locate(defaultTexture),
						SpectralDecorations.locate(blinkingTexture),
						SpectralDecorations.locate(angryTexture),
						SpectralDecorations.locate(clippedTexture),
						SpectralDecorations.locate(blinkingClippedTexture),
						SpectralDecorations.locate(angryClippedTexture),
						SpectralDecorations.locate(lootTable)
				)
		);
	}

	public static KindlingVariant getColoredVariant(DyeColor color) {
		return COLORED_VARIANTS.get(color);
	}

	public static Optional<DyeColor> getColor(KindlingVariant variant) {
		return Optional.ofNullable(COLORED_VARIANTS.inverse().getOrDefault(variant, null));
	}
	
}
