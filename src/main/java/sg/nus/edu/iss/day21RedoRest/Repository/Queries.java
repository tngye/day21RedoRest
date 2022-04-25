package sg.nus.edu.iss.day21RedoRest.Repository;

public interface Queries {

    public static final String SQL_SELECT_GAME_BY_GID = "select * from game where gid = ?";
    public static final String SQL_SELECT_COMMENT_BY_GID = "select * from comment where gid = ? limit ?, ? ";
    
}
