package com.bonial.batch.item

/**
 * batch-processor
 * @author Konstantin Bork
 * @version 0.1
 * @created 09/02/2015
 *
 * §DESCRIPTION§
 */

class NameItem {

    String firstName
    String lastName

    String toString() {
        if(!firstName) {
            if(!lastName) {
                return ""
            }
            return firstName
        }
        else if(!lastName) {
            return firstName
        }
        return "${firstName} ${lastName}"
    }

}