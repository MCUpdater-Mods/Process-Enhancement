package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.blocks.basic_capacitor.BasicCapacitorBlock;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class ModBlockStateProvider extends BlockStateProvider {

    private final ExistingFileHelper existingFileHelper;

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ProcessEnhancement.MODID, exFileHelper);
        this.existingFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        machine(Registration.BASICGENERATOR_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.BRICKS, new ResourceLocation(ProcessEnhancement.MODID, "block/generator"));
        machine(Registration.FURNACE_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.STONE, new ResourceLocation(ProcessEnhancement.MODID, "block/electric_furnace"));
        machine(Registration.SAWMILL_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.OAK_PLANKS, new ResourceLocation(ProcessEnhancement.MODID, "block/sawmill"));
        machine(Registration.GRINDER_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.IRON_BLOCK, new ResourceLocation(ProcessEnhancement.MODID, "block/grinder"));

        horizontalBlock(Registration.BASICCAPACITOR_BLOCK.get(), (blockState -> {
            int charge = blockState.getValue(BasicCapacitorBlock.CHARGE_LEVEL);
            return models().getBuilder(Registration.BASICCAPACITOR_BLOCK.get().getRegistryName().getPath() + (charge > 0 ? Integer.toString(charge) : ""))
                    .parent(new ModelFile.UncheckedModelFile("mculib:block/machine"))
                    .texture("frame", blockTexture(Blocks.COPPER_BLOCK))
                    .texture("corner", blockTexture(Blocks.COPPER_BLOCK))
                    .texture("face",blockTexture(Blocks.IRON_BLOCK))
                    .texture("inset", blockTexture(Blocks.BLACK_CONCRETE))
                    .texture("overlay", new ResourceLocation(ProcessEnhancement.MODID, "block/capacitor" + blockState.getValue(BasicCapacitorBlock.CHARGE_LEVEL)))
                    .texture("particle", blockTexture(Blocks.COPPER_BLOCK));
        }));
    }

    protected void machine(@NotNull Block block, Block frame, Block face, ResourceLocation overlay) {
        machine(block, frame, frame, face, Blocks.BLACK_CONCRETE, frame, overlay);
    }
    protected void machine(@NotNull Block block, Block frame, Block corner, Block face, Block inset, Block particle, ResourceLocation overlay) {
        horizontalBlock(block, models()
                .getBuilder(block.getRegistryName().getPath())
                .parent(new ModelFile.UncheckedModelFile("mculib:block/machine"))
                .texture("frame", blockTexture(frame))
                .texture("corner", blockTexture(corner))
                .texture("face", blockTexture(face))
                .texture("inset", blockTexture(inset))
                .texture("overlay", overlay)
                .texture("particle", blockTexture(particle))
        );
    }
}
