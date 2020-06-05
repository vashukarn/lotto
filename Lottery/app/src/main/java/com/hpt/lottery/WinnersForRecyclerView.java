package com.hpt.lottery;

public class WinnersForRecyclerView {
    private String name;
    private int prizewon;
    private int underscheme;

    public WinnersForRecyclerView(){
        // Empty Constructor Needed!
    }
    public WinnersForRecyclerView(String name, int prizewon, int underscheme){
        this.name = name;
        this.prizewon = prizewon;
        this.underscheme = underscheme;
    }

    public String getName() {
        return name;
    }

    public int getPrizewon() {
        return prizewon;
    }

    public int getUnderscheme() {
        return underscheme;
    }
}
