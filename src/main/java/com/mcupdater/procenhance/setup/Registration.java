package com.mcupdater.procenhance.setup;

import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.mculib.setup.ModSetup;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.blocks.basic_generator.BasicGeneratorBlock;
import com.mcupdater.procenhance.blocks.basic_generator.BasicGeneratorEntity;
import com.mcupdater.procenhance.blocks.basic_generator.BasicGeneratorMenu;
import com.mcupdater.procenhance.blocks.furnace.ElectricFurnaceBlock;
import com.mcupdater.procenhance.blocks.furnace.ElectricFurnaceEntity;
import com.mcupdater.procenhance.blocks.furnace.ElectricFurnaceMenu;
import com.mcupdater.procenhance.blocks.grinder.GrinderBlock;
import com.mcupdater.procenhance.blocks.grinder.GrinderEntity;
import com.mcupdater.procenhance.blocks.grinder.GrinderMenu;
import com.mcupdater.procenhance.blocks.sawmill.SawmillBlock;
import com.mcupdater.procenhance.blocks.sawmill.SawmillEntity;
import com.mcupdater.procenhance.blocks.sawmill.SawmillMenu;
import com.mcupdater.procenhance.recipe.GrinderRecipe;
import com.mcupdater.procenhance.recipe.SawmillRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
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

import java.util.HashMap;

import static com.mcupdater.mculib.setup.ModSetup.MCULIB_ITEM_GROUP;
import static com.mcupdater.procenhance.ProcessEnhancement.MODID;

public class Registration {
    public static final DeferredRegister<Block> MACHINES = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ProcessEnhancement.MODID);

    public static void init(IEventBus modEventBus) {
        MACHINES.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        CONTAINERS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
    }

    public static final RegistryObject<BasicGeneratorBlock> BASICGENERATOR_BLOCK = MACHINES.register("basic_generator", BasicGeneratorBlock::new);
    public static final RegistryObject<Item> BASICGENERATOR_BLOCKITEM = ITEMS.register("basic_generator", () -> new BlockItem(BASICGENERATOR_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<BasicGeneratorEntity>> BASICGENERATOR_BLOCKENTITY = BLOCK_ENTITIES.register("basic_generator", () -> BlockEntityType.Builder.of(BasicGeneratorEntity::new, BASICGENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<BasicGeneratorMenu>> BASICGENERATOR_MENU = CONTAINERS.register("basic_generator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        BasicGeneratorEntity blockEntity = (BasicGeneratorEntity) world.getBlockEntity(pos);
        return new BasicGeneratorMenu(windowId, world, pos, inv, inv.player, blockEntity.data);
    }));

    public static final RegistryObject<ElectricFurnaceBlock> FURNACE_BLOCK = MACHINES.register("electric_furnace", ElectricFurnaceBlock::new);
    public static final RegistryObject<Item> FURNACE_BLOCKITEM = ITEMS.register("electric_furnace", () -> new BlockItem(FURNACE_BLOCK.get(), new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<ElectricFurnaceEntity>> FURNACE_BLOCKENTITY = BLOCK_ENTITIES.register("electric_furnace", () -> BlockEntityType.Builder.of(ElectricFurnaceEntity::new, FURNACE_BLOCK.get()).build(null));
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

    public static final RegistryObject<Item> IRON_DUST = ITEMS.register("iron_dust", () -> new Item(new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<Item> GOLD_DUST = ITEMS.register("gold_dust", () -> new Item(new Item.Properties().tab(MCULIB_ITEM_GROUP)));
    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register("copper_dust", () -> new Item(new Item.Properties().tab(MCULIB_ITEM_GROUP)));

}
