package com.bigbass.recex.recipes.gregtech;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.bigbass.recex.recipes.ingredients.Fluid;
import com.bigbass.recex.recipes.ingredients.Item;
import com.bigbass.recex.recipes.ingredients.ItemOreDict;

import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeUtil {

    public static Item formatRegularItemStack(ItemStack stack) {
        if (stack == null || stack.getItem() == null) {
            return null;
        }

        Item item = new Item();
        try {
        item.a = stack.stackSize;
            item.m = stack.getItemDamageForDisplay();
            item.nbt = stack.hasTagCompound() ? stack.getTagCompound()
                .toString() : null;
        } catch (NullPointerException ignored) {
        }
        try {
            GameRegistry.UniqueIdentifier uniqueIdentifier = GameRegistry.findUniqueIdentifierFor(stack.getItem());
            if (uniqueIdentifier != null) {
                item.id = uniqueIdentifier.toString();
            } else {
                item.id = stack.getUnlocalizedName();
            }
        } catch (Exception ignored) {}
        try {
            item.lN = stack.getDisplayName();
        } catch (Exception ignored) {}

        return item;
    }

    /**
     * Might return null!
     *
     * @param name
     * @return
     */
    public static ItemOreDict parseOreDictionary(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        List<Item> items = searchOreDictionary(name);
        if (items == null || items.isEmpty()) {
            return null;
        }

        ItemOreDict item = new ItemOreDict();
        item.dns.add(name);
        item.ims = items;

        return item;
    }

    /**
     * Might return null!
     *
     * @param names
     * @return
     */
    public static ItemOreDict parseOreDictionary(String[] names) {
        if (names == null || names.length == 0) {
            return null;
        }

        ItemOreDict retItem = new ItemOreDict();
        for (String name : names) {
            ItemOreDict tmpItem = parseOreDictionary(name);
            if (tmpItem != null) {
                retItem.dns.addAll(tmpItem.dns);
                retItem.ims.addAll(tmpItem.ims);
            }
        }

        return retItem;
    }

    public static Fluid formatGregtechFluidStack(FluidStack stack) {
        if (stack == null) {
            return null;
        }

        Fluid fluid = new Fluid();

        fluid.a = stack.amount;
        try {
            fluid.id = stack.getFluid()
                .getName();
        } catch (Exception e) {}
        try {
            fluid.lN = stack.getFluid()
                .getName();
        } catch (Exception e2) {
            try {
                fluid.lN = stack.getLocalizedName();
            } catch (Exception e3) {}
        }
        return fluid;
    }

    /**
     * Retrieves all items which match a given OreDictionary name.
     *
     * @param name OreDictionary name
     * @return Collection of items retrieved from the OreDictionary
     */
    public static List<Item> searchOreDictionary(String name) {
        List<ItemStack> retrievedItemStacks = OreDictionary.getOres(name);
        List<Item> retrievedItems = new ArrayList<Item>();

        for (ItemStack stack : retrievedItemStacks) {
            Item item = RecipeUtil.formatRegularItemStack(stack);
            retrievedItems.add(item);
        }
        return retrievedItems;
    }
}
