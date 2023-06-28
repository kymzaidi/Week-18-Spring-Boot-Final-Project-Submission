package gym.waitlist.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gym.waitlist.controller.model.GymWaitlistData;
import gym.waitlist.controller.model.GymWaitlistData.GymMember;
import gym.waitlist.controller.model.GymWaitlistData.GymTrainer;
import gym.waitlist.service.GymWaitlistService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gym_waitlist")
@Slf4j
public class GymWaitlistController {
	
	@Autowired
	private GymWaitlistService gymWaitlistService;
	
	@PostMapping("/gym")
	@ResponseStatus(code = HttpStatus.CREATED)
	public GymWaitlistData createGym(
			@RequestBody GymWaitlistData gymWaitlistData) {
		log.info("Creating gym{}", gymWaitlistData);
		return gymWaitlistService.saveGym(gymWaitlistData);
		
	}
	
	@PutMapping("/gym/{gymId}")
	public GymWaitlistData modifyGymWaitlistData(@PathVariable Long gymId,
			@RequestBody GymWaitlistData gymWaitlistData) {
		gymWaitlistData.setGymId(gymId);
		log.info("Editing PetStore{}", gymWaitlistData);
		return gymWaitlistService.saveGym(gymWaitlistData);
		
	}
	
	@PostMapping("/gym/{gymId}/trainers")
	@ResponseStatus(code = HttpStatus.CREATED)
	public GymTrainer addTrainer(
			@PathVariable Long gymId,
			@RequestBody GymTrainer gymTrainer) {
		log.info("Adding trainer {} to gym with ID = {}", gymTrainer, gymId);
		return gymWaitlistService.saveTrainer(gymId, gymTrainer);
	}
	
	@PostMapping("/gym/{gymId}/members")
	@ResponseStatus(code = HttpStatus.CREATED)
	public GymMember addMember(
			@PathVariable Long gymId,
			@RequestBody GymMember gymMember) {
		log.info("Creating member {}), gymMember");
		return gymWaitlistService.saveMember(gymId, gymMember);
	}
	
	@GetMapping("/gym")
	public List<GymWaitlistData> retrieveAllGyms(){
		log.info("Retrieve all Gyms");
		return gymWaitlistService.retrieveAllGyms();
	}
	
	@GetMapping("/gym/{gymId}")
	public GymWaitlistData retrieveGymById(@PathVariable Long gymId) {
		log.info("Retrieve gym with ID = {}", gymId);
		return gymWaitlistService.retrieveGymById(gymId);
	}
	
	@DeleteMapping("/gym/{gymId}")
	public Map<String, String> deleteGymById(
			@PathVariable Long gymId){
		log.info("Deleting gym with ID = {}", gymId);
		gymWaitlistService.deleteGymById(gymId);
		return Map.of("message", "Deletion of the gym with ID = "
				+ gymId + " was successful.");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
