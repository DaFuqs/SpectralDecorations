package de.dafuqs.spectral_decorations.mixin.client;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.items.armor.*;
import net.fabricmc.api.*;
import net.minecraft.client.render.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Environment(EnvType.CLIENT)
@Mixin(BedrockArmorItem.class)
public abstract class BedrockArmorItemMixin {
	
	@Inject(at = @At("HEAD"), method = "getArmorTexture(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void spectral_decorations$modifyBedrockArmorColor(ItemStack stack, EquipmentSlot slot, CallbackInfoReturnable<Identifier> cir) {
		// feet do not have any color and therefore do use the default renderer
		if (slot != EquipmentSlot.FEET) {
			Optional<DyeColor> color = BedrockArmorColorizer.getColor(stack);
			if (color.isPresent()) {
				String colorString = color.get().asString();
				cir.setReturnValue(SpectralDecorations.locate("textures/armor/bedrock_armor_main_" + colorString + ".png"));
			}
		}
	}
	
	@Inject(at = @At("HEAD"), method = "getRenderLayer(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/RenderLayer;", cancellable = true)
	private void spectral_decorations$modifyBedrockArmorRenderLayer(ItemStack stack, CallbackInfoReturnable<RenderLayer> cir) {
		Optional<DyeColor> color = BedrockArmorColorizer.getColor(stack);
		if (color.isPresent()) {
			String colorString = color.get().asString();
			Identifier renderLayerId = SpectralDecorations.locate("textures/armor/bedrock_armor_main_" + colorString + ".png");
			cir.setReturnValue(RenderLayer.getEntitySolid(renderLayerId));
		}
	}
	
}