
CREATE TABLE IF NOT EXISTS clients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id VARCHAR(255) NOT NULL UNIQUE,
    client_name VARCHAR(255) NOT NULL,

    -- NULL for Public Clients (React SPA, Mobile Apps)
    -- BCrypt hash for Confidential Clients (Server Backends)
    client_secret_hash VARCHAR(255) NULL,

    -- Client type constraint: 'public' or 'confidential'
    client_type VARCHAR(50) NOT NULL CHECK (client_type IN ('public', 'confidential')),

    -- Allowed redirect URIs stored as JSONB array, e.g. ["https://app.com/callback"]
    redirect_uris JSONB NOT NULL,

    -- Allowed grant types, e.g. ["authorization_code", "refresh_token"]
    allowed_grant_types JSONB NOT NULL,

    -- Allowed scopes, e.g. ["openid", "profile", "email"]
    allowed_scopes JSONB NOT NULL,

    -- Require PKCE (Must be TRUE for public clients)
    require_pkce BOOLEAN NOT NULL DEFAULT TRUE,

    -- Token lifespan configurations (in seconds)
    access_token_ttl_seconds INT NOT NULL DEFAULT 900,         -- 15 minutes
    refresh_token_ttl_seconds INT NOT NULL DEFAULT 2592000,    -- 30 days

    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,

                             -- Enforce DB-level consistency: Public clients MUST have NULL secret
                             CONSTRAINT chk_public_client_secret CHECK (
                             (client_type = 'public' AND client_secret_hash IS NULL) OR
(client_type = 'confidential')
    )
    );

CREATE INDEX IF NOT EXISTS idx_clients_client_id ON clients(client_id);

ALTER TABLE refresh_tokens
    ADD COLUMN client_id VARCHAR(255) NOT NULL;

ALTER TABLE refresh_tokens
    ADD CONSTRAINT fk_refresh_tokens_client
        FOREIGN KEY (client_id)
            REFERENCES clients (client_id)
            ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS idx_refresh_tokens_client ON refresh_tokens (client_id);