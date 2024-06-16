package de.dafuqs.spectral_decorations.mixin.client;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.util.*;
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
	
	@Inject(at = @At("HEAD"), method = "mobTick()V")
	private void spectral_decorations$pathingThePuppy(CallbackInfo ci) {
		KindlingEntity kindling = (KindlingEntity) (Object) this;
		
		if (kindling.isSubmergedIn(SpectrumFluidTags.LIQUID_CRYSTAL)) {
			Optional<DyeColor> coloredVariant = SpectralDecorationsKindlingVariants.getColor(getKindlingVariant());
			if (coloredVariant.isPresent()) {
				setKindlingVariant(KindlingVariant.DEFAULT);
			}
		}
	}
	
}