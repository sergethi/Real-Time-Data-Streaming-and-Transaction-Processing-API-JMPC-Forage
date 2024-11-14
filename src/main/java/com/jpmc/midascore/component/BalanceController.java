package com.jpmc.midascore.component;

import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;

@RestController
public class BalanceController {
    private UserRepository userRepository;

    //  @Autowired
    public BalanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam Long userId){
        Optional<UserRecord> id = userRepository.findById(userId);
        if (id.isPresent()){
            UserRecord userRecord = id.get();
            return new Balance(userRecord.getBalance());
        }
        else{
            return new Balance(0);
        }

    }
}
