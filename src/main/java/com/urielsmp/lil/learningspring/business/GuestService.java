package com.urielsmp.lil.learningspring.business;

import com.urielsmp.lil.learningspring.data.Guest;
import com.urielsmp.lil.learningspring.data.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<GuestsModel> getGuests(){
        Iterable<Guest> guestsModels = this.guestRepository.findAll();

        List<GuestsModel> guestsModelList = new ArrayList<>();
        guestsModels.forEach(guest -> {
            GuestsModel guestsModel = new GuestsModel();
            guestsModel.setFirstName(guest.getFirstName());
            guestsModel.setLastName(guest.getLastName());
            guestsModel.setEmail(guest.getEmail());
            guestsModel.setPhoneNumber(guest.getPhoneNumber());
            guestsModelList.add(guestsModel);
        });

        return  guestsModelList;

    }
}
