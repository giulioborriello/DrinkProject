#include <neo4j-client.h>

int main() {
    // Set up the Neo4j configuration
    const char * uri = "0a15e502.databases.neo4j.io";
    neo4j_config_t *config = neo4j_new_config();
    neo4j_config_set_username(config, "neo4j");
    neo4j_config_set_password(config, "P4_wY9L5Sems1PnP_dkzklregQYSvSIJ-DmYjNgDXyo");
    neo4j_config_set_address(config, uri);
    neo4j_config_set_port(config, 7687); // Neo4j Bolt protocol port

    // Establish a new session with the Neo4j database
    neo4j_connection_t *connection = neo4j_connect(uri, config, NEO4J_NO_URI_CREDENTIALS);
    neo4j_session_t *session = neo4j_new_session(connection);
    // Use the session to run queries and interact with the database...


    // Clean up resources when finished
    neo4j_close(session);
    neo4j_disconnect(connection);
    neo4j_config_free(config);
    // 1
    return 0;
}
