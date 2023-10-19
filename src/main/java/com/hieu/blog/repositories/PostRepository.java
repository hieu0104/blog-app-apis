package com.hieu.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hieu.blog.entities.Category;
import com.hieu.blog.entities.Post;
import com.hieu.blog.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);

	/*
	 * the query which get post allow field post_title in table post have title like
	 * %key%
	 */
	@Query("select p from Post p where p.title like :key")
	List<Post> findByTitleContaining(@Param("key") String title);

}
