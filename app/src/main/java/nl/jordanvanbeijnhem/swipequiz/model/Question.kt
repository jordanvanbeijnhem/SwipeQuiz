package nl.jordanvanbeijnhem.swipequiz.model

class Question(
    var question: String,
    var answer: Boolean
) {

    companion object {
        val QUESTIONS = arrayOf(
            "A 'val' and 'var' are the same.",
            "Mobile Application Development grants 12 ECTS.",
            "A unit in Kotlin corresponds to a void in java",
            "In Kotlin when replaces the 'switch' operator in java."
        )

        val ANSWERS = arrayOf(
            false,
            false,
            true,
            true
        )
    }
}