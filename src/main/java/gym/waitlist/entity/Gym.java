package gym.waitlist.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Gym {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gymId;
	
	private String gymName;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private String phoneNumber;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "gym", cascade = CascadeType.ALL, orphanRemoval = true) 
	Set<Trainers> trainers = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "gym_waitlist_members", 
		joinColumns = @JoinColumn(name = "gym_id"), 
		inverseJoinColumns = @JoinColumn(name = "member_id"))
	public Set<Members> members = new HashSet<>();
}
