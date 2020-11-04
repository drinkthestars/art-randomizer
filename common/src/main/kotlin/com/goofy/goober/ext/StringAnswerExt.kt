package com.goofy.goober.ext

private val String.sauce: String
    get() = if (this == "white") "a $this" else this

private val String.style: String
    get() = if (this == "og neapolitan") "an $this" else "a $this"

// TODO: Finish this part
fun List<String>.makeAnswer(): String {
    return "Here's your date plan: ${this[0].style} with: \n" +
            "${this[1].sauce} sauce,  ${this[2]}, and ${this[3]}"
}
