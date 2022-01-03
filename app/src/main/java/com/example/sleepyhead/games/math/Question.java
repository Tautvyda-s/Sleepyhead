package com.example.sleepyhead.games.math;


import java.util.Random;

public class Question {
    private int firstNumber;
    private int secondNumber;
    private int answer;

    //correct answer position
    private int answerPosition;
    //answer choises
    private int[] answerArray;

    private int upperLimit;

    //string math equation
    private String questionPhrase;

    //constructure - generate equation
    public Question(int upperLimit){
        //random number generator
        this.upperLimit=upperLimit;
        Random randomNumberMaker = new Random();
        this.firstNumber = randomNumberMaker.nextInt(upperLimit);
        this.secondNumber = randomNumberMaker.nextInt(upperLimit);
        this.answer = this.firstNumber+this.secondNumber;
        this.questionPhrase=firstNumber+" + "+secondNumber+" = ";

        this.answerPosition = randomNumberMaker.nextInt(4);
        this.answerArray=new int[]{0,1,2,3};
        this.answerArray[0]=answer+1;
        this.answerArray[1]=answer+10;
        this.answerArray[2]=answer-5;
        this.answerArray[3]=answer-2;

        //shuffle wrong answers
        this.answerArray = shuffleArray(this.answerArray);
        answerArray[answerPosition]=answer;
    }

    private int[] shuffleArray(int[] answerArray) {
        int index, temp;
        Random randomNumberGenerator = new Random();
        for(int i = answerArray.length - 1;i>0;i--){
            index=randomNumberGenerator.nextInt(i+1);
            temp= answerArray[index];
            answerArray[index]=answerArray[i];
            answerArray[i]=temp;
        }
        return answerArray;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getAnswerPosition() {
        return answerPosition;
    }

    public void setAnswerPosition(int answerPosition) {
        this.answerPosition = answerPosition;
    }

    public int[] getAnswerArray() {
        return answerArray;
    }

    public void setAnswerArray(int[] answerArray) {
        this.answerArray = answerArray;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getQuestionPhrase() {
        return questionPhrase;
    }

    public void setQuestionPhrase(String questionPhrase) {
        this.questionPhrase = questionPhrase;
    }
}
