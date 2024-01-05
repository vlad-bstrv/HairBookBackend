package com.vladbstrv.utils

class Constants {
    object Role {
        const val ADMIN = "admin"
        const val GUEST = "guest"
    }

    object Error {
        const val GENERAL = "Something went wrong"
        const val WRONG_EMAIL = " Wrong email address"
        const val INCORRECT_PASSWORD = "Incorrect password"
        const val MISSING_FIELDS = "Missing some fields!"
        const val USER_NOT_FOUND = "User not found"
    }

    object Success {
        const val ADDED_SUCCESSFULLY = "Added Successfully!"
        const val UPDATE_SUCCESSFULLY = "Update Successfully!"
        const val DELETE_SUCCESSFULLY = "Delete Successfully!"
    }
}