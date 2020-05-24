package leonov.ru.translator.utils

import leonov.ru.model.entity.TranslateResult
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ExtensionsTest {

    private lateinit var translateResultList: List<TranslateResult>
    private lateinit var trans1: TranslateResult
    private lateinit var trans2: TranslateResult
    private lateinit var trans3: TranslateResult

    @Before
    fun before() {
        trans1 = TranslateResult("text1", "текст1", "заметка1", "", "", "", "", "")
        trans2 = TranslateResult("text2", "текст2", "заметка2", "", "", "", "", "")
        trans3 = TranslateResult("text3", "текст3", "заметка3", "", "", "", "", "")

        translateResultList = listOf(trans1, trans2, trans3)
    }

    @Test
    fun convertMeaningsToStringTest() {
        val result = translateResultList.convertTranslationToCommaString()

        assertEquals(result, "текст1, текст2, текст3")
    }

    @Test
    fun convertMeaningsToStringOneItemTest() {
        val result = listOf(trans1).convertTranslationToCommaString()

        assertEquals(result, "текст1")
    }

    @Test
    fun convertMeaningsToStringEmptyTest() {
        val result = listOf<TranslateResult>().convertTranslationToCommaString()

        assertEquals(result, "")
    }

}