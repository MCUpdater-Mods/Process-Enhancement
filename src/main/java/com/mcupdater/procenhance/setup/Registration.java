package com.mcupdater.procenhance.setup;

import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.ProcessEnhancement;
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
import com.mcupdater.procenhance.blocks.disenchanter.DisenchanterBlock;
import com.mcupdater.procenhance.blocks.disenchanter.DisenchanterEntity;
import com.mcupdater.procenhance.blocks.disenchanter.DisenchanterMenu;
import com.mcupdater.procenhance.blocks.furnace.*;
import com.mcupdater.procenhance.blocks.generator.*;
import com.mcupdater.procenhance.blocks.grinder.*;
import com.mcupdater.procenhance.blocks.lava_generator.*;
import com.mcupdater.procenhance.blocks.miner.*;
import com.mcupdater.procenhance.blocks.pump.*;
import com.mcupdater.procenhance.blocks.sawmill.SawmillBlock;
import com.mcupdater.procenhance.blocks.sawmill.SawmillEntity;
import com.mcupdater.procenhance.blocks.sawmill.SawmillMenu;
import com.mcupdater.procenhance.blocks.solidifier.*;
import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterBlock;
import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterEntity;
import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterMenu;
import com.mcupdater.procenhance.blocks.tank.*;
import com.mcupdater.procenhance.blocks.autopackager.*;
import com.mcupdater.procenhance.items.autopackager.*;
import com.mcupdater.procenhance.recipe.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.mcupdater.mculib.setup.ModSetup.MCULIB_ITEM_GROUP;
import static com.mcupdater.procenhance.ProcessEnhancement.MODID;

