package me.markiscool.listeners;


import lib.assets.MListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BreakBlockListener extends MListener {

    private final List<Material> smeltables = Arrays.asList(Material.IRON_ORE, Material.GOLD_ORE);

    @EventHandler
    public void onOreBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if(smeltables.contains(block.getType())) {
            event.setDropItems(false);
            event.getPlayer().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(block.getType(), 1));
            event.setExpToDrop(1);
        }
    }

}
