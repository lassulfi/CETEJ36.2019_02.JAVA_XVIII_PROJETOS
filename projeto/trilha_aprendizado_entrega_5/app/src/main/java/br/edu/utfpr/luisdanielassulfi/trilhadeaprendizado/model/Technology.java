package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Comparator;

@Entity(tableName = "technologies")
public class Technology implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private String preRequirements;

    @NonNull
    private double time;

    private boolean isMandatory;

    @NonNull
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
        id = in.readLong();
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull  String description) {
        this.description = description;
    }

    @NonNull
    public String getPreRequirements() {
        return preRequirements;
    }

    public void setPreRequirements(@NonNull String preRequirements) {
        this.preRequirements = preRequirements;
    }

    @NonNull
    public double getTime() {
        return time;
    }

    public void setTime(@NonNull double time) {
        this.time = time;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    @NonNull
    public String getTrail() {
        return trail;
    }

    @NonNull
    public void setTrail(@NonNull String trail) {
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
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(preRequirements);
        dest.writeDouble(time);
        dest.writeByte((byte) (isMandatory ? 1 : 0));
        dest.writeString(trail);
        dest.writeDouble(percentageKnown);
    }

    /**
     * Sort technology by name in ascending order
     */
    public static Comparator<Technology> technologyNameComparatorAsc = new Comparator<Technology>() {
        @Override
        public int compare(Technology o1, Technology o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

    /**
     * Sort by technology name in descending order
     */
    public static Comparator<Technology> technologyNameComparatorDesc = new Comparator<Technology>() {
        @Override
        public int compare(Technology o1, Technology o2) {
            return o2.getName().compareTo(o1.getName());
        }
    };

    /**
     * Sort by trail in ascending order
     */
    public static Comparator<Technology> trailComparatorAsc = new Comparator<Technology>() {
        @Override
        public int compare(Technology o1, Technology o2) {
            return o1.getTrail().compareTo(o2.getTrail());
        }
    };

    /**
     * Sort by trail in descending order
     */
    public static Comparator<Technology> trailComparatorDesc = new Comparator<Technology>() {
        @Override
        public int compare(Technology o1, Technology o2) {
            return o2.getTrail().compareTo(o1.getTrail());
        }
    };
}
