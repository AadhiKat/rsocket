package com.aadhikat.rsocket.dto;

public class ChartResponseDto {

    private int input;
    private int output;

    public int getInput() {
        return input;
    }

    public int getOutput() {
        return output;
    }
    
    public ChartResponseDto() {
    }

    public ChartResponseDto(int input, int output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public String toString() {
        return String.format(getFormat(this.output) , this.input , "X");
    }

    private String getFormat (int value) {
        return "%3s|%" + value + "s";
    }
}
