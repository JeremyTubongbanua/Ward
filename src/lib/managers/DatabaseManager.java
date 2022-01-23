package lib.managers;

import lib.assets.MDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private List<MDatabase> dbs;

    public DatabaseManager() {
        dbs = new ArrayList<MDatabase>();
    }

    public List<MDatabase> getDatabases() {
        return dbs;
    }

    public void addDatabase(MDatabase db) {
        dbs.add(db);
    }

}
