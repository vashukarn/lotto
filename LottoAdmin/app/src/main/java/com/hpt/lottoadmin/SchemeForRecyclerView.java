package com.hpt.lottoadmin;

public class SchemeForRecyclerView {
    private int participant;
    private int price;
    private int winneramountstart;
    private int winneramountend;
    private int noofwinners;

    public SchemeForRecyclerView(){
        //For Empty Constructor
    }

    public SchemeForRecyclerView(int price, int participant, int noofwinners, int winneramountstart, int winneramountend){
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
