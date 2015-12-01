package com.iopitme.betty.vendor;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.ioptime.betty.Appconstants;
import com.ioptime.betty.R;
import com.ioptime.extendablelibrary.IoptimeFragment;
import com.ioptime.selectimages.Action;
import com.ioptime.selectimages.CustomGallery;
import com.ioptime.selectimages.GalleryAdapter;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class AddNewProductFragment extends IoptimeFragment {
	Handler handler;
	ImageLoader imageLoader;
	ViewSwitcher viewSwitcher;
	ExpandableHeightGridView grid;
	GalleryAdapter adapter;
	View rootView;
	Button takePicBtn;
	ImageView imgSinglePick;
	int position;
	EditText catET, nameET, modelET, priceET, QuantityET, manET, decET;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.add_new_product, container, false);
		Button btnSave = (Button) rootView.findViewById(R.id.saveBtnVendor);
		catET = (EditText) rootView.findViewById(R.id.addPd_CatET);
		nameET = (EditText) rootView.findViewById(R.id.addPd_NameET);
		modelET = (EditText) rootView.findViewById(R.id.addPd_ModelET);
		QuantityET = (EditText) rootView.findViewById(R.id.addPd_QuantityET);
		manET = (EditText) rootView.findViewById(R.id.addPd_ManfET);
		priceET = (EditText) rootView.findViewById(R.id.addPd_PriceET);
		decET = (EditText) rootView.findViewById(R.id.addPd_DecET);
		
			grid = (ExpandableHeightGridView) rootView
					.findViewById(R.id.gridGallery);
			if (getArguments().getInt("position") != 0) {
				position = getArguments().getInt("position");
			nameET.setText(Appconstants.productsList.get(position).getName());
			modelET.setText(Appconstants.productsList.get(position).getModel());
			priceET.setText(""
					+ Appconstants.productsList.get(position).getPrice());
			decET.setText(Appconstants.productsList.get(position)
					.getDescription());
			manET.setText(Appconstants.productsList.get(position)
					.getStoreName());
		}
		// nameET.setText(Appconstants.productsList.get(position).getName());
		if (grid != null)
			grid.setExpanded(true);
		initImageLoader();
		init();
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getFragmentManager().popBackStack();
			}
		});
		return rootView;
	}

	private void initImageLoader() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				getActivity()).defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new WeakMemoryCache());

		ImageLoaderConfiguration config = builder.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}

	private void init() {

		handler = new Handler();
		grid.setFastScrollEnabled(true);
		adapter = new GalleryAdapter(getActivity().getApplicationContext(),
				imageLoader);
		adapter.setMultiplePick(false);
		grid.setAdapter(adapter);

		viewSwitcher = (ViewSwitcher) rootView.findViewById(R.id.viewSwitcher);
		viewSwitcher.setDisplayedChild(1);
		imgSinglePick = (ImageView) rootView.findViewById(R.id.imgSinglePick);
		imgSinglePick.setVisibility(View.GONE);
		takePicBtn = (Button) rootView.findViewById(R.id.takePicBtn);
		takePicBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openAlert();

			}
		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
			// adapter.clear();
			imgSinglePick.setVisibility(View.VISIBLE);
			adapter.clear();

			viewSwitcher.setDisplayedChild(1);
			String single_path = data.getStringExtra("single_path");
			imageLoader.displayImage("file://" + single_path, imgSinglePick);

		} else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
			imgSinglePick.setVisibility(View.GONE);
			String[] all_path = data.getStringArrayExtra("all_path");

			ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();

			for (String string : all_path) {
				CustomGallery item = new CustomGallery();
				Log.d("Images Path", "" + item.sdcardPath);
				item.sdcardPath = string;

				dataT.add(item);
				for (int j = 0; j < dataT.size(); j++) {
					Log.d("IMages Path After", "" + dataT.get(j));
				}

			}

			viewSwitcher.setDisplayedChild(0);
			adapter.addAll(dataT);
		}
	}

	private void openAlert() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		alertDialogBuilder.setTitle("Take Picture");
		alertDialogBuilder.setMessage("Upload picture");
		// set positive button: Yes message
		alertDialogBuilder.setPositiveButton("Camera",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent i = new Intent(Action.ACTION_PICK);
						startActivityForResult(i, 100);

						Log.d("HOuse", "id=" + id);
					}
				});
		// set negative button: No message
		alertDialogBuilder.setNegativeButton("Gallery",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent i = new Intent(Action.ACTION_MULTIPLE_PICK);
						startActivityForResult(i, 200);
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		// show alert
		alertDialog.show();
	}

}
