package archives.tater.netherarchives.mixin.client;

import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.render.model.json.ModelVariantOperator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockStateModelGenerator.class)
public interface BlockStateModelGeneratorAccessor {
    @Accessor static BlockStateVariantMap<ModelVariantOperator> getNORTH_DEFAULT_ROTATION_OPERATIONS() { throw new AssertionError(); }
    @Accessor static BlockStateVariantMap<ModelVariantOperator> getUP_DEFAULT_ROTATION_OPERATIONS() { throw new AssertionError(); }
    @Accessor static BlockStateVariantMap<ModelVariantOperator> getEAST_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS() { throw new AssertionError(); }
    @Accessor static BlockStateVariantMap<ModelVariantOperator> getSOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS() { throw new AssertionError(); }
    @Accessor static BlockStateVariantMap<ModelVariantOperator> getNORTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS() { throw new AssertionError(); }
}