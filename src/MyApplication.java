/**
 * <h1>Flower Shop Application</h1>
 *
 * <p>
 * The Flower Shop Application is a Java console-based program
 * that demonstrates interaction with a PostgreSQL database using JDBC.
 * The application follows a layered architecture consisting of
 * entity, repository, service, and data access layers.
 * </p>
 *
 * <h2>Application Features</h2>
 * <ul>
 *   <li>Add new flowers to the database</li>
 *   <li>Retrieve and display all available flowers</li>
 *   <li>Update flower price and stock</li>
 *   <li>Purchase flowers with stock validation</li>
 *   <li>Delete flowers from the database</li>
 * </ul>
 *
 * <h2>Architecture Overview</h2>
 * <ul>
 *   <li><b>Entity layer</b> – represents database objects (Flower)</li>
 *   <li><b>Repository layer</b> – handles SQL queries and database operations</li>
 *   <li><b>Service layer</b> – contains business logic and validation</li>
 *   <li><b>Data layer</b> – manages PostgreSQL database connections</li>
 * </ul>
 *
 * <h2>Technologies Used</h2>
 * <ul>
 *   <li>Java 21</li>
 *   <li>PostgreSQL</li>
 *   <li>JDBC</li>
 *   <li>IntelliJ IDEA</li>
 * </ul>
 *
 * <h2>Database</h2>
 * <p>
 * The application connects to a PostgreSQL database using the official
 * PostgreSQL JDBC driver. All flower data is stored in the
 * <code>flowers</code> table.
 * </p>
 *
 * <h2>Version</h2>
 * <p>
 * 1.0
 * </p>
 */
