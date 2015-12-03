package com.ioptime.betty;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

import com.ioptime.betty.model.Blog;
import com.ioptime.betty.model.Cart;
import com.ioptime.betty.model.Category;
import com.ioptime.betty.model.CompanyM;
import com.ioptime.betty.model.Product;
import com.ioptime.betty.model.User;
import com.ioptime.betty.model.Vendor;
import com.ioptime.betty.model.WorldCountries;
import com.ioptime.extendablelibrary.TinyDB;

public class Appconstants {
	public static final String Server = "http://sandbox.baitymall.com/mobile_app/";
	public static final String PREFNAME = "com.ioptime.betty";
	public static final String ImageUrl = "http://sandbox.baitymall.com/image/";
	public static ArrayList<Product> productsList = new ArrayList<Product>();
	public static ArrayList<Category> categoryList = new ArrayList<Category>();
	public static ArrayList<Blog> blogArray = new ArrayList<Blog>();
	public static User user = new User();
	public static Vendor vendor = new Vendor();
	public static boolean isVendor = false;
	public static String[] allPath;

	public static final String TAB_Product = ShopFollowedFragment.class
			.getSimpleName();
	public static final String TAB_WishList = ProductListFragment.class
			.getSimpleName();
	public static final String TAB_BlogList = MyBlogsFragment.class
			.getSimpleName();
	public static final String TAB_Profile = ProfileEditFragment.class
			.getSimpleName();

	public static ArrayList<String> worldlist;
	public static ArrayList<WorldCountries> world;
	/**
	 * First contain all vendors then all the customers , customer have a
	 * 
	 * 
	 * shop_id of 0
	 */
	public static ArrayList<CompanyM> messageNamesArray = new ArrayList<CompanyM>();
	public static int customers = 0;
	public static int vendors = 0;

	public static int getUserID(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFNAME,
				Context.MODE_PRIVATE);
		int userID = prefs.getInt("userid", 0);
		return userID;
	}

	/**
	 * every item contains of first is user id , then product id seperated by
	 * commas
	 */
	public static ArrayList<Cart> getCartList(Context ctx) {
		TinyDB tinydb = new TinyDB(ctx);

		ArrayList<String> string = tinydb.getListString("cart");
		ArrayList<Cart> cart = new ArrayList<Cart>();
		if (string == null) {
			return cart;
		} else {// return for

			for (int i = 0; i < string.size(); i++) {
				// spliting list over commas and adding it into arraylist to
				// return
				String[] cartArray = string.get(i).split(",");
				cart.add(new Cart(Integer.parseInt(cartArray[0]), Integer
						.parseInt(cartArray[1])));

			}

		}

		return cart;
	}

	public static void setCartList(ArrayList<Cart> cart, Context ctx) {
		TinyDB tinydb = new TinyDB(ctx);
		ArrayList<String> string = new ArrayList<String>();
		for (int i = 0; i < cart.size(); i++) {
			// spliting list over commas and adding it into arraylist to
			// return
			string.add(cart.get(i).getProductid() + ","
					+ cart.get(i).getUser_id());

		}

		tinydb.putListString("cart", string);
	}
}
