package com.ioptime.betty;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.iopitme.betty.vendor.MainMenuVendor;
import com.ioptime.adapters.CartAdapter;
import com.ioptime.betty.model.Cart;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class CartFragment extends IoptimeFragment {
	ListView listCart;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.cart, container, false);
		listCart = (ListView) rootView.findViewById(R.id.cartList);
		populateListView();
		return rootView;
	}

	private void populateListView() {
		ArrayList<Cart> cart = Appconstants.getCartList(getActivity()
				.getApplicationContext());

		if (cart.size() > 0) {
			CartAdapter cartAdapter = new CartAdapter(getActivity(), cart);
			listCart.setAdapter(cartAdapter);
			cartAdapter.notifyDataSetChanged();

		} else {
			Toast.makeText(getActivity(), "Nothing in cart to show",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		((MainMenu) getActivity()).getActionBar().setTitle("Cart");
	}
}
