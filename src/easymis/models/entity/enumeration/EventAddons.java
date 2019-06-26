/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easymis.models.entity.enumeration;

/**
 *
 * @author RashidKP
 */
public enum EventAddons {

    NORMAL_AC("AC"),
    ADDITIONAL_AC_1("Additional AC 1"),
    ADDITIONAL_AC_2("Additional AC 2"),
    ADDITIONAL_AC_3("Additional AC 3"),
    ADDITIONAL_AC_4("Additional AC 4"),
    ADDITIONAL_AC_5("Additional AC 5"),
    ADDITIONAL_AC_6("Additional AC 6");

    private String value;

    EventAddons(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
