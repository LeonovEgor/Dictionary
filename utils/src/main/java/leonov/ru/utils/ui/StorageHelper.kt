package leonov.ru.utils.ui

import android.content.SharedPreferences

private const val PARAM1_NAME = "PARAM1_NAME"

class StorageHelper(preferences: SharedPreferences) {
    var param1: String? by StringPreferencesDelegate(preferences, PARAM1_NAME, "")
}