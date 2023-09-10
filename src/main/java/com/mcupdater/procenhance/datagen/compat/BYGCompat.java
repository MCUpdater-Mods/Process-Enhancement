package com.mcupdater.procenhance.datagen.compat;

import com.mcupdater.procenhance.datagen.AbstractModRecipeCompatibilityProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import org.jetbrains.annotations.NotNull;
import potionstudios.byg.common.block.BYGWoodTypes;

import java.util.function.Consumer;

public class BYGCompat extends AbstractModRecipeCompatibilityProvider {
    public BYGCompat(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> finishedRecipeConsumer) {
        for (BYGWoodTypes woodType : BYGWoodTypes.values()) {
            generateSawmillRecipes(finishedRecipeConsumer, "byg", woodType.log(), woodType.planks(), woodType.stairs(), woodType.slab(), woodType.pressurePlate(), woodType.sign(), woodType.door(), woodType.trapdoor(), woodType.strippedLog(), woodType.strippedWood(), woodType.fence(), woodType.fenceGate(), woodType.boat() != null ? woodType.boat().get() : null, woodType.button(), null);
        }
    }
}
