package leonov.ru.model.data

import java.lang.Exception

enum class PartOfSpeechEnum(val value: String) {
    n("noun"),
    v("verb"),
    j("adjective"),
    r("adverb"),
    prp("preposition"),
    prn("pronoun"),
    crd("cardinal number"),
    cjc("conjunction"),
    exc("interjection"),
    det("article"),
    abb("abbreviation"),
    x("particle"),
    ord("ordinal_number"),
    md("modal verb"),
    ph("phrase"),
    phi("idiom"),
}