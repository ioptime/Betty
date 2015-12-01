package com.ioptime.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.image.loader.ImageLoader;
import com.ioptime.betty.Appconstants;
import com.ioptime.betty.MainMenu;
import com.ioptime.betty.R;
import com.ioptime.betty.model.Cart;
import com.ioptime.betty.model.Product;

public class CartAdapter extends BaseAdapter {

	private Activity activity;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	ArrayList<Cart> cartList;

	public CartAdapter(Activity a, ArrayList<Cart> cartlist) {
		activity = a;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext(),
				R.drawable.ic_launcher);
		cartList = new ArrayList<Cart>();

		Log.d("cart", "-" + cartlist.size());

		for (int i = 0; i < cartlist.size(); i++) {
			if (Collections.frequency(cartlist, cartlist.get(i)) == 1) {
				Cart c = cartlist.get(i);
				c.setQty(1);
				cartList.add(c);
			} else {
				Cart c = cartlist.get(i);
				int freq = Collections.frequency(cartlist, c);
				c.setQty(freq);
				cartList.add(c);
			}
		}

		// removing duplicates from list
		cartList = new ArrayList<Cart>(new LinkedHashSet<Cart>(cartList));

	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public int getCount() {
		return cartList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.item_cart, null);

		//
		try {

			LinearLayout layout = (LinearLayout) vi.findViewById(R.id.cartList);
			TextView textName = (TextView) vi.findViewById(R.id.cartListName);
			TextView textStoreName = (TextView) vi
					.findViewById(R.id.cartListStore);
			TextView textPrice = (TextView) vi.findViewById(R.id.cartListPrice);
			TextView textQty = (TextView) vi.findViewById(R.id.cartListQty);
			TextView textTotPrice = (TextView) vi
					.findViewById(R.id.cartListTPrice);
			ImageView image = (ImageView) vi.findViewById(R.id.cartImage);
			TextView tvRemove = (TextView) vi.findViewById(R.id.cartListRemove);

			tvRemove.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					cartList.get(position).setQty(
							cartList.get(position).getQty() - 1);
					removeFromCart(cartList.get(position).getProductid());
					if (cartList.get(position).getQty() == 0) {
						// remove element
						cartList.remove(position);
					}
					notifyDataSetChanged();
				}
			});

			int index = Collections.binarySearch(Appconstants.productsList,
					new Product(cartList.get(position).getProductid(), true),
					Product.c);

			if (index >= 0) {
				textName.setText(Appconstants.productsList.get(index).getName());
				textStoreName.setText(Appconstants.productsList.get(index)
						.getStoreName());
				textPrice.setText(""
						+ Appconstants.productsList.get(index).getPrice());

				textTotPrice.setText(" = " + cartList.get(position).getQty()
						* Appconstants.productsList.get(index).getPrice());
				imageLoader.DisplayImage(Appconstants.productsList.get(index)
						.getImage(), image, R.drawable.ic_launcher);

			}
			textQty.setText("X " + cartList.get(position).getQty());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vi;
	}

	protected void removeFromCart(int prod_id) {

		ArrayList<Cart> cartTemp = Appconstants.getCartList(activity
				.getApplicationContext());

		for (int i = 0; i < cartTemp.size(); i++) {
			Cart c = cartTemp.get(i);
			if (c.getProductid() == prod_id
					&& c.getUser_id() == Appconstants.user.getCustomer_id()) {
				cartTemp.remove(i);
				break;
			}
		}
		Appconstants.setCartList(cartTemp, activity.getApplicationContext());
		((MainMenu) activity).updateCartCount(cartTemp.size());
	}
}
