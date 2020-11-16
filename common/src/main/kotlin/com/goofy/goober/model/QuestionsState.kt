package com.goofy.goober.model

data class QuestionsState(
    val question: Question = Question.firstQuestion,
    val choicesSoFar: List<String> = emptyList(),
)
