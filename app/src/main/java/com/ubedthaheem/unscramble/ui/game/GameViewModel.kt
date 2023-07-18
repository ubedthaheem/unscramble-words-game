package com.ubedthaheem.unscramble.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ubedthaheem.unscramble.models.MAX_NO_OF_WORDS
import com.ubedthaheem.unscramble.models.SCORE_INCREASE
import com.ubedthaheem.unscramble.models.allWordsList

class GameViewModel : ViewModel() {

    // lets create variables to store & get data




    private var wordList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String


    // Declare private mutable variable that can only be modified
    // within the class it is declared.
    private var _count = 0

    // Declare another public immutable field and override its getter method.
    // Return the private property's value in the getter method.
    // When count is accessed, the get() function is called and
    // the value of _count is returned.
    val count: Int
        get() = _count

    private val _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    private fun getNextWord(){
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        if (wordList.contains(currentWord)){
            getNextWord()
        }else{
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordList.add(currentWord)
        }
    }

    // log the viewModel
    init {
        getNextWord()
    }



    fun nextWord(): Boolean {
        return if(_currentWordCount.value!! < MAX_NO_OF_WORDS){
            getNextWord()
            true
        }else false
    }

    // increase scrore
    private fun increaseScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    // get user's word
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)){
            increaseScore()
            return true
        }
        return false
    }

    /*
    * Reinitialize the game data to restart the game
    * */
    fun reinitializeData(){
        _score.value = 0;
        _currentWordCount.value = 0;
        wordList.clear()
        getNextWord()
    }




}