package com.mcupdater.procenhance.datagen;

import com.mcupdater.mculib.block.AbstractMachineBlock;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.blocks.battery.BatteryBlock;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class ModBlockStateProvider extends BlockStateProvider {

    private final ExistingFileHelper existingFileHelper;

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ProcessEnhancement.MODID, exFileHelper);
        this.existingFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        machine(Registration.CRUDEGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.SMOOTH_STONE, "block/generator", true);
        machine(Registration.BASICGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.BRICKS, "block/generator", true);
        machine(Registration.INTERGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.IRON_BLOCK, Blocks.BRICKS, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/generator", true);
        machine(Registration.ADVGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.GOLD_BLOCK, Blocks.BRICKS, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/generator", true);
        machine(Registration.INDGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.DIAMOND_BLOCK, Blocks.BRICKS, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/generator", true);
        machine(Registration.BASICLAVAGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.NETHER_BRICKS, "block/generator", true);
        machine(Registration.INTERLAVAGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.IRON_BLOCK, Blocks.NETHER_BRICKS, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/generator", true);
        machine(Registration.ADVLAVAGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.GOLD_BLOCK, Blocks.NETHER_BRICKS, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/generator", true);
        machine(Registration.INDLAVAGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.DIAMOND_BLOCK, Blocks.NETHER_BRICKS, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/generator", true);
        machine(Registration.BASICBIOGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.DEEPSLATE_BRICKS, "block/generator", true);
        machine(Registration.INTERBIOGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.IRON_BLOCK, Blocks.DEEPSLATE_BRICKS, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/generator", true);
        machine(Registration.ADVBIOGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.GOLD_BLOCK, Blocks.DEEPSLATE_BRICKS, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/generator", true);
        machine(Registration.INDBIOGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.DIAMOND_BLOCK, Blocks.DEEPSLATE_BRICKS, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/generator", true);
        machine(Registration.FURNACET1_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.COPPER_BLOCK, Blocks.STONE, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/electric_furnace", true);
        machine(Registration.FURNACET2_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.IRON_BLOCK, Blocks.STONE, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/electric_furnace", true);
        machine(Registration.FURNACET3_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.GOLD_BLOCK, Blocks.STONE, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/electric_furnace", true);
        machine(Registration.FURNACET4_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.DIAMOND_BLOCK, Blocks.STONE, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/electric_furnace", true);
        machine(Registration.SAWMILL_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.OAK_PLANKS, "block/sawmill", false);
        machine(Registration.GRINDERT1_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.IRON_BLOCK, "block/grinder", false);
        machine(Registration.GRINDERT2_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/grinder", false);
        machine(Registration.GRINDERT3_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/grinder", false);
        machine(Registration.GRINDERT4_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.DIAMOND_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/grinder", false);
        machine(Registration.STONECUTTER_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.STONE_BRICKS, "block/sawmill", false);
        machine(Registration.BUFFER_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.SPRUCE_PLANKS, "block/buffer", false);
        machine(Registration.TANKT1_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.IRON_BLOCK, "block/tank", false);
        machine(Registration.TANKT2_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/tank", false);
        machine(Registration.TANKT3_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/tank", false);
        machine(Registration.TANKT4_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.DIAMOND_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/tank", false);
        machine(Registration.PUMPT1_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.COPPER_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/pump", true);
        machine(Registration.PUMPT2_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/pump", true);
        machine(Registration.PUMPT3_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/pump", true);
        machine(Registration.PUMPT4_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.DIAMOND_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/pump", true);
        machine(Registration.MINERT1_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.COPPER_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/miner", true);
        machine(Registration.MINERT2_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/miner", true);
        machine(Registration.MINERT3_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/miner", true);
        machine(Registration.MINERT4_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.DIAMOND_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/miner", true);
        machine(Registration.DISENCHANTER_BLOCK.get(),Blocks.LAPIS_BLOCK,Blocks.AMETHYST_BLOCK, Blocks.POLISHED_BLACKSTONE, Blocks.NETHER_PORTAL, Blocks.LAPIS_BLOCK, "block/disenchanter", false);
        machine(Registration.DECONSTRUCTOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.AMETHYST_BLOCK, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/deconstructor", false);
        machine(Registration.AUTOPACKAGER_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.COPPER_BLOCK,Blocks.SMOOTH_STONE,Blocks.BLACK_CONCRETE, Blocks.COPPER_BLOCK, "block/crafter", false);
        solidifier(Registration.COBBLESTONESOLIDIFIER_BLOCK.get(),Blocks.COPPER_BLOCK,blockTexture(Blocks.COBBLESTONE), "block/solidifier", new ResourceLocation("minecraft", "block/water_flow"), new ResourceLocation("minecraft", "block/lava_flow"));
        solidifier(Registration.BASALTSOLIDIFIER_BLOCK.get(),Blocks.COPPER_BLOCK,new ResourceLocation("minecraft","block/basalt_side"), "block/solidifier", blockTexture(Blocks.BLUE_ICE), new ResourceLocation("minecraft", "block/lava_flow"));

        horizontalBlock(Registration.BASICBATTERY_BLOCK.get(), (blockState -> {
            int charge = blockState.getValue(BatteryBlock.CHARGE_LEVEL);
            return models().getBuilder(ForgeRegistries.BLOCKS.getKey(Registration.BASICBATTERY_BLOCK.get()).getPath() + (charge > 0 ? Integer.toString(charge) : ""))
                    .parent(new ModelFile.UncheckedModelFile("mculib:block/machine"))
                    .texture("frame", blockTexture(Blocks.COPPER_BLOCK))
                    .texture("corner", blockTexture(Blocks.COPPER_BLOCK))
                    .texture("face",blockTexture(Blocks.IRON_BLOCK))
                    .texture("inset", blockTexture(Blocks.BLACK_CONCRETE))
                    .texture("overlay", new ResourceLocation(ProcessEnhancement.MODID, "block/battery" + blockState.getValue(BatteryBlock.CHARGE_LEVEL)))
                    .texture("particle", blockTexture(Blocks.COPPER_BLOCK));
        }));
        horizontalBlock(Registration.INTBATTERY_BLOCK.get(), (blockState -> {
            int charge = blockState.getValue(BatteryBlock.CHARGE_LEVEL);
            return models().getBuilder(ForgeRegistries.BLOCKS.getKey(Registration.INTBATTERY_BLOCK.get()).getPath() + (charge > 0 ? Integer.toString(charge) : ""))
                    .parent(new ModelFile.UncheckedModelFile("mculib:block/machine"))
                    .texture("frame", blockTexture(Blocks.COPPER_BLOCK))
                    .texture("corner", blockTexture(Blocks.IRON_BLOCK))
                    .texture("face",blockTexture(Blocks.IRON_BLOCK))
                    .texture("inset", blockTexture(Blocks.BLACK_CONCRETE))
                    .texture("overlay", new ResourceLocation(ProcessEnhancement.MODID, "block/battery" + blockState.getValue(BatteryBlock.CHARGE_LEVEL)))
                    .texture("particle", blockTexture(Blocks.COPPER_BLOCK));
        }));
        horizontalBlock(Registration.ADVBATTERY_BLOCK.get(), (blockState -> {
            int charge = blockState.getValue(BatteryBlock.CHARGE_LEVEL);
            return models().getBuilder(ForgeRegistries.BLOCKS.getKey(Registration.ADVBATTERY_BLOCK.get()).getPath() + (charge > 0 ? Integer.toString(charge) : ""))
                    .parent(new ModelFile.UncheckedModelFile("mculib:block/machine"))
                    .texture("frame", blockTexture(Blocks.COPPER_BLOCK))
                    .texture("corner", blockTexture(Blocks.GOLD_BLOCK))
                    .texture("face",blockTexture(Blocks.IRON_BLOCK))
                    .texture("inset", blockTexture(Blocks.BLACK_CONCRETE))
                    .texture("overlay", new ResourceLocation(ProcessEnhancement.MODID, "block/battery" + blockState.getValue(BatteryBlock.CHARGE_LEVEL)))
                    .texture("particle", blockTexture(Blocks.COPPER_BLOCK));
        }));
        horizontalBlock(Registration.INDBATTERY_BLOCK.get(), (blockState -> {
            int charge = blockState.getValue(BatteryBlock.CHARGE_LEVEL);
            return models().getBuilder(ForgeRegistries.BLOCKS.getKey(Registration.INDBATTERY_BLOCK.get()).getPath() + (charge > 0 ? Integer.toString(charge) : ""))
                    .parent(new ModelFile.UncheckedModelFile("mculib:block/machine"))
                    .texture("frame", blockTexture(Blocks.COPPER_BLOCK))
                    .texture("corner", blockTexture(Blocks.DIAMOND_BLOCK))
                    .texture("face",blockTexture(Blocks.IRON_BLOCK))
                    .texture("inset", blockTexture(Blocks.BLACK_CONCRETE))
                    .texture("overlay", new ResourceLocation(ProcessEnhancement.MODID, "block/battery" + blockState.getValue(BatteryBlock.CHARGE_LEVEL)))
                    .texture("particle", blockTexture(Blocks.COPPER_BLOCK));
        }));
    }

    private void solidifier(Block block, Block frame, ResourceLocation face, String overlay, ResourceLocation left, ResourceLocation right) {
        horizontalBlock(block, (blockstate -> {
            return models().getBuilder(ForgeRegistries.BLOCKS.getKey(block).getPath())
                    .parent(new ModelFile.UncheckedModelFile("processenhancement:block/solidifier"))
                    .texture("frame", blockTexture(frame))
                    .texture("corner", blockTexture(frame))
                    .texture("face", face)
                    .texture("inset", blockTexture(Blocks.BLACK_CONCRETE))
                    .texture("overlay", new ResourceLocation(ProcessEnhancement.MODID, overlay))
                    .texture("overlay_left", left)
                    .texture("overlay_right", right)
                    .texture("particle", blockTexture(frame));
        }));
    }

    public ResourceLocation itemTexture(Item item) {
        ResourceLocation name = ForgeRegistries.ITEMS.getKey(item);
        return new ResourceLocation(name.getNamespace(), ModelProvider.ITEM_FOLDER + "/" + name.getPath());
    }

    protected void machine(@NotNull Block block, Block frame, Block face, String overlay, boolean splitActiveTextures) {
        machine(block, frame, frame, face, Blocks.BLACK_CONCRETE, frame, overlay, splitActiveTextures);
    }

    protected void machine(@NotNull Block block, Block frame, Block corner, Block face, Block inset, Block particle, String overlay, boolean splitActiveTextures) {
        horizontalBlock(block, (blockstate -> {
            boolean active = blockstate.getValue(AbstractMachineBlock.ACTIVE);
            return models().getBuilder(ForgeRegistries.BLOCKS.getKey(block).getPath() + (splitActiveTextures ? (active ? "_on" : "") : ""))
                    .parent(new ModelFile.UncheckedModelFile("mculib:block/machine"))
                    .texture("frame", blockTexture(frame))
                    .texture("corner", blockTexture(corner))
                    .texture("face", blockTexture(face))
                    .texture("inset", blockTexture(inset))
                    .texture("overlay", new ResourceLocation(ProcessEnhancement.MODID, overlay + (splitActiveTextures ? (active ? "_on" : "_off") : "")))
                    .texture("particle", blockTexture(particle));
        }
        ));
    }
}
