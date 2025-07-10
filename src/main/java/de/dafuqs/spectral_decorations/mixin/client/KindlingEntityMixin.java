package de.dafuqs.spectral_decorations.mixin.client;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Mixin(KindlingEntity.class)
public abstract class KindlingEntityMixin {
	
	@Shadow
	public abstract KindlingVariant getKindlingVariant();
	
	@Shadow
	public abstract void setKindlingVariant(KindlingVariant variant);
	
	@Inject(at = @At("HEAD"), method = "aiStep()V")
	private void spectral_decorations$bathingThePuppy(CallbackInfo ci) {
		KindlingEntity kindling = (KindlingEntity) (Object) this;
		
		if (kindling.isEyeInFluid(SpectrumFluidTags.LIQUID_CRYSTAL)) {
			Optional<DyeColor> coloredVariant = SpectralDecorationsKindlingVariants.getColor(getKindlingVariant());
			if (coloredVariant.isPresent()) {
				setKindlingVariant(KindlingVariant.DEFAULT);
			}
		}
	}
	
}