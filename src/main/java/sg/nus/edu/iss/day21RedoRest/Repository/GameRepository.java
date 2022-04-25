package sg.nus.edu.iss.day21RedoRest.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.day21RedoRest.model.Comment;
import sg.nus.edu.iss.day21RedoRest.model.Game;

import static sg.nus.edu.iss.day21RedoRest.Repository.Queries.*;

@Repository
public class GameRepository {

    @Autowired
    private JdbcTemplate template;

    public Optional<Game> getGameById(int gid) {

        final SqlRowSet rs = template.queryForRowSet(SQL_SELECT_GAME_BY_GID, gid);
        
        if(!rs.next()){
            return Optional.empty();
        }
        return Optional.of(Game.create(rs));
    }

    public List<Comment> getCommentById(Integer gid){
        return getCommentById(gid, 0, Integer.MAX_VALUE);
    }

    public List<Comment> getCommentById(int gid, Integer limit, Integer offset) {
        final SqlRowSet result = template.queryForRowSet(SQL_SELECT_COMMENT_BY_GID, gid, limit, offset);
        List<Comment> comments = new ArrayList<Comment>();

        while(result.next()){
            Comment comment = Comment.create(result);
            comments.add(comment);
        }

        return comments;
     }
    
}
