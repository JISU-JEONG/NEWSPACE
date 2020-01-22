package com.ssafy.edu.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.PostsDTO;

@Repository
public class PostsServiceDao {
	String ns="ssafy.posts.";
	@Autowired
	private SqlSession SQL;
	
	public List<PostsDTO> getPosts(){
		return SQL.selectList(ns+"getPosts");
	}

	public PostsDTO getPostById(int post_id) {
		return SQL.selectOne(ns+"getPostById",post_id);
	}
}
