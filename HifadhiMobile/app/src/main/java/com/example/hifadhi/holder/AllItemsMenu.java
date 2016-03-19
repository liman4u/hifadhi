package com.example.hifadhi.holder;

import java.util.Vector;

import com.example.hifadhi.models.ItemMenuList;


public class AllItemsMenu {
	public static Vector<ItemMenuList> allItemsMenuList = new Vector<ItemMenuList>();

	public static Vector<ItemMenuList> getAllItemsMenu() {
		return AllItemsMenu.allItemsMenuList;
	}

	public static void setAllItemsMenu(Vector<ItemMenuList> allCityMenuList) {
		AllItemsMenu.allItemsMenuList = allCityMenuList;
	}

	public static ItemMenuList getItemsMenuList(int pos) {
		return AllItemsMenu.allItemsMenuList.elementAt(pos);
	}

	public static void setItemsMenuList(ItemMenuList list) {
		AllItemsMenu.allItemsMenuList.addElement(list);
	}

	public static void removeAll() {
		AllItemsMenu.allItemsMenuList.removeAllElements();
	}

}
