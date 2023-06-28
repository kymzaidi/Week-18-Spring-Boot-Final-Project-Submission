package gym.waitlist.controller.model;

import java.util.HashSet;
import java.util.Set;

import gym.waitlist.entity.Gym;
import gym.waitlist.entity.Members;
import gym.waitlist.entity.Trainers;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class GymWaitlistData {
	
	private Long gymId;
	private String gymName;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private String phoneNumber;
	private Set<GymMember> members = new HashSet<>();
	private Set<GymTrainer> trainers = new HashSet<>();
	
	public GymWaitlistData(Gym gym) {
		gymId = gym.getGymId();
		gymName = gym.getGymName();
		address = gym.getAddress();
		city = gym.getCity();
		state = gym.getState();
		zipcode = gym.getZipcode();
		phoneNumber = gym.getPhoneNumber();
		
		for (Members member : gym.getMembers()) {
			members.add(new GymMember(member));
		}
		for (Trainers trainer : gym.getTrainers()) {
			trainers.add(new GymTrainer(trainer));
		}
	}

	@Data
	@NoArgsConstructor
	public	static class GymMember {
		private Long memberId;
		private String memberFirstName;
		private String memberLastName;
		private String memberEmail;
		private String memberPhoneNumber;
		
		public GymMember(Members members) {
			memberId = members.getMemberId();
			memberFirstName = members.getMemberFirstName();
			memberLastName = members.getMemberLastName();
			memberEmail = members.getMemberEmail();
			memberPhoneNumber = members.getMemberPhoneNumber();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class GymTrainer {
		private Long trainerId;
		private String trainerFirstName;
		private String trainerLastName;
		private String trainerEmail;
		private String trainerPhoneNumber;
		
		public GymTrainer (Trainers trainers) {
			trainerId = trainers.getTrainerId();
			trainerFirstName = trainers.getTrainerFirstName();
			trainerLastName = trainers.getTrainerLastName();
			trainerEmail = trainers.getTrainerEmail();
			trainerPhoneNumber = trainers.getTrainerPhoneNumber();
		}

	}
}
