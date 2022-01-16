package org.csu.mypetstore.persistence;

public interface CartUpdateDAO {
    void cartNumberUpdateByUsername(String username, String intemId, String quantity);
}
