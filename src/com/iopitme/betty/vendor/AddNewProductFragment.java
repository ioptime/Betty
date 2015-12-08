package com.iopitme.betty.vendor;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
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
import com.ioptime.betty.model.Product;
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
		grid.setExpanded(true);
		initImageLoader();
		init();
		if (getArguments().getInt("switch") != 0) {
			position = getArguments().getInt("position");
			Product product = Appconstants.productsList.get(position);
			imageLoader.displayImage(product.getImage(), imgSinglePick);
			nameET.setText(product.getName());
			modelET.setText(product.getModel());
			priceET.setText("" + product.getPrice());
			decET.setText(product.getDescription());
			manET.setText(product.getManufacturer());
			catET.setText(product.getCategory());
			QuantityET.setText(product.getQuantity() + "");
		}
		// nameET.setText(Appconstants.productsList.get(position).getName());

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
		Log.d("activityresult", requestCode + "-");
		if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
			try {
				// get the cropped bitmap
				Bitmap bitmap = (Bitmap) data.getExtras().get("data");

				String file_path = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/BeityMall";
				File dir = new File(file_path);
				if (!dir.exists())
					dir.mkdirs();

				File fileImage = new File(dir, "temp"
						+ System.currentTimeMillis() + ".jpeg");

				FileOutputStream fOut;

				fOut = new FileOutputStream(fileImage);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);

				bitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, true);
				imgSinglePick.setImageBitmap(bitmap);
				fOut.flush();
				fOut.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
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
						Intent cameraIntent;

						String BX1 = android.os.Build.MANUFACTURER;

						if (BX1.equalsIgnoreCase("samsung")) {
							cameraIntent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							startActivityForResult(cameraIntent, 2);

						} else {
							cameraIntent = new Intent(
									android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
							startActivityForResult(cameraIntent, 2);
						}

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

	@Override
	public void onResume() {
		super.onResume();
		((MainMenuVendor) getActivity()).getActionBar().setTitle(
				"Add New Products");
	}
}
