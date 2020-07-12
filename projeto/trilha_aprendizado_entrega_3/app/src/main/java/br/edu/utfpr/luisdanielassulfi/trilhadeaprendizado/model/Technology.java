package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.model;

public class Technology {

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
}
