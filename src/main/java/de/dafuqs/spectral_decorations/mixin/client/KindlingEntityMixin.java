package de.dafuqs.spectral_decorations.mixin.client;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

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
				Level level = ((KindlingEntity) (Object) this).level();
				
				Registry<KindlingVariant> registry = level.registryAccess().registry(SpectrumRegistryKeys.KINDLING_VARIANT).get();
				Optional<Holder.Reference<KindlingVariant>> defaultVariant = registry.getHolder(KindlingVariant.DEFAULT);
				if (defaultVariant.isPresent()) {
					setKindlingVariant(defaultVariant.get());
				}
			}
		}
	}
	
}