package com.mcupdater.procenhance.setup;

import com.mcupdater.procenhance.blocks.autoharvester.HarvesterBlock;
import com.mcupdater.procenhance.blocks.autoharvester.HarvesterEntity;
import com.mcupdater.procenhance.blocks.autoharvester.HarvesterMenu;
import com.mcupdater.procenhance.blocks.battery.*;
import com.mcupdater.procenhance.blocks.biogenerator.*;
import com.mcupdater.procenhance.blocks.buffer.BufferBlock;
import com.mcupdater.procenhance.blocks.buffer.BufferEntity;
import com.mcupdater.procenhance.blocks.buffer.BufferMenu;
import com.mcupdater.procenhance.blocks.copper_wire.CopperWireBlock;
import com.mcupdater.procenhance.blocks.copper_wire.CopperWireEntity;
import com.mcupdater.procenhance.blocks.crude_generator.CrudeGeneratorBlock;
import com.mcupdater.procenhance.blocks.crude_generator.CrudeGeneratorEntity;
import com.mcupdater.procenhance.blocks.crude_generator.CrudeGeneratorMenu;
import com.mcupdater.procenhance.blocks.deconstructor.DeconstructorBlock;
import com.mcupdater.procenhance.blocks.deconstructor.DeconstructorEntity;
import com.mcupdater.procenhance.blocks.deconstructor.DeconstructorMenu;
import com.mcupdater.procenhance.blocks.dehydrator.DehydratorBlock;
import com.mcupdater.procenhance.blocks.dehydrator.DehydratorEntity;
import com.mcupdater.procenhance.blocks.dehydrator.DehydratorMenu;
import com.mcupdater.procenhance.blocks.disenchanter.DisenchanterBlock;
import com.mcupdater.procenhance.blocks.disenchanter.DisenchanterEntity;
import com.mcupdater.procenhance.blocks.disenchanter.DisenchanterMenu;
import com.mcupdater.procenhance.blocks.furnace.*;
import com.mcupdater.procenhance.blocks.generator.*;
import com.mcupdater.procenhance.blocks.grinder.*;
import com.mcupdater.procenhance.blocks.hydrator.HydratorBlock;
import com.mcupdater.procenhance.blocks.hydrator.HydratorEntity;
import com.mcupdater.procenhance.blocks.hydrator.HydratorMenu;
import com.mcupdater.procenhance.blocks.lava_generator.*;
import com.mcupdater.procenhance.blocks.miner.*;
import com.mcupdater.procenhance.blocks.planter.PlanterBlock;
import com.mcupdater.procenhance.blocks.planter.PlanterEntity;
import com.mcupdater.procenhance.blocks.planter.PlanterMenu;
import com.mcupdater.procenhance.blocks.pump.*;
import com.mcupdater.procenhance.blocks.sawmill.SawmillBlock;
import com.mcupdater.procenhance.blocks.sawmill.SawmillEntity;
import com.mcupdater.procenhance.blocks.sawmill.SawmillMenu;
import com.mcupdater.procenhance.blocks.soilmanager.SoilManagerBlock;
import com.mcupdater.procenhance.blocks.soilmanager.SoilManagerEntity;
import com.mcupdater.procenhance.blocks.soilmanager.SoilManagerMenu;
import com.mcupdater.procenhance.blocks.solidifier.*;
import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterBlock;
import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterEntity;
import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterMenu;
import com.mcupdater.procenhance.blocks.tank.*;
import com.mcupdater.procenhance.blocks.autopackager.*;
import com.mcupdater.procenhance.items.autopackager.*;
import com.mcupdater.procenhance.loot.functions.RetainEnchantmentsFunction;
import com.mcupdater.procenhance.loot.functions.RetainEnergyFunction;
import com.mcupdater.procenhance.loot.functions.RetainFluidFunction;
import com.mcupdater.procenhance.recipe.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.*;

import java.util.function.Supplier;

import static com.mcupdater.procenhance.ProcessEnhancement.MODID;

