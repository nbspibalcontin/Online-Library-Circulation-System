package Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Entities.Bookentity;

public interface BookEntityRepository extends JpaRepository<Bookentity, Long>{

    @Query("SELECT b FROM Bookentity b WHERE b.title LIKE %:searchString%")
    List<Bookentity> searchBytitle(@Param("searchString") String keyword);
    
    @Query("SELECT b FROM Bookentity b WHERE b.author LIKE %:searchString%")
    List<Bookentity> searchByauthor(@Param("searchString") String keyword);
	
    @Query("SELECT b FROM Bookentity b WHERE b.subject LIKE %:searchString%")
    List<Bookentity> searchBysubject(@Param("searchString") String keyword);
	
    @Query("SELECT b FROM Bookentity b WHERE b.datepublish LIKE %:searchString%")
    List<Bookentity> searchBydatepublish(@Param("searchString") String keyword);
    
}
