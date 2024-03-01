CREATE TABLE matches (
    match_id VARCHAR(255) PRIMARY KEY,
    puuid VARCHAR(255) REFERENCES riot_accounts(puuid)
);

CREATE TABLE metadata (
    match_id VARCHAR(255) PRIMARY KEY,
    data_version VARCHAR(255) NOT NULL,
    participants VARCHAR(255)[],
    FOREIGN KEY (match_id) REFERENCES matches(match_id)
);

CREATE TABLE info (
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
    FOREIGN KEY (match_id) REFERENCES matches(match_id)
);

CREATE TABLE participant(
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
    PRIMARY KEY (match_id, puuid),
    FOREIGN KEY (match_id) REFERENCES info(match_id)
)