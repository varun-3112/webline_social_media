package com.webline.socialmedia.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webline.socialmedia.entity.Post;
import com.webline.socialmedia.entity.PostFilter;

@Service
public interface PostService {

	List<Post> getPostsByUser(String filterFlag, PostFilter postFilter, Long userId);

	List<Post> getTopPosts(Long userId, Long numberOfPosts, String typeOfPosts);

	boolean checkPostExist(Long postId);

	void likeDislikePost(Long postId, Long userId,String operation);

	Post addPost(Post post);
	
	String uploadImage(String imagePath,MultipartFile file,Long userId,String postTitle);
}
