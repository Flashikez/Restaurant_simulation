/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.objects;

/**
 * Stol
 * @author MarekPC
 */
public class Table {

    private int id;
    private int tableSize;
    private CustomerGroup group;

    public int getTableSize() {
        return tableSize;
    }
    private boolean free;

    public Table(int tableID, int tableS) {
        this.id = tableID;
        this.free = true;
        this.tableSize = tableS;
    }

    public int getId() {
        return id;
    }

    public void setId(int tableID) {
        this.id = tableID;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free, CustomerGroup group) {
        this.free = free;
        this.group = group;
    }

    public CustomerGroup getGroup() {
        return group;
    }

    public int getGroupId() {
        if (group == null) {
            return -1;
        }
        return group.getId();
    }

    public int getGroupSize() {
        if (group == null) {
            return -1;
        }
        return group.getGroupSize();
    }

    public String getActivity() {
        if (group == null) {
            return "";
        }
        return group.getActivity();
    }

}
