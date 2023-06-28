package gym.waitlist.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Members {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;
	
	private String memberFirstName;
	private String memberLastName;
	private String memberEmail;
	private String memberPhoneNumber;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "members", cascade = CascadeType.PERSIST)
	Set<Gym> gym = new HashSet<>();

}
