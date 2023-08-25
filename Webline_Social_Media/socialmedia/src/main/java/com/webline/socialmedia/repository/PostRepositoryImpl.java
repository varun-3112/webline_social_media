package com.webline.socialmedia.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.webline.socialmedia.entity.Post;
import com.webline.socialmedia.entity.PostFilter;
import com.webline.socialmedia.entity.UserDto;

@Repository
public class PostRepositoryImpl implements PostRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Environment env;

	@Override
	public List<Post> getPostsByUser(PostFilter postFilter, Long userId) {

		String GET_POSTS_BY_USER = env.getProperty("GET_POSTS_BY_USER");
		StringBuilder sb = new StringBuilder(GET_POSTS_BY_USER);
		List<Object> filterValues = new ArrayList<Object>();
		filterValues.add(userId);

		if (postFilter.getPostTitle() != null && !postFilter.getPostTitle().isEmpty()) {
			sb.append(" AND POST_TITLE LIKE (?) ");
			filterValues.add(postFilter.getPostTitle());
		}
		if (postFilter.getPostContent() != null && !postFilter.getPostContent().isEmpty()) {
			sb.append(" AND POST_CONTENT LIKE (?) ");
			filterValues.add(postFilter.getPostContent());
		}
		if (postFilter.getNumberOfLikes() != null && !postFilter.getNumberOfLikes().isEmpty()) {
			sb.append(" AND LIKES=? ");
			filterValues.add(Long.parseLong(postFilter.getNumberOfLikes()));
		}
		if (postFilter.getNumberOfDislikes() != null && !postFilter.getNumberOfDislikes().isEmpty()) {
			sb.append(" AND DISLIKES=? ");
			filterValues.add(Long.parseLong(postFilter.getNumberOfDislikes()));
		}
		if (postFilter.getCreatedAt() != null && !postFilter.getCreatedAt().isEmpty()) {
			sb.append(" AND DATE_FORMAT(CREATED_AT, '%Y-%m-%d')=? ");
			filterValues.add(Long.parseLong(postFilter.getCreatedAt()));
		}
		if (postFilter.getUpdatedAt() != null && !postFilter.getUpdatedAt().isEmpty()) {
			sb.append(" AND DATE_FORMAT(UPDATED_AT, '%Y-%m-%d')=? ");
			filterValues.add(Long.parseLong(postFilter.getUpdatedAt()));
		}

		sb.append(" ORDER BY " + postFilter.getSortedBy() + " " + postFilter.getSortType() + " "
				+ env.getProperty("PAGINATION_QUERY"));
		filterValues.add(Long.parseLong(postFilter.getPageSize()));
		filterValues.add(Long.parseLong(postFilter.getPageNumber()));

		return jdbcTemplate.query(sb.toString(), (rs, rowNum) -> {
			return new Post(rs.getLong("post_id"), rs.getString("post_title"), rs.getString("post_content"),
					rs.getTimestamp("created_at"), rs.getTimestamp("updated_at"), rs.getString("image_path"),
					rs.getLong("likes"), rs.getLong("dislikes"));
		}, filterValues.toArray());
	}

	@Override
	public List<UserDto> getPostLikedByUsers(Long postId) {
		String GET_POST_LIKED_BY_USERS = env.getProperty("GET_POST_LIKED_BY_USERS");
		return jdbcTemplate.query(GET_POST_LIKED_BY_USERS, (rs, rowNum) -> {
			return new UserDto(rs.getLong("user_id"), rs.getString("user_name"), rs.getString("email"),
					rs.getString("role"), rs.getTimestamp("liked_disliked_at"));
		}, postId);
	}

	@Override
	public List<UserDto> getPostDisLikedByUsers(Long postId) {
		String GET_POST_DISLIKED_BY_USERS = env.getProperty("GET_POST_DISLIKED_BY_USERS");
		return jdbcTemplate.query(GET_POST_DISLIKED_BY_USERS, (rs, rowNum) -> {
			return new UserDto(rs.getLong("user_id"), rs.getString("user_name"), rs.getString("email"),
					rs.getString("role"), rs.getTimestamp("liked_disliked_at"));
		}, postId);
	}

	@Override
	public List<Post> getTopPosts(Long userId, Long numberOfPosts, String typeOfPosts) {

		String GET_TOP_POSTS = env.getProperty("GET_TOP_POSTS");
		StringBuilder sb = new StringBuilder(GET_TOP_POSTS);

		List<Object> filterValues = new ArrayList<Object>();

		if (userId != 0) {
			sb.append(" AND U.USER_ID=? ");
			filterValues.add(userId);
		}

		sb.append(" ORDER BY " + typeOfPosts + " DESC LIMIT ?");
		filterValues.add(numberOfPosts);

		return jdbcTemplate.query(sb.toString(), (rs, rowNum) -> {
			return new Post(rs.getLong("post_id"), rs.getString("post_title"), rs.getString("post_content"),
					rs.getTimestamp("created_at"), rs.getTimestamp("updated_at"), rs.getString("image_path"),
					rs.getLong("likes"), rs.getLong("dislikes"));
		}, filterValues.toArray());
	}

	@Override
	public boolean checkIsLikedDisliked(Long postId, Long userId) {

		String IS_POST_LIKED_DISLIKED = env.getProperty("IS_POST_LIKED_DISLIKED");

		int count = jdbcTemplate.queryForObject(IS_POST_LIKED_DISLIKED, Integer.class, postId, userId);

		return count > 0;
	}

	@Override
	public void likeDislikePost(Long postId, Long userId, String operation, String queryType) {

		if (operation.equals("like")) {
			if (queryType.equals("ADD")) {
				jdbcTemplate.update(env.getProperty("ADD_LIKE_DISLIKE_FOR_POST"), postId, userId, "Y", "N",
						new Timestamp(new Date().getTime()));
			} else {
				jdbcTemplate.update(env.getProperty("UPDATE_LIKE_DISLIKE_FOR_POST"), "Y", "N",
						new Timestamp(new Date().getTime()), postId, userId);
			}
		} else {
			if (queryType.equals("ADD")) {
				jdbcTemplate.update(env.getProperty("ADD_LIKE_DISLIKE_FOR_POST"), postId, userId, "N", "Y",
						new Timestamp(new Date().getTime()));
			} else {
				jdbcTemplate.update(env.getProperty("UPDATE_LIKE_DISLIKE_FOR_POST"), "N", "Y",
						new Timestamp(new Date().getTime()), postId, userId);
			}
		}
	}

	@Override
	public boolean checkPostExist(Long postId) {
		int count = jdbcTemplate.queryForObject(env.getProperty("CHECK_POST_EXIST"), Integer.class, postId);
		return count > 0;
	}

	@Override
	public Post AddUpdatePost(Post post, Long postId) {
		if (postId == 0) {
			// Add new post
			jdbcTemplate.update(env.getProperty("ADD_PRODUCT_FOR_USER"), post.getPostTitle(), post.getPostContent(),
					post.getUserId(), post.getImagePath(), post.getCreatedAt(), post.getUpdatedAt());
		} else {
			// Update post
			jdbcTemplate.update(env.getProperty("UPDATE_PRODUCT_FOR_USER"), post.getPostTitle(), post.getPostContent(),
					post.getUserId(), post.getImagePath(), post.getUpdatedAt(), postId);
		}
		return post;
	}

	@Override
	public Long checkPostForDuplicate(Post post) {
		Long postId = 0L;
		try {
			postId = jdbcTemplate.queryForObject(env.getProperty("CHECK_POST_FOR_DUPLICATE"), Long.class,
					post.getUserId(), post.getPostTitle());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postId;
	}

}
