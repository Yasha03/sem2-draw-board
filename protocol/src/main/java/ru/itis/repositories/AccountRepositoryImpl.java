package ru.itis.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Account;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

public class AccountRepositoryImpl implements AccountRepository {
    private final String SQL_FIND_BY_EMAIL = "SELECT * FROM account WHERE email = ?";

    private final String SQL_FIND_BY_ID = "SELECT * FROM account WHERE id = ?";

    private final String SQL_SAVE_ACCOUNT = "INSERT INTO account " +
            "(id ,login, email, password) " +
            "VALUES (?, ?, ?, ?)";


    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Account> accountMapper = ( (rs, rowNum) -> Account.builder()
            .id(rs.getObject("id", UUID.class))
            .login(rs.getString("login"))
            .email(rs.getString("email"))
            .password(rs.getString("password"))
            .build());

    public AccountRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Account account) {
        jdbcTemplate.update(SQL_SAVE_ACCOUNT,
                UUID.randomUUID(), account.getLogin(), account.getEmail(), account.getPassword()
        );
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.query(SQL_FIND_BY_EMAIL, new Object[]{email},
                accountMapper).stream().findAny().orElse(null));
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return Optional.ofNullable(jdbcTemplate.query(SQL_FIND_BY_ID, new Object[]{id},
                accountMapper).stream().findAny().orElse(null));
    }

    @Override
    public void update(Account account) {

    }
}
