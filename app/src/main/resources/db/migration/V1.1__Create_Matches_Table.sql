CREATE TABLE matches (
    match_id SERIAL PRIMARY KEY,
    puuid VARCHAR(36) REFERENCES riot_accounts(puuid)
)