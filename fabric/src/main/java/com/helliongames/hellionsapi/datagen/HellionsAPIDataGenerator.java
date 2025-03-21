package com.helliongames.hellionsapi.datagen;

import com.helliongames.hellionsapi.registration.holders.BlockDataHolder;
import com.helliongames.hellionsapi.registration.holders.ItemDataHolder;
import com.helliongames.hellionsapi.registration.holders.MobEffectDataHolder;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIBlockRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIEffectRegistry;
import com.helliongames.hellionsapi.registration.registries.HellionsAPIItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class HellionsAPIDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(HellionsAPIWorldGenProvider::new);
        pack.addProvider(HellionsAPIBlockTagProvider::new);
        pack.addProvider(HellionsAPIItemTagProvider::new);
        pack.addProvider(HellionsAPIBlockLootTableProvider::new);
        pack.addProvider(HellionsAPIModelProvider::new);
        pack.addProvider(HellionsAPILangProvider::new);
    }

    private static class HellionsAPIWorldGenProvider extends FabricDynamicRegistryProvider {
        public HellionsAPIWorldGenProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(HolderLookup.Provider registries, Entries entries) {
            entries.addAll(registries.lookupOrThrow(Registries.BIOME));
            entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
            entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
            entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_CARVER));
        }

        @Override
        public String getName() {
            return "World Gen";
        }
    }

    private static class HellionsAPILangProvider extends FabricLanguageProvider {
        protected HellionsAPILangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder builder) {
            // This handles all supplied block and item entries automatically
            for (HellionsAPIBlockRegistry module : HellionsAPIBlockRegistry.getModules()) {
                for (BlockDataHolder<?> blockDataHolder : module.getBlockRegistry().values()) {
                    if (blockDataHolder.hasTranslation()) {
                        builder.add(blockDataHolder.get(), blockDataHolder.getTranslation());
                    }

                    if (blockDataHolder.isGlass()) {
                        builder.add(blockDataHolder.getPaneBlock().get(), blockDataHolder.getTranslation() + " Pane");
                    }

                    for (Map.Entry<BlockDataHolder.Model, BlockDataHolder<?>> blocksetEntry : blockDataHolder.getBlocksets().entrySet()) {
                        if (blockDataHolder.hasTranslation()) {
                            builder.add(blocksetEntry.getValue().get(), blockDataHolder.getTranslation() + " " + blocksetEntry.getKey().getLang());
                        }
                    }
                }
            }

            for (HellionsAPIItemRegistry module : HellionsAPIItemRegistry.getModules()) {
                for (ItemDataHolder<?> itemDataHolder : module.getItemRegistry().values()) {
                    if (itemDataHolder.hasTranslation()) {
                        builder.add(itemDataHolder.get(), itemDataHolder.getTranslation());
                    }
                }
            }

            for (HellionsAPIEffectRegistry module : HellionsAPIEffectRegistry.getModules()) {
                for (Map.Entry<ResourceLocation, MobEffectDataHolder<?>> entry : module.getEffectRegistry().entrySet()) {
                    if (entry.getValue().hasTranslation()) {
                        builder.add(entry.getValue().get(), entry.getValue().getTranslation());

                        if (entry.getValue().hasPotion()) {
                            String id = entry.getKey().getPath();

                            builder.add("item.minecraft.potion.effect." + id, "Potion of " + entry.getValue().getTranslation());
                            builder.add("item.minecraft.splash_potion.effect." + id, "Splash Potion of " + entry.getValue().getTranslation());
                            builder.add("item.minecraft.lingering_potion.effect." + id, "Lingering Potion of " + entry.getValue().getTranslation());
                        }
                    }
                }
            }
        }
    }

    private static class HellionsAPIBlockTagProvider extends FabricTagProvider.BlockTagProvider {
        public HellionsAPIBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            for (Map.Entry<TagKey<Block>, List<BlockDataHolder<?>>> entry : BlockDataHolder.getBlockTags().entrySet()) {
                FabricTagProvider<Block>.FabricTagBuilder tagBuilder = getOrCreateTagBuilder(entry.getKey());

                entry.getValue().forEach(b -> tagBuilder.add(b.get()));
            }
        }
    }

    private static class HellionsAPIItemTagProvider extends FabricTagProvider.ItemTagProvider {
        public HellionsAPIItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            for (Map.Entry<TagKey<Item>, List<ItemDataHolder<?>>> entry : ItemDataHolder.getItemTags().entrySet()) {
                FabricTagProvider<Item>.FabricTagBuilder tagBuilder = getOrCreateTagBuilder(entry.getKey());

                entry.getValue().forEach(b -> tagBuilder.add(b.get()));
            }
        }
    }

    private static class HellionsAPIBlockLootTableProvider extends FabricBlockLootTableProvider {
        protected HellionsAPIBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            for (HellionsAPIBlockRegistry module : HellionsAPIBlockRegistry.getModules()) {
                for (BlockDataHolder<?> blockDataHolder : module.getBlockRegistry().values()) {
                    for (BlockDataHolder<?> blocksetHolder : blockDataHolder.getBlocksets().values()) {
                        if (blocksetHolder.hasModel()) {
                            if (Objects.requireNonNull(blockDataHolder.getModel()) == BlockDataHolder.Model.SLAB) {
                                add(blockDataHolder.get(), createSlabItemTable(blockDataHolder.get()));
                            }
                        }
                    }

                    if (blockDataHolder.isGlass()) {
                        add(blockDataHolder.get(), createSilkTouchOnlyTable(blockDataHolder.get()));
                        add(blockDataHolder.getPaneBlock().get(), createSilkTouchOnlyTable(blockDataHolder.getPaneBlock().get()));
                    } else if (blockDataHolder.hasModel()) {
                        switch (blockDataHolder.getModel()) {
                            case SLAB -> {
                                add(blockDataHolder.get(), createSlabItemTable(blockDataHolder.get()));
                                continue;
                            }
                            case DOOR -> {
                                add(blockDataHolder.get(), createDoorTable(blockDataHolder.get()));
                                continue;
                            }
                        }
                    }

                    if (blockDataHolder.hasDrop()) {
                        if (blockDataHolder.getDropCount() == null)
                            add(blockDataHolder.get(), createSilkTouchOnlyTable(blockDataHolder.getDrop().get()));
                        else
                            add(blockDataHolder.get(), createSingleItemTable(blockDataHolder.getDrop().get(), blockDataHolder.getDropCount()));
                    }

                    for (Map.Entry<BlockDataHolder.Model, BlockDataHolder<?>> entry : blockDataHolder.getBlocksets().entrySet()) {
                        switch (entry.getKey()) {
                            case SLAB -> {
                                add(entry.getValue().get(), createSlabItemTable(entry.getValue().get()));
                            }
                            case DOOR -> {
                                add(entry.getValue().get(), createDoorTable(entry.getValue().get()));
                            }
                            default -> {
                                add(entry.getValue().get(), createSingleItemTable(entry.getValue().get()));
                            }
                        }
                    }
                }
            }
        }
    }

    private static class HellionsAPIModelProvider extends FabricModelProvider {
        public HellionsAPIModelProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators generator) {
            for (HellionsAPIBlockRegistry module : HellionsAPIBlockRegistry.getModules()) {
                for (BlockDataHolder<?> blockDataHolder : module.getBlockRegistry().values()) {
                    if (blockDataHolder.getBlocksets().isEmpty()) {

                        if (blockDataHolder.isGlass()) {
                            generator.createGlassBlocks(blockDataHolder.get(), blockDataHolder.getPaneBlock().get());
                        } else if (blockDataHolder.hasModel()) {
                            switch (blockDataHolder.getModel()) {
                                case CUBE -> generator.createTrivialCube(blockDataHolder.get());
                                case PILLAR -> {
                                    var pillar = generator.woodProvider(blockDataHolder.get());
                                    pillar.log(blockDataHolder.get());
                                }
                                case ROTATABLE -> generator.createRotatedVariantBlock(blockDataHolder.get());
                                case CROSS ->
                                        generator.createCrossBlockWithDefaultItem(blockDataHolder.get(), BlockModelGenerators.TintState.NOT_TINTED);
                                case DOOR -> generator.createDoor(blockDataHolder.get());
                                case TRAPDOOR -> generator.createTrapdoor(blockDataHolder.get());
                            }
                        }
                    } else {
                        BlockModelGenerators.BlockFamilyProvider familyProvider = generator.family(blockDataHolder.get());
                        for (Map.Entry<BlockDataHolder.Model, BlockDataHolder<?>> entry : blockDataHolder.getBlocksets().entrySet()) {
                            switch (entry.getKey()) {
                                case STAIRS -> familyProvider.stairs(entry.getValue().get());
                                case SLAB -> familyProvider.slab(entry.getValue().get());
                                case WALL -> familyProvider.wall(entry.getValue().get());
                                case PRESSURE_PLATE -> familyProvider.pressurePlate(entry.getValue().get());
                                case BUTTON -> familyProvider.button(entry.getValue().get());
                                case FENCE -> familyProvider.fence(entry.getValue().get());
                                case FENCE_GATE -> familyProvider.fenceGate(entry.getValue().get());
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void generateItemModels(ItemModelGenerators generator) {
            for (HellionsAPIItemRegistry module : HellionsAPIItemRegistry.getModules()) {
                for (ItemDataHolder<?> itemDataHolder : module.getItemRegistry().values()) {
                    if (itemDataHolder.hasModel()) {
                        generator.generateFlatItem(itemDataHolder.get(), itemDataHolder.getModel());
                    }
                }
            }
        }
    }
}
