package com.urielsmp.lil.learningspring.web;

import com.urielsmp.lil.learningspring.business.GuestService;
import com.urielsmp.lil.learningspring.business.GuestsModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getReservations(Model model){

        List<GuestsModel> guestsModels = this.guestService.getGuests();
        model.addAttribute("guestModels", guestsModels);

        return  "guestmodel";

    }
}
