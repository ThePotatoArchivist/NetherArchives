package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.client.duck.SoulGlassRevealed;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements SoulGlassRevealed {
    @Unique
    private boolean revealed = false;

    @Override
    public boolean netherarchives$isRevealed() {
        return revealed;
    }

    @Override
    public void netherarchives$setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}
