package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.PartyInquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyInquiryEntityRepository extends JpaRepository<PartyInquiryEntity, Long> {
}
