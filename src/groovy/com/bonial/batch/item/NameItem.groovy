package com.bonial.batch.item

/**
 * batch-processor
 * @author Konstantin Bork
 * @version 0.6
 * @created 09/02/2015
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