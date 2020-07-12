/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.managment;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Table;
import event_sim_core.statictics.AverageSizeStat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MarekPC
 */
public class TablesManagment {

    private List<Table> tables;
    private int _2freeTables, _4freeTables, _6freeTables;
    public AverageSizeStat _2tablesFree, _4tablesFree, _6tablesFree;

    public TablesManagment() {
        this.tables = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            tables.add(new Table(i, 2));
        }
        this._2freeTables = 10;
        for (int i = 11; i <= 17; i++) {
            tables.add(new Table(i, 4));
        }
        this._4freeTables = 7;
        for (int i = 18; i <= 23; i++) {
            tables.add(new Table(i, 6));
        }
        this._6freeTables = 6;
        this._2tablesFree = new AverageSizeStat();
        this._4tablesFree = new AverageSizeStat();
        this._6tablesFree = new AverageSizeStat();

    }

    public void setFree(Table tab, double time) {
        switch (tab.getTableSize()) {
            case 2:
                this._2tablesFree.add(_2freeTables, time);
                this._2freeTables++;
                break;
            case 4:
                this._4tablesFree.add(_4freeTables, time);
                this._4freeTables++;
                break;
            case 6:
                this._6tablesFree.add(_6freeTables, time);
                this._6freeTables++;
                break;
        }

        tab.setFree(true,null);
    }

    public void setOccupied(Table tab, double time,CustomerGroup group) {
        switch (tab.getTableSize()) {
            case 2:
                this._2tablesFree.add(_2freeTables, time);
                this._2freeTables--;
                break;
            case 4:
                this._4tablesFree.add(_4freeTables, time);
                this._4freeTables--;
                break;
            case 6:
                this._6tablesFree.add(_6freeTables, time);
                this._6freeTables--;
                break;
        }

        tab.setFree(false,group);
    }

    public Table getFreeTable_lowestSizePossible(int groupSize) {
        for (Table table : tables) {
            if (table.isFree() && table.getTableSize() >= groupSize) {
                return table;
            }

        }
        return null;
    }

    public List<Table> getList() {
        return tables;
    }
}
