package com.ssafy.edu.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.CommentDTO;

@Repository
public class CommentServiceDao {

	String ns="ssafy.comment.";
	@Autowired
	private SqlSession SQL;
	
	public List<CommentDTO> getComment(int news_id) {
		return SQL.selectList(ns + "getComment", news_id);
	}

	public void addComment(CommentDTO comment) {
		SQL.insert(ns + "addComment", comment);
	}

	public CommentDTO getCommentOne(int comment_id) {
		// TODO Auto-generated method stub
		return SQL.selectOne(ns + "getCommentOne", comment_id);
	}

	public void deleteComment(int comment_id) {
		// TODO Auto-generated method stub
		SQL.delete(ns + "deleteComment", comment_id);
	}

	public int getCount(int member_id) {
		// TODO Auto-generated method stub
		return SQL.selectOne(ns + "getCount", member_id);
	}
	
}
