package database;

import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DbConnect {
    private transient String url;
    private transient String username;
    private transient String password;

    /**
     * Connect to DB.
     */
    public DbConnect() {
        this.url = "jdbc:postgresql://localhost:5432/Snake_DB";
        this.username = "postgres";
        this.password = "postgres"; //you can change this to your local PgAdmin user password.
    }

    /**
     * Connect with the dataSource.
     *
     * @return DataSource object.
     */
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
