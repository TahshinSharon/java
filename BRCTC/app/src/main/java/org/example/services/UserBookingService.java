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

    private static final String users_path = "/Users/shikho/personal/Learn/Java/BRCTC/app/src/main/java/org/example/localDb/users.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUserListFromFile();
    }

    public UserBookingService() throws IOException {
        loadUserListFromFile();
    }

    private void loadUserListFromFile() throws IOException {
        userList = objectMapper.readValue(new File(users_path), new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1-> {return user1.getName().equalsIgnoreCase(user.getName())&& UserServiceUtil.checkPassword(user.getPassword(),user1.getHashPassword());}).findFirst();
        System.out.println(foundUser.isPresent());
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
    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train, int row, int seat) {
        try {
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeats();
            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if (seats.get(row).get(seat) == 0) {
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    trainService.addTrain(train);
                    return true; // Booking successful
                } else {
                    return false; // Seat is already booked
                }
            } else {
                return false; // Invalid row or seat index
            }
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }
}
