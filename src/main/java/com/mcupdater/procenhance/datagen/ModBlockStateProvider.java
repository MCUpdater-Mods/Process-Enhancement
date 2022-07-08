package com.mcupdater.procenhance.datagen;

import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.data.DataGenerator;
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
        machine(Registration.BASICGENERATOR_BLOCK.get(),Blocks.COBBLESTONE,Blocks.COPPER_BLOCK);
        machine(Registration.FURNACE_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.STONE);
        machine(Registration.SAWMILL_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.OAK_PLANKS);
        machine(Registration.GRINDER_BLOCK.get(),Blocks.COPPER_BLOCK,Blocks.IRON_BLOCK);
    }

    protected void machine(@NotNull Block block, Block frame, Block face) {
        machine(block, frame, frame, face, Blocks.BLACK_CONCRETE, frame);
    }
    protected void machine(@NotNull Block block, Block frame, Block corner, Block face, Block inset, Block particle) {
        horizontalBlock(block, models()
                .getBuilder(block.getRegistryName().getPath())
                .parent(new ModelFile.UncheckedModelFile("mculib:block/machine"))
                .texture("frame", blockTexture(frame))
                .texture("corner", blockTexture(corner))
                .texture("face", blockTexture(face))
                .texture("inset", blockTexture(inset))
                .texture("overlay", blockTexture(block))
                .texture("particle", blockTexture(particle))
        );
    }
}
