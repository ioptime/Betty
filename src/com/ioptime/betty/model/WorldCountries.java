package com.ioptime.betty.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WorldCountries implements Parcelable {
	int id;
	String name;
	String iso_code_3;
	String addressFormat;
	int postcodeRequired;
	int status;

	public WorldCountries(int id, String name, String iso_code_3,
			String addressFormat, int postcodeRequired, int status) {
		super();
		this.id = id;
		this.name = name;
		this.iso_code_3 = iso_code_3;
		this.addressFormat = addressFormat;
		this.postcodeRequired = postcodeRequired;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIso_code_3() {
		return iso_code_3;
	}

	public void setIso_code_3(String iso_code_3) {
		this.iso_code_3 = iso_code_3;
	}

	public String getAddressFormat() {
		return addressFormat;
	}

	public void setAddressFormat(String addressFormat) {
		this.addressFormat = addressFormat;
	}

	public int getPostcodeRequired() {
		return postcodeRequired;
	}

	public void setPostcodeRequired(int postcodeRequired) {
		this.postcodeRequired = postcodeRequired;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static final Parcelable.Creator<WorldCountries> CREATOR = new Parcelable.Creator<WorldCountries>() {
		public WorldCountries createFromParcel(Parcel in) {
			return new WorldCountries(in);
		}

		public WorldCountries[] newArray(int size) {
			return new WorldCountries[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(iso_code_3);
		dest.writeString(addressFormat);
		dest.writeString(name);
		dest.writeInt(postcodeRequired);
		dest.writeInt(status);
	}

	private WorldCountries(Parcel in) {
		this.id = in.readInt();
		this.iso_code_3 = in.readString();
		this.addressFormat = in.readString();
		this.name = in.readString();
		this.postcodeRequired = in.readInt();
		this.status = in.readInt();
	}
}
