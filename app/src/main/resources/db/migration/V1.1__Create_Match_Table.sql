CREATE TABLE Match (
    match_id VARCHAR(255) PRIMARY KEY
);

CREATE TABLE Metadata (
    match_id VARCHAR(255) PRIMARY KEY,
    data_version VARCHAR(255) NOT NULL,
    participants VARCHAR(255)[],
    FOREIGN KEY (match_id) REFERENCES Match(match_id)
);

CREATE TABLE Info (
    match_id VARCHAR(255) PRIMARY KEY,
    game_id BIGINT NOT NULL,
    map_id INTEGER NOT NULL,
    queue_id INTEGER NOT NULL,
    platform_id VARCHAR(255) NOT NULL,
    game_creation BIGINT NOT NULL,
    game_duration BIGINT NOT NULL,
    game_start_timestamp BIGINT NOT NULL,
    game_end_timestamp BIGINT NOT NULL,
    game_mode VARCHAR(255) NOT NULL,
    game_name VARCHAR(255) NOT NULL,
    game_type VARCHAR(255) NOT NULL,
    game_version VARCHAR(255) NOT NULL,
    FOREIGN KEY (match_id) REFERENCES Match(match_id)
);

CREATE TABLE Participant (
    match_id VARCHAR(255),
    puuid VARCHAR(255) NOT NULL,
    participant_id INTEGER NOT NULL,
    champion_id INTEGER NOT NULL,
    champion_Name VARCHAR(255),
    individual_position VARCHAR(255),
    role VARCHAR(255),
    summoner_id VARCHAR(255),
    summoner_name VARCHAR(255),
    team_id INTEGER,
    team_position VARCHAR(255),
    time_played INTEGER,
    win BOOLEAN,
    PRIMARY KEY (match_id, puuid),
    FOREIGN KEY (match_id) REFERENCES Info(match_id)
)