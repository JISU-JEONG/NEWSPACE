package com.ssafy.edu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dao.CommentServiceDao;
import com.ssafy.edu.dto.CommentDTO;


@Service
public class CommentService implements ICommentService {

	@Autowired
	private CommentServiceDao dao;

	@Override
	public List<CommentDTO> getComment(int news_id) {
		return dao.getComment(news_id);
	}

	@Override
	public void addComment(CommentDTO comment) {
		dao.addComment(comment);
	}

	@Override
	public CommentDTO getCommentOne(int comment_id) {
		// TODO Auto-generated method stub
		return dao.getCommentOne(comment_id);
	}

	@Override
	public void deleteComment(int comment_id) {
		// TODO Auto-generated method stub
		dao.deleteComment(comment_id);
	}

	@Override
	public int getCount(int member_id) {
		// TODO Auto-generated method stub
		return dao.getCount(member_id);
	}
	
}
