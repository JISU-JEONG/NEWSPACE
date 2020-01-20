package com.ssafy.edu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dao.PostsServiceDao;
import com.ssafy.edu.dto.PostsDTO;
@Service
public class PostsService implements IPostsService{

	@Autowired
	private PostsServiceDao dao;
	
	@Override
	public List<PostsDTO> getPosts() {
		return dao.getPosts();
	}

	@Override
	public PostsDTO getPostById(int post_id) {
		return dao.getPostById(post_id);
	}
	
}
