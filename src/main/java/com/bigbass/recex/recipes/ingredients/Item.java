package com.bigbass.recex.recipes.ingredients;

public class Item implements IItem {

    /** amount */
    public int a;

    /** damage */
    public int m;

    /** unlocalizedName */
    public String uN;

    /** localizedName */
    public String lN;

    public Item() {

    }

    public Item(int amount, int damage, String unlocalizedName, String displayName) {
        this.a = amount;
        this.m = damage;
        this.uN = unlocalizedName;
        this.lN = displayName;
    }
}
