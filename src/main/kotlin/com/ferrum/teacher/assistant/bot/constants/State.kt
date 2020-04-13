/**
 *  Author: BurningPapaya
 *  Date: 12.04.2020
 *  Time: 22:16
 */
package com.ferrum.teacher.assistant.bot.constants


enum class State {
    MAIN_MENU,
    // test creation
    NEW_TEST,
    NEW_QUESTION,
    CORRECT_CHOICE,
    NEW_CHOICE,
    // test viewing
    LIST,
    VIEW_TEST,
    // test solving
    FIND_TEST,
    SOLVE_TEST,
    SOLVE_QUESTION
}