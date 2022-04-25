package sg.nus.edu.iss.day21RedoRest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import sg.nus.edu.iss.day21RedoRest.Repository.GameRepository;
import sg.nus.edu.iss.day21RedoRest.model.Comment;
import sg.nus.edu.iss.day21RedoRest.model.Game;

@RestController
@RequestMapping("/game")
public class GameRestController {

    @Autowired
    private GameRepository gamerepo;
    
    @GetMapping(path="/{gid}", produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGame(@PathVariable int gid){
        Optional<Game> opt = gamerepo.getGameById(gid);

        JsonObjectBuilder builder = Json.createObjectBuilder();
        if(opt.isEmpty()){
            return ResponseEntity.status(404)
                    .body(builder.add("error", "not found: %s".formatted(gid))
                            .build().toString()); 
        }

        Game game = opt.get();
        JsonArrayBuilder arrbuilder = Json.createArrayBuilder();
        builder.add("id", game.getGameId())
            .add("name", game.getName())
            .add("ranking", game.getRanking())
            .add("year", game.getYear())
            .add("userRated", game.getUsersRated())
            .add("url", game.getUrl());

        List<Comment> list = gamerepo.getCommentById(gid);

        if (list.size()> 0){
            for(Comment c: list){
                arrbuilder.add("/comment/%s".formatted(c.getCommentId()));
            }
            builder.add("comments", arrbuilder.build());
        }

        return ResponseEntity.ok(builder.build().toString());
    }
        
}
