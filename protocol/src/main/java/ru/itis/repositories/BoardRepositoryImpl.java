package ru.itis.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Account;
import ru.itis.models.Board;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BoardRepositoryImpl implements BoardRepository{

    private final String SQL_FIND_BY_ID = "SELECT * FROM board WHERE id = ?";

    private final String SQL_SAVE_BOARD = "INSERT INTO board " +
            "(id, creator_id) " +
            "VALUES (?, ?)";

    private final String SQL_LOAD_ALL_BOARD = "SELECT * FROM board";

    private static final RowMapper<Board> boardRowMapper = ( (rs, rowNum) -> Board.builder()
            .id(rs.getObject("id", UUID.class))
            .creatorId(rs.getObject("creator_id", UUID.class))
            .build());

    private final JdbcTemplate jdbcTemplate;

    public BoardRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Board board) {
        jdbcTemplate.update(SQL_SAVE_BOARD,
                UUID.randomUUID(), board.getCreatorId()
        );
    }

    @Override
    public Optional<Board> findById(UUID id) {
        return Optional.ofNullable(jdbcTemplate.query(SQL_FIND_BY_ID, new Object[]{id},
                boardRowMapper).stream().findAny().orElse(null));
    }

    @Override
    public List<Board> loadAll() {
        return jdbcTemplate.query(SQL_LOAD_ALL_BOARD, new Object[]{}, boardRowMapper).stream().toList();
    }
}
