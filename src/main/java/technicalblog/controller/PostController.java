package technicalblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import technicalblog.model.Category;
import technicalblog.model.Post;
import technicalblog.model.User;
import technicalblog.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping("/posts")
    public String getPosts(Model model){
        List<Post> post = postService.getAllPosts();
        model.addAttribute("posts",post);
        return "posts";

    }

    @RequestMapping("/posts/newPost")
    public String newPost(){
        return "posts/create";
    }

    @RequestMapping(value="/posts/create", method = RequestMethod.POST)
    public String createPost(Post newPost, HttpSession session){
        User user =  (User)session.getAttribute("loggeduser");
        newPost.setUser(user);

        if(newPost.getJavaBlog()!=null){
            Category javaBlogCategory =  new Category();
            javaBlogCategory.setCategory(newPost.getJavaBlog());
            newPost.getCategories().add(javaBlogCategory);
        }

        if(newPost.getSpringBlog() != null){
            Category springBlogCategory = new Category();
            springBlogCategory.setCategory( newPost.getSpringBlog());
            newPost.getCategories().add(springBlogCategory);
        }

        postService.createPost(newPost);
        return "redirect:/posts";
    }

    @RequestMapping("/editPost")
    public String editPost(@RequestParam(name="postId") int postId, Model model){
        Post post = postService.getPost(postId);
        model.addAttribute("post",post);
        return "/posts/edit";
    }

    @RequestMapping(value = "/editPost", method = RequestMethod.PUT)
    public String editPostSubmit(@RequestParam(name = "postId") int postId, Post updatedPost, HttpSession session){
        updatedPost.setId(postId);
        User user =  (User)session.getAttribute("loggeduser");
        updatedPost.setUser(user);
        postService.updatePost(updatedPost);
        return "redirect:/posts";
    }

    @RequestMapping(value="/deletePost", method = RequestMethod.DELETE)
    public String deletePost(@RequestParam(name="postId") int id){
        postService.deletePost(id);
        return "redirect:/posts";
    }
}
