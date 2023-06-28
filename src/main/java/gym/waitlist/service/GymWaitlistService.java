package gym.waitlist.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gym.waitlist.controller.model.GymWaitlistData;
import gym.waitlist.controller.model.GymWaitlistData.GymMember;
import gym.waitlist.controller.model.GymWaitlistData.GymTrainer;
import gym.waitlist.dao.GymDao;
import gym.waitlist.dao.MembersDao;
import gym.waitlist.dao.TrainersDao;
import gym.waitlist.entity.Gym;
import gym.waitlist.entity.Trainers;
import gym.waitlist.entity.Members;


@Service
public class GymWaitlistService {
	
	@Autowired
	private GymDao gymDao;
	
	@Autowired
	private MembersDao membersDao;
	
	@Autowired
	private TrainersDao trainersDao;

	public GymWaitlistData saveGym(GymWaitlistData gymWaitlistData) {
		Long gymId = gymWaitlistData.getGymId();
		Gym gym = findOrCreateGym(gymId);
		setFieldsInGym(gym, gymWaitlistData);
		return new GymWaitlistData(gymDao.save(gym));
	}

	private void setFieldsInGym(Gym gym, GymWaitlistData gymWaitlistData) {
		gym.setGymName(gymWaitlistData.getGymName());
		gym.setAddress(gymWaitlistData.getAddress());
		gym.setCity(gymWaitlistData.getCity());
		gym.setState(gymWaitlistData.getState());
		gym.setZipcode(gymWaitlistData.getZipcode());
		gym.setPhoneNumber(gymWaitlistData.getPhoneNumber());
		
	}

	private Gym findOrCreateGym(Long gymId) {
		Gym gym;
		if(Objects.isNull(gymId)) {
			gym = new Gym();
		}
		else {
			gym = findGymById(gymId);
		}
		return gym;
	}

	private Gym findGymById(Long gymId) {
		return gymDao.findById(gymId)
				.orElseThrow(() -> new NoSuchElementException(
						"Gym with ID" + gymId + " was not found."));
	}

	@Transactional(readOnly = false)
	public GymTrainer saveTrainer(Long gymId, GymTrainer gymTrainer) {
		Gym gym = findGymById(gymId);
		Long trainerId = gymTrainer.getTrainerId();
		Trainers trainer = findOrCreateTrainer(gymId, trainerId);
		copyTrainerFields(trainer, gymTrainer);
		trainer.setGym(gym);
		gym.getTrainers().add(trainer);
		Trainers dbTrainer = trainersDao.save(trainer);
		return new GymTrainer(dbTrainer);
		
	}
	
	private Trainers findOrCreateTrainer(Long gymId, Long trainerId) {
		if (trainerId == null) {
			return new Trainers();
		}
		else {
			return findTrainerById(gymId, trainerId);
		}
	}

	private Trainers findTrainerById(Long gymId, Long trainerId) {
		Trainers trainer = trainersDao.findById(trainerId).
				orElseThrow(() -> new NoSuchElementException("Trainer with ID = " + trainerId + "was not found."));
	
		if (!trainer.getGym().getGymId().equals(gymId)) {
			throw new IllegalArgumentException("Trainer with ID " + trainerId +
					" does not belong to Gym with ID " + gymId);
		}
		
		return trainer;
	}
	

	private void copyTrainerFields(Trainers trainer, GymTrainer gymTrainer) {
		trainer.setTrainerId(gymTrainer.getTrainerId());
		trainer.setTrainerFirstName(gymTrainer.getTrainerFirstName());
		trainer.setTrainerLastName(gymTrainer.getTrainerLastName());
		trainer.setTrainerEmail(gymTrainer.getTrainerEmail());
		trainer.setTrainerPhoneNumber(gymTrainer.getTrainerPhoneNumber());
		
	}

	@Transactional(readOnly = false)
	public GymMember saveMember(Long gymId, GymMember gymMember) {
		Gym gym = findGymById(gymId);
		Long memberId = gymMember.getMemberId();
		Members member = findOrCreateMember(gymId, memberId);
		copyMemberFields(member, gymMember);
		member.getGym().add(gym);
		gym.getMembers().add(member);
		Members dbMember = membersDao.save(member);
		return new GymMember(dbMember);
	}

	private void copyMemberFields(Members member, GymMember gymMember) {
		member.setMemberId(gymMember.getMemberId());
		member.setMemberFirstName(gymMember.getMemberFirstName());
		member.setMemberLastName(gymMember.getMemberLastName());
		member.setMemberEmail(gymMember.getMemberEmail());
		member.setMemberPhoneNumber(gymMember.getMemberPhoneNumber());
		
	}

	private Members findOrCreateMember(Long gymId, Long memberId) {
		if (memberId == null) {
			return new Members();
		}
		else {
			return findMemberById(gymId, memberId);
		}
	}

	private Members findMemberById(Long gymId, Long memberId) {
		Members member = membersDao.findById(memberId)
				.orElseThrow(() -> new NoSuchElementException("Member with ID " + memberId + " was not found."));
	boolean foundGym = false;
	for (Gym gym : member.getGym()) {
		if (gym.getGymId().equals(gymId)) {
			foundGym = true;
			break;
		}
	}
	if (!foundGym) {
		throw new IllegalArgumentException("Member with ID " + memberId +
				" does not belong to Gym with ID " + gymId);
	}
	return member;
	
	
	}

	@Transactional(readOnly = true)
	public List<GymWaitlistData> retrieveAllGyms() {
		List<Gym> gyms = gymDao.findAll();
		List<GymWaitlistData> gymWaitlistDataList = new LinkedList<>();
		for (Gym gym : gyms) {
			GymWaitlistData gwd = new GymWaitlistData(gym);
			gwd.getMembers().clear();
			gwd.getTrainers().clear();
			gymWaitlistDataList.add(gwd);
		}
		return gymWaitlistDataList;
	}

	@Transactional(readOnly = true)
	public GymWaitlistData retrieveGymById(Long gymId) {
		Gym gym = findGymById(gymId);
		return new GymWaitlistData(gym);
	}

	public void deleteGymById(Long gymId) {
		Gym gym = findGymById(gymId);
		gymDao.delete(gym);
		
	}




}








