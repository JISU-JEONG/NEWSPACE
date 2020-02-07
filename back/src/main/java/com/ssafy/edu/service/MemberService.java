package com.ssafy.edu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dao.MemberDao;
import com.ssafy.edu.dto.Member;

@Service
public class MemberService {
	@Autowired
	private MemberDao dao;

    public Member signin(Member member) {
    	if(dao.getMember(member)!=null) {
    		return dao.getMember(member);
    	}
        else {
            throw new RuntimeException("아이디 혹은 비밀번호 에러입니다.");
        }
    }

    public Member getEmail(String email) {
    	return dao.getEmail(email);
    }
    
	public int insertMember(Member member) {
		return dao.insertMember(member);
		
	}

	public List<Member> getMember() {
		return dao.getMember();
	}

	public Member getMember(int member_id) {
		return dao.getMember(member_id);
	}
	
	public boolean emailcheck(Member member) {
		Member m = dao.emailcheck(member);
		if(m==null) {			
			return false;
		}
		else {
			dao.emailupdate(member);
			return true;
		}
	}

	public void updatemember(Member member) {
		dao.updatemember(member);
	}

}