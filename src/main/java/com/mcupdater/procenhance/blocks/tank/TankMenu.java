package com.mcupdater.procenhance.blocks.tank;

import com.mcupdater.mculib.block.AbstractConfigurableBlockEntity;
import com.mcupdater.mculib.block.IConfigurableMenu;
import com.mcupdater.mculib.helpers.DataHelper;
import com.mcupdater.procenhance.blocks.autopackager.PackagerEntity;
import com.mcupdater.procenhance.blocks.autopackager.PackagerMenu;
import com.mcupdater.procenhance.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class TankMenu extends AbstractContainerMenu implements IConfigurableMenu {
    private final Map<Direction, String> adjacentNames;
    private final Player player;
    private final IItemHandler playerInventory;
    private final AbstractConfigurableBlockEntity tileEntity;
    private final Container transientSlots = new SimpleContainer(2) {
        @Override
        public void setChanged() {
            super.setChanged();
            TankMenu.this.slotsChanged(this);
        }
    };

    public TankMenu(int pContainerId, Level level, BlockPos worldPosition, Inventory pPlayerInventory, Player pPlayer, Map<Direction, String> adjacentNames) {
        super(Registration.TANK_MENU.get(), pContainerId);
        this.tileEntity = level.getBlockEntity(worldPosition) instanceof TankEntity ? (TankEntity) level.getBlockEntity(worldPosition) : null;
        this.adjacentNames = adjacentNames;
        this.player = pPlayer;
        this.playerInventory = new InvWrapper(pPlayerInventory);
        this.addSlot(new Slot(this.transientSlots,0,8,16){
            @Override
            public boolean mayPlace(ItemStack pStack) {
                @Nullable IFluidHandlerItem itemFluidHandler = pStack.getCapability(Capabilities.FluidHandler.ITEM, null);
                if (itemFluidHandler != null) {
                    IFluidHandler tankFluidHandler = TankMenu.this.tileEntity.getFluidHandler().getInternalHandler();
                    return tankFluidHandler.isFluidValid(0, itemFluidHandler.getFluidInTank(0));
                }
                return false;
            }
        });
        this.addSlot(new Slot(this.transientSlots, 1, 8, 51){
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }
        });

        layoutPlayerInventorySlots(8, 84);
    }

    public static TankMenu factory(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        BlockPos pos = extraData.readBlockPos();
        Level world = playerInv.player.level();
        TankEntity te = (TankEntity) world.getBlockEntity(pos);
        return new TankMenu(containerId, world, pos, playerInv, playerInv.player, DataHelper.readDirectionMap(extraData));
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    @Override
    public AbstractConfigurableBlockEntity getBlockEntity() {
        return this.tileEntity;
    }

    @Override
    public String getSideName(Direction direction) {
        return this.adjacentNames.get(direction);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return tileEntity != null ? ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()).evaluate((level, blockPos) -> playerIn.distanceToSqr((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) <= 64.0D, true) : false;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        //TODO
        return ItemStack.EMPTY;
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()).execute((level, blockPos) -> {
            this.clearContainer(pPlayer, this.transientSlots);
        });
    }

    @Override
    public void slotsChanged(Container pContainer) {
        super.slotsChanged(pContainer);
        if (pContainer == this.transientSlots) {
            IFluidHandlerItem itemFluidHandler = this.transientSlots.getItem(0).getCapability(Capabilities.FluidHandler.ITEM, null);
            if (!this.transientSlots.getItem(0).isEmpty() && this.transientSlots.getItem(1).isEmpty() && itemFluidHandler != null) {
                IFluidHandler tankFluidHandler = TankMenu.this.tileEntity.getFluidHandler().getInternalHandler();
                if (itemFluidHandler.getFluidInTank(0).isEmpty()) {
                    // Fill item
                    FluidStack testFluidStack = tankFluidHandler.drain(tankFluidHandler.getTankCapacity(0), IFluidHandler.FluidAction.SIMULATE);
                    int fillAmount = itemFluidHandler.fill(testFluidStack, IFluidHandler.FluidAction.SIMULATE);
                    if (fillAmount > 0) {
                        itemFluidHandler.fill(testFluidStack, IFluidHandler.FluidAction.EXECUTE);
                        tankFluidHandler.drain(fillAmount, IFluidHandler.FluidAction.EXECUTE);
                        this.transientSlots.setItem(1, itemFluidHandler.getContainer());
                        this.transientSlots.setItem(0, ItemStack.EMPTY);
                    }
                } else {
                    if (itemFluidHandler.getFluidInTank(0).getFluid().isSame(tankFluidHandler.getFluidInTank(0).getFluid()) || tankFluidHandler.getFluidInTank(0).isEmpty()) {
                        // Drain item
                        FluidStack testFluidStack = itemFluidHandler.drain(itemFluidHandler.getTankCapacity(0), IFluidHandler.FluidAction.SIMULATE);
                        int fillAmount = tankFluidHandler.fill(testFluidStack, IFluidHandler.FluidAction.SIMULATE);
                        if (fillAmount > 0) {
                            tankFluidHandler.fill(testFluidStack, IFluidHandler.FluidAction.EXECUTE);
                            itemFluidHandler.drain(fillAmount, IFluidHandler.FluidAction.EXECUTE);
                            this.transientSlots.setItem(1, itemFluidHandler.getContainer().copy());
                            this.transientSlots.setItem(0, ItemStack.EMPTY);
                        }
                    } else {
                        // Reject item
                        this.transientSlots.setItem(1, this.transientSlots.getItem(0).copy());
                        this.transientSlots.setItem(0, ItemStack.EMPTY);
                    }
                }
            }
        }
    }
}
