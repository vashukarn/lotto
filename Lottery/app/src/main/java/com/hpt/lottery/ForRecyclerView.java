package com.hpt.lottery;

public class ForRecyclerView {
    private int participant;
    private int price;
    private int winneramountstart;
    private int winneramountend;
    private int noofwinners;

    public ForRecyclerView(){
        //For Empty Constructor
    }

    public ForRecyclerView(int price, int participant, int noofwinners, int winneramountstart, int winneramountend){
        this.noofwinners = noofwinners;
        this.winneramountend = winneramountend;
        this.winneramountstart = winneramountstart;
        this.price = price;
        this.participant = participant;
    }

    public int getParticipant() {
        return participant;
    }

    public int getPrice() {
        return price;
    }

    public int getWinneramountstart() {
        return winneramountstart;
    }

    public int getWinneramountend() {
        return winneramountend;
    }

    public int getNoofwinners() {
        return noofwinners;
    }
}
