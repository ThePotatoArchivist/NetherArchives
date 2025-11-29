package archives.tater.netherarchives.mixin.client;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.renderer.block.model.VariantMutator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockModelGenerators.class)
public interface BlockStateModelGeneratorAccessor {
    @Accessor static PropertyDispatch<VariantMutator> getROTATION_FACING() { throw new AssertionError(); }
    @Accessor static PropertyDispatch<VariantMutator> getROTATIONS_COLUMN_WITH_FACING() { throw new AssertionError(); }
    @Accessor static PropertyDispatch<VariantMutator> getROTATION_TORCH() { throw new AssertionError(); }
    @Accessor static PropertyDispatch<VariantMutator> getROTATION_HORIZONTAL_FACING_ALT() { throw new AssertionError(); }
    @Accessor static PropertyDispatch<VariantMutator> getROTATION_HORIZONTAL_FACING() { throw new AssertionError(); }
}