public class Registration {
    public static final DeferredRegister<Block> MACHINES = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Block> BATTERIES = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Block> TANKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Block> MINERS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Item> PATTERNS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ProcessEnhancement.MODID);


    public static void init(IEventBus modEventBus) {
        MACHINES.register(modEventBus);
        BATTERIES.register(modEventBus);
        TANKS.register(modEventBus);
        MINERS.register(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        PATTERNS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        MENUS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
    }

    public static final RegistryObject<CrudeGeneratorBlock> CRUDEGENERATOR_BLOCK = MACHINES.register("crude_generator", CrudeGeneratorBlock::new);
    public static final RegistryObject<Item> CRUDEGENERATOR_BLOCKITEM = ITEMS.register("crude_generator", () -> new BlockItem(CRUDEGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<CrudeGeneratorEntity>> CRUDEGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("crude_generator", () -> BlockEntityType.Builder.of(CrudeGeneratorEntity::new, CRUDEGENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<CrudeGeneratorMenu>> CRUDEGENERATOR_MENU = MENUS.register("crude_generator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        return new CrudeGeneratorMenu(windowId, world, pos, inv, inv.player, DataHelper.readDirectionMap(data));
    }));

    public static final RegistryObject<GeneratorBlockT1> BASICGENERATOR_BLOCK = MACHINES.register("basic_generator", GeneratorBlockT1::new);
    public static final RegistryObject<Item> BASICGENERATOR_BLOCKITEM = ITEMS.register("basic_generator", () -> new BlockItem(BASICGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<GeneratorEntityT1>> BASICGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("basic_generator", () -> BlockEntityType.Builder.of(GeneratorEntityT1::new, BASICGENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<GeneratorBlockT2> INTERGENERATOR_BLOCK = MACHINES.register("intermediate_generator", GeneratorBlockT2::new);
    public static final RegistryObject<Item> INTERGENERATOR_BLOCKITEM = ITEMS.register("intermediate_generator", () -> new BlockItem(INTERGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<GeneratorEntityT2>> INTERGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("intermediate_generator", () -> BlockEntityType.Builder.of(GeneratorEntityT2::new, INTERGENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<GeneratorBlockT3> ADVGENERATOR_BLOCK = MACHINES.register("advanced_generator", GeneratorBlockT3::new);
    public static final RegistryObject<Item> ADVGENERATOR_BLOCKITEM = ITEMS.register("advanced_generator", () -> new BlockItem(ADVGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<GeneratorEntityT3>> ADVGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("advanced_generator", () -> BlockEntityType.Builder.of(GeneratorEntityT3::new, ADVGENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<GeneratorBlockT4> INDGENERATOR_BLOCK = MACHINES.register("industrial_generator", GeneratorBlockT4::new);
    public static final RegistryObject<Item> INDGENERATOR_BLOCKITEM = ITEMS.register("industrial_generator", () -> new BlockItem(INDGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<GeneratorEntityT4>> INDGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("industrial_generator", () -> BlockEntityType.Builder.of(GeneratorEntityT4::new, INDGENERATOR_BLOCK.get()).build(null));

    public static final RegistryObject<MenuType<GeneratorMenu>> GENERATOR_MENU = MENUS.register("generator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        GeneratorEntity blockEntity = (GeneratorEntity) world.getBlockEntity(pos);
        return new GeneratorMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    }));

    public static final RegistryObject<LavaGeneratorBlockT1> BASICLAVAGENERATOR_BLOCK = MACHINES.register("basic_lava_generator", LavaGeneratorBlockT1::new);
    public static final RegistryObject<Item> BASICLAVALGENERATOR_BLOCKITEM = ITEMS.register("basic_lava_generator", () -> new BlockItem(BASICLAVAGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<LavaGeneratorEntityT1>> BASICLAVAGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("basic_lava_generator", () -> BlockEntityType.Builder.of(LavaGeneratorEntityT1::new, BASICLAVAGENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<LavaGeneratorBlockT2> INTERLAVAGENERATOR_BLOCK = MACHINES.register("intermediate_lava_generator", LavaGeneratorBlockT2::new);
    public static final RegistryObject<Item> INTERLAVAGENERATOR_BLOCKITEM = ITEMS.register("intermediate_lava_generator", () -> new BlockItem(INTERLAVAGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<LavaGeneratorEntityT2>> INTERLAVAGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("intermediate_lava_generator", () -> BlockEntityType.Builder.of(LavaGeneratorEntityT2::new, INTERLAVAGENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<LavaGeneratorBlockT3> ADVLAVAGENERATOR_BLOCK = MACHINES.register("advanced_lava_generator", LavaGeneratorBlockT3::new);
    public static final RegistryObject<Item> ADVLAVAGENERATOR_BLOCKITEM = ITEMS.register("advanced_lava_generator", () -> new BlockItem(ADVLAVAGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<LavaGeneratorEntityT3>> ADVLAVAGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("advanced_lava_generator", () -> BlockEntityType.Builder.of(LavaGeneratorEntityT3::new, ADVLAVAGENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<LavaGeneratorBlockT4> INDLAVAGENERATOR_BLOCK = MACHINES.register("industrial_lava_generator", LavaGeneratorBlockT4::new);
    public static final RegistryObject<Item> INDLAVAGENERATOR_BLOCKITEM = ITEMS.register("industrial_lava_generator", () -> new BlockItem(INDLAVAGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<LavaGeneratorEntityT4>> INDLAVAGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("industrial_lava_generator", () -> BlockEntityType.Builder.of(LavaGeneratorEntityT4::new, INDLAVAGENERATOR_BLOCK.get()).build(null));

    public static final RegistryObject<MenuType<LavaGeneratorMenu>> LAVAGENERATOR_MENU = MENUS.register("lava_generator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        LavaGeneratorEntity blockEntity = (LavaGeneratorEntity) world.getBlockEntity(pos);
        return new LavaGeneratorMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    }));

    public static final RegistryObject<BiogeneratorBlockT1> BASICBIOGENERATOR_BLOCK = MACHINES.register("basic_biogenerator", BiogeneratorBlockT1::new);
    public static final RegistryObject<Item> BASICBIOLGENERATOR_BLOCKITEM = ITEMS.register("basic_biogenerator", () -> new BlockItem(BASICBIOGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<BiogeneratorEntityT1>> BASICBIOGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("basic_biogenerator", () -> BlockEntityType.Builder.of(BiogeneratorEntityT1::new, BASICBIOGENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BiogeneratorBlockT2> INTERBIOGENERATOR_BLOCK = MACHINES.register("intermediate_biogenerator", BiogeneratorBlockT2::new);
    public static final RegistryObject<Item> INTERBIOGENERATOR_BLOCKITEM = ITEMS.register("intermediate_biogenerator", () -> new BlockItem(INTERBIOGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<BiogeneratorEntityT2>> INTERBIOGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("intermediate_biogenerator", () -> BlockEntityType.Builder.of(BiogeneratorEntityT2::new, INTERBIOGENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BiogeneratorBlockT3> ADVBIOGENERATOR_BLOCK = MACHINES.register("advanced_biogenerator", BiogeneratorBlockT3::new);
    public static final RegistryObject<Item> ADVBIOGENERATOR_BLOCKITEM = ITEMS.register("advanced_biogenerator", () -> new BlockItem(ADVBIOGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<BiogeneratorEntityT3>> ADVBIOGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("advanced_biogenerator", () -> BlockEntityType.Builder.of(BiogeneratorEntityT3::new, ADVBIOGENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BiogeneratorBlockT4> INDBIOGENERATOR_BLOCK = MACHINES.register("industrial_biogenerator", BiogeneratorBlockT4::new);
    public static final RegistryObject<Item> INDBIOGENERATOR_BLOCKITEM = ITEMS.register("industrial_biogenerator", () -> new BlockItem(INDBIOGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<BiogeneratorEntityT4>> INDBIOGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("industrial_biogenerator", () -> BlockEntityType.Builder.of(BiogeneratorEntityT4::new, INDBIOGENERATOR_BLOCK.get()).build(null));

    public static final RegistryObject<MenuType<BiogeneratorMenu>> BIOGENERATOR_MENU = MENUS.register("biogenerator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        BiogeneratorEntity blockEntity = (BiogeneratorEntity) world.getBlockEntity(pos);
        return new BiogeneratorMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    }));

    public static final RegistryObject<BatteryBlockT1> BASICBATTERY_BLOCK = BATTERIES.register("basic_battery", BatteryBlockT1::new);
    public static final RegistryObject<Item> BASICBATTERY_BLOCKITEM = ITEMS.register("basic_battery", () -> new BatteryBlockItem(BASICBATTERY_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP), 10000));
    public static final RegistryObject<BlockEntityType<BatteryEntityT1>> BASICBATTERY_BLOCKENTITY = BLOCK_ENTITIES.register("basic_battery", () -> BlockEntityType.Builder.of(BatteryEntityT1::new, BASICBATTERY_BLOCK.get()).build(null) );
    public static final RegistryObject<BatteryBlockT2> INTBATTERY_BLOCK = BATTERIES.register("intermediate_battery", BatteryBlockT2::new);
    public static final RegistryObject<Item> INTBATTERY_BLOCKITEM = ITEMS.register("intermediate_battery", () -> new BatteryBlockItem(INTBATTERY_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP), 20000));
    public static final RegistryObject<BlockEntityType<BatteryEntityT2>> INTBATTERY_BLOCKENTITY = BLOCK_ENTITIES.register("intermediate_battery", () -> BlockEntityType.Builder.of(BatteryEntityT2::new, INTBATTERY_BLOCK.get()).build(null) );
    public static final RegistryObject<BatteryBlockT3> ADVBATTERY_BLOCK = BATTERIES.register("advanced_battery", BatteryBlockT3::new);
    public static final RegistryObject<Item> ADVBATTERY_BLOCKITEM = ITEMS.register("advanced_battery", () -> new BatteryBlockItem(ADVBATTERY_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP),40000));
    public static final RegistryObject<BlockEntityType<BatteryEntityT3>> ADVBATTERY_BLOCKENTITY = BLOCK_ENTITIES.register("advanced_battery", () -> BlockEntityType.Builder.of(BatteryEntityT3::new, ADVBATTERY_BLOCK.get()).build(null) );
    public static final RegistryObject<BatteryBlockT4> INDBATTERY_BLOCK = BATTERIES.register("industrial_battery", BatteryBlockT4::new);
    public static final RegistryObject<Item> INDBATTERY_BLOCKITEM = ITEMS.register("industrial_battery", () -> new BatteryBlockItem(INDBATTERY_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP), 80000));
    public static final RegistryObject<BlockEntityType<BatteryEntityT4>> INDBATTERY_BLOCKENTITY = BLOCK_ENTITIES.register("industrial_battery", () -> BlockEntityType.Builder.of(BatteryEntityT4::new, INDBATTERY_BLOCK.get()).build(null) );
    public static final RegistryObject<MenuType<BatteryMenu>> BATTERY_MENU = MENUS.register("battery", () -> IForgeMenuType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.level;
        return new BatteryMenu(windowId, level, pos, inv, inv.player, DataHelper.readDirectionMap(data));
    })));
    public static final RegistryObject<RecipeSerializer<BatteryUpgradeRecipe>> BATTERYUPGRADE_SERIALIZER = RECIPE_SERIALIZERS.register("battery_upgrade", () -> BatteryUpgradeRecipe.SERIALIZER);

    public static final RegistryObject<ElectricFurnaceBlockT1> FURNACET1_BLOCK = MACHINES.register("basic_furnace", ElectricFurnaceBlockT1::new);
    public static final RegistryObject<Item> FURNACET1_BLOCKITEM = ITEMS.register("basic_furnace", () -> new BlockItem(FURNACET1_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<ElectricFurnaceEntityT1>> FURNACET1_BLOCKENTITY = BLOCK_ENTITIES.register("basic_furnace", () -> {
        return BlockEntityType.Builder.of(ElectricFurnaceEntityT1::new, FURNACET1_BLOCK.get()).build(null);
    });
    public static final RegistryObject<ElectricFurnaceBlockT2> FURNACET2_BLOCK = MACHINES.register("intermediate_furnace", ElectricFurnaceBlockT2::new);
    public static final RegistryObject<Item> FURNACET2_BLOCKITEM = ITEMS.register("intermediate_furnace", () -> new BlockItem(FURNACET2_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<ElectricFurnaceEntityT2>> FURNACET2_BLOCKENTITY = BLOCK_ENTITIES.register("intermediate_furnace", () -> {
        return BlockEntityType.Builder.of(ElectricFurnaceEntityT2::new, FURNACET2_BLOCK.get()).build(null);
    });
    public static final RegistryObject<ElectricFurnaceBlockT3> FURNACET3_BLOCK = MACHINES.register("advanced_furnace", ElectricFurnaceBlockT3::new);
    public static final RegistryObject<Item> FURNACET3_BLOCKITEM = ITEMS.register("advanced_furnace", () -> new BlockItem(FURNACET3_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<ElectricFurnaceEntityT3>> FURNACET3_BLOCKENTITY = BLOCK_ENTITIES.register("advanced_furnace", () -> {
        return BlockEntityType.Builder.of(ElectricFurnaceEntityT3::new, FURNACET3_BLOCK.get()).build(null);
    });
    public static final RegistryObject<ElectricFurnaceBlockT4> FURNACET4_BLOCK = MACHINES.register("industrial_furnace", ElectricFurnaceBlockT4::new);
    public static final RegistryObject<Item> FURNACET4_BLOCKITEM = ITEMS.register("industrial_furnace", () -> new BlockItem(FURNACET4_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<ElectricFurnaceEntityT4>> FURNACET4_BLOCKENTITY = BLOCK_ENTITIES.register("industrial_furnace", () -> {
        return BlockEntityType.Builder.of(ElectricFurnaceEntityT4::new, FURNACET4_BLOCK.get()).build(null);
    });
    public static final RegistryObject<MenuType<ElectricFurnaceMenu>> FURNACE_MENU = MENUS.register("electric_furnace", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        ElectricFurnaceEntity blockEntity = (ElectricFurnaceEntity) world.getBlockEntity(pos);
        return new ElectricFurnaceMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    }));

    public static final RegistryObject<SawmillBlock> SAWMILL_BLOCK = MACHINES.register("sawmill", SawmillBlock::new);
    public static final RegistryObject<Item> SAWMILL_BLOCKITEM = ITEMS.register("sawmill", () -> new BlockItem(SAWMILL_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<SawmillEntity>> SAWMILL_BLOCKENTITY = BLOCK_ENTITIES.register("sawmill", () -> BlockEntityType.Builder.of(SawmillEntity::new, SAWMILL_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<SawmillMenu>> SAWMILL_MENU = MENUS.register("sawmill", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        SawmillEntity blockEntity = (SawmillEntity) world.getBlockEntity(pos);
        return new SawmillMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    }));
    public static final RegistryObject<RecipeSerializer<SawmillRecipe>> SAWMILL_SERIALIZER = RECIPE_SERIALIZERS.register("sawmill",() -> SawmillRecipe.Serializer.INSTANCE);

    public static final RegistryObject<GrinderBlockT1> GRINDERT1_BLOCK = MACHINES.register("basic_grinder", GrinderBlockT1::new);
    public static final RegistryObject<Item> GRINDERT1_BLOCKITEM = ITEMS.register("basic_grinder", () -> new BlockItem(GRINDERT1_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<GrinderEntityT1>> GRINDERT1_BLOCKENTITY = BLOCK_ENTITIES.register("basic_grinder", () -> BlockEntityType.Builder.of(GrinderEntityT1::new, GRINDERT1_BLOCK.get()).build(null));
    public static final RegistryObject<GrinderBlockT2> GRINDERT2_BLOCK = MACHINES.register("intermediate_grinder", GrinderBlockT2::new);
    public static final RegistryObject<Item> GRINDERT2_BLOCKITEM = ITEMS.register("intermediate_grinder", () -> new BlockItem(GRINDERT2_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<GrinderEntityT2>> GRINDERT2_BLOCKENTITY = BLOCK_ENTITIES.register("intermediate_grinder", () -> BlockEntityType.Builder.of(GrinderEntityT2::new, GRINDERT2_BLOCK.get()).build(null));
    public static final RegistryObject<GrinderBlockT3> GRINDERT3_BLOCK = MACHINES.register("advanced_grinder", GrinderBlockT3::new);
    public static final RegistryObject<Item> GRINDERT3_BLOCKITEM = ITEMS.register("advanced_grinder", () -> new BlockItem(GRINDERT3_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<GrinderEntityT3>> GRINDERT3_BLOCKENTITY = BLOCK_ENTITIES.register("advanced_grinder", () -> BlockEntityType.Builder.of(GrinderEntityT3::new, GRINDERT3_BLOCK.get()).build(null));
    public static final RegistryObject<GrinderBlockT4> GRINDERT4_BLOCK = MACHINES.register("industrial_grinder", GrinderBlockT4::new);
    public static final RegistryObject<Item> GRINDERT4_BLOCKITEM = ITEMS.register("industrial_grinder", () -> new BlockItem(GRINDERT4_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<GrinderEntityT4>> GRINDERT4_BLOCKENTITY = BLOCK_ENTITIES.register("industrial_grinder", () -> BlockEntityType.Builder.of(GrinderEntityT4::new, GRINDERT4_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<GrinderMenu>> GRINDER_MENU = MENUS.register("grinder", () -> IForgeMenuType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        GrinderEntity blockEntity = (GrinderEntity) world.getBlockEntity(pos);
        return new GrinderMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    })));
    public static final RegistryObject<RecipeSerializer<GrinderRecipe>> GRINDER_SERIALIZER = RECIPE_SERIALIZERS.register("grinder", () -> GrinderRecipe.Serializer.INSTANCE);

    public static final RegistryObject<ElectricStonecutterBlock> STONECUTTER_BLOCK = MACHINES.register("stonecutter", ElectricStonecutterBlock::new);
    public static final RegistryObject<Item> STONECUTTER_BLOCKITEM = ITEMS.register("stonecutter", () -> new BlockItem(STONECUTTER_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<ElectricStonecutterEntity>> STONECUTTER_ENTITY = BLOCK_ENTITIES.register("stonecutter", () -> BlockEntityType.Builder.of(ElectricStonecutterEntity::new, STONECUTTER_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<ElectricStonecutterMenu>> STONECUTTER_MENU = MENUS.register("stonecutter", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.level;
        ElectricStonecutterEntity blockEntity = (ElectricStonecutterEntity) level.getBlockEntity(pos);
        return new ElectricStonecutterMenu(windowId, level, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    }));

    public static final RegistryObject<CopperWireBlock> COPPERWIRE_BLOCK = BLOCKS.register("copper_wire", CopperWireBlock::new);
    public static final RegistryObject<Item> COPPERWIRE_BLOCKITEM = ITEMS.register("copper_wire", () -> new BlockItem(COPPERWIRE_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<CopperWireEntity>> COPPERWIRE_BLOCKENTITY = BLOCK_ENTITIES.register("copper_wire", ()-> BlockEntityType.Builder.of(CopperWireEntity::new, COPPERWIRE_BLOCK.get()).build(null));

    public static final RegistryObject<BufferBlock> BUFFER_BLOCK = MACHINES.register("buffer", BufferBlock::new);
    public static final RegistryObject<Item> BUFFER_BLOCKITEM = ITEMS.register("buffer", () -> new BlockItem(BUFFER_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<BufferEntity>> BUFFER_ENTITY = BLOCK_ENTITIES.register("buffer", () -> BlockEntityType.Builder.of(BufferEntity::new, BUFFER_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<BufferMenu>> BUFFER_MENU = MENUS.register("buffer", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.level;
        BufferEntity blockEntity = (BufferEntity) level.getBlockEntity(pos);
        return new BufferMenu(windowId, level, pos, inv, inv.player, DataHelper.readDirectionMap(data));
    }));

    public static final RegistryObject<TankBlockT1> TANKT1_BLOCK = TANKS.register("basic_tank", TankBlockT1::new);
    public static final RegistryObject<Item> TANKT1_BLOCKITEM = ITEMS.register("basic_tank", () -> new TankBlockItem(TANKT1_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP), 10000));
    public static final RegistryObject<BlockEntityType<TankEntityT1>> TANKT1_ENTITY = BLOCK_ENTITIES.register("basic_tank", () -> BlockEntityType.Builder.of(TankEntityT1::new, TANKT1_BLOCK.get()).build(null));
    public static final RegistryObject<TankBlockT2> TANKT2_BLOCK = TANKS.register("intermediate_tank", TankBlockT2::new);
    public static final RegistryObject<Item> TANKT2_BLOCKITEM = ITEMS.register("intermediate_tank", () -> new TankBlockItem(TANKT2_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP),100000));
    public static final RegistryObject<BlockEntityType<TankEntityT2>> TANKT2_ENTITY = BLOCK_ENTITIES.register("intermediate_tank", () -> BlockEntityType.Builder.of(TankEntityT2::new, TANKT2_BLOCK.get()).build(null));
    public static final RegistryObject<TankBlockT3> TANKT3_BLOCK = TANKS.register("advanced_tank", TankBlockT3::new);
    public static final RegistryObject<Item> TANKT3_BLOCKITEM = ITEMS.register("advanced_tank", () -> new TankBlockItem(TANKT3_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP),1000000));
    public static final RegistryObject<BlockEntityType<TankEntityT3>> TANKT3_ENTITY = BLOCK_ENTITIES.register("advanced_tank", () -> BlockEntityType.Builder.of(TankEntityT3::new, TANKT3_BLOCK.get()).build(null));
    public static final RegistryObject<TankBlockT4> TANKT4_BLOCK = TANKS.register("industrial_tank", TankBlockT4::new);
    public static final RegistryObject<Item> TANKT4_BLOCKITEM = ITEMS.register("industrial_tank", () -> new TankBlockItem(TANKT4_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP),10000000));
    public static final RegistryObject<BlockEntityType<TankEntityT4>> TANKT4_ENTITY = BLOCK_ENTITIES.register("industrial_tank", () -> BlockEntityType.Builder.of(TankEntityT4::new, TANKT4_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<TankMenu>> TANK_MENU = MENUS.register("tank", () -> IForgeMenuType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        TankEntity blockEntity = (TankEntity) world.getBlockEntity(pos);
        return new TankMenu(windowId, world, pos, inv, inv.player, DataHelper.readDirectionMap(data));
    })));
    public static final RegistryObject<RecipeSerializer<TankUpgradeRecipe>> TANKUPGRADE_SERIALIZER = RECIPE_SERIALIZERS.register("tank_upgrade", () -> TankUpgradeRecipe.SERIALIZER);

    public static final RegistryObject<PumpBlockT1> PUMPT1_BLOCK = MACHINES.register("basic_pump", PumpBlockT1::new);
    public static final RegistryObject<Item> PUMPT1_BLOCKITEM = ITEMS.register("basic_pump", () -> new BlockItem(PUMPT1_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<PumpEntityT1>> PUMPT1_ENTITY = BLOCK_ENTITIES.register("basic_pump", () -> BlockEntityType.Builder.of(PumpEntityT1::new, PUMPT1_BLOCK.get()).build(null));
    public static final RegistryObject<PumpBlockT2> PUMPT2_BLOCK = MACHINES.register("intermediate_pump", PumpBlockT2::new);
    public static final RegistryObject<Item> PUMPT2_BLOCKITEM = ITEMS.register("intermediate_pump", () -> new BlockItem(PUMPT2_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<PumpEntityT2>> PUMPT2_ENTITY = BLOCK_ENTITIES.register("intermediate_pump", () -> BlockEntityType.Builder.of(PumpEntityT2::new, PUMPT2_BLOCK.get()).build(null));
    public static final RegistryObject<PumpBlockT3> PUMPT3_BLOCK = MACHINES.register("advanced_pump", PumpBlockT3::new);
    public static final RegistryObject<Item> PUMPT3_BLOCKITEM = ITEMS.register("advanced_pump", () -> new BlockItem(PUMPT3_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<PumpEntityT3>> PUMPT3_ENTITY = BLOCK_ENTITIES.register("advanced_pump", () -> BlockEntityType.Builder.of(PumpEntityT3::new, PUMPT3_BLOCK.get()).build(null));
    public static final RegistryObject<PumpBlockT4> PUMPT4_BLOCK = MACHINES.register("industrial_pump", PumpBlockT4::new);
    public static final RegistryObject<Item> PUMPT4_BLOCKITEM = ITEMS.register("industrial_pump", () -> new BlockItem(PUMPT4_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<PumpEntityT4>> PUMPT4_ENTITY = BLOCK_ENTITIES.register("industrial_pump", () -> BlockEntityType.Builder.of(PumpEntityT4::new, PUMPT4_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<PumpMenu>> PUMP_MENU = MENUS.register("pump", () -> IForgeMenuType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        PumpEntity blockEntity = (PumpEntity) world.getBlockEntity(pos);
        return new PumpMenu(windowId, world, pos, inv, inv.player, DataHelper.readDirectionMap(data));
    })));

    public static final RegistryObject<MinerBlockT1> MINERT1_BLOCK = MINERS.register("basic_miner", MinerBlockT1::new);
    public static final RegistryObject<Item> MINERT1_BLOCKITEM = ITEMS.register("basic_miner", () -> new BlockItem(MINERT1_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<MinerEntityT1>> MINERT1_ENTITY = BLOCK_ENTITIES.register("basic_miner", () -> BlockEntityType.Builder.of(MinerEntityT1::new, MINERT1_BLOCK.get()).build(null));
    public static final RegistryObject<MinerBlockT2> MINERT2_BLOCK = MINERS.register("intermediate_miner", MinerBlockT2::new);
    public static final RegistryObject<Item> MINERT2_BLOCKITEM = ITEMS.register("intermediate_miner", () -> new BlockItem(MINERT2_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<MinerEntityT2>> MINERT2_ENTITY = BLOCK_ENTITIES.register("intermediate_miner", () -> BlockEntityType.Builder.of(MinerEntityT2::new, MINERT2_BLOCK.get()).build(null));
    public static final RegistryObject<MinerBlockT3> MINERT3_BLOCK = MINERS.register("advanced_miner", MinerBlockT3::new);
    public static final RegistryObject<Item> MINERT3_BLOCKITEM = ITEMS.register("advanced_miner", () -> new BlockItem(MINERT3_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<MinerEntityT3>> MINERT3_ENTITY = BLOCK_ENTITIES.register("advanced_miner", () -> BlockEntityType.Builder.of(MinerEntityT3::new, MINERT3_BLOCK.get()).build(null));
    public static final RegistryObject<MinerBlockT4> MINERT4_BLOCK = MINERS.register("industrial_miner", MinerBlockT4::new);
    public static final RegistryObject<Item> MINERT4_BLOCKITEM = ITEMS.register("industrial_miner", () -> new BlockItem(MINERT4_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<MinerEntityT4>> MINERT4_ENTITY = BLOCK_ENTITIES.register("industrial_miner", () -> BlockEntityType.Builder.of(MinerEntityT4::new, MINERT4_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<MinerMenu>> MINER_MENU = MENUS.register("miner", () -> IForgeMenuType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        MinerEntity blockEntity = (MinerEntity) world.getBlockEntity(pos);
        return new MinerMenu(windowId, world, pos, inv, inv.player, DataHelper.readDirectionMap(data));
    })));
    public static final RegistryObject<RecipeSerializer<MinerRecipe>> MINERRECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("miner", () -> MinerRecipe.SERIALIZER);

    public static final RegistryObject<DisenchanterBlock> DISENCHANTER_BLOCK = MACHINES.register("disenchanter", DisenchanterBlock::new);
    public static final RegistryObject<Item> DISENCHANTER_BLOCKITEM = ITEMS.register("disenchanter", () -> new BlockItem(DISENCHANTER_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<DisenchanterEntity>> DISENCHANTER_ENTITY = BLOCK_ENTITIES.register("disenchanter", () -> BlockEntityType.Builder.of(DisenchanterEntity::new, DISENCHANTER_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<DisenchanterMenu>> DISENCHANTER_MENU = MENUS.register("disenchanter", () -> IForgeMenuType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        DisenchanterEntity blockEntity = (DisenchanterEntity) world.getBlockEntity(pos);
        return new DisenchanterMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    })));

    public static final RegistryObject<DeconstructorBlock> DECONSTRUCTOR_BLOCK = MACHINES.register("deconstructor", DeconstructorBlock::new);
    public static final RegistryObject<Item> DECONSTRUCTOR_BLOCKITEM = ITEMS.register("deconstructor", () -> new BlockItem(DECONSTRUCTOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<DeconstructorEntity>> DECONSTRUCTOR_ENTITY = BLOCK_ENTITIES.register("deconstructor", () -> BlockEntityType.Builder.of(DeconstructorEntity::new, DECONSTRUCTOR_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<DeconstructorMenu>> DECONSTRUCTOR_MENU = MENUS.register("deconstructor", () -> IForgeMenuType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        DeconstructorEntity blockEntity = (DeconstructorEntity) world.getBlockEntity(pos);
        return new DeconstructorMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    })));

    public static final RegistryObject<CobblestoneSolidifierBlock> COBBLESTONESOLIDIFIER_BLOCK = MACHINES.register("cobblestone_solidifier", CobblestoneSolidifierBlock::new);
    public static final RegistryObject<Item> COBBLESTONESOLIDIFIER_BLOCKITEM = ITEMS.register("cobblestone_solidifier", () -> new BlockItem(COBBLESTONESOLIDIFIER_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<CobblestoneSolidifierEntity>> COBBLESTONESOLIDIFIER_ENTITY = BLOCK_ENTITIES.register("cobblestone_solidifier", () -> BlockEntityType.Builder.of(CobblestoneSolidifierEntity::new, COBBLESTONESOLIDIFIER_BLOCK.get()).build(null));
    public static final RegistryObject<BasaltSolidifierBlock> BASALTSOLIDIFIER_BLOCK = MACHINES.register("basalt_solidifier", BasaltSolidifierBlock::new);
    public static final RegistryObject<Item> BASALTSOLIDIFIER_BLOCKITEM = ITEMS.register("basalt_solidifier", () -> new BlockItem(BASALTSOLIDIFIER_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<BasaltSolidifierEntity>> BASALTSOLIDIFIER_ENTITY = BLOCK_ENTITIES.register("basalt_solidifier", () -> BlockEntityType.Builder.of(BasaltSolidifierEntity::new, BASALTSOLIDIFIER_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<SolidifierMenu>> SOLIDIFIER_MENU = MENUS.register("solidifier", () -> IForgeMenuType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        AbstractSolidifierEntity blockEntity = (AbstractSolidifierEntity) world.getBlockEntity(pos);
        return new SolidifierMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    })));

    public static final RegistryObject<Item> IRON_DUST = ITEMS.register("iron_dust", () -> new Item(new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<Item> GOLD_DUST = ITEMS.register("gold_dust", () -> new Item(new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register("copper_dust", () -> new Item(new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<Item> CAPACITOR = ITEMS.register("capacitor", () -> new Item(new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<Item> PLANT_DUST = ITEMS.register("plant_dust", () -> new Item(new Item.Properties().tab(MCULIB_ITEM_GROUP)));


    public static final RegistryObject<PackagerBlock> AUTOPACKAGER_BLOCK = MACHINES.register("autopackager", PackagerBlock::new);
    public static final RegistryObject<Item> AUTOPACKAGER_BLOCKITEM = ITEMS.register("autopackager", () -> new BlockItem(AUTOPACKAGER_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<PackagerEntity>> AUTOPACKAGER_ENTITY = BLOCK_ENTITIES.register("autopackager", () -> BlockEntityType.Builder.of(PackagerEntity::new, AUTOPACKAGER_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<PackagerMenu>> AUTOPACKAGER_MENU = MENUS.register("autopackager", () -> IForgeMenuType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        PackagerEntity blockEntity = (PackagerEntity) world.getBlockEntity(pos);
        return new PackagerMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    })));
    public static final RegistryObject<Item> SLATE_2x2 = PATTERNS.register("slate_2x2", () -> new Pattern2x2Item(new Item.Properties().tab(MCULIB_ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SLATE_3x3 = PATTERNS.register("slate_3x3", () -> new Pattern3x3Item(new Item.Properties().tab(MCULIB_ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SLATE_CROSS = PATTERNS.register("slate_cross", () -> new PatternCrossItem(new Item.Properties().tab(MCULIB_ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SLATE_DOOR = PATTERNS.register("slate_door", () -> new PatternDoorItem(new Item.Properties().tab(MCULIB_ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SLATE_HOLLOW = PATTERNS.register("slate_hollow", () -> new PatternHollowItem(new Item.Properties().tab(MCULIB_ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SLATE_SLAB = PATTERNS.register("slate_slab", () -> new PatternSlabItem(new Item.Properties().tab(MCULIB_ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SLATE_STAIR = PATTERNS.register("slate_stair", () -> new PatternStairItem(new Item.Properties().tab(MCULIB_ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SLATE_UNPACKAGE = PATTERNS.register("slate_unpackage", () -> new PatternUnpackageItem(new Item.Properties().tab(MCULIB_ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SLATE_WALL = PATTERNS.register("slate_wall", () -> new PatternWallItem(new Item.Properties().tab(MCULIB_ITEM_GROUP).stacksTo(1)));
}
