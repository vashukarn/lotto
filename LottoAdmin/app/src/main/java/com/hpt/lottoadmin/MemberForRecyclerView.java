package com.hpt.lottoadmin;

public class MemberForRecyclerView {
    private String name;
    private String email;

    public MemberForRecyclerView(){
        // Empty Constructor Needed
    }

    public MemberForRecyclerView(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
