package com.booking.controller;

import com.booking.modal.req.SaveBookingReq;
import com.booking.service.BookingService;
import com.booking.entity.Booking;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/book-type")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	/*// display list of employees
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}*/
	
	@GetMapping("/showNewBookingForm")
	public String showNewEmployeeForm(@RequestParam("pageNo") String pageNo,@RequestParam("isLogin") Boolean isLogin,Model model) {
		// create model attribute to bind form data
		Booking booking = new Booking();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("isLogin", isLogin);
		String seatTypeCode = bookingService.getLatestSeatTypeCode();
		int seatTypeCodeInt = Integer.parseInt(seatTypeCode);
		if (seatTypeCodeInt > 99){
			model.addAttribute("error", true);
		}
		booking.setSeatTypeCode(seatTypeCode);
		model.addAttribute("booking", booking);
		return "new_booking";
	}
	
	@PostMapping("/register")
	public String saveNewBooking(@Valid @ModelAttribute("booking") SaveBookingReq request, BindingResult bindingResult,@RequestParam("isLogin") Boolean isLogin, Model model) {
		// save employee to database
		model.addAttribute("currentPage", 1);
		model.addAttribute("isLogin", isLogin);
		if (!request.getSeatTypeCode().equalsIgnoreCase(bookingService.getLatestSeatTypeCode())){
			bindingResult.addError(new ObjectError("seatTypeCode", "seatTypeCode is invalid!"));
			bindingResult.rejectValue("seatTypeCode", "seatTypeCode", "seatTypeCode is invalid!");
		}
		if (bindingResult.hasErrors()){
			model.addAttribute("booking", request);
			return "new_booking";
		}
		bookingService.saveNewBooking(request);
		String redirect = (isLogin)? ("redirect:/user/book-seats/page/1") : "redirect:/book-type/page/1";
		return redirect;
	}

	@PostMapping("/save")
	public String updateBooking(@Valid @ModelAttribute("booking") SaveBookingReq request, BindingResult bindingResult,
								@RequestParam("pageNo") String pageNo,@RequestParam("isLogin") Boolean isLogin, Model model) {
		// save employee to database
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("isLogin", isLogin);
		if (bindingResult.hasErrors()){
			String errMsg = bindingResult.getFieldError().getDefaultMessage();
			model.addAttribute("booking", request);
			return "update_booking";
		}
		Booking booking = bookingService.updateBooking(request);

		if (ObjectUtils.isEmpty(booking)){
			model.addAttribute("errMsg", "Can not update the this booking. Please check again!");
			model.addAttribute("booking", request);
			return "update_booking";
		}
		String redirect = (isLogin)? ("redirect:/user/book-seats/page/" + pageNo) : "redirect:/book-type/page/" + pageNo;
		return redirect;
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id,@RequestParam("pageNo") String pageNo,@RequestParam("isLogin") Boolean isLogin, Model model) {
		
		// get employee from the service
		Optional<Booking> optional = bookingService.getBookingById(id);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("isLogin", isLogin);
		// set employee as a model attribute to pre-populate the form
		if (!optional.isPresent()){
			model.addAttribute("error", true);
			Booking booking = new Booking();
			booking.setSeatTypeCode(bookingService.getLatestSeatTypeCode());
			model.addAttribute("booking", booking);
			return "new_booking";
		}
		model.addAttribute("booking", optional.get());
		return "update_booking";
	}

	@GetMapping("/duplicate/{id}")
	public String duplicateBooking(@PathVariable ( value = "id") long id,@RequestParam("pageNo") String pageNo,@RequestParam("isLogin") Boolean isLogin,Model model) {

		// get employee from the service
		Optional<Booking> optional = bookingService.getBookingById(id);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("isLogin", isLogin);
		// set employee as a model attribute to pre-populate the form
		if (!optional.isPresent()){
			model.addAttribute("error", true);
			Booking booking = new Booking();
			booking.setSeatTypeCode(bookingService.getLatestSeatTypeCode());
			model.addAttribute("booking", booking);
			return "new_booking";
		}
		Booking booking = optional.get();
		String seatTypeCode = bookingService.getLatestSeatTypeCode();
		int seatTypeCodeInt = Integer.parseInt(seatTypeCode);
		if (seatTypeCodeInt > 99){
			model.addAttribute("error", true);
		}
		booking.setSeatTypeCode(seatTypeCode);
		booking.setId(null);
		model.addAttribute("booking", booking);
		return "new_booking";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable ( value = "id") long id,@RequestParam("pageNo") String pageNo,@RequestParam("isLogin") Boolean isLogin,Model model) {

		// call delete employee method
		if (!bookingService.deleteEmployeeById(id)){
			model.addAttribute("error", true);
			model.addAttribute("errorMsg", "Booking is not exist to delete. please check again!");
		}
		String redirect = (isLogin)? ("redirect:/user/book-seats/page/" + pageNo) : "redirect:/book-type/page/" + pageNo;
		return redirect;
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			Model model) {
		int pageSize = 10;

		Page<Booking> page = bookingService.findPaginatedBooking(pageNo, pageSize);
		List<Booking> listBookings = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		

		model.addAttribute("listBookings", listBookings);
		return "booking-list";
	}
}
