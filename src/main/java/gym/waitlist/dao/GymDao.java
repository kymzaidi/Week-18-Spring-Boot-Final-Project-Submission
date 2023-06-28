package gym.waitlist.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gym.waitlist.entity.Gym;

public interface GymDao extends JpaRepository<Gym, Long> {
	Optional<Gym> findByGymId(Long gymId);

}
