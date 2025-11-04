package ec.edu.arguello.services;

import ec.edu.arguello.models.User;

public class LoginServicio {
    
    public boolean login (User user){
        if (user.getUsername().equals("MONSTER") && user.getPassword().equals("MONSTER9")){
            return true;
        }
        return false;    
    }
}
