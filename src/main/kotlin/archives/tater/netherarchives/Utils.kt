package archives.tater.netherarchives

import net.minecraft.util.math.BlockPos

// iterateInSquare uses the same blockPos object and mutates it, so we need this to use proper collection operations
fun Iterable<BlockPos>.listCopy(): List<BlockPos> = map { it.mutableCopy() }
