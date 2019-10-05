package edu.ualr.recyclerviewasignment.model;

/**
 * Created by irconde on 2019-10-04.
 */
public class DeviceSection extends DeviceListItem{
    private String label = "";
    private int numItems = 0;

    public DeviceSection(String label) {
        this.init(label, 0);
    }

    public DeviceSection(String label, int numItems) {
        this.init(label, numItems);
    }

    private void init(String label, int numItems) {
        super.isSection = true;
        this.label = label;
        this.numItems = numItems;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getNumItems() {
        return this.numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }
}
