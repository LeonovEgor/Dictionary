package leonov.ru.utils.ui

import android.content.SharedPreferences

private const val PARAM1_NAME = "PARAM1_NAME"
private const val PARAM2_NAME = "PARAM2_NAME"

class StorageHelper(preferences: SharedPreferences) {
    var param1: String? by StringPreferencesDelegate(preferences, PARAM1_NAME, "")
    var param2: String? by StringPreferencesDelegate(preferences, PARAM2_NAME, "")

}