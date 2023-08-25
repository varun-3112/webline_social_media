package com.webline.socialmedia.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.webline.socialmedia.entity.Post;
import com.webline.socialmedia.entity.PostFilter;
import com.webline.socialmedia.entity.UserDto;

@Repository
public interface PostRepository {

	List<Post> getPostsByUser(PostFilter postFilter, Long userId);
	
	List<UserDto> getPostLikedByUsers(Long postId);
	
	List<UserDto> getPostDisLikedByUsers(Long postId);

	List<Post> getTopPosts(Long userId, Long numberOfPosts, String typeOfPosts);

	void likeDislikePost(Long postId, Long userId, String operation, String queryType);

	boolean checkIsLikedDisliked(Long postId, Long userId);

	boolean checkPostExist(Long postId);

	Post AddUpdatePost(Post post,Long postId);

	Long checkPostForDuplicate(Post post);
}
