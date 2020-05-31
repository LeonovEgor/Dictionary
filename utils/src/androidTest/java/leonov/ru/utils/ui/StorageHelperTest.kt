package leonov.ru.utils.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class StorageHelperTest {

    private val newValue = "newParamValue"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var context: Context

    @Before
    fun setup() {
        context = getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", MODE_PRIVATE)
    }

    @Test
    fun storageSaveParamTest() {

        val storage = StorageHelper(sharedPreferences)
        storage.param1 = newValue

        val param1Value = sharedPreferences.getString("PARAM1_NAME", "")

        assertEquals(newValue, param1Value)
    }
}