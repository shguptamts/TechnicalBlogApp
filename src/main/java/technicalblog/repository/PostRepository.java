package technicalblog.repository;

import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import technicalblog.model.Post;
import technicalblog.model.User;

import javax.persistence.*;
import java.util.List;

@Repository
public class PostRepository {

    @PersistenceUnit(unitName = "techblog")
    private EntityManagerFactory emf;

    public List<Post> getAllPosts(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Post> query = em.createQuery("Select p from Post p", Post.class);
        List<Post> posts = query.getResultList();
        return  posts;
    }

    public Post getLatestPost(){
        EntityManager em = emf.createEntityManager();
        return em.find(Post.class, 2);
    }

    public Post createPost(Post newPost){
        EntityManager em =  emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();
            em.persist(newPost);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            System.out.println("Rollbacking the new post transaction. \n New post object used " + newPost);
        }
        return newPost;
    }

    public Post getPost(int id){
        EntityManager em = emf.createEntityManager();
        return em.find(Post.class,id);
    }

    public void updatePost(Post updatedPost){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction =  em.getTransaction();

        try{
            transaction.begin();
            em.merge(updatedPost);
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
            System.out.println("Rollbacking the update post transaction. \n update post object used " + updatedPost);
        }
    }

    public void deletePost(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            Post post = em.find(Post.class, id);
            em.remove(post);
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
        }
    }


}
