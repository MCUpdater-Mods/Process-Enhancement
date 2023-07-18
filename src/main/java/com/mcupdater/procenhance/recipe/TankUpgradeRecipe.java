package com.mcupdater.procenhance.recipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mcupdater.procenhance.ProcessEnhancement;
import com.mcupdater.procenhance.blocks.tank.TankBlockItem;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TankUpgradeRecipe extends ShapedRecipe {

    public static final Serializer SERIALIZER = new Serializer();

    public TankUpgradeRecipe(ResourceLocation pId, String pGroup, int pWidth, int pHeight, NonNullList<Ingredient> pRecipeItems, ItemStack pResult) {
        super(pId, pGroup, pWidth, pHeight, pRecipeItems, pResult);
    }

    @Override
    public ItemStack assemble(CraftingContainer pInv) {
        ItemStack outputStack = super.assemble(pInv);
        getMachine(pInv).flatMap(tank -> Optional.ofNullable(tank.getTag())).ifPresent(tag -> outputStack.setTag(tag.copy()));
        return outputStack;
    }

    private Optional<ItemStack> getMachine(CraftingContainer pInv) {
        for (int slot = 0; slot < pInv.getContainerSize(); slot++) {
            ItemStack slotStack = pInv.getItem(slot);
            if (slotStack.getItem() instanceof TankBlockItem) {
                return Optional.of(slotStack);
            }
        }
        return Optional.of(ItemStack.EMPTY);
    }

    static Map<String, Ingredient> keyFromJson(JsonObject pKeyEntry) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : pKeyEntry.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    private static Map<String, Ingredient> mapFromJson(JsonObject pKeyEntry) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : pKeyEntry.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static String[] patternFromJson(JsonArray pArray) {
        String[] patternArray = new String[pArray.size()];
        if (patternArray.length > 3) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
        } else if (patternArray.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for(int rowIndex = 0; rowIndex < patternArray.length; ++rowIndex) {
                String row = GsonHelper.convertToString(pArray.get(rowIndex), "pattern[" + rowIndex + "]");
                if (row.length() > 3) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
                }

                if (rowIndex > 0 && patternArray[0].length() != row.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                patternArray[rowIndex] = row;
            }
            return patternArray;
        }
    }

    static NonNullList<Ingredient> dissolvePattern(String[] pPattern, Map<String, Ingredient> pIngredientMap, int pWidth, int pHeight) {
        NonNullList<Ingredient> recipe = NonNullList.withSize(pWidth * pHeight, Ingredient.EMPTY);
        Set<String> keySet = Sets.newHashSet(pIngredientMap.keySet());
        keySet.remove(" ");

        for (int rowIndex = 0; rowIndex < pPattern.length; ++ rowIndex) {
            for (int colIndex = 0; colIndex < pPattern[rowIndex].length(); ++colIndex) {
                String slotKey = pPattern[rowIndex].substring(colIndex,colIndex+1);
                Ingredient ingredient = pIngredientMap.get(slotKey);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + slotKey + "' but it's not defined in the key");
                }

                keySet.remove(slotKey);
                recipe.set(colIndex + pWidth * rowIndex, ingredient);
            }
        }

        if (!keySet.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + keySet);
        } else {
            return recipe;
        }
    }

    public static class Serializer implements RecipeSerializer<TankUpgradeRecipe> {
        public static final ResourceLocation ID = new ResourceLocation(ProcessEnhancement.MODID, "tank_upgrade");

        @Override
        public TankUpgradeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            Map<String, Ingredient> ingredientMap = TankUpgradeRecipe.mapFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "key"));
            String[] patternArray = TankUpgradeRecipe.patternFromJson(GsonHelper.getAsJsonArray(pSerializedRecipe, "pattern"));
            int width = patternArray[0].length();
            int height = patternArray.length;
            NonNullList<Ingredient> recipe = TankUpgradeRecipe.dissolvePattern(patternArray, ingredientMap, width, height);
            String group = GsonHelper.getAsString(pSerializedRecipe, "group", "");
            return new TankUpgradeRecipe(pRecipeId, group, width, height, recipe, output);
        }

        @Nullable
        @Override
        public TankUpgradeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            int width = pBuffer.readVarInt();
            int height = pBuffer.readVarInt();
            String group = pBuffer.readUtf();
            NonNullList<Ingredient> recipe = NonNullList.withSize(width * height, Ingredient.EMPTY);

            for(int slot = 0; slot < recipe.size(); ++ slot) {
                recipe.set(slot, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new TankUpgradeRecipe(pRecipeId, group, width, height, recipe, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, TankUpgradeRecipe pRecipe) {
            pBuffer.writeVarInt(pRecipe.getRecipeWidth());
            pBuffer.writeVarInt(pRecipe.getRecipeHeight());
            pBuffer.writeUtf(pRecipe.getGroup());

            for(Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItem(pRecipe.getResultItem());
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return TankUpgradeRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }

}
