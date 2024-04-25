package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.registries.color.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.item.v1.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.*;
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
		
		ItemTooltipCallback.EVENT.register(new ItemTooltipCallback() {
			@Override
			public void getTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
				if (stack.isIn(SpectralDecorationsItemTags.BEDROCK_ARMOR)) {
					Optional<DyeColor> optionalColor = BedrockArmorColorizer.getColor(stack);
					if (optionalColor.isPresent()) {
						DyeColor c = optionalColor.get();
						Text t = Text.translatable("tooltip.spectral-decorations.colored").append(Text.translatable("color.minecraft." + c.asString()));
						lines.add(1, t);
					}
				}
			}
		});
		
	}
	
	public static Identifier locate(String name) {
		return new Identifier(MOD_ID, name);
	}
	
}
