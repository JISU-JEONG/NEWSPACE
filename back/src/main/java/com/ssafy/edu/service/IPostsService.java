package com.ssafy.edu.service;

import java.util.List;

import com.ssafy.edu.dto.PostsDTO;

public interface IPostsService {
	List<PostsDTO> getPosts();
	PostsDTO getPostById(int post_id);
}
