package com.mcupdater.procenhance.setup;

import com.mcupdater.mculib.setup.ModSetup;
import com.mcupdater.procenhance.blocks.basic_generator.BasicGeneratorBlock;
import com.mcupdater.procenhance.blocks.basic_generator.BasicGeneratorEntity;
import com.mcupdater.procenhance.blocks.basic_generator.BasicGeneratorMenu;
import com.mcupdater.procenhance.blocks.furnace.ElectricFurnaceBlock;
import com.mcupdater.procenhance.blocks.furnace.ElectricFurnaceEntity;
import com.mcupdater.procenhance.blocks.furnace.ElectricFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.mcupdater.procenhance.ProcessEnhancement.MODID;

public class Registration {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<BasicGeneratorBlock> BASICGENERATOR_BLOCK = BLOCKS.register("basic_generator", BasicGeneratorBlock::new);
    public static final RegistryObject<Item> BASICGENERATOR_BLOCKITEM = ITEMS.register("basic_generator", () -> new BlockItem(BASICGENERATOR_BLOCK.get(), new Item.Properties().tab(ModSetup.MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<BasicGeneratorEntity>> BASICGENERATOR_BLOCKENTITY = TILES.register("basic_generator", () -> BlockEntityType.Builder.of(BasicGeneratorEntity::new, BASICGENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<BasicGeneratorMenu>> BASICGENERATOR_MENU = CONTAINERS.register("basic_generator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        BasicGeneratorEntity blockEntity = (BasicGeneratorEntity) world.getBlockEntity(pos);
        return new BasicGeneratorMenu(windowId, world, pos, inv, inv.player, blockEntity.data);
    }));

    public static final RegistryObject<ElectricFurnaceBlock> FURNACE_BLOCK = BLOCKS.register("electric_furnace", ElectricFurnaceBlock::new);
    public static final RegistryObject<Item> FURNACE_BLOCKITEM = ITEMS.register("electric_furnace", () -> new BlockItem(FURNACE_BLOCK.get(), new Item.Properties().tab(ModSetup.MCULIB_ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<ElectricFurnaceEntity>> FURNACE_BLOCKENTITY = TILES.register("electric_furnace", () -> BlockEntityType.Builder.of(ElectricFurnaceEntity::new, FURNACE_BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<ElectricFurnaceMenu>> FURNACE_MENU = CONTAINERS.register("electric_furnace", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        ElectricFurnaceEntity blockEntity = (ElectricFurnaceEntity) world.getBlockEntity(pos);
        return new ElectricFurnaceMenu(windowId, world, pos, inv, inv.player, blockEntity.data);
    }));
}