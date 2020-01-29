package com.ssafy.edu.service;

import java.util.List;

import com.ssafy.edu.dto.CommentDTO;

public interface ICommentService {

	List<CommentDTO> getComment(int news_id);

	void addComment(CommentDTO comment);

	CommentDTO getCommentOne(int comment_id);

	void deleteComment(int comment_id);

}
