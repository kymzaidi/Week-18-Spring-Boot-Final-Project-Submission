package gym.waitlist.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gym.waitlist.entity.Members;

public interface MembersDao extends JpaRepository<Members, Long> {
	Optional<Members> findByMemberId(Long memberId);

}
