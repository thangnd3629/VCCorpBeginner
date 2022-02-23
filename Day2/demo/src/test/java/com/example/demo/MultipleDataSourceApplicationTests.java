package com.example.demo;

import com.example.demo.model.Card;
import com.example.demo.model.CardHolder;
import com.example.demo.model.Member;
import com.example.demo.repository.CardHolderRepository;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.MemberRepository;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class MultipleDataSourceApplicationTests {


}
