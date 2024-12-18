package com.mcupdater.procenhance.recipe;

import com.mcupdater.procenhance.setup.Config;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;

public final class ConfigCondition implements ICondition {
    public static final ConfigCondition INSTANCE = new ConfigCondition();
    public static MapCodec<ConfigCondition> CODEC = MapCodec.unit(INSTANCE).stable();

    public ConfigCondition() {}

    @Override
    public boolean test(IContext context) {
        return Config.GRINDER_RESOURCES.get();
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
