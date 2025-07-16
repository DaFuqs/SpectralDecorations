package de.dafuqs.spectral_decorations.mixin.client;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.items.armor.*;
import net.fabricmc.api.*;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Environment(EnvType.CLIENT)
@Mixin(BedrockArmorItem.class)
public abstract class BedrockArmorItemMixin {
	
	@Inject(at = @At("HEAD"), method = "getArmorTexture(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
	private void spectral_decorations$modifyBedrockArmorColor(ItemStack stack, EquipmentSlot slot, CallbackInfoReturnable<ResourceLocation> cir) {
		// feet do not have any color and therefore do use the default renderer
		Optional<DyeColor> color = BedrockArmorColorizer.getColor(stack);
		if (color.isPresent()) {
			String colorName = color.get().getSerializedName();
			cir.setReturnValue(SpectralDecorations.locate("textures/armor/bedrock_armor_" + colorName + ".png"));
		}
	}
	
	@Inject(at = @At("HEAD"), method = "getRenderLayer(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/renderer/RenderType;", cancellable = true)
	private void spectral_decorations$modifyBedrockArmorRenderLayer(ItemStack stack, CallbackInfoReturnable<RenderType> cir) {
		Optional<DyeColor> color = BedrockArmorColorizer.getColor(stack);
		if (color.isPresent()) {
			String colorName = color.get().getSerializedName();
			ResourceLocation renderLayerId = SpectralDecorations.locate("textures/armor/bedrock_armor_" + colorName + ".png");
			cir.setReturnValue(RenderType.entitySolid(renderLayerId));
		}
	}
	
}