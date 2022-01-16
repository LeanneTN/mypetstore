package org.csu.mypetstore.persistence;

public interface VerificationDAO {
    void updateVerificationCode();

    void checkVerificationValue();
}
