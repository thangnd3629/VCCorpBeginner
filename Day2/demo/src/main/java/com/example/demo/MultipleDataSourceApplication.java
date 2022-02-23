package com.example.demo;

import com.example.demo.model.Card;
import com.example.demo.model.CardHolder;
import com.example.demo.model.Member;
import com.example.demo.repository.CardHolderRepository;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@SpringBootApplication
public class MultipleDataSourceApplication implements CommandLineRunner {


	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private CardHolderRepository cardHolderRepository;
	@Autowired
	private CardRepository cardRepository;


	@PersistenceContext
	private EntityManager entityManager;

	private Member member;
	private Card card;
	private CardHolder cardHolder;



	public static void main(String[] args) {
		SpringApplication.run(MultipleDataSourceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		member = new Member();
		member.setMemberId("M001");
		member.setName("Maureen Mpofu");
		cardHolder = new CardHolder();
		cardHolder.setCardNumber("4111111111111111");
		cardHolder.setMemberId(member.getMemberId());
		card = new Card();
		card.setExpirationMonth(01);
		card.setExpirationYear(2020);
		card.setName(member.getName());
		Member savedMember =memberRepository.save(member);
		Optional<Member> memberFromDb= memberRepository.findById(savedMember.getId());
		System.out.println(memberFromDb);
		CardHolder savedCardHolder =cardHolderRepository.save(cardHolder);
		Optional<CardHolder> cardHolderFromDb= cardHolderRepository.findById(savedCardHolder.getId());
		System.out.println(cardHolderFromDb);
		Card savedCard = cardRepository.save(card);
		Optional<Card> cardFromDb= cardRepository.findById(savedCard.getId());
		System.out.println(cardFromDb);
	}
}
