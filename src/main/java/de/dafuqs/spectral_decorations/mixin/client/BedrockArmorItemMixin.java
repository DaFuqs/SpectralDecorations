package de.dafuqs.spectral_decorations.mixin.client;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.items.armor.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Mixin(BedrockArmorItem.class)
public abstract class BedrockArmorItemMixin {
	
	@Inject(at = @At("HEAD"), method = "getArmorTexture(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
	private void spectral_decorations$modifyBedrockArmorColor(ItemStack stack, EquipmentSlot slot, CallbackInfoReturnable<ResourceLocation> cir) {
		Optional<DyeColor> color = BedrockArmorColorizer.getColor(stack);
		if (color.isPresent()) {
			String colorName = color.get().getSerializedName();
			cir.setReturnValue(SpectralDecorations.locate("textures/armor/bedrock_armor_" + colorName + ".png"));
		}
	}
	
	@Inject(at = @At("HEAD"), method = "getArmorTexture(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
	private void spectral_decorations$modifyBedrockArmorRenderLayer(ItemStack stack, EquipmentSlot slot, CallbackInfoReturnable<ResourceLocation> cir) {
		Optional<DyeColor> color = BedrockArmorColorizer.getColor(stack);
		if (color.isPresent()) {
			String colorName = color.get().getSerializedName();
			cir.setReturnValue(SpectralDecorations.locate("textures/armor/bedrock_armor_" + colorName + ".png"));
		}
	}
	
}