package com.cricketcraft.chisel.client;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.cricketcraft.chisel.Chisel;
import com.cricketcraft.chisel.init.ChiselBlocks;
import com.cricketcraft.chisel.init.ChiselItems;
import com.cricketcraft.chisel.util.IItemWithVariants;

import static com.cricketcraft.chisel.block.variant.BlockVariants.*;

@SideOnly(Side.CLIENT)
public class ModelsChisel {
	public static void prepareModelLoader() {

	}

	public static void registerModels() {
		registerBlockModels();
		registerItemModels();
	}

	private static void registerBlockModels() {
		registerBlockModelVariant(ChiselBlocks.cloud, CLOUD_NORMAL.getMeta(), "cloud_normal");
		registerBlockModelVariant(ChiselBlocks.cloud, CLOUD_GRID.getMeta(), "cloud_grid");
		registerBlockModelVariant(ChiselBlocks.cloud, CLOUD_LARGE.getMeta(), "cloud_large");
		registerBlockModelVariant(ChiselBlocks.cloud, CLOUD_SMALL.getMeta(), "cloud_small");
		registerBlockModelVariant(ChiselBlocks.cloud, CLOUD_VERTICAL.getMeta(), "cloud_vertical");
	}

	private static void registerItemModels() {
		registerItemModel(ChiselItems.cloudInABottle);
		registerItemModel(ChiselItems.smashing_rock);
		registerItemModel(ChiselItems.ballOMoss);
	}

	private static void registerBlockModel(Block block) {
		ResourceLocation resourceLocation = (ResourceLocation) Block.blockRegistry.getNameForObject(block);

		registerBlockModel(block, 0, resourceLocation.getResourcePath());
	}

	private static void registerItemModel(Item item) {
		ResourceLocation resourceLocation = (ResourceLocation) Item.itemRegistry.getNameForObject(item);

		registerItemModel(item, 0, resourceLocation.getResourcePath());
	}

	private static void registerBlockModel(Block block, int meta, String modelName) {
		registerItemModel(Item.getItemFromBlock(block), meta, modelName);
	}

	private static void registerItemModel(Item item, int meta, String resourcePath) {
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(getResource(resourcePath), "inventory");

		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, modelResourceLocation);
	}

	private static void registerBlockModelVariant(Block block, int meta, String resourcePath) {
		Item item = Item.getItemFromBlock(block);

		registerItemModel(item, meta, resourcePath);

		ModelBakery.addVariantName(item, getResource(resourcePath));
	}

	public static void registerItemModelVariant(Item item) {
		for (int i = 0; i < ((IItemWithVariants) item).getVariantNames().length; i++) {
			String NAME = item.getUnlocalizedName().substring(5) + "_" + ((IItemWithVariants) item).getVariantNames()[i];
			ModelBakery.addVariantName(item, (Chisel.MOD_ID + ":") + NAME);
			registerItemModel(item, i, NAME);
		}
	}

	public static void registerItemSubTypesModel(Item item, CreativeTabs tab) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		item.getSubItems(item, tab, list);
		for (ItemStack i : list) {
			registerItemModel(item, i.getItemDamage(), item.getUnlocalizedName().substring(5));
		}
	}

	public static String getResource(String resource) {
		return (Chisel.MOD_ID + ":") + resource;
	}
}
