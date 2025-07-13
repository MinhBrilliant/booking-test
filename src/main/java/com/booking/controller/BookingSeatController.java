package com.booking.controller;

import com.booking.entity.Booking;
import com.booking.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/book-seats")
public class BookingSeatController {
    private final BookingService bookingService;

    public BookingSeatController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{id}")
    public String bookingSeat(@PathVariable( value = "id") long id, @RequestParam("pageNo") String pageNo, Model model) {

        // get employee from the service
        Optional<Booking> optional = bookingService.getBookingById(id);
        model.addAttribute("currentPage", pageNo);
        // set employee as a model attribute to pre-populate the form
        if (optional.isEmpty()){
            model.addAttribute("error", true);
        }
        Booking booking = optional.get();
        booking.setBooked(true);
        bookingService.update(booking);
        return "redirect:/user/book-seats/page/1?seatType=" + booking.getSeatTypeCode();
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo, @RequestParam(value = "seatType", required = false) String seatType,
                                Model model) {
        int pageSize = 10;

        Page<Booking> page = bookingService.findPaginatedBookingSeats(pageNo, pageSize);
        List<Booking> listBookings = page.getContent();
        if (StringUtils.hasLength(seatType)){
            model.addAttribute("seatType", "You are booked successfully with seat type: " + seatType);
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());


        model.addAttribute("listBookings", listBookings);
        return "book-seat-list";
    }
}
