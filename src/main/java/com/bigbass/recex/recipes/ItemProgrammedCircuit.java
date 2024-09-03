package com.bigbass.recex.recipes;

import com.bigbass.recex.recipes.ingredients.Item;

public class ItemProgrammedCircuit extends Item {

    /** circuit config */
    public int cfg;

    public ItemProgrammedCircuit() {

    }

    public ItemProgrammedCircuit(Item item, int cfg) {
        super(item.a, cfg, item.uN, item.lN);

        this.cfg = cfg;
    }
}
