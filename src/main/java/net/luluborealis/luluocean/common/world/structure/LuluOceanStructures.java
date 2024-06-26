package net.luluborealis.luluocean.common.world.structure;

import corgitaco.corgilib.math.blendingfunction.BlendingFunction;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.biome.LuluOceanBiomeTags;
import net.luluborealis.luluocean.common.world.feature.config.NoisySphereConfig;
import net.luluborealis.luluocean.common.world.feature.features.LuluOceanStructurePlacedFeatures;
import net.luluborealis.luluocean.common.world.structure.arch.ArchConfiguration;
import net.luluborealis.luluocean.common.world.structure.arch.ArchStructure;
import net.luluborealis.luluocean.mixin.access.StructuresAccess;
import net.minecraft.Util;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.List;
import java.util.Map;


public class LuluOceanStructures {

    public static final Map<ResourceKey<Structure>, StructureFactory> STRUCTURE_FACTORIES = new Reference2ObjectOpenHashMap<>();

    public static final ResourceKey<Structure> OVERGROWN_STONE_ARCH = register("overgrown_stone_arch", (structureFactoryBootstapContext) -> {
        HolderGetter<PlacedFeature> lookup = structureFactoryBootstapContext.lookup(Registries.PLACED_FEATURE);

        return new ArchStructure(structure(structureFactoryBootstapContext.lookup(Registries.BIOME).getOrThrow(LuluOceanBiomeTags.HAS_OVERGROWN_STONE_ARCH), GenerationStep.Decoration.RAW_GENERATION, TerrainAdjustment.NONE), Util.make(new ArchConfiguration.Builder(), builder -> {
            WeightedStateProvider blockProvider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(Blocks.STONE.defaultBlockState(), 6)
                    .add(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 3)
                    .add(Blocks.ANDESITE.defaultBlockState(), 1)
            );
            builder.withSphereConfig(new NoisySphereConfig.Builder()
                    .withRadiusSettings(
                            new NoisySphereConfig.RadiusSettings(UniformInt.of(10, 15), UniformInt.of(10, 15), 0, UniformInt.of(10, 15))
                    ).withBlockProvider(
                            blockProvider
                    ).withNoiseFrequency(0.1F)
                    .withTopBlockProvider(
                            blockProvider
                    ).withSpawningFeatures(List.of(lookup.getOrThrow(LuluOceanStructurePlacedFeatures.ARCH_MOSS_PATCH), lookup.getOrThrow(LuluOceanStructurePlacedFeatures.ARCH_MOSS_PATCH_CEILING)))
                    .build()
            );
            builder.withMatchingBlendingFunctionChance(ConstantFloat.of(0.2F));
            builder.withPercentageDestroyed(ConstantFloat.of(0));
            builder.withLength(UniformInt.of(50, 100));
            builder.withHeight(UniformInt.of(50, 100));
            builder.withBlendingFunctionType(SimpleWeightedRandomList.<BlendingFunction>builder().add(BlendingFunction.EaseOutQuint.INSTANCE, 5).add(BlendingFunction.EaseOutElastic.INSTANCE, 2).add(BlendingFunction.EaseOutBounce.INSTANCE, 5).add(BlendingFunction.EaseOutCubic.INSTANCE, 5).build());
        }).build());
    });

    public static final ResourceKey<Structure> STONE_ARCH = register("stone_arch", (structureFactoryBootstapContext) -> new ArchStructure(structure(structureFactoryBootstapContext.lookup(Registries.BIOME).getOrThrow(LuluOceanBiomeTags.HAS_STONE_ARCH), GenerationStep.Decoration.RAW_GENERATION, TerrainAdjustment.NONE), Util.make(new ArchConfiguration.Builder(), builder -> {
        WeightedStateProvider blockProvider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(Blocks.STONE.defaultBlockState(), 4)
                .add(Blocks.ANDESITE.defaultBlockState(), 1)
        );
        builder.withSphereConfig(new NoisySphereConfig.Builder()
                .withRadiusSettings(
                        new NoisySphereConfig.RadiusSettings(UniformInt.of(10, 15), UniformInt.of(10, 15), 0, UniformInt.of(10, 15))
                ).withBlockProvider(
                        blockProvider
                ).withNoiseFrequency(0.1F)
                .withTopBlockProvider(
                        blockProvider
                ).withSpawningFeatures(List.of(
                ))
                .build()
        );
        builder.withMatchingBlendingFunctionChance(ConstantFloat.of(0.2F));
        builder.withPercentageDestroyed(ConstantFloat.of(0));
        builder.withLength(UniformInt.of(50, 100));
        builder.withHeight(UniformInt.of(50, 100));
        builder.withBlendingFunctionType(SimpleWeightedRandomList.<BlendingFunction>builder().add(BlendingFunction.EaseOutQuint.INSTANCE, 5).add(BlendingFunction.EaseOutElastic.INSTANCE, 2).add(BlendingFunction.EaseOutBounce.INSTANCE, 5).add(BlendingFunction.EaseOutCubic.INSTANCE, 5).build());
    }).build()));

    private static ResourceKey<Structure> register(String id, StructureFactory factory) {
        ResourceKey<Structure> structureSetResourceKey = ResourceKey.create(Registries.STRUCTURE, LuluOcean.createLocation(id));
        STRUCTURE_FACTORIES.put(structureSetResourceKey, factory);
        return structureSetResourceKey;
    }

    @SuppressWarnings("unused")
    private static Structure.StructureSettings structure(HolderSet<Biome> tag, TerrainAdjustment adj) {
        return StructuresAccess.structure(tag, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, adj);
    }

    @SuppressWarnings("SameParameterValue")
    private static Structure.StructureSettings structure(HolderSet<Biome> tag, GenerationStep.Decoration decoration, TerrainAdjustment adj) {
        return StructuresAccess.structure(tag, Map.of(), decoration, adj);
    }

    public static void loadClass() {
    }

    @FunctionalInterface
    public interface StructureFactory {
        Structure generate(BootstapContext<Structure> structureFactoryBootstapContext);
    }
}