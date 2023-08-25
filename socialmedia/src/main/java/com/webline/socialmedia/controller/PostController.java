package com.webline.socialmedia.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webline.socialmedia.entity.Post;
import com.webline.socialmedia.entity.PostFilter;
import com.webline.socialmedia.entity.User;
import com.webline.socialmedia.repository.PostRepository;
import com.webline.socialmedia.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Post APIs")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private Environment env;

	@Autowired
	private PostRepository postRepository;

	@Operation(description = "Add/Update Post")
	@RequestMapping(path = "/addPost", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Post> addProduct(
			@Parameter(description = "Title of the post") @RequestParam("postTitle") String postTitle,
			@Parameter(description = "Content of the post") @RequestParam("postContent") String postContent,
			@Parameter(description = "Image for post") @RequestPart("file") MultipartFile file,
			Authentication authentication) {

		Post post = new Post(postTitle, postContent);

		Post savedPost = null;

		//Getting logged in user
		User user = (User) authentication.getPrincipal();

		// File upload
		if (!file.isEmpty()) {
			post.setImagePath(
					postService.uploadImage(env.getProperty("imagePath"), file, user.getUserId(), post.getPostTitle()));
		}

		post.setUserId(user.getUserId());
		post.setCreatedAt(new Timestamp(new Date().getTime()));
		post.setUpdatedAt(new Timestamp(new Date().getTime()));

		savedPost = postService.addPost(post);
		return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
	}

	@Operation(description = "Get all posts added by the logged in user")
	@GetMapping("/getPostsByUser")
	public ResponseEntity<List<Post>> getPostsByUser(
			@Parameter(description = "Column name by which records are sorted") @RequestParam(value = "sortedBy", defaultValue = "POST_ID", required = false) String sortedBy,
			@Parameter(description = "Use ASC - for Ascending order , DESC - for Descending order ") @RequestParam(value = "sortType", defaultValue = "ASC", required = false) String sortType,
			@Parameter(description = "Page Size") @RequestParam(value = "pageSize", defaultValue = "5", required = false) String pageSize,
			@Parameter(description = "Page Number") @RequestParam(value = "pageNumber", defaultValue = "0", required = false) String pageNumber,
			@Parameter(description = "Title of post") @RequestParam(value = "postTitle", defaultValue = "", required = false) String postTitle,
			@Parameter(description = "Content of the post") @RequestParam(value = "postContent", defaultValue = "", required = false) String postContent,
			@Parameter(description = "Number of likes") @RequestParam(value = "numberOfLikes", defaultValue = "", required = false) String numberOfLikes,
			@Parameter(description = "Number of dislikes") @RequestParam(value = "numberOfDislikes", defaultValue = "", required = false) String numberOfDislikes,
			@Parameter(description = "Post creation date - Format: YYYY-MM-DD") @RequestParam(value = "createdAt", defaultValue = "", required = false) String createdAt,
			@Parameter(description = "Post updation date - Format: YYYY-MM-DD") @RequestParam(value = "updatedAt", defaultValue = "", required = false) String updatedAt,

			Authentication authentication) {

		//Creating filter object based on request parameters
		PostFilter postFilter = new PostFilter(sortedBy, sortType, pageSize, pageNumber, postTitle, postContent,
				numberOfLikes, numberOfDislikes, createdAt, updatedAt);

		//Getting logged in user
		User user = (User) authentication.getPrincipal();

		//Getting posts created by logged in user
		List<Post> posts = postService.getPostsByUser(postFilter, user.getUserId());

		//Setting recently liked & disliked top 5 users with their basic information
		for (Post post : posts) {
			post.setLikedByUsers(postRepository.getPostLikedByUsers(post.getPostId()));
			post.setDislikedByUsers(postRepository.getPostDisLikedByUsers(post.getPostId()));
		}

		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@Operation(description = "Get top liked/disliked posts")
	@GetMapping("/getTopPosts")
	public ResponseEntity<List<Post>> getTopPosts(
			@Parameter(description = "Get posts for specific user id") @RequestParam(value = "userId", defaultValue = "0", required = false) Long userId,
			@Parameter(description = "Number of posts in result") @RequestParam(value = "numberOfPosts", defaultValue = "10", required = false) Long numberOfPosts,
			@Parameter(description = "Use likes->order by number of likes , dislikes->order by number of dislikes") @RequestParam(value = "typeOfPosts", defaultValue = "likes", required = false) String typeOfPosts) {

		//Getting top liked/disliked posts
		List<Post> posts = postService.getTopPosts(userId, numberOfPosts, typeOfPosts);

		//Setting recently liked & disliked top 5 users with their basic information
		for (Post post : posts) {
			post.setLikedByUsers(postRepository.getPostLikedByUsers(post.getPostId()));
			post.setDislikedByUsers(postRepository.getPostDisLikedByUsers(post.getPostId()));
		}

		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@Operation(description = "Like a post")
	@PostMapping("/like")
	public ResponseEntity<String> likePost(@RequestParam("postId") Long postId, Authentication authentication) {

		//check if post a exist with given post id 
		boolean postExist = postService.checkPostExist(postId);
		if (!postExist) {
			return new ResponseEntity<>("Post does not exist", HttpStatus.BAD_REQUEST);
		}

		//Getting logged in user
		User user = (User) authentication.getPrincipal();

		try {
			postService.likeDislikePost(postId, user.getUserId(), "like");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error in liking the post", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>("User " + user.getUserName() + " has liked the post", HttpStatus.OK);
	}

	@Operation(description = "Dislike a post")
	@PostMapping("/dislike")
	public ResponseEntity<String> dislikePost(@RequestParam("postId") Long postId, Authentication authentication) {

		boolean postExist = postService.checkPostExist(postId);
		if (!postExist) {
			return new ResponseEntity<>("Post does not exist", HttpStatus.BAD_REQUEST);
		}

		//Getting logged in user
		User user = (User) authentication.getPrincipal();

		try {
			postService.likeDislikePost(postId, user.getUserId(), "dislike");
		} catch (Exception e) {
			return new ResponseEntity<>("Error in disliking the post", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("User " + user.getUserName() + " has disliked the post", HttpStatus.OK);
	}
}
