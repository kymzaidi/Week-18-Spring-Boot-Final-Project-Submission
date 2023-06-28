package gym.waitlist.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Trainers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trainerId;
	
	private String trainerFirstName;
	private String trainerLastName;
	private String trainerEmail;
	private String trainerPhoneNumber;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gym_id")
	private Gym gym;

}
