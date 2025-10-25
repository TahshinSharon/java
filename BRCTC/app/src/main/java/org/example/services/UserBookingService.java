package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;

    private List<User> userList;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String users_path = "app/src/main/java/org/example//localDb/users.json";

    public UserBookingService() throws IOException {
        loadUsers();
    }
    public List<User> loadUsers() throws IOException {
        File users = new File(users_path);
        return objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }
    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1-> {return user1.getName().equalsIgnoreCase(user.getName())&& UserServiceUtil.checkPassword(user.getPassword(),user1.getHashPassword());}).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;

        }catch(Exception e){
            return Boolean.FALSE;
        }
    }
    private void saveUserListToFile() throws IOException {
        File usersFile = new File(users_path);
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBooking(){
        user.printTichekts();
    }
    public Boolean cancelBooking(String ticketId){

        try{
            Optional<User> foundUserOpt = userList.stream().filter(u->u.getName().equalsIgnoreCase(user.getName())).findFirst();

            if (foundUserOpt.isEmpty()){
                System.out.println("User not found!");
                return Boolean.FALSE;
            }

            User foundUser = foundUserOpt.get();

            boolean removed = foundUser.getTickets().removeIf(t->t.getTicketId().equalsIgnoreCase(ticketId));

            if (!removed){
                System.out.println("Ticket not found!");
                return Boolean.FALSE;
            }
            saveUserListToFile();

            System.out.println("User booking has been canceled!");
            return Boolean.TRUE;
        }catch (Exception e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
    public List<Train> getTrains(String source, String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source,destination);
        }catch (Exception e){
            return null;
        }
    }
}
