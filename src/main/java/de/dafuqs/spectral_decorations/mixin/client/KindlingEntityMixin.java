package de.dafuqs.spectral_decorations.mixin.client;

import de.dafuqs.spectral_decorations.SpectralDecorationsKindlingVariantTags;
import de.dafuqs.spectrum.entity.entity.KindlingEntity;
import de.dafuqs.spectrum.entity.variants.KindlingVariant;
import de.dafuqs.spectrum.registries.SpectrumFluidTags;
import de.dafuqs.spectrum.registries.SpectrumRegistryKeys;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(KindlingEntity.class)
public abstract class KindlingEntityMixin {
	
	@Shadow
	public abstract Holder<KindlingVariant> getKindlingVariant();
	
	@Shadow
	public abstract void setKindlingVariant(Holder<KindlingVariant> variant);
	
	@Inject(at = @At("HEAD"), method = "aiStep()V")
	private void spectral_decorations$bathingThePuppy(CallbackInfo ci) {
		KindlingEntity kindling = (KindlingEntity) (Object) this;
		
		if (!kindling.isEyeInFluid(SpectrumFluidTags.LIQUID_CRYSTAL)) {
			if (getKindlingVariant().is(SpectralDecorationsKindlingVariantTags.WASHES_TO_DEFAULT)) {
				Level level = kindling.level();
				
				Registry<KindlingVariant> registry = level.registryAccess().registry(SpectrumRegistryKeys.KINDLING_VARIANT).get();
				Optional<Holder.Reference<KindlingVariant>> defaultVariant = registry.getHolder(KindlingVariant.DEFAULT);
				if (defaultVariant.isPresent()) {
					setKindlingVariant(defaultVariant.get());
				}
			}
		}
	}
	
}