package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.*;

public class SpectralDecorationsKindlingVariants {

	public static Map<DyeColor, KindlingVariant> COLORED_VARIANTS = new HashMap<>();
	
	public static void register() {
		for (DyeColor color : DyeColor.values()) {
			String s = color.asString();
			COLORED_VARIANTS.put(color, register(s,
					"textures/entity/kindling/" + s + ".png",
					"textures/entity/kindling/" + s + "_blinking.png",
					"textures/entity/kindling/" + s + "_angry.png",
					"textures/entity/kindling/" + s + "_clipped.png",
					"textures/entity/kindling/" + s + "_blinking_clipped.png",
					"textures/entity/kindling/" + s + "_angry_clipped.png")
			);
		}
	}
	
	private static KindlingVariant register(String name, String defaultTexture, String blinkingTexture, String angryTexture, String clippedTexture, String blinkingClippedTexture, String angryClippedTexture) {
		return Registry.register(SpectrumRegistries.KINDLING_VARIANT,
				SpectralDecorations.locate(name),
				new KindlingVariant(SpectralDecorations.locate(defaultTexture), SpectralDecorations.locate(blinkingTexture), SpectralDecorations.locate(angryTexture), SpectralDecorations.locate(clippedTexture), SpectralDecorations.locate(blinkingClippedTexture), SpectralDecorations.locate(angryClippedTexture))
		);
	}

	public static KindlingVariant getColoredVariant(DyeColor color) {
		return COLORED_VARIANTS.get(color);
	}
	
}
