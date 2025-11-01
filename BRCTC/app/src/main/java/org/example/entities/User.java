package org.example.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.util.List;

public class User {
    @JsonProperty("user_id")
    private String userId;

    private String name;
    private String password;
    private String hashPassword;
    @JsonProperty("tickets_booked")
    @JsonSetter(nulls = Nulls.SKIP)
    private List<Ticket> tickets;

    public User() {
    }
    public User(String name, String password, String hashPassword, List<Ticket> tickets, String userId) {
        this.name = name;
        this.password = password;
        this.hashPassword = hashPassword;
        this.tickets = tickets;
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getHashPassword() {
        return hashPassword;
    }
    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }
    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void printTichekts() {
        for (int i = 0; i < tickets.size(); i++) {
            System.out.println(tickets.get(i).getTicketInfo());
        }
    }
}