public class Registration {
    public static final DeferredRegister.Blocks MACHINES = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Blocks BATTERIES = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Blocks TANKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Blocks MINERS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister.Items PATTERNS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, MODID);
    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, MODID);
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MODID);

    public static void init(IEventBus modEventBus) {
        MACHINES.register(modEventBus);
        BATTERIES.register(modEventBus);
        TANKS.register(modEventBus);
        MINERS.register(modEventBus);
        BLOCKS.register(modEventBus);
        BLOCK_ITEMS.register(modEventBus);
        ITEMS.register(modEventBus);
        PATTERNS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        MENUS.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        CONDITION_CODECS.register(modEventBus);
        LOOT_FUNCTION_TYPES.register(modEventBus);
        DATA_COMPONENTS.register(modEventBus);
    }

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ConfigCondition>> CONFIG_CONDITION = CONDITION_CODECS.register("config", () -> ConfigCondition.CODEC);

    public static final DeferredBlock<CrudeGeneratorBlock> CRUDEGENERATOR_BLOCK = MACHINES.register("crude_generator", () -> new CrudeGeneratorBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .strength(5.0f)
            .requiresCorrectToolForDrops()
    ));
    public static final DeferredItem<Item> CRUDEGENERATOR_BLOCKITEM = BLOCK_ITEMS.register("crude_generator", () -> new BlockItem(CRUDEGENERATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CrudeGeneratorEntity>> CRUDEGENERATOR_ENTITY = BLOCK_ENTITIES.register("crude_generator", () -> BlockEntityType.Builder.of(CrudeGeneratorEntity::new, CRUDEGENERATOR_BLOCK.get()).build(null));
    public static final Supplier<MenuType<CrudeGeneratorMenu>> CRUDEGENERATOR_MENU = MENUS.register("crude_generator", () -> IMenuTypeExtension.create(CrudeGeneratorMenu::factory));

    public static final DeferredBlock<GeneratorBlockT1> BASICGENERATOR_BLOCK = MACHINES.register("basic_generator", () -> new GeneratorBlockT1(GeneratorBlock.defaultProperties()));
    public static final DeferredItem<Item> BASICGENERATOR_BLOCKITEM = BLOCK_ITEMS.register("basic_generator", () -> new BlockItem(BASICGENERATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GeneratorEntityT1>> GENERATORT1_ENTITY = BLOCK_ENTITIES.register("basic_generator", () -> BlockEntityType.Builder.of(GeneratorEntityT1::new, BASICGENERATOR_BLOCK.get()).build(null));
    public static final DeferredBlock<GeneratorBlockT2> INTERGENERATOR_BLOCK = MACHINES.register("intermediate_generator", () -> new GeneratorBlockT2(GeneratorBlock.defaultProperties()));
    public static final DeferredItem<Item> INTERGENERATOR_BLOCKITEM = BLOCK_ITEMS.register("intermediate_generator", () -> new BlockItem(INTERGENERATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GeneratorEntityT2>> GENERATORT2_ENTITY = BLOCK_ENTITIES.register("intermediate_generator", () -> BlockEntityType.Builder.of(GeneratorEntityT2::new, INTERGENERATOR_BLOCK.get()).build(null));
    public static final DeferredBlock<GeneratorBlockT3> ADVGENERATOR_BLOCK = MACHINES.register("advanced_generator", () -> new GeneratorBlockT3(GeneratorBlock.defaultProperties()));
    public static final DeferredItem<Item> ADVGENERATOR_BLOCKITEM = BLOCK_ITEMS.register("advanced_generator", () -> new BlockItem(ADVGENERATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GeneratorEntityT3>> GENERATORT3_ENTITY = BLOCK_ENTITIES.register("advanced_generator", () -> BlockEntityType.Builder.of(GeneratorEntityT3::new, ADVGENERATOR_BLOCK.get()).build(null));
    public static final DeferredBlock<GeneratorBlockT4> INDGENERATOR_BLOCK = MACHINES.register("industrial_generator", () -> new GeneratorBlockT4(GeneratorBlock.defaultProperties()));
    public static final DeferredItem<Item> INDGENERATOR_BLOCKITEM = BLOCK_ITEMS.register("industrial_generator", () -> new BlockItem(INDGENERATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GeneratorEntityT4>> GENERATORT4_ENTITY = BLOCK_ENTITIES.register("industrial_generator", () -> BlockEntityType.Builder.of(GeneratorEntityT4::new, INDGENERATOR_BLOCK.get()).build(null));

    public static final Supplier<MenuType<GeneratorMenu>> GENERATOR_MENU = MENUS.register("generator", () -> IMenuTypeExtension.create(GeneratorMenu::factory));

    public static final DeferredBlock<LavaGeneratorBlockT1> BASICLAVAGENERATOR_BLOCK = MACHINES.register("basic_lava_generator", () -> new LavaGeneratorBlockT1(LavaGeneratorBlock.defaultProperties()));
    public static final DeferredItem<Item> BASICLAVALGENERATOR_BLOCKITEM = BLOCK_ITEMS.register("basic_lava_generator", () -> new BlockItem(BASICLAVAGENERATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LavaGeneratorEntityT1>> LAVAGENERATORT1_ENTITY = BLOCK_ENTITIES.register("basic_lava_generator", () -> BlockEntityType.Builder.of(LavaGeneratorEntityT1::new, BASICLAVAGENERATOR_BLOCK.get()).build(null));
    public static final DeferredBlock<LavaGeneratorBlockT2> INTERLAVAGENERATOR_BLOCK = MACHINES.register("intermediate_lava_generator", () -> new LavaGeneratorBlockT2(LavaGeneratorBlock.defaultProperties()));
    public static final DeferredItem<Item> INTERLAVAGENERATOR_BLOCKITEM = BLOCK_ITEMS.register("intermediate_lava_generator", () -> new BlockItem(INTERLAVAGENERATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LavaGeneratorEntityT2>> LAVAGENERATORT2_ENTITY = BLOCK_ENTITIES.register("intermediate_lava_generator", () -> BlockEntityType.Builder.of(LavaGeneratorEntityT2::new, INTERLAVAGENERATOR_BLOCK.get()).build(null));
    public static final DeferredBlock<LavaGeneratorBlockT3> ADVLAVAGENERATOR_BLOCK = MACHINES.register("advanced_lava_generator", () -> new LavaGeneratorBlockT3(LavaGeneratorBlock.defaultProperties()));
    public static final DeferredItem<Item> ADVLAVAGENERATOR_BLOCKITEM = BLOCK_ITEMS.register("advanced_lava_generator", () -> new BlockItem(ADVLAVAGENERATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LavaGeneratorEntityT3>> LAVAGENERATORT3_ENTITY = BLOCK_ENTITIES.register("advanced_lava_generator", () -> BlockEntityType.Builder.of(LavaGeneratorEntityT3::new, ADVLAVAGENERATOR_BLOCK.get()).build(null));
    public static final DeferredBlock<LavaGeneratorBlockT4> INDLAVAGENERATOR_BLOCK = MACHINES.register("industrial_lava_generator", () -> new LavaGeneratorBlockT4(LavaGeneratorBlock.defaultProperties()));
    public static final DeferredItem<Item> INDLAVAGENERATOR_BLOCKITEM = BLOCK_ITEMS.register("industrial_lava_generator", () -> new BlockItem(INDLAVAGENERATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LavaGeneratorEntityT4>> LAVAGENERATORT4_ENTITY = BLOCK_ENTITIES.register("industrial_lava_generator", () -> BlockEntityType.Builder.of(LavaGeneratorEntityT4::new, INDLAVAGENERATOR_BLOCK.get()).build(null));

    public static final Supplier<MenuType<LavaGeneratorMenu>> LAVAGENERATOR_MENU = MENUS.register("lava_generator", () -> IMenuTypeExtension.create(LavaGeneratorMenu::factory));

    public static final DeferredBlock<BiogeneratorBlockT1> BASICBIOGENERATOR_BLOCK = MACHINES.register("basic_biogenerator", () -> new BiogeneratorBlockT1(BiogeneratorBlock.defaultProperties()));
    public static final DeferredItem<Item> BASICBIOLGENERATOR_BLOCKITEM = BLOCK_ITEMS.register("basic_biogenerator", () -> new BlockItem(BASICBIOGENERATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BiogeneratorEntityT1>> BIOGENERATORT1_ENTITY = BLOCK_ENTITIES.register("basic_biogenerator", () -> BlockEntityType.Builder.of(BiogeneratorEntityT1::new, BASICBIOGENERATOR_BLOCK.get()).build(null));
    public static final DeferredBlock<BiogeneratorBlockT2> INTERBIOGENERATOR_BLOCK = MACHINES.register("intermediate_biogenerator", () -> new BiogeneratorBlockT2(BiogeneratorBlock.defaultProperties()));
    public static final DeferredItem<Item> INTERBIOGENERATOR_BLOCKITEM = BLOCK_ITEMS.register("intermediate_biogenerator", () -> new BlockItem(INTERBIOGENERATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BiogeneratorEntityT2>> BIOGENERATORT2_ENTITY = BLOCK_ENTITIES.register("intermediate_biogenerator", () -> BlockEntityType.Builder.of(BiogeneratorEntityT2::new, INTERBIOGENERATOR_BLOCK.get()).build(null));
    public static final DeferredBlock<BiogeneratorBlockT3> ADVBIOGENERATOR_BLOCK = MACHINES.register("advanced_biogenerator", () -> new BiogeneratorBlockT3(BiogeneratorBlock.defaultProperties()));
    public static final DeferredItem<Item> ADVBIOGENERATOR_BLOCKITEM = BLOCK_ITEMS.register("advanced_biogenerator", () -> new BlockItem(ADVBIOGENERATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BiogeneratorEntityT3>> BIOGENERATORT3_ENTITY = BLOCK_ENTITIES.register("advanced_biogenerator", () -> BlockEntityType.Builder.of(BiogeneratorEntityT3::new, ADVBIOGENERATOR_BLOCK.get()).build(null));
    public static final DeferredBlock<BiogeneratorBlockT4> INDBIOGENERATOR_BLOCK = MACHINES.register("industrial_biogenerator", () -> new BiogeneratorBlockT4(BiogeneratorBlock.defaultProperties()));
    public static final DeferredItem<Item> INDBIOGENERATOR_BLOCKITEM = BLOCK_ITEMS.register("industrial_biogenerator", () -> new BlockItem(INDBIOGENERATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BiogeneratorEntityT4>> BIOGENERATORT4_ENTITY = BLOCK_ENTITIES.register("industrial_biogenerator", () -> BlockEntityType.Builder.of(BiogeneratorEntityT4::new, INDBIOGENERATOR_BLOCK.get()).build(null));

    public static final Supplier<MenuType<BiogeneratorMenu>> BIOGENERATOR_MENU = MENUS.register("biogenerator", () -> IMenuTypeExtension.create(BiogeneratorMenu::factory));

    public static final DeferredBlock<BatteryBlockT1> BASICBATTERY_BLOCK = BATTERIES.register("basic_battery", () -> new BatteryBlockT1(BatteryBlock.defaultProperties()));
    public static final DeferredItem<Item> BATTERYT1_ITEM = BLOCK_ITEMS.register("basic_battery", () -> new BatteryBlockItem(BASICBATTERY_BLOCK.get(), new Item.Properties(), 10000));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BatteryEntityT1>> BATTERYT1_ENTITY = BLOCK_ENTITIES.register("basic_battery", () -> BlockEntityType.Builder.of(BatteryEntityT1::new, BASICBATTERY_BLOCK.get()).build(null) );
    public static final DeferredBlock<BatteryBlockT2> INTBATTERY_BLOCK = BATTERIES.register("intermediate_battery",() -> new BatteryBlockT2(BatteryBlock.defaultProperties()));
    public static final DeferredItem<Item> BATTERYT2_ITEM = BLOCK_ITEMS.register("intermediate_battery", () -> new BatteryBlockItem(INTBATTERY_BLOCK.get(), new Item.Properties(), 20000));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BatteryEntityT2>> BATTERYT2_ENTITY = BLOCK_ENTITIES.register("intermediate_battery", () -> BlockEntityType.Builder.of(BatteryEntityT2::new, INTBATTERY_BLOCK.get()).build(null) );
    public static final DeferredBlock<BatteryBlockT3> ADVBATTERY_BLOCK = BATTERIES.register("advanced_battery", () -> new BatteryBlockT3(BatteryBlock.defaultProperties()));
    public static final DeferredItem<Item> BATTERYT3_ITEM = BLOCK_ITEMS.register("advanced_battery", () -> new BatteryBlockItem(ADVBATTERY_BLOCK.get(), new Item.Properties(),40000));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BatteryEntityT3>> BATTERYT3_ENTITY = BLOCK_ENTITIES.register("advanced_battery", () -> BlockEntityType.Builder.of(BatteryEntityT3::new, ADVBATTERY_BLOCK.get()).build(null) );
    public static final DeferredBlock<BatteryBlockT4> INDBATTERY_BLOCK = BATTERIES.register("industrial_battery",() -> new BatteryBlockT4(BatteryBlock.defaultProperties()));
    public static final DeferredItem<Item> BATTERYT4_ITEM = BLOCK_ITEMS.register("industrial_battery", () -> new BatteryBlockItem(INDBATTERY_BLOCK.get(), new Item.Properties(), 80000));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BatteryEntityT4>> BATTERYT4_ENTITY = BLOCK_ENTITIES.register("industrial_battery", () -> BlockEntityType.Builder.of(BatteryEntityT4::new, INDBATTERY_BLOCK.get()).build(null) );
    public static final Supplier<MenuType<BatteryMenu>> BATTERY_MENU = MENUS.register("battery", () -> IMenuTypeExtension.create(BatteryMenu::factory));
    public static final Supplier<RecipeSerializer<BatteryUpgradeRecipe>> BATTERYUPGRADE_SERIALIZER = RECIPE_SERIALIZERS.register("battery_upgrade", BatteryUpgradeRecipe.Serializer::new);

    public static final DeferredBlock<ElectricFurnaceBlockT1> FURNACET1_BLOCK = MACHINES.register("basic_furnace", () -> new ElectricFurnaceBlockT1(ElectricFurnaceBlock.defaultProperties()));
    public static final DeferredItem<Item> FURNACET1_BLOCKITEM = BLOCK_ITEMS.register("basic_furnace", () -> new BlockItem(FURNACET1_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ElectricFurnaceEntityT1>> FURNACET1_ENTITY = BLOCK_ENTITIES.register("basic_furnace", () -> BlockEntityType.Builder.of(ElectricFurnaceEntityT1::new, FURNACET1_BLOCK.get()).build(null));
    public static final DeferredBlock<ElectricFurnaceBlockT2> FURNACET2_BLOCK = MACHINES.register("intermediate_furnace", () -> new ElectricFurnaceBlockT2(ElectricFurnaceBlock.defaultProperties()));
    public static final DeferredItem<Item> FURNACET2_BLOCKITEM = BLOCK_ITEMS.register("intermediate_furnace", () -> new BlockItem(FURNACET2_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ElectricFurnaceEntityT2>> FURNACET2_ENTITY = BLOCK_ENTITIES.register("intermediate_furnace", () -> BlockEntityType.Builder.of(ElectricFurnaceEntityT2::new, FURNACET2_BLOCK.get()).build(null));
    public static final DeferredBlock<ElectricFurnaceBlockT3> FURNACET3_BLOCK = MACHINES.register("advanced_furnace", () -> new ElectricFurnaceBlockT3(ElectricFurnaceBlock.defaultProperties()));
    public static final DeferredItem<Item> FURNACET3_BLOCKITEM = BLOCK_ITEMS.register("advanced_furnace", () -> new BlockItem(FURNACET3_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ElectricFurnaceEntityT3>> FURNACET3_ENTITY = BLOCK_ENTITIES.register("advanced_furnace", () -> BlockEntityType.Builder.of(ElectricFurnaceEntityT3::new, FURNACET3_BLOCK.get()).build(null));
    public static final DeferredBlock<ElectricFurnaceBlockT4> FURNACET4_BLOCK = MACHINES.register("industrial_furnace", () -> new ElectricFurnaceBlockT4(ElectricFurnaceBlock.defaultProperties()));
    public static final DeferredItem<Item> FURNACET4_BLOCKITEM = BLOCK_ITEMS.register("industrial_furnace", () -> new BlockItem(FURNACET4_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ElectricFurnaceEntityT4>> FURNACET4_ENTITY = BLOCK_ENTITIES.register("industrial_furnace", () -> BlockEntityType.Builder.of(ElectricFurnaceEntityT4::new, FURNACET4_BLOCK.get()).build(null));
    public static final Supplier<MenuType<ElectricFurnaceMenu>> FURNACE_MENU = MENUS.register("electric_furnace", () -> IMenuTypeExtension.create(ElectricFurnaceMenu::factory));

    public static final DeferredBlock<SawmillBlock> SAWMILL_BLOCK = MACHINES.register("sawmill", () -> new SawmillBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .strength(5.0f)
            .requiresCorrectToolForDrops()
    ));
    public static final DeferredItem<Item> SAWMILL_BLOCKITEM = BLOCK_ITEMS.register("sawmill", () -> new BlockItem(SAWMILL_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SawmillEntity>> SAWMILL_ENTITY = BLOCK_ENTITIES.register("sawmill", () -> BlockEntityType.Builder.of(SawmillEntity::new, SAWMILL_BLOCK.get()).build(null));
    public static final Supplier<MenuType<SawmillMenu>> SAWMILL_MENU = MENUS.register("sawmill", () -> IMenuTypeExtension.create(SawmillMenu::factory));
    public static final Supplier<RecipeType<SawmillRecipe>> SAWMILL_RECIPE = RECIPE_TYPES.register("sawmill", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(MODID, "sawmill")));
    public static final Supplier<RecipeSerializer<SawmillRecipe>> SAWMILL_SERIALIZER = RECIPE_SERIALIZERS.register("sawmill",SawmillRecipe.Serializer::new);

    public static final DeferredBlock<GrinderBlockT1> GRINDERT1_BLOCK = MACHINES.register("basic_grinder", () -> new GrinderBlockT1(GrinderBlock.defaultProperties()));
    public static final DeferredItem<Item> GRINDERT1_BLOCKITEM = BLOCK_ITEMS.register("basic_grinder", () -> new BlockItem(GRINDERT1_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrinderEntityT1>> GRINDERT1_ENTITY = BLOCK_ENTITIES.register("basic_grinder", () -> BlockEntityType.Builder.of(GrinderEntityT1::new, GRINDERT1_BLOCK.get()).build(null));
    public static final DeferredBlock<GrinderBlockT2> GRINDERT2_BLOCK = MACHINES.register("intermediate_grinder", () -> new GrinderBlockT2(GrinderBlock.defaultProperties()));
    public static final DeferredItem<Item> GRINDERT2_BLOCKITEM = BLOCK_ITEMS.register("intermediate_grinder", () -> new BlockItem(GRINDERT2_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrinderEntityT2>> GRINDERT2_ENTITY = BLOCK_ENTITIES.register("intermediate_grinder", () -> BlockEntityType.Builder.of(GrinderEntityT2::new, GRINDERT2_BLOCK.get()).build(null));
    public static final DeferredBlock<GrinderBlockT3> GRINDERT3_BLOCK = MACHINES.register("advanced_grinder", () -> new GrinderBlockT3(GrinderBlock.defaultProperties()));
    public static final DeferredItem<Item> GRINDERT3_BLOCKITEM = BLOCK_ITEMS.register("advanced_grinder", () -> new BlockItem(GRINDERT3_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrinderEntityT3>> GRINDERT3_ENTITY = BLOCK_ENTITIES.register("advanced_grinder", () -> BlockEntityType.Builder.of(GrinderEntityT3::new, GRINDERT3_BLOCK.get()).build(null));
    public static final DeferredBlock<GrinderBlockT4> GRINDERT4_BLOCK = MACHINES.register("industrial_grinder", () -> new GrinderBlockT4(GrinderBlock.defaultProperties()));
    public static final DeferredItem<Item> GRINDERT4_BLOCKITEM = BLOCK_ITEMS.register("industrial_grinder", () -> new BlockItem(GRINDERT4_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrinderEntityT4>> GRINDERT4_ENTITY = BLOCK_ENTITIES.register("industrial_grinder", () -> BlockEntityType.Builder.of(GrinderEntityT4::new, GRINDERT4_BLOCK.get()).build(null));
    public static final Supplier<MenuType<GrinderMenu>> GRINDER_MENU = MENUS.register("grinder", () -> IMenuTypeExtension.create(GrinderMenu::factory));
    public static final Supplier<RecipeType<GrinderRecipe>> GRINDER_RECIPE = RECIPE_TYPES.register("grinder", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(MODID, "grinder")));
    public static final Supplier<RecipeSerializer<GrinderRecipe>> GRINDER_SERIALIZER = RECIPE_SERIALIZERS.register("grinder", GrinderRecipe.Serializer::new);

    public static final DeferredBlock<ElectricStonecutterBlock> STONECUTTER_BLOCK = MACHINES.register("stonecutter", () -> new ElectricStonecutterBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .strength(5.0f)
            .requiresCorrectToolForDrops()
    ));
    public static final DeferredItem<Item> STONECUTTER_BLOCKITEM = BLOCK_ITEMS.register("stonecutter", () -> new BlockItem(STONECUTTER_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ElectricStonecutterEntity>> STONECUTTER_ENTITY = BLOCK_ENTITIES.register("stonecutter", () -> BlockEntityType.Builder.of(ElectricStonecutterEntity::new, STONECUTTER_BLOCK.get()).build(null));
    public static final Supplier<MenuType<ElectricStonecutterMenu>> STONECUTTER_MENU = MENUS.register("stonecutter", () -> IMenuTypeExtension.create(ElectricStonecutterMenu::factory));

    public static final DeferredBlock<CopperWireBlock> COPPERWIRE_BLOCK = BLOCKS.register("copper_wire", () -> new CopperWireBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .strength(1.5f)
            .noOcclusion()
            .isValidSpawn((blockState,blockGetter,blockPos,entityType) -> false)
    ));
    public static final DeferredItem<Item> COPPERWIRE_BLOCKITEM = BLOCK_ITEMS.register("copper_wire", () -> new BlockItem(COPPERWIRE_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CopperWireEntity>> COPPERWIRE_ENTITY = BLOCK_ENTITIES.register("copper_wire", ()-> BlockEntityType.Builder.of(CopperWireEntity::new, COPPERWIRE_BLOCK.get()).build(null));

    public static final DeferredBlock<BufferBlock> BUFFER_BLOCK = MACHINES.register("buffer", () -> new BufferBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .strength(5.0f)
            .requiresCorrectToolForDrops()
    ));
    public static final DeferredItem<Item> BUFFER_BLOCKITEM = BLOCK_ITEMS.register("buffer", () -> new BlockItem(BUFFER_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BufferEntity>> BUFFER_ENTITY = BLOCK_ENTITIES.register("buffer", () -> BlockEntityType.Builder.of(BufferEntity::new, BUFFER_BLOCK.get()).build(null));
    public static final Supplier<MenuType<BufferMenu>> BUFFER_MENU = MENUS.register("buffer", () -> IMenuTypeExtension.create(BufferMenu::factory));

    public static final DeferredBlock<TankBlockT1> TANKT1_BLOCK = TANKS.register("basic_tank", () -> new TankBlockT1(TankBlock.defaultProperties()));
    public static final DeferredItem<Item> TANKT1_ITEM = BLOCK_ITEMS.register("basic_tank", () -> new TankBlockItem(TANKT1_BLOCK.get(), new Item.Properties(), 10000));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TankEntityT1>> TANKT1_ENTITY = BLOCK_ENTITIES.register("basic_tank", () -> BlockEntityType.Builder.of(TankEntityT1::new, TANKT1_BLOCK.get()).build(null));
    public static final DeferredBlock<TankBlockT2> TANKT2_BLOCK = TANKS.register("intermediate_tank", () -> new TankBlockT2(TankBlock.defaultProperties()));
    public static final DeferredItem<Item> TANKT2_ITEM = BLOCK_ITEMS.register("intermediate_tank", () -> new TankBlockItem(TANKT2_BLOCK.get(), new Item.Properties(),100000));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TankEntityT2>> TANKT2_ENTITY = BLOCK_ENTITIES.register("intermediate_tank", () -> BlockEntityType.Builder.of(TankEntityT2::new, TANKT2_BLOCK.get()).build(null));
    public static final DeferredBlock<TankBlockT3> TANKT3_BLOCK = TANKS.register("advanced_tank", () -> new TankBlockT3(TankBlock.defaultProperties()));
    public static final DeferredItem<Item> TANKT3_ITEM = BLOCK_ITEMS.register("advanced_tank", () -> new TankBlockItem(TANKT3_BLOCK.get(), new Item.Properties(),1000000));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TankEntityT3>> TANKT3_ENTITY = BLOCK_ENTITIES.register("advanced_tank", () -> BlockEntityType.Builder.of(TankEntityT3::new, TANKT3_BLOCK.get()).build(null));
    public static final DeferredBlock<TankBlockT4> TANKT4_BLOCK = TANKS.register("industrial_tank", () -> new TankBlockT4(TankBlock.defaultProperties()));
    public static final DeferredItem<Item> TANKT4_ITEM = BLOCK_ITEMS.register("industrial_tank", () -> new TankBlockItem(TANKT4_BLOCK.get(), new Item.Properties(),10000000));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TankEntityT4>> TANKT4_ENTITY = BLOCK_ENTITIES.register("industrial_tank", () -> BlockEntityType.Builder.of(TankEntityT4::new, TANKT4_BLOCK.get()).build(null));
    public static final Supplier<MenuType<TankMenu>> TANK_MENU = MENUS.register("tank", () -> IMenuTypeExtension.create(TankMenu::factory));
    public static final Supplier<RecipeSerializer<TankUpgradeRecipe>> TANK_UPGRADE_SERIALIZER = RECIPE_SERIALIZERS.register("tank_upgrade", TankUpgradeRecipe.Serializer::new);

    public static final DeferredBlock<PumpBlockT1> PUMPT1_BLOCK = MACHINES.register("basic_pump", () -> new PumpBlockT1(PumpBlock.defaultProperties()));
    public static final DeferredItem<Item> PUMPT1_BLOCKITEM = BLOCK_ITEMS.register("basic_pump", () -> new BlockItem(PUMPT1_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PumpEntityT1>> PUMPT1_ENTITY = BLOCK_ENTITIES.register("basic_pump", () -> BlockEntityType.Builder.of(PumpEntityT1::new, PUMPT1_BLOCK.get()).build(null));
    public static final DeferredBlock<PumpBlockT2> PUMPT2_BLOCK = MACHINES.register("intermediate_pump", () -> new PumpBlockT2(PumpBlock.defaultProperties()));
    public static final DeferredItem<Item> PUMPT2_BLOCKITEM = BLOCK_ITEMS.register("intermediate_pump", () -> new BlockItem(PUMPT2_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PumpEntityT2>> PUMPT2_ENTITY = BLOCK_ENTITIES.register("intermediate_pump", () -> BlockEntityType.Builder.of(PumpEntityT2::new, PUMPT2_BLOCK.get()).build(null));
    public static final DeferredBlock<PumpBlockT3> PUMPT3_BLOCK = MACHINES.register("advanced_pump", () -> new PumpBlockT3(PumpBlock.defaultProperties()));
    public static final DeferredItem<Item> PUMPT3_BLOCKITEM = BLOCK_ITEMS.register("advanced_pump", () -> new BlockItem(PUMPT3_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PumpEntityT3>> PUMPT3_ENTITY = BLOCK_ENTITIES.register("advanced_pump", () -> BlockEntityType.Builder.of(PumpEntityT3::new, PUMPT3_BLOCK.get()).build(null));
    public static final DeferredBlock<PumpBlockT4> PUMPT4_BLOCK = MACHINES.register("industrial_pump", () -> new PumpBlockT4(PumpBlock.defaultProperties()));
    public static final DeferredItem<Item> PUMPT4_BLOCKITEM = BLOCK_ITEMS.register("industrial_pump", () -> new BlockItem(PUMPT4_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PumpEntityT4>> PUMPT4_ENTITY = BLOCK_ENTITIES.register("industrial_pump", () -> BlockEntityType.Builder.of(PumpEntityT4::new, PUMPT4_BLOCK.get()).build(null));
    public static final Supplier<MenuType<PumpMenu>> PUMP_MENU = MENUS.register("pump", () -> IMenuTypeExtension.create(PumpMenu::factory));

    public static final DeferredBlock<MinerBlockT1> MINERT1_BLOCK = MINERS.register("basic_miner", () -> new MinerBlockT1(MinerBlock.defaultProperties()));
    public static final DeferredItem<Item> MINERT1_BLOCKITEM = BLOCK_ITEMS.register("basic_miner", () -> new BlockItem(MINERT1_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MinerEntityT1>> MINERT1_ENTITY = BLOCK_ENTITIES.register("basic_miner", () -> BlockEntityType.Builder.of(MinerEntityT1::new, MINERT1_BLOCK.get()).build(null));
    public static final DeferredBlock<MinerBlockT2> MINERT2_BLOCK = MINERS.register("intermediate_miner", () -> new MinerBlockT2(MinerBlock.defaultProperties()));
    public static final DeferredItem<Item> MINERT2_BLOCKITEM = BLOCK_ITEMS.register("intermediate_miner", () -> new BlockItem(MINERT2_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MinerEntityT2>> MINERT2_ENTITY = BLOCK_ENTITIES.register("intermediate_miner", () -> BlockEntityType.Builder.of(MinerEntityT2::new, MINERT2_BLOCK.get()).build(null));
    public static final DeferredBlock<MinerBlockT3> MINERT3_BLOCK = MINERS.register("advanced_miner", () -> new MinerBlockT3(MinerBlock.defaultProperties()));
    public static final DeferredItem<Item> MINERT3_BLOCKITEM = BLOCK_ITEMS.register("advanced_miner", () -> new BlockItem(MINERT3_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MinerEntityT3>> MINERT3_ENTITY = BLOCK_ENTITIES.register("advanced_miner", () -> BlockEntityType.Builder.of(MinerEntityT3::new, MINERT3_BLOCK.get()).build(null));
    public static final DeferredBlock<MinerBlockT4> MINERT4_BLOCK = MINERS.register("industrial_miner", () -> new MinerBlockT4(MinerBlock.defaultProperties()));
    public static final DeferredItem<Item> MINERT4_BLOCKITEM = BLOCK_ITEMS.register("industrial_miner", () -> new BlockItem(MINERT4_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MinerEntityT4>> MINERT4_ENTITY = BLOCK_ENTITIES.register("industrial_miner", () -> BlockEntityType.Builder.of(MinerEntityT4::new, MINERT4_BLOCK.get()).build(null));
    public static final Supplier<MenuType<MinerMenu>> MINER_MENU = MENUS.register("miner", () -> IMenuTypeExtension.create(MinerMenu::factory));
    public static final Supplier<RecipeSerializer<MinerRecipe>> MINER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("miner", MinerRecipe.Serializer::new);

    public static final DeferredBlock<DisenchanterBlock> DISENCHANTER_BLOCK = MACHINES.register("disenchanter", () -> new DisenchanterBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .strength(15.0f)
            .requiresCorrectToolForDrops()
            .lightLevel(blockState -> 7)
    ));
    public static final DeferredItem<Item> DISENCHANTER_BLOCKITEM = BLOCK_ITEMS.register("disenchanter", () -> new BlockItem(DISENCHANTER_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DisenchanterEntity>> DISENCHANTER_ENTITY = BLOCK_ENTITIES.register("disenchanter", () -> BlockEntityType.Builder.of(DisenchanterEntity::new, DISENCHANTER_BLOCK.get()).build(null));
    public static final Supplier<MenuType<DisenchanterMenu>> DISENCHANTER_MENU = MENUS.register("disenchanter", () -> IMenuTypeExtension.create(DisenchanterMenu::factory));

    public static final DeferredBlock<DeconstructorBlock> DECONSTRUCTOR_BLOCK = MACHINES.register("deconstructor", () -> new DeconstructorBlock( BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .strength(15.0f)
            .requiresCorrectToolForDrops()
    ));
    public static final DeferredItem<Item> DECONSTRUCTOR_BLOCKITEM = BLOCK_ITEMS.register("deconstructor", () -> new BlockItem(DECONSTRUCTOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DeconstructorEntity>> DECONSTRUCTOR_ENTITY = BLOCK_ENTITIES.register("deconstructor", () -> BlockEntityType.Builder.of(DeconstructorEntity::new, DECONSTRUCTOR_BLOCK.get()).build(null));
    public static final Supplier<MenuType<DeconstructorMenu>> DECONSTRUCTOR_MENU = MENUS.register("deconstructor", () -> IMenuTypeExtension.create(DeconstructorMenu::factory));

    public static final DeferredBlock<CobblestoneSolidifierBlock> COBBLESTONESOLIDIFIER_BLOCK = MACHINES.register("cobblestone_solidifier", () -> new CobblestoneSolidifierBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .strength(5.0f)
            .requiresCorrectToolForDrops()
    ));
    public static final DeferredItem<Item> COBBLESTONESOLIDIFIER_BLOCKITEM = BLOCK_ITEMS.register("cobblestone_solidifier", () -> new BlockItem(COBBLESTONESOLIDIFIER_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CobblestoneSolidifierEntity>> COBBLESTONESOLIDIFIER_ENTITY = BLOCK_ENTITIES.register("cobblestone_solidifier", () -> BlockEntityType.Builder.of(CobblestoneSolidifierEntity::new, COBBLESTONESOLIDIFIER_BLOCK.get()).build(null));
    public static final DeferredBlock<BasaltSolidifierBlock> BASALTSOLIDIFIER_BLOCK = MACHINES.register("basalt_solidifier", () -> new BasaltSolidifierBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .strength(5.0f)
            .requiresCorrectToolForDrops()
    ));
    public static final DeferredItem<Item> BASALTSOLIDIFIER_BLOCKITEM = BLOCK_ITEMS.register("basalt_solidifier", () -> new BlockItem(BASALTSOLIDIFIER_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BasaltSolidifierEntity>> BASALTSOLIDIFIER_ENTITY = BLOCK_ENTITIES.register("basalt_solidifier", () -> BlockEntityType.Builder.of(BasaltSolidifierEntity::new, BASALTSOLIDIFIER_BLOCK.get()).build(null));
    public static final Supplier<MenuType<SolidifierMenu>> SOLIDIFIER_MENU = MENUS.register("solidifier", () -> IMenuTypeExtension.create(SolidifierMenu::factory));

    public static final DeferredItem<Item> IRON_DUST = ITEMS.register("iron_dust", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLD_DUST = ITEMS.register("gold_dust", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COPPER_DUST = ITEMS.register("copper_dust", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CAPACITOR = ITEMS.register("capacitor", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PLANT_DUST = ITEMS.register("plant_dust", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NETHER_DUST = ITEMS.register("nether_dust", () -> new Item(new Item.Properties()));

    public static final DeferredBlock<PackagerBlock> AUTOPACKAGER_BLOCK = MACHINES.register("autopackager", () -> new PackagerBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .strength(10.0f, 200.0f)
            .requiresCorrectToolForDrops()
    ));
    public static final DeferredItem<Item> AUTOPACKAGER_BLOCKITEM = BLOCK_ITEMS.register("autopackager", () -> new BlockItem(AUTOPACKAGER_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PackagerEntity>> AUTOPACKAGER_ENTITY = BLOCK_ENTITIES.register("autopackager", () -> BlockEntityType.Builder.of(PackagerEntity::new, AUTOPACKAGER_BLOCK.get()).build(null));
    public static final Supplier<MenuType<PackagerMenu>> AUTOPACKAGER_MENU = MENUS.register("autopackager", () -> IMenuTypeExtension.create(PackagerMenu::factory));
    public static final DeferredItem<Item> SLATE_2x2 = PATTERNS.register("slate_2x2", () -> new Pattern2x2Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SLATE_3x3 = PATTERNS.register("slate_3x3", () -> new Pattern3x3Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SLATE_CROSS = PATTERNS.register("slate_cross", () -> new PatternCrossItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SLATE_DOOR = PATTERNS.register("slate_door", () -> new PatternDoorItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SLATE_HOLLOW = PATTERNS.register("slate_hollow", () -> new PatternHollowItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SLATE_SLAB = PATTERNS.register("slate_slab", () -> new PatternSlabItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SLATE_STAIR = PATTERNS.register("slate_stair", () -> new PatternStairItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SLATE_UNPACKAGE = PATTERNS.register("slate_unpackage", () -> new PatternUnpackageItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SLATE_WALL = PATTERNS.register("slate_wall", () -> new PatternWallItem(new Item.Properties().stacksTo(1)));

    public static final DeferredBlock<HydratorBlock> HYDRATOR_BLOCK = MACHINES.register("hydrator", () -> new HydratorBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .strength(10.0f)
            .requiresCorrectToolForDrops()
    ));
    public static final DeferredItem<Item> HYDRATOR_BLOCKITEM = BLOCK_ITEMS.register("hydrator", () -> new BlockItem(HYDRATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HydratorEntity>> HYDRATOR_ENTITY = BLOCK_ENTITIES.register("hydrator", () -> BlockEntityType.Builder.of(HydratorEntity::new, HYDRATOR_BLOCK.get()).build(null));
    public static final Supplier<MenuType<HydratorMenu>> HYDRATOR_MENU = MENUS.register("hydrator", () -> IMenuTypeExtension.create(HydratorMenu::factory));
    public static final Supplier<RecipeType<HydratorRecipe>> HYDRATOR_RECIPE = RECIPE_TYPES.register("hydrator", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(MODID, "hydrator")));
    public static final Supplier<RecipeSerializer<HydratorRecipe>> HYDRATOR_SERIALIZER = RECIPE_SERIALIZERS.register("hydrator",HydratorRecipe.Serializer::new);

    public static final DeferredBlock<DehydratorBlock> DEHYDRATOR_BLOCK = MACHINES.register("dehydrator", () -> new DehydratorBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .strength(10.0f)
            .requiresCorrectToolForDrops()
    ));
    public static final DeferredItem<Item> DEHYDRATOR_BLOCKITEM = BLOCK_ITEMS.register("dehydrator", () -> new BlockItem(DEHYDRATOR_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DehydratorEntity>> DEHYDRATOR_ENTITY = BLOCK_ENTITIES.register("dehydrator", () -> BlockEntityType.Builder.of(DehydratorEntity::new, DEHYDRATOR_BLOCK.get()).build(null));
    public static final Supplier<MenuType<DehydratorMenu>> DEHYDRATOR_MENU = MENUS.register("dehydrator", () -> IMenuTypeExtension.create(DehydratorMenu::factory));
    public static final Supplier<RecipeType<DehydratorRecipe>> DEHYDRATOR_RECIPE = RECIPE_TYPES.register("dehydrator", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(MODID, "dehydrator")));
    public static final Supplier<RecipeSerializer<DehydratorRecipe>> DEHYDRATOR_SERIALIZER = RECIPE_SERIALIZERS.register("dehydrator",DehydratorRecipe.Serializer::new);

    public static final DeferredBlock<HarvesterBlock> HARVESTER_BLOCK = MACHINES.register("autoharvester", () -> new HarvesterBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .strength(15.0f)
            .requiresCorrectToolForDrops()
    ));
    public static final DeferredItem<Item> HARVESTER_BLOCKITEM = BLOCK_ITEMS.register("autoharvester", () -> new BlockItem(HARVESTER_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HarvesterEntity>> HARVESTER_ENTITY = BLOCK_ENTITIES.register("harvester", () -> BlockEntityType.Builder.of(HarvesterEntity::new, HARVESTER_BLOCK.get()).build(null));
    public static final Supplier<MenuType<HarvesterMenu>> HARVESTER_MENU = MENUS.register("autoharvester", () -> IMenuTypeExtension.create(HarvesterMenu::factory));

    public static final DeferredBlock<PlanterBlock> PLANTER_BLOCK = MACHINES.register("planter", () -> new PlanterBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .strength(15.0f)
            .requiresCorrectToolForDrops()
    ));
    public static final DeferredItem<Item> PLANTER_BLOCKITEM = BLOCK_ITEMS.register("planter", () -> new BlockItem(PLANTER_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PlanterEntity>> PLANTER_ENTITY = BLOCK_ENTITIES.register("planter", () -> BlockEntityType.Builder.of(PlanterEntity::new, PLANTER_BLOCK.get()).build(null));
    public static final Supplier<MenuType<PlanterMenu>> PLANTER_MENU = MENUS.register("planter", () -> IMenuTypeExtension.create(PlanterMenu::factory));

    public static final DeferredBlock<SoilManagerBlock> SOILMANAGER_BLOCK = MACHINES.register("soilmanager", () -> new SoilManagerBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .strength(15.0f)
            .requiresCorrectToolForDrops()
    ));
    public static final DeferredItem<Item> SOILMANAGER_BLOCKITEM = BLOCK_ITEMS.register("soilmanager", () -> new BlockItem(SOILMANAGER_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SoilManagerEntity>> SOILMANAGER_ENTITY = BLOCK_ENTITIES.register("soilmanager", () -> BlockEntityType.Builder.of(SoilManagerEntity::new, SOILMANAGER_BLOCK.get()).build(null));
    public static final Supplier<MenuType<SoilManagerMenu>> SOILMANAGER_MENU = MENUS.register("soilmanager", () -> IMenuTypeExtension.create(SoilManagerMenu::factory));

    public static final Supplier<LootItemFunctionType<? extends LootItemConditionalFunction>> RETAIN_ENCHANTMENTS = LOOT_FUNCTION_TYPES.register("retain_enchantments", () -> new LootItemFunctionType(RetainEnchantmentsFunction.CODEC));
    public static final Supplier<LootItemFunctionType<? extends LootItemConditionalFunction>> RETAIN_ENERGY = LOOT_FUNCTION_TYPES.register("retain_energy", () -> new LootItemFunctionType(RetainEnergyFunction.CODEC));
    public static final Supplier<LootItemFunctionType<? extends LootItemConditionalFunction>> RETAIN_FLUID = LOOT_FUNCTION_TYPES.register("retain_fluid", () -> new LootItemFunctionType(RetainFluidFunction.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> STORED_ENERGY = DATA_COMPONENTS.register("energy", () -> DataComponentType.<Integer>builder().persistent(Codec.INT.orElse(0)).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> STORED_FLUID = DATA_COMPONENTS.register("fluid", () -> DataComponentType.<SimpleFluidContent>builder().persistent(SimpleFluidContent.CODEC.orElse(SimpleFluidContent.EMPTY)).networkSynchronized(SimpleFluidContent.STREAM_CODEC).build());

    public static final TagKey<Item> IRON_DUST_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dusts/iron"));
    public static final TagKey<Item> COPPER_DUST_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dusts/copper"));
    public static final TagKey<Item> GOLD_DUST_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dusts/gold"));
    public static final TagKey<Item> PLANT_DUST_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dusts/plant"));
}
