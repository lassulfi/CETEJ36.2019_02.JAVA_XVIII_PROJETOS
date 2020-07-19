package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Technology implements Parcelable {

    private String name;
    private String description;
    private String preRequirements;
    private double time;
    private boolean isMandatory;
    private String trail;
    private double percentageKnown;

    public Technology(String name, String description, String preRequirements, double time,
                      boolean isMandatory, String trail, double percentageKnown) {
        this.name = name;
        this.description = description;
        this.preRequirements = preRequirements;
        this.time = time;
        this.isMandatory = isMandatory;
        this.trail = trail;
        this.percentageKnown = percentageKnown;
    }

    protected Technology(Parcel in) {
        name = in.readString();
        description = in.readString();
        preRequirements = in.readString();
        time = in.readDouble();
        isMandatory = in.readByte() != 0;
        trail = in.readString();
        percentageKnown = in.readDouble();
    }

    public static final Creator<Technology> CREATOR = new Creator<Technology>() {
        @Override
        public Technology createFromParcel(Parcel in) {
            return new Technology(in);
        }

        @Override
        public Technology[] newArray(int size) {
            return new Technology[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreRequirements() {
        return preRequirements;
    }

    public void setPreRequirements(String preRequirements) {
        this.preRequirements = preRequirements;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public String getTrail() {
        return trail;
    }

    public void setTrail(String trail) {
        this.trail = trail;
    }

    public double getPercentageKnown() {
        return percentageKnown;
    }

    public void setPercentageKnown(double percentageKnown) {
        this.percentageKnown = percentageKnown;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(preRequirements);
        dest.writeDouble(time);
        dest.writeByte((byte) (isMandatory ? 1 : 0));
        dest.writeString(trail);
        dest.writeDouble(percentageKnown);
    }
}
