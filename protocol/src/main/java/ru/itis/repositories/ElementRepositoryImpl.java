package ru.itis.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Board;
import ru.itis.models.Element;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ElementRepositoryImpl implements ElementRepository {

    private final String SQL_SAVE_BOARD = "INSERT INTO element " +
            "(id, creator_id, board_id, type, size, value, color) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String SQL_FIND_BY_ID = "SELECT * FROM element WHERE id = ?";

    private final String SQL_FIND_BY_BOARD_ID = "SELECT * FROM element WHERE board_id = ?";

    private static final RowMapper<Element> elementRowMapper = ((rs, rowNum) -> Element.builder()
            .id(rs.getObject("id", UUID.class))
            .creatorId(rs.getObject("creator_id", UUID.class))
            .boardId(rs.getObject("board_id", UUID.class))
            .type(Element.Type.valueOf(rs.getString("type")))
            .size(rs.getDouble("size"))
            .value(rs.getString("value"))
            .color(rs.getString("color"))
            .build());

    private final JdbcTemplate jdbcTemplate;

    public ElementRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Element element) {
        jdbcTemplate.update(SQL_SAVE_BOARD,
                element.getId(), element.getCreatorId(), element.getBoardId(), element.getType().toString(),
                element.getSize(), element.getValue(), element.getColor()
        );
    }

    @Override
    public Optional<Element> findById(UUID id) {
        return Optional.ofNullable(jdbcTemplate.query(SQL_FIND_BY_ID, new Object[]{id},
                elementRowMapper).stream().findAny().orElse(null));
    }

    @Override
    public List<Element> loadAllByBoardId(UUID id) {
        return jdbcTemplate.query(SQL_FIND_BY_BOARD_ID, new Object[]{id}, elementRowMapper).stream().toList();
    }
}
