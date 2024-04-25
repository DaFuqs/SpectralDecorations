package de.dafuqs.spectral_decorations.mixin;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.items.armor.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

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
	
	/*@Inject(at = @At("HEAD"), method = "Lde/dafuqs/spectrum/items/armor/BedrockArmorItem;getRenderLayer(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/RenderLayer;", cancellable = true)
	private void spectral_decorations$modifyBedrockArmorRenderLayer(ItemStack stack, CallbackInfoReturnable<Identifier> cir) {
		Optional<DyeColor> color = BedrockArmorColorizer.getColor(stack);
		if (color.isPresent()) {
			String colorString = color.get().asString();
			cir.setReturnValue(SpectralDecorations.locate("textures/armor/bedrock_armor_main_" + colorString + ".png"));
		}
	}*/
	
}