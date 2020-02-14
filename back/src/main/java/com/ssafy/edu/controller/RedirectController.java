package com.ssafy.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ssafy.edu.dto.Member;
import com.ssafy.edu.service.MemberService;

@Controller
public class RedirectController {
	@Autowired
	private MemberService memberservice;

	@GetMapping(value = "/member/{key}/{email}")
	public String sendmailckeck(@PathVariable String key, @PathVariable String email) {
		Member member = new Member();
		member.setCertifiedkey(key);
		member.setEmail(email);
		System.out.println("key: "+ key);
		System.out.println("email"+ email);

		boolean check = memberservice.emailcheck(member);
		String url = "";
		if(check) {
			url = "https://i02b208.p.ssafy.io/Profile";
		}
		else {
			url = "https://i02b208.p.ssafy.io/";
		}
		return "redirect:"+url;
	}
}
