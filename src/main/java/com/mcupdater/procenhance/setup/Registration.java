package com.mcupdater.procenhance.setup;

import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.blocks.battery.*;
import com.mcupdater.procenhance.blocks.buffer.BufferBlock;
import com.mcupdater.procenhance.blocks.buffer.BufferEntity;
import com.mcupdater.procenhance.blocks.buffer.BufferMenu;
import com.mcupdater.procenhance.blocks.copper_wire.CopperWireBlock;
import com.mcupdater.procenhance.blocks.copper_wire.CopperWireEntity;
import com.mcupdater.procenhance.blocks.crude_generator.CrudeGeneratorBlock;
import com.mcupdater.procenhance.blocks.crude_generator.CrudeGeneratorEntity;
import com.mcupdater.procenhance.blocks.crude_generator.CrudeGeneratorMenu;
import com.mcupdater.procenhance.blocks.furnace.ElectricFurnaceBlock;
import com.mcupdater.procenhance.blocks.furnace.ElectricFurnaceEntity;
import com.mcupdater.procenhance.blocks.furnace.ElectricFurnaceMenu;
import com.mcupdater.procenhance.blocks.generator.*;
import com.mcupdater.procenhance.blocks.grinder.GrinderBlock;
import com.mcupdater.procenhance.blocks.grinder.GrinderEntity;
import com.mcupdater.procenhance.blocks.grinder.GrinderMenu;
import com.mcupdater.procenhance.blocks.sawmill.SawmillBlock;
import com.mcupdater.procenhance.blocks.sawmill.SawmillEntity;
import com.mcupdater.procenhance.blocks.sawmill.SawmillMenu;
import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterBlock;
import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterEntity;
import com.mcupdater.procenhance.blocks.stonecutter.ElectricStonecutterMenu;
import com.mcupdater.procenhance.recipe.GrinderRecipe;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
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
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ProcessEnhancement.MODID);

    public static void init(IEventBus modEventBus) {
        MACHINES.register(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        CONTAINERS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
    }
    public static final RegistryObject<CrudeGeneratorBlock> CRUDEGENERATOR_BLOCK = MACHINES.register("crude_generator", CrudeGeneratorBlock::new);
    public static final RegistryObject<Item> CRUDEGENERATOR_BLOCKITEM = ITEMS.register("crude_generator", () -> new BlockItem(CRUDEGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<CrudeGeneratorEntity>> CRUDEGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("crude_generator", () -> BlockEntityType.Builder.of(CrudeGeneratorEntity::new, CRUDEGENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<CrudeGeneratorMenu>> CRUDEGENERATOR_MENU = CONTAINERS.register("crude_generator", () -> IForgeMenuType.create((windowId, inv, data) -> {
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

    public static final RegistryObject<MenuType<GeneratorMenu>> GENERATOR_MENU = CONTAINERS.register("generator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        GeneratorEntity blockEntity = (GeneratorEntity) world.getBlockEntity(pos);
        return new GeneratorMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    }));

    public static final RegistryObject<BatteryBlockT1> BASICBATTERY_BLOCK = MACHINES.register("basic_battery", BatteryBlockT1::new);
    public static final RegistryObject<Item> BASICBATTERY_BLOCKITEM = ITEMS.register("basic_battery", () -> new BlockItem(BASICBATTERY_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<BatteryEntityT1>> BASICBATTERY_BLOCKENTITY = BLOCK_ENTITIES.register("basic_battery", () -> BlockEntityType.Builder.of(BatteryEntityT1::new, BASICBATTERY_BLOCK.get()).build(null) );
    public static final RegistryObject<BatteryBlockT2> INTBATTERY_BLOCK = MACHINES.register("intermediate_battery", BatteryBlockT2::new);
    public static final RegistryObject<Item> INTBATTERY_BLOCKITEM = ITEMS.register("intermediate_battery", () -> new BlockItem(INTBATTERY_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<BatteryEntityT2>> INTBATTERY_BLOCKENTITY = BLOCK_ENTITIES.register("intermediate_battery", () -> BlockEntityType.Builder.of(BatteryEntityT2::new, INTBATTERY_BLOCK.get()).build(null) );
    public static final RegistryObject<BatteryBlockT3> ADVBATTERY_BLOCK = MACHINES.register("advanced_battery", BatteryBlockT3::new);
    public static final RegistryObject<Item> ADVBATTERY_BLOCKITEM = ITEMS.register("advanced_battery", () -> new BlockItem(ADVBATTERY_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<BatteryEntityT3>> ADVBATTERY_BLOCKENTITY = BLOCK_ENTITIES.register("advanced_battery", () -> BlockEntityType.Builder.of(BatteryEntityT3::new, ADVBATTERY_BLOCK.get()).build(null) );
    public static final RegistryObject<BatteryBlockT4> INDBATTERY_BLOCK = MACHINES.register("industrial_battery", BatteryBlockT4::new);
    public static final RegistryObject<Item> INDBATTERY_BLOCKITEM = ITEMS.register("industrial_battery", () -> new BlockItem(INDBATTERY_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<BatteryEntityT4>> INDBATTERY_BLOCKENTITY = BLOCK_ENTITIES.register("industrial_battery", () -> BlockEntityType.Builder.of(BatteryEntityT4::new, INDBATTERY_BLOCK.get()).build(null) );
    public static final RegistryObject<MenuType<BatteryMenu>> BATTERY_MENU = CONTAINERS.register("battery", () -> IForgeMenuType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.level;
        return new BatteryMenu(windowId, level, pos, inv, inv.player, DataHelper.readDirectionMap(data));
    })));

    public static final RegistryObject<ElectricFurnaceBlock> FURNACE_BLOCK = MACHINES.register("electric_furnace", ElectricFurnaceBlock::new);
    public static final RegistryObject<Item> FURNACE_BLOCKITEM = ITEMS.register("electric_furnace", () -> new BlockItem(FURNACE_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<ElectricFurnaceEntity>> FURNACE_BLOCKENTITY = BLOCK_ENTITIES.register("electric_furnace", () -> {
        return BlockEntityType.Builder.of(ElectricFurnaceEntity::new, FURNACE_BLOCK.get()).build(null);
    });
    public static final RegistryObject<MenuType<ElectricFurnaceMenu>> FURNACE_MENU = CONTAINERS.register("electric_furnace", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        ElectricFurnaceEntity blockEntity = (ElectricFurnaceEntity) world.getBlockEntity(pos);
        return new ElectricFurnaceMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    }));

    public static final RegistryObject<SawmillBlock> SAWMILL_BLOCK = MACHINES.register("sawmill", SawmillBlock::new);
    public static final RegistryObject<Item> SAWMILL_BLOCKITEM = ITEMS.register("sawmill", () -> new BlockItem(SAWMILL_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<SawmillEntity>> SAWMILL_BLOCKENTITY = BLOCK_ENTITIES.register("sawmill", () -> BlockEntityType.Builder.of(SawmillEntity::new, SAWMILL_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<SawmillMenu>> SAWMILL_MENU = CONTAINERS.register("sawmill", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        SawmillEntity blockEntity = (SawmillEntity) world.getBlockEntity(pos);
        return new SawmillMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    }));
    public static final RegistryObject<RecipeSerializer<SawmillRecipe>> SAWMILL_SERIALIZER = RECIPE_SERIALIZERS.register("sawmill",() -> SawmillRecipe.Serializer.INSTANCE);

    public static final RegistryObject<GrinderBlock> GRINDER_BLOCK = MACHINES.register("grinder", GrinderBlock::new);
    public static final RegistryObject<Item> GRINDER_BLOCKITEM = ITEMS.register("grinder", () -> new BlockItem(GRINDER_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<GrinderEntity>> GRINDER_BLOCKENTITY = BLOCK_ENTITIES.register("grinder", () -> BlockEntityType.Builder.of(GrinderEntity::new, GRINDER_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<GrinderMenu>> GRINDER_MENU = CONTAINERS.register("grinder", () -> IForgeMenuType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        GrinderEntity blockEntity = (GrinderEntity) world.getBlockEntity(pos);
        return new GrinderMenu(windowId, world, pos, inv, inv.player, blockEntity.data, DataHelper.readDirectionMap(data));
    })));
    public static final RegistryObject<RecipeSerializer<GrinderRecipe>> GRINDER_SERIALIZER = RECIPE_SERIALIZERS.register("grinder", () -> GrinderRecipe.Serializer.INSTANCE);

    public static final RegistryObject<ElectricStonecutterBlock> STONECUTTER_BLOCK = MACHINES.register("stonecutter", ElectricStonecutterBlock::new);
    public static final RegistryObject<Item> STONECUTTER_BLOCKITEM = ITEMS.register("stonecutter", () -> new BlockItem(STONECUTTER_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<ElectricStonecutterEntity>> STONECUTTER_ENTITY = BLOCK_ENTITIES.register("stonecutter", () -> BlockEntityType.Builder.of(ElectricStonecutterEntity::new, STONECUTTER_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<ElectricStonecutterMenu>> STONECUTTER_MENU = CONTAINERS.register("stonecutter", () -> IForgeMenuType.create((windowId, inv, data) -> {
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
    public static final RegistryObject<MenuType<BufferMenu>> BUFFER_MENU = CONTAINERS.register("buffer", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.level;
        BufferEntity blockEntity = (BufferEntity) level.getBlockEntity(pos);
        return new BufferMenu(windowId, level, pos, inv, inv.player, DataHelper.readDirectionMap(data));
    }));

    public static final RegistryObject<Item> IRON_DUST = ITEMS.register("iron_dust", () -> new Item(new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<Item> GOLD_DUST = ITEMS.register("gold_dust", () -> new Item(new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register("copper_dust", () -> new Item(new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<Item> CAPACITOR = ITEMS.register("capacitor", () -> new Item(new Item.Properties().tab(MCULIB_ITEM_GROUP)));

}
